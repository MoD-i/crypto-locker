package com.psiphiglobal.proto._core;

public final class Constants
{
    /* API Server */
    public static final String VERSION = "v0.9";
    public static final String BASE_URI = "/" + VERSION + "/";
    public static final String HTTP_SCHEME = "http";
    public static final String HOST = "0.0.0.0";
    public static final int PORT = 7070;

    /* Blockchain Node */
    public static final String BLOCKCHAIN_RPC_URL = "http://54.71.117.175:4798/";
    public static final String BLOCKCHAIN_RPC_USERNAME = "multichainrpc";
    public static final String BLOCKCHAIN_RPC_PASSWORD = "9f6LGez2SnVvw1TbSoNEdnCcvNBqssxAUee3vefpSY2a";

    /* Chain */
    public static final String CHAIN_NAME = "dummychain";
    public static final long DOCUMENT_SIZE_LIMIT = 3 * 1024 * 1024; // in bytes

    private Constants()
    {
    }
}
