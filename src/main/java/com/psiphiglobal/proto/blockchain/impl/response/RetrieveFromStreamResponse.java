package com.psiphiglobal.proto.blockchain.impl.response;

import com.google.gson.annotations.SerializedName;
import com.psiphiglobal.proto.util.GsonProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by harsh on 28/09/16.
 */
public class RetrieveFromStreamResponse {

    @SerializedName("result")
    private ArrayList<Result> result;

    public List<Result> getResults() {
        return result;
    }

    public Result getResult(int index)
    {
        return result.get(index);
    }

    public static class Result
    {
        @SerializedName("key")
        private String key;

        @SerializedName("data")
        private String data;

        @SerializedName("publishers")
        private ArrayList<String> publishers;

        public String getKey() {
            return key;
        }

        public String getData() {
            return data;
        }

        public ArrayList<String> getPublishers() {
            return publishers;
        }
    }

    public static RetrieveFromStreamResponse parse(String responseJson)
    {
        return GsonProvider.get().fromJson(responseJson, RetrieveFromStreamResponse.class);
    }


}
