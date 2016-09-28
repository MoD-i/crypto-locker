package com.psiphiglobal.proto.blockchain.impl.response;

import com.google.gson.annotations.SerializedName;
import com.psiphiglobal.proto.util.GsonProvider;

public class PublishToStreamResponse
{
    @SerializedName("result")
    private String transactionId;

    public String getTransactionId()
    {
        return transactionId;
    }

    public static PublishToStreamResponse parse(String responseJson)
    {
        return GsonProvider.get().fromJson(responseJson, PublishToStreamResponse.class);
    }

}
