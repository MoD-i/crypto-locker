package com.psiphiglobal.proto.blockchain.impl.request;

import java.util.List;

/**
 * Created by harsh on 28/09/16.
 */
public class RetrieveFromStreamRequest extends JsonRpcRequest{

    public static final String METHOD_NAME = "liststreamkeyitems";

    public RetrieveFromStreamRequest(List<Object> params)
    {
        super(METHOD_NAME, params);
    }

}

