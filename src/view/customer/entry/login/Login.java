package view.customer.entry.login;

import controller.Customer;
import controller.Database;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sample.Alert;

import java.io.IOException;
import java.util.Objects;

import static view.Utils.setScene;

public class Login {
    @FXML TextField username;
    @FXML PasswordField password = new PasswordField();

    @FXML
    private void loginCustomer() {
        if (Customer.findCustomer(username.getText()) == null) {
            Alert.display("Error", "Account with this username does not exist.");
            return;
        }
        if (Objects.requireNonNull(Customer.findCustomer(username.getText())).getPassword().equals(password.getText())) {
            Database.getInstance().setCurrentCustomer(Customer.findCustomer(username.getText()));
        }
        Alert.display("Success!", "Account created successfully.");
    }

    @FXML private void back() throws IOException { setScene("LoginSignupMenu.fxml"); }
}
