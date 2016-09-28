package com.psiphiglobal.proto;

import com.psiphiglobal.proto._core.Constants;
import com.psiphiglobal.proto.blockchain.impl._core.JsonRpcClient;
import com.psiphiglobal.proto.blockchain.impl._core.JsonRpcException;
import com.psiphiglobal.proto.blockchain.impl.request.PublishToStreamRequest;
import com.psiphiglobal.proto.blockchain.impl.response.PublishToStreamResponse;
import com.psiphiglobal.proto.model.User;
import com.psiphiglobal.proto.util.GsonProvider;
import org.apache.commons.codec.binary.Hex;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by harsh on 28/09/16.
 */
public class Main {

    public static void main(String[] args) {

        JsonRpcClient.init(GsonProvider.get(), Constants.BLOCKCHAIN_RPC_URL, Constants.BLOCKCHAIN_RPC_USERNAME, Constants.BLOCKCHAIN_RPC_PASSWORD);
        JsonRpcClient jsonRpcClient = JsonRpcClient.getInstance();

        User user = new User("user2", "765843");

        String userJson = GsonProvider.get().toJson(user);
        String userJsonHex = Hex.encodeHexString(userJson.getBytes());

        List<Object> params = new ArrayList<>();
        params.add(Constants.USER_STREAM_NAME);
        params.add(user.getUsername());
        params.add(userJsonHex);

        try {
            PublishToStreamResponse publishToStreamResponse = PublishToStreamResponse.parse(jsonRpcClient.sendRequest(new PublishToStreamRequest(params)));
            System.out.println("Transaction ID ---- " + publishToStreamResponse.getTransactionId());

        } catch (JsonRpcException e) {

            System.out.printf("EXCEPTION HERE");
        }
    }
}
