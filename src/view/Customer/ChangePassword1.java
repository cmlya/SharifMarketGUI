package view.Customer;

import controller.Database;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static view.Customer.UserUtils.setScene;

public class ChangePassword1 implements Initializable {
    @FXML PasswordField testString = new PasswordField();
    @FXML Label incorrectPassword = new Label();
    @FXML Button exitButton = new Button();

    @FXML
    private void checkPassword() throws IOException {
        if (testString.getText().equals(Database.getInstance().getCurrentCustomer().getPassword()))
            setScene("ChangePassword2.fxml");
        else incorrectPassword.setVisible(true);
    }

    @FXML private void resetView() { incorrectPassword.setVisible(false); }
    @FXML private void mainMenu () throws IOException { setScene("MainMenu.fxml"); }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        exitButton.setOnAction(actionEvent -> UserUtils.exit());
    }
}
