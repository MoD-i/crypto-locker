package com.psiphiglobal.proto.blockchain.api;

import com.psiphiglobal.proto.model.User;

public interface UserApi
{
    boolean userExists(String username);

    void create(User user);

    User get(String username);
}
