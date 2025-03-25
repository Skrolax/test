package com.socketprogramming.chat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class LoginWindowController {

    @FXML
    TextField usernameTextField;
    @FXML
    TextField passwordTextField;
    @FXML
    Button submitButton;

    private static Socket socket;
    private static ObjectOutputStream objectOutputStream;
    private static ObjectInputStream objectInputStream;
    private static User user;

    public static Socket getSocket(){
        return socket;
    }
    public static ObjectOutputStream getObjectOutputStream(){
        return objectOutputStream;
    }
    public static ObjectInputStream getObjectInputStream(){
        return objectInputStream;
    }
    public static User getUser(){
        return user;
    }

    @FXML
    public void submitData() throws IOException {
        user = new User(usernameTextField.getText(), passwordTextField.getText());
        usernameTextField.clear();
        sendUserToServer(user);
        openChattingWindow();
    }

    public void sendUserToServer(User user) throws IOException {
        socket = new Socket(InetAddress.getLocalHost(), 5000);
        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectInputStream = new ObjectInputStream(socket.getInputStream());
        synchronized (objectOutputStream) {
            objectOutputStream.writeObject(user);
        }
    }

    public void openChattingWindow() throws IOException {
        Stage stage = (Stage) usernameTextField.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(LoginWindowController.class.getResource("chat-window.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Chat");
        stage.setScene(scene);
        stage.show();
    }

}
