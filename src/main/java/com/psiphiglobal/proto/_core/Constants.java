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
    public static final String BLOCKCHAIN_RPC_URL = "http://54.71.117.175:2880/";
    public static final String BLOCKCHAIN_RPC_USERNAME = "multichainrpc";
    public static final String BLOCKCHAIN_RPC_PASSWORD = "BJydUxp8gWv1fvzgmpaF3G2qW9eAZnMiryknak8E9nVj";

    /* Chain */
    public static final String CHAIN_NAME = "testchain";
    public static final long DOCUMENT_SIZE_LIMIT = 3 * 1024 * 1024; // in bytes

    private Constants()
    {
    }
}
