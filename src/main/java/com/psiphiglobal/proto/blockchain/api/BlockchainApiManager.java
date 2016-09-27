package com.psiphiglobal.proto.blockchain.api;

import com.psiphiglobal.proto.blockchain.api.model.Node;

public class BlockchainApiManager
{
    private static BlockchainApiManager instance;

    public static BlockchainApiManager getInstance()
    {
        if (instance == null)
        {
            instance = new BlockchainApiManager();
        }

        return instance;
    }

    private NodeApi nodeApi;

    private BlockchainApiManager()
    {
    }

    public NodeApi getNodeApi()
    {
        if (nodeApi == null)
        {
            // TODO : Replace with actual implementation
            nodeApi = () -> new Node("1.0 alpha 24", 10006, "morty", "Morty: The dumb testnet", "multichain", 7417, 1);
        }

        return nodeApi;
    }
}
