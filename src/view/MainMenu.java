package view;

import controller.Database;
import javafx.fxml.FXML;

import java.io.IOException;

import static view.Utils.setScene;

public class MainMenu {



    @FXML private void signOut() throws IOException {
        setScene("LoginSignupMenu.fxml");
        Database.getInstance().setCurrentCustomer(null);
    }
    @FXML private void exit() { Utils.exit(); }


}
