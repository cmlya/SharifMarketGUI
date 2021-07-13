package view.Admin;

import controller.Database;
import controller.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import static view.Admin.AdminUtils.setScene;

public class OrderHistory implements Initializable {
    @FXML TableView<Order> orderTableView = new TableView<>();
    @FXML TableColumn<Order, Integer> orderIDColumn = new TableColumn<>();
    @FXML TableColumn<Order, String> customerColumn = new TableColumn<>();
    @FXML TableColumn<Order, String> itemNameColumn = new TableColumn<>();
    @FXML TableColumn<Order, Integer> itemIDColumn = new TableColumn<>();
    @FXML TableColumn<Order, Integer> countColumn = new TableColumn<>();
    @FXML TableColumn<Order, String> dateColumn = new TableColumn<>();
    @FXML Button exitButton = new Button();

    private ObservableList<Order> getOrderHistory() {
        ObservableList<Order> orderHistory = FXCollections.observableArrayList();
        orderHistory.addAll(Database.getInstance().getOrderHistory());
        return orderHistory;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        exitButton.setOnAction(e -> System.exit(1400));
        orderIDColumn.setMinWidth(100);
        customerColumn.setMinWidth(100);
        itemNameColumn.setMinWidth(100);
        itemIDColumn.setMinWidth(100);
        countColumn.setMinWidth(100);
        dateColumn.setMinWidth(150);
        orderIDColumn.setCellValueFactory(new PropertyValueFactory<>("orderID"));
        customerColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        itemIDColumn.setCellValueFactory(new PropertyValueFactory<>("itemID"));
        countColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        orderTableView.setItems(getOrderHistory());
    }

    @FXML private void mainMenu() throws IOException { setScene("AdminMainMenu.fxml"); }
}
