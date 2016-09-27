package com.psiphiglobal.proto.blockchain.data.core;

import com.psiphiglobal.proto.blockchain.data.constants.Constants;

import java.util.ArrayList;

/**
 * Created by harsh on 27/09/16.
 */
public class JsonRpcRequest {

    private String method;
    private ArrayList<Object> params;
    private String id;
    private String chain_name;

    public JsonRpcRequest(String method, ArrayList<Object> params, String id) {
        this.method = method;
        this.params = params;
        this.id = id;
        this.chain_name = Constants.chainName;
    }
}
