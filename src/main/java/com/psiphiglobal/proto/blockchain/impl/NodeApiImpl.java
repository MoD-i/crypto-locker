package com.psiphiglobal.proto.blockchain.impl;

import com.psiphiglobal.proto.blockchain.api.NodeApi;
import com.psiphiglobal.proto.model.Node;
import com.psiphiglobal.proto.blockchain.impl._core.JsonRpcClient;
import com.psiphiglobal.proto.blockchain.impl.request.NodeInfoRequest;
import com.psiphiglobal.proto.blockchain.impl.response.NodeInfoResponse;

public class NodeApiImpl implements NodeApi
{
    private JsonRpcClient jsonRpcClient;

    public NodeApiImpl(JsonRpcClient jsonRpcClient)
    {
        this.jsonRpcClient = jsonRpcClient;
    }

    @Override
    public Node getInfo()
    {
        try
        {
            NodeInfoResponse getInfoResponse = NodeInfoResponse.parse(jsonRpcClient.sendRequest(new NodeInfoRequest()));

            return new Node(getInfoResponse.getVersion(), getInfoResponse.getProtocolVersion(),
                    getInfoResponse.getChainName(), getInfoResponse.getDescription(),
                    getInfoResponse.getProtocol(), getInfoResponse.getPort());
        }
        catch (Exception e)
        {
            return null;
        }
    }
}
