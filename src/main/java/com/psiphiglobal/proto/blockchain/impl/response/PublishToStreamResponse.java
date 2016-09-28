package com.psiphiglobal.proto.blockchain.impl.response;

import com.google.gson.annotations.SerializedName;
import com.psiphiglobal.proto.util.GsonProvider;

/**
 * Created by harsh on 28/09/16.
 */
public class PublishToStreamResponse {

    @SerializedName("result")
    private String transactionId;

    @SerializedName("id")
    private String requestId;

    public String getTransactionId() {
        return transactionId;
    }

    public String getRequestId() {
        return requestId;
    }

    public static PublishToStreamResponse parse(String responseJson)
    {
        return GsonProvider.get().fromJson(responseJson, PublishToStreamResponse.class);
    }

}
