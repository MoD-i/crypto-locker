package com.psiphiglobal.proto.blockchain.api;

import com.psiphiglobal.proto.blockchain.data.exceptions.JsonRpcException;
import com.psiphiglobal.proto.blockchain.api.model.Node;

public interface NodeApi
{
    Node getInfo() throws JsonRpcException;
}
