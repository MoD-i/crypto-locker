package com.psiphiglobal.proto.blockchain.impl;

import com.psiphiglobal.proto._core.Constants;
import com.psiphiglobal.proto.blockchain.api.UserApi;
import com.psiphiglobal.proto.blockchain.impl._core.JsonRpcClient;
import com.psiphiglobal.proto.blockchain.impl._core.JsonRpcException;
import com.psiphiglobal.proto.blockchain.impl.request.PublishToStreamRequest;
import com.psiphiglobal.proto.blockchain.impl.request.RetrieveFromStreamRequest;
import com.psiphiglobal.proto.blockchain.impl.response.PublishToStreamResponse;
import com.psiphiglobal.proto.blockchain.impl.response.RetrieveFromStreamResponse;
import com.psiphiglobal.proto.model.User;
import com.psiphiglobal.proto.util.GsonProvider;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by harsh on 28/09/16.
 */
public class UserApiImpl implements UserApi {

    private JsonRpcClient jsonRpcClient;

    public UserApiImpl(JsonRpcClient jsonRpcClient)
    {
        this.jsonRpcClient = jsonRpcClient;
    }

    @Override
    public boolean registerUser(User user) {

        boolean usernameAlreadyExist = checkIfUsernameAlreadyExists(user.getUsername());

        if(usernameAlreadyExist)
        {
            return false;
        }

        List<Object> params = new ArrayList<>();
        params.add(Constants.USER_STREAM_NAME);
        params.add(user.getUsername());
        params.add(convertToHex(user));

        try {
            PublishToStreamResponse publishToStreamResponse = PublishToStreamResponse.parse(jsonRpcClient.sendRequest(new PublishToStreamRequest(params)));
            System.out.println("Transaction ID ---- " + publishToStreamResponse.getTransactionId());
            return true; // TODO : What to return? Transaction ID?
        } catch (JsonRpcException e) {

            return false;
        }
    }

    @Override
    public User getUser(String username){

        List<Object> params = new ArrayList<>();
        params.add(Constants.USER_STREAM_NAME);
        params.add(username);

        try {
            RetrieveFromStreamResponse retrieveFromStreamResponse = RetrieveFromStreamResponse.parse(jsonRpcClient.sendRequest(new RetrieveFromStreamRequest(params)));

            if(retrieveFromStreamResponse.getResults().size() == 0)
            {
                return null;
            }

            return convertHexToUser(retrieveFromStreamResponse.getResult(0).getData());

        } catch (JsonRpcException e) {

            return null;
        }
    }

    private String convertToHex(User user)
    {
        String userJson = GsonProvider.get().toJson(user);
        String userJsonHex = Hex.encodeHexString(userJson.getBytes());
        return userJsonHex;
    }

    private static User convertHexToUser(String jsonHex)
    {
        String json = null;
        try {
            json = new String(Hex.decodeHex(jsonHex.toCharArray()));
        } catch (DecoderException e) {

            return null;
        }
        return GsonProvider.get().fromJson(json, User.class);
    }

    private boolean checkIfUsernameAlreadyExists(String username) {

        List<Object> params = new ArrayList<>();
        params.add(Constants.USER_STREAM_NAME);
        params.add(username);

        try {
            RetrieveFromStreamResponse retrieveFromStreamResponse = RetrieveFromStreamResponse.parse(jsonRpcClient.sendRequest(new RetrieveFromStreamRequest(params)));

            if(retrieveFromStreamResponse.getResults().size() == 0)
            {
                return false;
            }else{

                return true;
            }

        } catch (JsonRpcException e) {

            return true;
        }
    }

}
