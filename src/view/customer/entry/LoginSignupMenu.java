package view.customer.entry;

import javafx.fxml.FXML;

import java.io.IOException;

import static view.Utils.setScene;

public class LoginSignupMenu {
    @FXML private void signup() throws IOException { setScene("Signup.fxml"); }
    @FXML private void login() throws IOException { setScene("Login.fxml"); }

    @FXML private void exit() {
        System.out.println("EXIT CLICKED");
    } // TODO
}
