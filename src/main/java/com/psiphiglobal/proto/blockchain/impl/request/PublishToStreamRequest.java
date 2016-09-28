package com.psiphiglobal.proto.blockchain.impl.request;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by harsh on 28/09/16.
 */
public class PublishToStreamRequest extends JsonRpcRequest {

    private static final String METHOD_NAME = "publish";

    public PublishToStreamRequest(List<Object> params) {
        super(METHOD_NAME, params);
    }
}
