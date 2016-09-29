package com.psiphiglobal.proto.endpoints;

import com.google.gson.reflect.TypeToken;
import com.psiphiglobal.proto.blockchain.api.DocumentApi;
import com.psiphiglobal.proto.blockchain.api.ShareApi;
import com.psiphiglobal.proto.blockchain.api.UserApi;
import com.psiphiglobal.proto.model.Document;
import com.psiphiglobal.proto.model.DocumentSummary;
import com.psiphiglobal.proto.model.User;
import com.psiphiglobal.proto.model.helper.DocumentHelper;
import com.psiphiglobal.proto.util.GsonProvider;

import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Path("share")
public class ShareEndpoint extends AbstractEndpoint
{
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void shareDocument(String inputJson, @Suspended AsyncResponse asyncResponse)
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

            String to = input.get("to");
            String documentId = input.get("document_id");
            String encryptedKey = input.get("encryptedKey");
            String signature = input.get("signature");

            if (to == null || to.isEmpty() || documentId == null || documentId.isEmpty() || encryptedKey == null || encryptedKey.isEmpty())
            {
                asyncResponse.resume(Response.status(Response.Status.BAD_REQUEST).build());
                return;
            }

            UserApi userApi = blockchainApiManager.getUserApi();
            DocumentApi documentApi = blockchainApiManager.getDocumentApi();
            ShareApi shareApi = null; // TODO: Link with impl

            Document document = documentApi.get(documentId);
            if (document == null)
            {
                asyncResponse.resume(Response.status(Response.Status.NOT_FOUND).build());
                return;
            }

            DocumentSummary share = DocumentHelper.summarize(document);
            share.setEncryptedKey(encryptedKey);

            User creator = userApi.get(document.getCreator());

            if (!DocumentHelper.validateShareSignature(creator, to, share, signature))
            {
                asyncResponse.resume(Response.status(Response.Status.UNAUTHORIZED).build());
                return;
            }

            share.setCreatedAt(Instant.now().getEpochSecond());
            shareApi.share(to, share);
            asyncResponse.resume(buildEmptySuccessResponse());

        });
    }
}

