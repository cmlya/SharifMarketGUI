package view.Customer;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import static view.Customer.UserUtils.setScene;

public class LoginSignupMenu implements Initializable {
    @FXML Button exitButton = new Button();
    @FXML private void signup() throws IOException { setScene("Signup.fxml"); }
    @FXML private void login() throws IOException { setScene("Login.fxml"); }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        exitButton.setOnAction(actionEvent -> System.exit(1400));
    }
}
