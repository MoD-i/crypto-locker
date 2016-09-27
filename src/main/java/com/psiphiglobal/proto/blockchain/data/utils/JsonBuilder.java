package com.psiphiglobal.proto.blockchain.data.utils;

import com.psiphiglobal.proto.util.GsonProvider;
import com.psiphiglobal.proto.blockchain.data.core.JsonRpcRequest;

/**
 * Created by harsh on 27/09/16.
 */
public class JsonBuilder {

    public static String buildJson(JsonRpcRequest jsonRpcRequest) {

        String json = GsonProvider.get().toJson(jsonRpcRequest);
        return json;
    }

}
