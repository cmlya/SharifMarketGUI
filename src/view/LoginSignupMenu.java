package view;

import javafx.fxml.FXML;

import java.io.IOException;

import static view.UserUtils.setScene;

public class LoginSignupMenu {
    @FXML private void signup() throws IOException { setScene("Signup.fxml"); }
    @FXML private void login() throws IOException { setScene("Login.fxml"); }
    @FXML private void exit() { UserUtils.exit(); }
}
