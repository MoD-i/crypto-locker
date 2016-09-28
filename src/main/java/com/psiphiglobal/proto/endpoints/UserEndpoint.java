package com.psiphiglobal.proto.endpoints;

import com.google.gson.reflect.TypeToken;
import com.psiphiglobal.proto.blockchain.api.UserApi;
import com.psiphiglobal.proto.model.Node;
import com.psiphiglobal.proto.model.User;
import com.psiphiglobal.proto.model.helper.UserHelper;
import com.psiphiglobal.proto.util.GsonProvider;

import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.lang.reflect.Type;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

@Path("user")
public class UserEndpoint extends AbstractEndpoint
{
    @GET
    @Path("{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public void getUser(@PathParam("username") String username, @Suspended AsyncResponse asyncResponse)
    {
        workerPool.execute(() -> {

            User user = blockchainApiManager.getUserApi().get(username);
            if (user != null)
            {
                asyncResponse.resume(buildSuccessJsonResponse(user));
            }
            else
            {
                asyncResponse.resume(Response.status(Response.Status.NOT_FOUND).build());
            }
        });
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void createUser(String inputJson, @Suspended AsyncResponse asyncResponse)
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

            User user = new User();
            for (Map.Entry<String, String> in : input.entrySet())
            {
                String key = in.getKey();
                String value = in.getValue();

                switch (key)
                {
                    case "username":
                        user.setUsername(value);
                        break;
                    case "name":
                        user.setName(value);
                        break;
                    case "email":
                        user.setEmail(value);
                        break;
                    case "public_key":
                        user.setPublicKey(value);
                        break;
                    case "signature":
                        user.setSignature(value);
                        break;
                    default:
                        user.addMetadata(key, value);
                        break;
                }
            }

            UserApi userApi = blockchainApiManager.getUserApi();
            if (!UserHelper.validate(user))
            {
                asyncResponse.resume(Response.status(Response.Status.BAD_REQUEST).build());
                return;
            }

            if (!UserHelper.validateSignature(user))
            {
                asyncResponse.resume(Response.status(Response.Status.UNAUTHORIZED).build());
                return;
            }

            if (userApi.userExists(user.getUsername()))
            {
                asyncResponse.resume(Response.status(Response.Status.CONFLICT).build());
                return;
            }

            user.setCreatedAt(Instant.now().getEpochSecond());
            userApi.create(user);
            asyncResponse.resume(buildSuccessJsonResponse(user));
        });
    }
}

