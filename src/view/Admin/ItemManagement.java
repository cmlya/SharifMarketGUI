package view.Admin;

import controller.Database;
import controller.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.converter.IntegerStringConverter;
import java.io.IOException;
import static controller.Database.write;
import static controller.Utils.randomCode;
import static view.Admin.AdminUtils.setScene;

public class ItemManagement {
    @FXML RadioButton availableItems = new RadioButton();
    @FXML RadioButton unavailableItems = new RadioButton();
    @FXML VBox vBox = new VBox();
    @FXML TableView<Item> itemTableView = new TableView<>();
    @FXML TableColumn<Item, String> nameColumn = new TableColumn<>();
    @FXML TableColumn<Item, Integer> codeColumn = new TableColumn<>();
    @FXML TableColumn<Item, Integer> sellingPriceColumn = new TableColumn<>();
    @FXML TableColumn<Item, Integer> buyingPriceColumn = new TableColumn<>();
    @FXML TableColumn<Item, Integer> inStockColumn = new TableColumn<>();
    Boolean showAvailable = false;
    Boolean showUnavailable = false;
    @FXML TextField nameInput = new TextField();
    @FXML TextField sellingPriceInput = new TextField();
    @FXML TextField buyingPriceInput = new TextField();
    @FXML TextField inStockInput = new TextField();
    @FXML Button add = new Button();
    @FXML Button remove = new Button();
    @FXML HBox hBox = new HBox();
    @FXML Label invalidName = new Label();
    @FXML Label invalidInputs = new Label();
    @FXML Label invalidPrice = new Label();
    Boolean validItem = true;

    @FXML
    private void setDisplay() {
        showAvailable = availableItems.isSelected();
        showUnavailable = unavailableItems.isSelected();
        showItems();
    }

    @FXML
    private void validateInput() {
        validItem = true;
        invalidName.setVisible(false);
        invalidInputs.setVisible(false);
        invalidPrice.setVisible(false);

        if (Item.findItemByName(nameInput.getText()) != null) {
            invalidName.setVisible(true);
            validItem = false;
        }
        if (!nameInput.getText().matches(".*[a-z].*")) {
            invalidInputs.setVisible(true);
            validItem = false;
        }

        try {
            int newSellingPrice = 0;
            int newBuyingPrice = 0;
            int newInStock;
            if (!sellingPriceInput.getText().isEmpty())
                newSellingPrice = Integer.parseInt(sellingPriceInput.getText());
            else validItem = false;
            if (!buyingPriceInput.getText().isEmpty())
                newBuyingPrice = Integer.parseInt(buyingPriceInput.getText());
            else validItem = false;
            if (!inStockInput.getText().isEmpty())
                newInStock = Integer.parseInt(inStockInput.getText());
            else validItem = false;
            if (!sellingPriceInput.getText().isEmpty() && !buyingPriceInput.getText().isEmpty() &&
                    newSellingPrice < newBuyingPrice) {
                invalidPrice.setVisible(true);
                validItem = false;
            }
        } catch (NumberFormatException e) {
            invalidInputs.setVisible(true);
            validItem = false;
        }
        add.setDisable(!validItem);
    }

    @FXML
    private void addItem() {
        new Item(nameInput.getText(), randomCode(), Integer.parseInt(buyingPriceInput.getText()),
                Integer.parseInt(sellingPriceInput.getText()), Integer.parseInt(inStockInput.getText()));
        nameInput.clear();
        buyingPriceInput.clear();
        sellingPriceInput.clear();
        inStockInput.clear();
        add.setDisable(true);
        setDisplay();
    }

    @FXML
    private void removeItem() {
        ObservableList<Item> itemsSelected = itemTableView.getSelectionModel().getSelectedItems();
        ObservableList<Item> items = itemTableView.getItems();
        for (Item item : itemsSelected) {
            Database.getInstance().addDeletedItem(item);
            Database.getInstance().removeItem(item);
        }
        itemsSelected.forEach(items :: remove);
    }

    private void showItems() {
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

        add.setOnAction(e -> addItem());
        remove.setOnAction(e -> removeItem());

        itemTableView.setItems(getItems());
        itemTableView.setVisible(true);
    }

    private ObservableList<Item> getItems() {
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

    // TODO in stock, buying price and selling price validation (not negative, sp>bp), displaying alert messages
    @FXML
    private void editable() {
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nameColumn.setOnEditCommit(e -> {
            if (validNewName(e.getNewValue())) {
                e.getTableView().getItems().get(e.getTablePosition().getRow()).setName(e.getNewValue());
                write();
            }
            itemTableView.refresh();
        });
        buyingPriceColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        buyingPriceColumn.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setBuyingPrice(e.getNewValue());
            write();
        });
        sellingPriceColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        sellingPriceColumn.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setSellingPrice(e.getNewValue());
            write();
        });
        inStockColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        inStockColumn.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setInStock(e.getNewValue());
            write();
            itemTableView.refresh();
        });
        itemTableView.setEditable(true);
    }

    private boolean validNewName(String newName) {
        if (Item.findItemByName(newName) != null) return false;
        return newName.matches(".*[a-z].*");
    }

    // TODO
    private boolean validInStock(String newInStock) {
        try {
            int tmp = Integer.parseInt(newInStock);
            return true;
        }
        catch (NumberFormatException e) { return false; }
    }

    @FXML private void exitButton() { AdminUtils.exit(); }
    @FXML private void mainMenu () throws IOException { setScene("AdminMainMenu.fxml"); }
}
