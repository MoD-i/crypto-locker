package com.psiphiglobal.proto.model;

public final class Node
{
    private final String version;
    private final int protocolVersion;
    private final String chainName;
    private final String description;
    private final String protocol;
    private final int port;

    public Node(String version, int protocolVersion, String chainName, String description, String protocol, int port)
    {
        this.version = version;
        this.protocolVersion = protocolVersion;
        this.chainName = chainName;
        this.description = description;
        this.protocol = protocol;
        this.port = port;
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
}
