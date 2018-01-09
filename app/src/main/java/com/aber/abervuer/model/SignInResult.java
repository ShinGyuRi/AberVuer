package com.aber.abervuer.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by scona on 2018-01-09.
 */

public class SignInResult {

    @SerializedName("user")
    public User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
