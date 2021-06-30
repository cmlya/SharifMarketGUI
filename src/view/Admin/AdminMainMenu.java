package view.Admin; // ADMIN

import javafx.fxml.FXML;
import java.io.IOException;
import static view.Admin.AdminUtils.setScene;

public class AdminMainMenu {

    @FXML private void itemManagement() throws IOException { setScene("ItemManagement.fxml"); }
    @FXML private void exit() { AdminUtils.exit(); }
}