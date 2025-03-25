package com.socketprogramming.chat;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    ServerSocket serverSocket = new ServerSocket(5000);
    ArrayList<User> users = new ArrayList<>();

    private User user;
    private Socket socket;
    private ClientHandler client;

    public Server() throws IOException, ClassNotFoundException {
        while(true){
            socket = serverSocket.accept();
            client = new ClientHandler(socket, user);
            client.start();
            receiveTheUserData(client);
            System.out.println(user.getUsername() + " joined the chat");
        }
    }

    private void receiveTheUserData(ClientHandler client) throws IOException, ClassNotFoundException {
        synchronized (client.getObjectInputStream()) {
             user = (User) client.getObjectInputStream().readObject();
        }
        users.add(user);
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        new Server();
    }


}
