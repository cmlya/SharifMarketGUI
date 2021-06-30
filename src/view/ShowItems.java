package view;

import controller.Database;
import controller.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import static view.UserUtils.setScene;

public class ShowItems {
    @FXML Pane pane = new Pane();
    @FXML TableView<Item> itemTableView = new TableView<>();
    @FXML TableColumn<Item, String> nameColumn = new TableColumn<>("Item Name");
    @FXML TableColumn<Item, Integer> codeColumn = new TableColumn<>("Item Code");
    @FXML TableColumn<Item, Integer> sellingPriceColumn = new TableColumn<>("Price");
    @FXML TableColumn<Item, Integer> inStockColumn = new TableColumn<>("In Stock");
    @FXML VBox vBox = new VBox();
    Boolean showAvailable = true;

    @FXML private void showInStock() {
        showAvailable = true;
        showItems();
    }
    @FXML private void showOutOfStock() {
        showAvailable = false;
        showItems();
    }

    public void showItems() {
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
    }

    public ObservableList<Item> getItems() {
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

    @FXML private void mainMenu () throws IOException { setScene("MainMenu.fxml"); }
    @FXML private void exit() { UserUtils.exit(); }
}
