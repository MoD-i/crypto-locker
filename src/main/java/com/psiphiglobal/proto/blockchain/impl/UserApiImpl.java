package com.psiphiglobal.proto.blockchain.impl;

import com.psiphiglobal.proto.blockchain.api.UserApi;
import com.psiphiglobal.proto.blockchain.impl._core.JsonRpcClient;
import com.psiphiglobal.proto.blockchain.impl._core.JsonRpcException;
import com.psiphiglobal.proto.blockchain.impl.request.PublishToStreamRequest;
import com.psiphiglobal.proto.blockchain.impl.request.RetrieveFromStreamRequest;
import com.psiphiglobal.proto.blockchain.impl.response.PublishToStreamResponse;
import com.psiphiglobal.proto.blockchain.impl.response.RetrieveFromStreamResponse;
import com.psiphiglobal.proto.exception.UnknownException;
import com.psiphiglobal.proto.model.User;
import com.psiphiglobal.proto.model.helper.UserHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserApiImpl implements UserApi
{
    private static Logger LOG = LogManager.getLogger();

    public static final String STREAM_USERS = "users";

    private JsonRpcClient jsonRpcClient;

    public UserApiImpl(JsonRpcClient jsonRpcClient)
    {
        this.jsonRpcClient = jsonRpcClient;
    }

    @Override
    public boolean userExists(String username)
    {
        try
        {
            RetrieveFromStreamResponse response = RetrieveFromStreamResponse.parse(jsonRpcClient.sendRequest(new RetrieveFromStreamRequest(STREAM_USERS, username)));
            return response.getResults().size() != 0;
        }
        catch (JsonRpcException e)
        {
            throw new UnknownException();
        }
        catch (Exception e)
        {
            LOG.error("Unable to check if user exists", e);
            throw new UnknownException();
        }
    }

    @Override
    public void create(User user)
    {
        try
        {
            PublishToStreamResponse response = PublishToStreamResponse.parse(jsonRpcClient.sendRequest(new PublishToStreamRequest(STREAM_USERS, user.getUsername(), UserHelper.serialize(user))));
            LOG.info("[CREATE USER] Transaction ID: " + response.getTransactionId());
        }
        catch (JsonRpcException e)
        {
            throw new UnknownException();
        }
        catch (Exception e)
        {
            LOG.error("Unable to create user", e);
            throw new UnknownException();
        }
    }

    @Override
    public User get(String username)
    {
        try
        {
            RetrieveFromStreamResponse response = RetrieveFromStreamResponse.parse(jsonRpcClient.sendRequest(new RetrieveFromStreamRequest(STREAM_USERS, username)));
            if (response.getResults().size() == 0)
            {
                return null;
            }
            return UserHelper.deserialize(response.getResult(0).getData());

        }
        catch (JsonRpcException e)
        {
            throw new UnknownException();
        }
        catch (Exception e)
        {
            LOG.error("Unable to fetch user", e);
            throw new UnknownException();
        }
    }
}
