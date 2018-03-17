package com.banregio.examenbregio.net.data;

/**
 * Created by jordan on 16/03/2018.
 */

public class LoginRequest {
    String user;
    String password;

    public LoginRequest(String user, String password) {
        this.user = user;
        this.password = password;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
