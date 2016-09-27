package com.psiphiglobal.proto.blockchain.data.api.node;

import com.psiphiglobal.proto.util.GsonProvider;
import com.psiphiglobal.proto.blockchain.data.api.JsonRpcResponse;
import com.psiphiglobal.proto.blockchain.data.exceptions.JsonRpcException;
import com.psiphiglobal.proto.blockchain.data.utils.JsonRpcClient;
import com.psiphiglobal.proto.blockchain.api.NodeApi;
import com.psiphiglobal.proto.blockchain.api.model.Node;

import java.net.MalformedURLException;
import java.util.ArrayList;

/**
 * Created by harsh on 27/09/16.
 */
public class NodeApiImpl implements NodeApi{

    private JsonRpcClient jsonRpcClient;

    public NodeApiImpl() throws MalformedURLException {

        jsonRpcClient = JsonRpcClient.getJsonRpcClient();
    }

    @Override
    public Node getInfo() throws JsonRpcException {

        JsonRpcResponse getInfoResponse = jsonRpcClient.sendRequest("getinfo", new ArrayList<>());
        System.out.println("Response ----- " + getInfoResponse.getJson());
        Node node = GsonProvider.get().fromJson(getInfoResponse.getJson(), Node.class);

        JsonRpcResponse getPeerInfoResponse = jsonRpcClient.sendRequest("getpeerinfo", new ArrayList<>());
        System.out.println("Response ----- " + getPeerInfoResponse.getJson());
        System.out.println(getPeerInfoResponse.getJson());

        return node;
    }
}
