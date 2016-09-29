package com.psiphiglobal.proto.endpoints;

import com.google.gson.reflect.TypeToken;
import com.psiphiglobal.proto.blockchain.api.DocumentApi;
import com.psiphiglobal.proto.blockchain.api.ShareApi;
import com.psiphiglobal.proto.blockchain.api.UserApi;
import com.psiphiglobal.proto.model.Document;
import com.psiphiglobal.proto.model.DocumentSummary;
import com.psiphiglobal.proto.model.User;
import com.psiphiglobal.proto.model.helper.DocumentHelper;
import com.psiphiglobal.proto.model.helper.UserHelper;
import com.psiphiglobal.proto.util.GsonProvider;

import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("document")
public class DocumentEndpoint extends AbstractEndpoint
{
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void getDocument(@PathParam("id") String documentId, @Suspended AsyncResponse asyncResponse)
    {
        workerPool.execute(() -> {

            if (documentId == null || documentId.isEmpty())
            {
                asyncResponse.resume(Response.status(Response.Status.BAD_REQUEST).build());
                return;
            }

            Document document = blockchainApiManager.getDocumentApi().get(documentId);
            if (document != null)
            {
                asyncResponse.resume(buildSuccessJsonResponse(document));
            }
            else
            {
                asyncResponse.resume(Response.status(Response.Status.NOT_FOUND).build());
            }
        });
    }

    @GET
    @Path("user/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public void getDocumentsForUser(@PathParam("username") String username, @Suspended AsyncResponse asyncResponse)
    {
        workerPool.execute(() -> {

            if (username == null || username.isEmpty())
            {
                asyncResponse.resume(Response.status(Response.Status.BAD_REQUEST).build());
                return;
            }

            List<DocumentSummary> documents = blockchainApiManager.getDocumentApi().getAll(username);
            asyncResponse.resume(buildSuccessJsonResponse(documents));
        });
    }

    @GET
    @Path("shared/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public void getDocumentsSharedWith(@PathParam("username") String username, @Suspended AsyncResponse asyncResponse)
    {
        workerPool.execute(() -> {

            if (username == null || username.isEmpty())
            {
                asyncResponse.resume(Response.status(Response.Status.BAD_REQUEST).build());
                return;
            }

            List<DocumentSummary> documents = blockchainApiManager.getShareApi().getSharedDocuments(username);
            asyncResponse.resume(buildSuccessJsonResponse(documents));
        });
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void createDocument(String inputJson, @Suspended AsyncResponse asyncResponse)
    {
        workerPool.execute(() -> {

            Map<String, String> input = null;
            try
            {
                Type type = new TypeToken<HashMap<String, String>>()
                {
                }.getType();
                input = GsonProvider.get().fromJson(inputJson, type);
                if (input == null)
                {
                    throw new NullPointerException();
                }
            }
            catch (Exception e)
            {
                asyncResponse.resume(Response.status(Response.Status.BAD_REQUEST).build());
                return;
            }

            Document document = new Document();
            document.setName(input.get("name"));
            document.setCreator(input.get("creator"));
            document.setEncryptedContent(input.get("encrypted_content"));
            document.setEncryptedKey(input.get("encrypted_key"));
            document.setSignature(input.get("signature"));

            UserApi userApi = blockchainApiManager.getUserApi();
            DocumentApi documentApi = blockchainApiManager.getDocumentApi();

            if (!DocumentHelper.validate(document))
            {
                asyncResponse.resume(Response.status(Response.Status.BAD_REQUEST).build());
                return;
            }

            if (!DocumentHelper.isSizeOk(document))
            {
                asyncResponse.resume(Response.status(Response.Status.REQUEST_ENTITY_TOO_LARGE).build());
                return;
            }

            User creator = userApi.get(document.getCreator());
            if (creator == null)
            {
                asyncResponse.resume(Response.status(Response.Status.BAD_REQUEST).build());
                return;
            }

            if (!DocumentHelper.validateSignature(creator, document))
            {
                asyncResponse.resume(Response.status(Response.Status.UNAUTHORIZED).build());
                return;
            }

            document.setId(DocumentHelper.generateId(document));
            document.setCreatedAt(Instant.now().getEpochSecond());
            documentApi.create(document);
            asyncResponse.resume(buildSuccessJsonResponse(DocumentHelper.summarize(document)));
        });
    }
}

