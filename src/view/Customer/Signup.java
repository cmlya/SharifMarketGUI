package view.Customer;

import controller.Customer;
import controller.Database;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import view.Alert;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import static view.Customer.UserUtils.setScene;

public class Signup implements Initializable {
    @FXML TextField newUsername;
    @FXML PasswordField newPassword = new PasswordField();
    @FXML PasswordField testString = new PasswordField();
    @FXML Label usernameNotAvailable = new Label();
    @FXML Label invalidUsername = new Label();
    @FXML Label invalidPassword = new Label();
    @FXML Label passwordsDoNotMatch = new Label();
    @FXML Button signUpButton = new Button();
    @FXML Button exitButton = new Button();

    @FXML
    private void checkUsername() {
        String username = newUsername.getText().trim().toLowerCase(Locale.ROOT);
        usernameNotAvailable.setVisible(Customer.findCustomer(username) != null);
        invalidUsername.setVisible(username.isEmpty());
        signUpButton.setDisable(Customer.findCustomer(username) != null || username.isEmpty());
    }

    @FXML
    private void checkPassword() {
        String password = newPassword.getText();
        invalidPassword.setVisible(password.length() < 3);
        signUpButton.setDisable(password.length() < 3 || !testString.getText().equals(newPassword.getText()));
        if (!testString.getText().isEmpty()) passwordMatch();
    }

    @FXML
    private void passwordMatch() {
        passwordsDoNotMatch.setVisible(!testString.getText().equals(newPassword.getText()));
        signUpButton.setDisable(!testString.getText().equals(newPassword.getText()) ||
                newPassword.getText().length() < 3);
    }

    @FXML
    private void addCustomer() throws IOException {
        String username = newUsername.getText().trim().toLowerCase(Locale.ROOT);
        String password = newPassword.getText();
        Customer customer = new Customer(username, password);
        Alert.display("Success!", "Account created successfully.");
        Database.getInstance().setCurrentCustomer(customer);
        setScene("MainMenu.fxml");
    }

    @FXML private void back() throws IOException { setScene("LoginSignupMenu.fxml"); }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        exitButton.setOnAction(e -> System.exit(1400));
    }
}
