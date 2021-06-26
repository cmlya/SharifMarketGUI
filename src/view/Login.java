package view;

import controller.Customer;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sample.Alert;
import java.io.IOException;
import java.util.Locale;
import java.util.Objects;
import static controller.Database.getInstance;
import static view.Utils.setScene;

public class Login {
    @FXML TextField usernameInput;
    @FXML PasswordField password = new PasswordField();
    @FXML Label usernameNonexistent = new Label();
    @FXML Label incorrectPassword = new Label();

    @FXML private void resetLoginPage() {
        if (usernameNonexistent.isVisible()) {
            password.clear();
            usernameNonexistent.setVisible(false);
        }
        incorrectPassword.setVisible(false);
    }

    @FXML
    private void loginCustomer() {
        String username = usernameInput.getText().trim().toLowerCase(Locale.ROOT);
        if (Customer.findCustomer(username) == null) {
            usernameNonexistent.setVisible(true);
            return;
        }
        Customer customer = Customer.findCustomer(username);
        if (Objects.requireNonNull(customer).getPassword().equals(password.getText())) {
            getInstance().setCurrentCustomer(customer);
            Alert.display("Success!", "Logged in successfully.");
        }
        else Alert.display("Unsuccessful", "Incorrect password.");
    }

    @FXML private void back() throws IOException { setScene("LoginSignupMenu.fxml"); }
    @FXML private void exit() { Utils.exit(); }
}
