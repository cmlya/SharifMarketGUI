package view; // ADMIN

import controller.Database;
import controller.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import java.io.IOException;
import static view.AdminUtils.setScene;

public class ItemManagement {
    @FXML RadioButton availableItems = new RadioButton();
    @FXML RadioButton unavailableItems = new RadioButton();
    @FXML VBox vBox = new VBox();
    @FXML TableView<Item> itemTableView = new TableView<>();
    @FXML TableColumn<Item, String> nameColumn = new TableColumn<>("Item Name");
    @FXML TableColumn<Item, Integer> codeColumn = new TableColumn<>("Item Code");
    @FXML TableColumn<Item, Integer> sellingPriceColumn = new TableColumn<>("Selling Price");
    @FXML TableColumn<Item, Integer> buyingPriceColumn = new TableColumn<>("Buying Price");
    @FXML TableColumn<Item, Integer> inStockColumn = new TableColumn<>("In Stock");
    Boolean showAvailable = false;
    Boolean showUnavailable = false;

    public void setDisplay() {
        showAvailable = availableItems.isSelected();
        showUnavailable = unavailableItems.isSelected();
        showItems();
    }

    public void showItems() {
        nameColumn.setMinWidth(100);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        codeColumn.setMinWidth(100);
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        sellingPriceColumn.setMinWidth(150);
        sellingPriceColumn.setCellValueFactory(new PropertyValueFactory<>("sellingPrice"));
        buyingPriceColumn.setMinWidth(150);
        buyingPriceColumn.setCellValueFactory(new PropertyValueFactory<>("buyingPrice"));
        inStockColumn.setMinWidth(50);
        inStockColumn.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        itemTableView.setItems(getItems());
        itemTableView.setVisible(true);
    }

    public ObservableList<Item> getItems() {
        ObservableList<Item> items = FXCollections.observableArrayList();
        if (showAvailable) {
            for (Item item : Database.getInstance().getItems())
                if (item.getInStock() != 0)
                    items.add(item);
        }
        if (showUnavailable) for (Item item : Database.getInstance().getItems())
            if (item.getInStock() == 0)
                items.add(item);
        return items;
    }


    @FXML private void exit() { AdminUtils.exit(); }
    @FXML private void mainMenu () throws IOException { setScene("AdminMainMenu.fxml"); }

}
