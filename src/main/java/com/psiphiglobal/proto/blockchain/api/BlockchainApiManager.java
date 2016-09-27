package com.psiphiglobal.proto.blockchain.api;

import com.psiphiglobal.proto.blockchain.data.api.node.NodeApiImpl;

import java.net.MalformedURLException;

public class BLockchainApiManager
{
    private static BLockchainApiManager instance;

    public static BLockchainApiManager getInstance()
    {
        if (instance == null)
        {
            instance = new BLockchainApiManager();
        }

        return instance;
    }

    private NodeApi nodeApi;

    private BLockchainApiManager()
    {
    }

    public NodeApi getNodeApi() throws MalformedURLException {
        if (nodeApi == null)
        {
            nodeApi = new NodeApiImpl();
        }

        return nodeApi;
    }
}
