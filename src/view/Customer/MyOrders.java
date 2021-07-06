package view.Customer;

import javafx.fxml.FXML;

import java.io.IOException;

import static view.Customer.UserUtils.setScene;

public class MyOrders {
    @FXML private void backButton () throws IOException { setScene("ShowItems.fxml"); }
    @FXML private void exitButton() { UserUtils.exit(); }
}
