package view.Admin;

import controller.Database;
import controller.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import static view.Admin.AdminUtils.setScene;

public class SaleStatistics implements Initializable {
    @FXML TableView<Item> statisticsTableView = new TableView<>();
    @FXML TableColumn<Item, Integer> itemIDColumn = new TableColumn<>();
    @FXML TableColumn<Item, String> itemNameColumn = new TableColumn<>();
    @FXML TableColumn<Item, Integer> ordersInColumn = new TableColumn<>();
    @FXML TableColumn<Item, Integer> numberSoldColumn = new TableColumn<>();
    @FXML TableColumn<Item, Integer> moneyReceivedColumn = new TableColumn<>();
    @FXML TableColumn<Item, Integer> profitColumn = new TableColumn<>();
    @FXML Label totalSalesLabel = new Label();
    @FXML Label totalProfitLabel = new Label();
    @FXML Button exitButton = new Button();

    private ObservableList<Item> getItems() {
        ObservableList<Item> items = FXCollections.observableArrayList();
        items.addAll(Database.getInstance().getItems());
        items.addAll(Database.getInstance().getDeletedItems());
        return items;
    }

    @FXML private void mainMenu() throws IOException { setScene("AdminMainMenu.fxml"); }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        exitButton.setOnAction(e -> System.exit(1400));
        int totalProfit = 0;
        int totalSales = 0;
        for (Item item : Database.getInstance().getItems()) {
            totalProfit += item.getItemProfit();
            totalSales += item.getMoneyMadeFrom();
        }
        for (Item item : Database.getInstance().getDeletedItems()) {
            totalProfit += item.getItemProfit();
            totalSales += item.getMoneyMadeFrom();
        }
        totalProfitLabel.setText(String.valueOf(totalProfit));
        totalSalesLabel.setText(String.valueOf(totalSales));

        itemIDColumn.setMinWidth(100);
        itemNameColumn.setMinWidth(100);
        ordersInColumn.setMinWidth(50);
        numberSoldColumn.setMinWidth(50);
        moneyReceivedColumn.setMinWidth(100);
        profitColumn.setMinWidth(100);
        itemIDColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        ordersInColumn.setCellValueFactory(new PropertyValueFactory<>("ordersIn"));
        numberSoldColumn.setCellValueFactory(new PropertyValueFactory<>("numberSold"));
        moneyReceivedColumn.setCellValueFactory(new PropertyValueFactory<>("moneyMadeFrom"));
        profitColumn.setCellValueFactory(new PropertyValueFactory<>("itemProfit"));
        statisticsTableView.setItems(getItems());
    }
}
