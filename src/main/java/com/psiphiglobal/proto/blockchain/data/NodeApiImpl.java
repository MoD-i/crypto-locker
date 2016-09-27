package com.psiphiglobal.proto.blockchain.data;

import com.psiphiglobal.proto.blockchain.data.core.JsonRpcRequest;
import com.psiphiglobal.proto.blockchain.data.core.JsonRpcResponse;
import com.psiphiglobal.proto.util.GsonProvider;
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

        JsonRpcResponse getInfoResponse = jsonRpcClient.sendRequest(new JsonRpcRequest("getinfo", new ArrayList<Object>(), generateId()));
        Node node = GsonProvider.get().fromJson(getInfoResponse.getJson(), Node.class);

        JsonRpcResponse getPeerInfoResponse = jsonRpcClient.sendRequest(new JsonRpcRequest("getpeerinfo", new ArrayList<>(), generateId()));
        System.out.println(getPeerInfoResponse.getJson());

        return node;
    }

    private String generateId()
    {
        long id = ((int)(10000 * Math.random())) + System.nanoTime();
        return String.valueOf(id);
    }

}
