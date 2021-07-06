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
import javafx.scene.layout.VBox;
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
    @FXML VBox vBox = new VBox();
    @FXML Button order = new Button();
    @FXML Spinner<Integer> count = new Spinner();
    @FXML Button myOrders = new Button();
    Boolean showAvailable = true;

    @FXML private void showInStock() {
        showAvailable = true;
        showItems();
    }
    @FXML private void showOutOfStock() {
        showAvailable = false;
        showItems();
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
        order.setVisible(showAvailable);
        order.setDisable(true);
        count.setVisible(showAvailable);
        count.setDisable(true);
    }

    @FXML
    private void setControls() {
        order.setDisable(getItem() == null);
        count.setDisable(getItem() == null);
        SpinnerValueFactory<Integer> valueFactory;
        if (getItem() != null)
            valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, getItem().getInStock());
        else valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1);
        valueFactory.setValue(1);
        count.valueProperty().addListener((observableValue, integer, t1) -> {
         // TODO not enough wallet
        });
        count.setValueFactory(valueFactory);
    }
    @FXML private Item getItem() { return itemTableView.getSelectionModel().getSelectedItem(); }

    // TODO not enough wallet
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
        itemTableView.refresh();
        resetControls();
    }

    @FXML private void mainMenu () throws IOException { setScene("MainMenu.fxml"); }
    @FXML private void myOrders() throws IOException { setScene("MyOrders.fxml"); }
    @FXML private void exitButton() { UserUtils.exit(); }
}
