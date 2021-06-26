package view;

import controller.Customer;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

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
        if (incorrectPassword.isVisible()) {
            password.clear();
            incorrectPassword.setVisible(false);
        }
    }

    @FXML
    private void loginCustomer() throws IOException {
        String username = usernameInput.getText().trim().toLowerCase(Locale.ROOT);
        if (Customer.findCustomer(username) == null) {
            usernameNonexistent.setVisible(true);
            return;
        }
        Customer customer = Customer.findCustomer(username);
        if (Objects.requireNonNull(customer).getPassword().equals(password.getText())) {
            getInstance().setCurrentCustomer(customer);
            setScene("MainMenu.fxml");
        }
        else incorrectPassword.setVisible(true);
    }

    @FXML private void back() throws IOException { setScene("LoginSignupMenu.fxml"); }
    @FXML private void exit() { Utils.exit(); }
}
