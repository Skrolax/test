package com.socketprogramming.chat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class User implements Serializable {

    private int ID;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    private String username;
    private String password;
    private String email;

    User(String username, String password) throws IOException {
        this.username = username;
        this.password = password;
    }

}
