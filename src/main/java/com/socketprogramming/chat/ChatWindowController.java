package com.socketprogramming.chat;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class ChatWindowController implements Initializable {

    @FXML
    Button sendMessageButton;
    @FXML
    Label usernameLabel;
    @FXML
    TextField sendMessageTextField;
    @FXML
    TextArea messageTextArea;

    private Socket socket;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private User user;

    @FXML
    public void sendMessage() throws IOException {
        synchronized (objectOutputStream) {
            objectOutputStream.writeObject(user.getUsername() + ": " +sendMessageTextField.getText());
        }
        sendMessageTextField.clear();
    }

    private void receiveMessage(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while(true) {
                        synchronized (objectInputStream) {
                            if(objectInputStream.readObject() != null) {
                                System.out.println(objectInputStream.readObject());
                                //messageTextArea.appendText(user.getUsername() + ": " + (String) objectInputStream.readObject());
                            }
                        }
                    }
                } catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        socket = LoginWindowController.getSocket();
        objectOutputStream = LoginWindowController.getObjectOutputStream();
        objectInputStream = LoginWindowController.getObjectInputStream();
        user = LoginWindowController.getUser();
        usernameLabel.setText(user.getUsername());
        receiveMessage();
    }
}
