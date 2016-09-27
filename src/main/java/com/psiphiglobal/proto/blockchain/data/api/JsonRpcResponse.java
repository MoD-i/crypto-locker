package com.psiphiglobal.proto.blockchain.data.api;

/**
 * Created by harsh on 27/09/16.
 */
public class JsonRpcResponse {

    private int code;
    private String json;

    public JsonRpcResponse(String json, int code) {
        this.json = json;
        this.code =code;
    }

    public String getJson() {
        return json;
    }

    public int getCode() {
        return code;
    }
}
