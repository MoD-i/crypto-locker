package com.psiphiglobal.proto.util.crypto;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

public final class HashUtil
{
    public static String hash(String message)
    {
        return Hex.encodeHexString(DigestUtils.sha256(message));
    }

    private HashUtil()
    {
    }
}
