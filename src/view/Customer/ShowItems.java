package view.Customer;

import controller.Customer;
import controller.Database;
import controller.Item;
import controller.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import java.io.IOException;
import static controller.Utils.date;
import static controller.Utils.randomCode;
import static view.Customer.UserUtils.setScene;

public class ShowItems {
    @FXML Pane pane = new Pane();
    @FXML TableView<Item> itemTableView = new TableView<>();
    @FXML TableColumn<Item, String> nameColumn = new TableColumn<>("Item Name");
    @FXML TableColumn<Item, Integer> codeColumn = new TableColumn<>("Item Code");
    @FXML TableColumn<Item, Integer> sellingPriceColumn = new TableColumn<>("Price");
    @FXML TableColumn<Item, Integer> inStockColumn = new TableColumn<>("In Stock");
    @FXML Button order = new Button();
    @FXML Spinner<Integer> count = new Spinner();
    Boolean showAvailable = true;
    Boolean showingOrders = false;

    @FXML TableView<Order> orderTableView = new TableView<>();
    @FXML TableColumn<Order, Integer> orderIDColumn = new TableColumn<>();
    @FXML TableColumn<Order, String> itemNameColumn = new TableColumn<>();
    @FXML TableColumn<Order, Integer> itemIDColumn = new TableColumn<>();
    @FXML TableColumn<Order, Integer> countColumn = new TableColumn<>();
    @FXML TableColumn<Order, Long> spentColumn = new TableColumn<>();
    @FXML TableColumn<Order, String> dateColumn = new TableColumn<>();
    @FXML Button cancelOrder = new Button();
    @FXML Label notEnoughMoney = new Label();

    @FXML private void showInStock() { showAvailable = true; showingOrders = false; showItems(); }
    @FXML private void showOutOfStock() { showAvailable = false; showingOrders = false; showItems(); }
    @FXML private void myOrders() { showingOrders = true; showAvailable = false; showOrders(); }

    private void showOrders() {
        orderIDColumn.setMinWidth(100);
        orderIDColumn.setCellValueFactory(new PropertyValueFactory<>("orderID"));
        itemNameColumn.setMinWidth(100);
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        itemIDColumn.setMinWidth(100);
        itemIDColumn.setCellValueFactory(new PropertyValueFactory<>("itemID"));
        countColumn.setMinWidth(50);
        countColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        spentColumn.setMinWidth(100);
        spentColumn.setCellValueFactory(new PropertyValueFactory<>("spent"));
        dateColumn.setMinWidth(150);
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        orderTableView.setItems(getOrders());
        orderTableView.setVisible(true);
        resetControls();
    }

    private ObservableList<Order> getOrders() {
        ObservableList<Order> orders = FXCollections.observableArrayList();
        for (Order order : Database.getInstance().getOrders())
            if (order.getUsername().equals(Database.getInstance().getCurrentCustomer().getUsername()))
                orders.add(order);
        return orders;
    }

    private void showItems() {
        nameColumn.setMinWidth(100);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        codeColumn.setMinWidth(100);
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        sellingPriceColumn.setMinWidth(100);
        sellingPriceColumn.setCellValueFactory(new PropertyValueFactory<>("sellingPrice"));
        inStockColumn.setMinWidth(50);
        inStockColumn.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        itemTableView.setItems(getItems());
        itemTableView.setVisible(true);
        resetControls();
    }

    private ObservableList<Item> getItems() {
        ObservableList<Item> items = FXCollections.observableArrayList();
        if (showAvailable) {
            for (Item item : Database.getInstance().getItems())
                if (item.getInStock() != 0)
                    items.add(item);
        }
        else for (Item item : Database.getInstance().getItems())
            if (item.getInStock() == 0)
                items.add(item);
        return items;
    }

    private void resetControls() {
        cancelOrder.setVisible(showingOrders);
        orderTableView.setVisible(showingOrders);
        itemTableView.setVisible(!showingOrders);
        order.setVisible(showAvailable);
        order.setDisable(true);
        count.setVisible(showAvailable);
        count.setDisable(true);
        cancelOrder.setDisable(true);
        notEnoughMoney.setVisible(false);
    }

    @FXML
    private void setControls() {
        order.setDisable(getItem() == null);
        count.setDisable(getItem() == null);
        cancelOrder.setDisable(getOrder() == null);
        notEnoughMoney.setVisible(false);
        if (showAvailable) {
            SpinnerValueFactory<Integer> valueFactory;
            if (getItem() != null)
                valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, getItem().getInStock());
            else valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1);
            valueFactory.setValue(1);
            count.setValueFactory(valueFactory);
            walletCheck();
            count.valueProperty().addListener((observableValue, integer, t1) -> {
                walletCheck();
            });
        }
    }

    private void walletCheck() {
        if ((long) count.getValue() * getItem().getSellingPrice() >
                Database.getInstance().getCurrentCustomer().getWallet()) {
            order.setDisable(true);
            notEnoughMoney.setVisible(true);
        }
        else {
            order.setDisable(false);
            notEnoughMoney.setVisible(false);
        }
    }

    @FXML private Item getItem() { return itemTableView.getSelectionModel().getSelectedItem(); }
    @FXML private Order getOrder() { return orderTableView.getSelectionModel().getSelectedItem(); }

    @FXML
    private void order() {
        Item item = getItem();
        Customer customer = Database.getInstance().getCurrentCustomer();
        int quantity = count.getValue();
        new Order(customer.getUsername(), date(), item.getID(),
                quantity, randomCode(), item.getName());
        Database.getInstance().setCount(item, item.getInStock() - quantity);
        Database.getInstance().setWallet(customer,
                customer.getWallet() - (long) quantity * item.getSellingPrice());
        showItems();
        itemTableView.refresh();
        resetControls();
    }

    @FXML
    private void cancelOrder() {
        Order order = getOrder();
        Item item = Item.findItem(order.getItemID());
        if (item != null) item.setInStock(item.getInStock() + order.getNumber());
        Database.getInstance().removeOrder(order);
        Customer customer = Database.getInstance().getCurrentCustomer();
        Database.getInstance().setWallet(customer, customer.getWallet() + order.getSpent());
        orderTableView.refresh();
        itemTableView.refresh();
        showOrders();
    }

    @FXML private void mainMenu () throws IOException { setScene("MainMenu.fxml"); }
    @FXML private void exitButton() { UserUtils.exit(); }
}
