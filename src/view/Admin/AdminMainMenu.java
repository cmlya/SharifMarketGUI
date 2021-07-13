package view.Admin;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import static view.Admin.AdminUtils.setScene;

public class AdminMainMenu implements Initializable {
    @FXML Button exitButton = new Button();
    @FXML private void itemManagement() throws IOException { setScene("ItemManagement.fxml"); }
    @FXML private void newOrders() throws IOException { setScene("NewOrders.fxml"); }
    @FXML private void orderHistory() throws IOException { setScene("OrderHistory.fxml"); }
    @FXML private void saleStatistics() throws IOException { setScene("SaleStatistics.fxml"); }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        exitButton.setOnAction(e -> System.exit(1400));
    }
}