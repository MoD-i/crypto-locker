package com.psiphiglobal.proto.model.helper;

import com.psiphiglobal.proto.model.User;
import com.psiphiglobal.proto.util.GsonProvider;
import com.psiphiglobal.proto.util.crypto.AsymmetricCryptoUtil;
import org.apache.commons.codec.binary.Base64;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

public final class UserHelper
{
    public static boolean validate(User user)
    {
        return !(user.getUsername() == null || user.getUsername().isEmpty() ||
                user.getName() == null || user.getName().isEmpty() ||
                !isValidEmail(user.getEmail()) ||
                user.getPublicKey() == null || user.getPublicKey().isEmpty() ||
                user.getSignature() == null || user.getSignature().isEmpty());
    }

    public static boolean validateSignature(User user)
    {
        String raw = user.getUsername() + "|" + user.getName() + "|" + user.getEmail();
        byte[] rawBytes = raw.getBytes();
        byte[] signature = Base64.decodeBase64(user.getSignature());
        byte[] publicKey = Base64.decodeBase64(user.getPublicKey());
        return AsymmetricCryptoUtil.verifySign(publicKey, signature, rawBytes);
    }

    public static String serialize(User user)
    {
        return GsonProvider.get().toJson(user);
    }

    public static User deserialize(String serializedUser)
    {
        return GsonProvider.get().fromJson(serializedUser, User.class);
    }

    private static boolean isValidEmail(String email)
    {
        return email != null && !email.isEmpty();
    }

    private UserHelper()
    {
    }
}
