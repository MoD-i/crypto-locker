package com.psiphiglobal.proto.blockchain.api;

import com.psiphiglobal.proto.model.User;

/**
 * Created by harsh on 28/09/16.
 */
public interface UserApi {

    boolean registerUser(User user);

    User getUser(String username);
}
