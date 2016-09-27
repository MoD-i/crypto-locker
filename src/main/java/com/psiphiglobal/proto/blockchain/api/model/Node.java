package com.psiphiglobal.proto.blockchain.api.model;

public final class Node
{
    private final String version;
    private final int protocolVersion;
    private final String chainName;
    private final String description;
    private final String protocol;
    private final int port;
    private final int peerCount;

    public Node(String version, int protocolVersion, String chainName, String description, String protocol, int port, int peerCount)
    {
        this.version = version;
        this.protocolVersion = protocolVersion;
        this.chainName = chainName;
        this.description = description;
        this.protocol = protocol;
        this.port = port;
        this.peerCount = peerCount;
    }

    public String getVersion()
    {
        return version;
    }

    public int getProtocolVersion()
    {
        return protocolVersion;
    }

    public String getChainName()
    {
        return chainName;
    }

    public String getDescription()
    {
        return description;
    }

    public String getProtocol()
    {
        return protocol;
    }

    public int getPort()
    {
        return port;
    }

    public int getPeerCount()
    {
        return peerCount;
    }
}
