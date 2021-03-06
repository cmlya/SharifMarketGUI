package view.Customer;

import controller.Database;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import static view.Customer.UserUtils.setScene;

public class ChangePassword2 implements Initializable {
    @FXML PasswordField testString = new PasswordField();
    @FXML PasswordField newPassword = new PasswordField();
    @FXML Label invalidPassword = new Label();
    @FXML Button submitButton = new Button();
    @FXML Label passwordsDoNotMatch = new Label();
    @FXML Label success = new Label();
    @FXML Button exitButton = new Button();

    @FXML
    private void checkPassword() {
        String password = newPassword.getText();
        invalidPassword.setVisible(password.length() < 3);
        submitButton.setDisable(password.length() < 3 || !testString.getText().equals(newPassword.getText()));
        if (!testString.getText().isEmpty()) passwordMatch();
    }

    @FXML
    private void passwordMatch() {
        passwordsDoNotMatch.setVisible(!testString.getText().equals(newPassword.getText()));
        submitButton.setDisable(!testString.getText().equals(newPassword.getText())
                                || newPassword.getText().length() < 3);
    }

    @FXML private void changePassword() {
        Database.getInstance().getCurrentCustomer().setPassword(newPassword.getText());
        success.setVisible(true);

        Task<Void> sleeper = new Task<>() {
            @Override
            protected Void call() {
                try { Thread.sleep(2000); } catch (InterruptedException ignored) { }
                return null;
            }
        };
        sleeper.setOnSucceeded(event -> { try { mainMenu(); } catch (IOException e) { e.printStackTrace(); } });
        new Thread(sleeper).start();
    }
    @FXML private void mainMenu () throws IOException { setScene("MainMenu.fxml"); }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        exitButton.setOnAction(actionEvent -> UserUtils.exit());
    }
}
