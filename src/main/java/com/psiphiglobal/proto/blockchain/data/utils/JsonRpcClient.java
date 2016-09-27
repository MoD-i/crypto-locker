package com.psiphiglobal.proto.blockchain.data.utils;

import com.psiphiglobal.proto.blockchain.data.api.JsonRpcRequest;
import com.psiphiglobal.proto.blockchain.data.api.JsonRpcResponse;
import com.psiphiglobal.proto.blockchain.data.constants.Constants;
import com.psiphiglobal.proto.blockchain.data.exceptions.JsonRpcException;
import com.squareup.okhttp.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by harsh on 27/09/16.
 */
public class JsonRpcClient {

    private static JsonRpcClient instance;
    private OkHttpClient okHttpClient;
    private URL serverUrl;
    private final MediaType JSON
            = MediaType.parse(Constants.mediaType);

    public static JsonRpcClient getJsonRpcClient() throws MalformedURLException {
        if (instance == null) {
            instance = new JsonRpcClient();
            return instance;
        } else {

            return instance;
        }
    }

    private JsonRpcClient() throws MalformedURLException {
        okHttpClient = new OkHttpClient();

        serverUrl = new URL(Constants.serverUrl);
    }


    public JsonRpcResponse sendRequest(String methodName, ArrayList<Object> params) throws JsonRpcException {

        long id = ((int)(10000 * Math.random())) + System.nanoTime();

        RequestBody body = RequestBody.create(JSON, JsonBuilder.buildJson(new JsonRpcRequest(methodName, params, String.valueOf(id))));
        System.out.println(JsonBuilder.buildJson(new JsonRpcRequest(methodName, params, String.valueOf(id))));

        Request request = new Request.Builder()
                .header(Constants.authHeader, Constants.basicAuth)
                .url(serverUrl)
                .post(body)
                .build();

        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
            throw new JsonRpcException("Null Response");
        }

//        if(response.code() != 200)
//        {
//            throw new JsonRpcException("Response Code is not 200.");
//        }

        JsonRpcResponse jsonRpcResponse = null;
        try {
            jsonRpcResponse = new JsonRpcResponse(response.body().string(), response.code());
        } catch (IOException e) {
            e.printStackTrace();
            throw new JsonRpcException("Malformed Response");
        }

        return jsonRpcResponse;
    }
}
