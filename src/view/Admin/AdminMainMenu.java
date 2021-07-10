package view.Admin;

import javafx.fxml.FXML;
import java.io.IOException;
import static view.Admin.AdminUtils.setScene;

public class AdminMainMenu {
    @FXML private void itemManagement() throws IOException { setScene("ItemManagement.fxml"); }
    @FXML private void newOrders() throws IOException { setScene("NewOrders.fxml"); }
    @FXML private void orderHistory() throws IOException { setScene("OrderHistory.fxml"); }
    @FXML private void saleStatistics() throws IOException { setScene("SaleStatistics.fxml"); }
    @FXML private void exitButton() { AdminUtils.exit(); }
}