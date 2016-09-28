package com.psiphiglobal.proto.model;

/**
 * Created by harsh on 28/09/16.
 */
public class User {

    private final String username;
    private final String publicKey;

    public User(String username, String publicKey) {
        this.username = username;
        this.publicKey = publicKey;
    }

    public String getUsername() {
        return username;
    }

    public String getPublicKey() {
        return publicKey;
    }
}
