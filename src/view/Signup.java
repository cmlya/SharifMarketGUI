package view;

import controller.Customer;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sample.Alert;

import java.io.IOException;

import static view.Utils.setScene;

public class Signup {
    @FXML TextField newUsername;
    @FXML PasswordField newPassword = new PasswordField();
    @FXML PasswordField testString = new PasswordField();

    @FXML
    private void addCustomer() {
        String username = newUsername.getText();
        if (Customer.findCustomer(username) != null) {
            Alert.display("Error", "This username is not available.");
            return;
        }
        String password = newPassword.getText();
        if (!testString.getText().equals(password)) {
            Alert.display("Error", "Passwords do not match.");
            return;
        }
        new Customer(username, password);
        Alert.display("Success!", "Account created successfully.");
    }

    @FXML private void back() throws IOException { setScene("LoginSignupMenu.fxml"); }
}
