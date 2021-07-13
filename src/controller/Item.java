package controller;

import java.util.Locale;

public class Item {
    private String name;
    private int inStock;
    private int buyingPrice;
    private int sellingPrice;
    private final int ID;
    private int ordersIn = 0;
    private int numberSold = 0;
    private int moneyMadeFrom = 0;
    private int itemProfit = 0;

    public Item(String name, int ID, int buyingPrice, int sellingPrice, int inStock) {
        this.name = name;
        this.ID = ID;
        this.buyingPrice = buyingPrice;
        this.sellingPrice = sellingPrice;
        this.inStock = inStock;
        Database.getInstance().addItem(this);
    }

    public static Item findItemByName(String name) {
        for (Item item : Database.getInstance().getItems())
            if (item.name.toLowerCase(Locale.ROOT).equals(name.toLowerCase(Locale.ROOT)))
                return item;
        return null;
    }

    public static Item findItem(int ID) {
        for (Item item : Database.getInstance().getItems())
            if (item.ID == ID)
                return item;
        return null;
    }

    public static Item findDeletedItem(int ID) {
        for (Item item : Database.getInstance().getDeletedItems())
            if (item.ID == ID)
                return item;
        return null;
    }

    public static void updateSales(Order order, Item item) {
        item.ordersIn++;
        item.numberSold += order.getNumber();
        item.moneyMadeFrom += order.getSpent();
        item.itemProfit += order.getProfit();
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getInStock() { return inStock; }
    public void setInStock(int inStock) { this.inStock = inStock; }
    public int getBuyingPrice() { return buyingPrice; }
    public void setBuyingPrice(int buyingPrice) { this.buyingPrice = buyingPrice; }
    public int getSellingPrice() { return sellingPrice; }
    public void setSellingPrice(int sellingPrice) { this.sellingPrice = sellingPrice; }
    public int getID() { return ID; }
    public int getOrdersIn() { return ordersIn; }
    public void setOrdersIn(int ordersIn) { this.ordersIn = ordersIn; }
    public int getNumberSold() { return numberSold; }
    public void setNumberSold(int numberSold) { this.numberSold = numberSold; }
    public int getMoneyMadeFrom() { return moneyMadeFrom; }
    public void setMoneyMadeFrom(int moneyMadeFrom) { this.moneyMadeFrom = moneyMadeFrom; }
    public int getItemProfit() { return itemProfit; }
    public void setItemProfit(int itemProfit) { this.itemProfit = itemProfit; }
}