package com.psiphiglobal.proto.blockchain.api;

import com.psiphiglobal.proto._core.Constants;
import com.psiphiglobal.proto.blockchain.impl.NodeApiImpl;
import com.psiphiglobal.proto.blockchain.impl.UserApiImpl;
import com.psiphiglobal.proto.blockchain.impl._core.JsonRpcClient;
import com.psiphiglobal.proto.util.GsonProvider;

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

    /* Blockchain APIs */
    private NodeApi nodeApi;
    private UserApi userApi;

    private BlockchainApiManager()
    {
        JsonRpcClient.init(GsonProvider.get(), Constants.BLOCKCHAIN_RPC_URL, Constants.BLOCKCHAIN_RPC_USERNAME, Constants.BLOCKCHAIN_RPC_PASSWORD);
    }

    public NodeApi getNodeApi()
    {
        if (nodeApi == null)
        {
            nodeApi = new NodeApiImpl(JsonRpcClient.getInstance());
        }

        return nodeApi;
    }

    public UserApi getUserApi() {

        if(userApi == null)
        {
            userApi = new UserApiImpl(JsonRpcClient.getInstance());
        }

        return userApi;
    }
}
