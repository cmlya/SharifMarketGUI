package view.Customer;

import controller.Database;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import static view.Customer.UserUtils.setScene;

public class MainMenu implements Initializable {
    @FXML Button exitButton = new Button();
    @FXML private void showItems() throws IOException { setScene("ShowItems.fxml"); }
    @FXML private void changePassword() throws IOException { setScene("ChangePassword1.fxml");}
    @FXML private void signOut() throws IOException {
        setScene("LoginSignupMenu.fxml");
        Database.getInstance().setCurrentCustomer(null);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        exitButton.setOnAction(actionEvent -> UserUtils.exit());
    }
}
