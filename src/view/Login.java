package view;

import controller.Customer;
import controller.Database;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sample.Alert;
import java.io.IOException;
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
        Customer customer = Customer.findCustomer(username.getText());
        assert customer != null;
        if (customer.getPassword().equals(password.getText())) {
            Database.getInstance().setCurrentCustomer(Customer.findCustomer(username.getText()));
            Alert.display("Success!", "Logged in successfully.");
        }
    }

    @FXML private void back() throws IOException { setScene("LoginSignupMenu.fxml"); }
}
