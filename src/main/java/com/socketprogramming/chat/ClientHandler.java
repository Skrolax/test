package com.socketprogramming.chat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler extends Thread{

    private final Socket socket;
    private final ObjectOutputStream objectOutputStream;
    private final ObjectInputStream objectInputStream;
    private final User user;

    public Socket getSocket() {
        return socket;
    }
    public ObjectInputStream getObjectInputStream() {
        return objectInputStream;
    }
    public ObjectOutputStream getObjectOutputStream() {
        return objectOutputStream;
    }


    ClientHandler(Socket socket, User user) throws IOException {
        this.socket = socket;
        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectInputStream = new ObjectInputStream(socket.getInputStream());
        this.user = user;
    }

    private String message;

    @Override
    public void run(){
        try {
            while(true) {
                message = receiveMessage();
                broadcastMessage(message);
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void broadcastMessage(String message) throws IOException {
        synchronized (objectOutputStream) {
            objectOutputStream.writeObject(message);
        }
    }

    private String receiveMessage() throws IOException, ClassNotFoundException {
        String message;
        synchronized (objectInputStream) {
            if((message = (String) objectInputStream.readObject()) != null){
                System.out.println(message);
            }
        }
        return message;
    }
}
