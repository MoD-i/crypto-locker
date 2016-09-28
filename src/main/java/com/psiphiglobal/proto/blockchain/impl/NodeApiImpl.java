package com.psiphiglobal.proto.blockchain.impl;

import com.psiphiglobal.proto.blockchain.api.NodeApi;
import com.psiphiglobal.proto.blockchain.impl._core.JsonRpcClient;
import com.psiphiglobal.proto.blockchain.impl._core.JsonRpcException;
import com.psiphiglobal.proto.blockchain.impl.request.NodeInfoRequest;
import com.psiphiglobal.proto.blockchain.impl.response.NodeInfoResponse;
import com.psiphiglobal.proto.model.Node;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NodeApiImpl implements NodeApi
{
    private static Logger LOG = LogManager.getLogger();

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
            NodeInfoResponse response = NodeInfoResponse.parse(jsonRpcClient.sendRequest(new NodeInfoRequest()));
            Node node = new Node();
            node.setVersion(response.getVersion());
            node.setProtocolVersion(response.getProtocolVersion());
            node.setChainName(response.getChainName());
            node.setDescription(response.getDescription());
            node.setProtocol(response.getProtocol());
            node.setPort(response.getPort());
            return node;
        }
        catch (JsonRpcException e)
        {
            return null;
        }
        catch (Exception e)
        {
            LOG.error("Unable to get node info", e);
            return null;
        }
    }
}
