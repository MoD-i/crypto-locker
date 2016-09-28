package com.psiphiglobal.proto.blockchain.impl.request;

import java.util.List;

public class RetrieveFromStreamRequest extends JsonRpcRequest{

    public static final String METHOD_NAME = "liststreamkeyitems";

    public RetrieveFromStreamRequest(String streamName, String key)
    {
        super(METHOD_NAME);
        params.add(streamName);
        params.add(key);
    }

}

