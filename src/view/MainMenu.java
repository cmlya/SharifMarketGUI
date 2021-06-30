package view;

import controller.Database;
import javafx.fxml.FXML;
import java.io.IOException;
import static view.UserUtils.setScene;

public class MainMenu {
    @FXML private void showItems() throws IOException { setScene("ShowItems.fxml"); }
    @FXML private void changePassword() throws IOException { setScene("ChangePassword1.fxml");}
    @FXML private void signOut() throws IOException {
        setScene("LoginSignupMenu.fxml");
        Database.getInstance().setCurrentCustomer(null);
    }
    @FXML private void exit() { UserUtils.exit(); }
}
