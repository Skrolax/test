module com.socketprogramming.chat {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;


    opens com.socketprogramming.chat to javafx.fxml;
    exports com.socketprogramming.chat;
}