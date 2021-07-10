package view.Admin;

import controller.Database;
import controller.Item;
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
import java.util.Objects;
import java.util.ResourceBundle;
import static view.Admin.AdminUtils.setScene;

public class NewOrders implements Initializable {
    @FXML TableView<Order> orderTableView = new TableView<>();
    @FXML TableColumn<Order, Integer> orderIDColumn = new TableColumn<>();
    @FXML TableColumn<Order, String> customerColumn = new TableColumn<>();
    @FXML TableColumn<Order, String> itemNameColumn = new TableColumn<>();
    @FXML TableColumn<Order, Integer> itemIDColumn = new TableColumn<>();
    @FXML TableColumn<Order, Integer> countColumn = new TableColumn<>();
    @FXML TableColumn<Order, String> dateColumn = new TableColumn<>();
    @FXML Button checkout = new Button();

    @FXML
    private void checkout() {
        Order order = getOrder();
        Item item;
        if (Item.findItem(order.getItemID()) == null)
            item = Item.findDeletedItem(order.getItemID());
        else item = Item.findItem(order.getItemID());
        Item.updateSales(order, Objects.requireNonNull(item));
        Database.getInstance().addOrderHistory(order);
        Database.getInstance().removeOrder(order);
        orderTableView.refresh();
        checkout.setDisable(true);
        showOrders();
    }

    private void showOrders() {
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
        orderTableView.setItems(getOrders());
    }

    private ObservableList<Order> getOrders() {
        ObservableList<Order> orders = FXCollections.observableArrayList();
        int numOfOrders = Database.getInstance().getOrders().size();
        int count = 0;
        for (int i = numOfOrders - 1; i >= 0 ; i--) {
            if (count == 10) break;
            orders.add(Database.getInstance().getOrders().get(i));
            count++;
        }
        return orders;
    }

    @FXML private Order getOrder() { return orderTableView.getSelectionModel().getSelectedItem(); }

    @FXML
    private void setControls() { checkout.setDisable(orderTableView.getSelectionModel().getSelectedItem() == null); }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
        orderTableView.setItems(getOrders());
    }

    @FXML private void mainMenu() throws IOException { setScene("AdminMainMenu.fxml"); }
    @FXML private void exitButton() { AdminUtils.exit(); }
}
