package controller;

import java.util.Objects;

public class Order {
    private final String username;
    private final String date;
    private final int itemID;
    private final int number;
    private final int orderID;
    private final String itemName;
    private final long spent;
    private final long profit;

    public Order(String username, String date, int itemID, int number, int orderID, String itemName) {
        this.username = username;
        this.date = date;
        this.itemID = itemID;
        this.number = number;
        this.orderID = orderID;
        this.itemName = itemName;
        this.spent = (long) Objects.requireNonNull(Item.findItem(itemID)).getSellingPrice() * number;
        this.profit = (long) (Objects.requireNonNull(Item.findItem(itemID)).getSellingPrice() -
                Objects.requireNonNull(Item.findItem(itemID)).getBuyingPrice()) * number;
        Database.getInstance().addOrder(this);
    }

    public static Order findOrder(int orderID) {
        for (Order order : Database.getInstance().getOrders())
            if (order.orderID == orderID)
                return order;
        return null;
    }
public String getUsername() { return username; }
    public String getDate() { return date; }
    public int getItemID() { return itemID; }
    public int getNumber() { return number; }
    public int getOrderID() { return orderID; }
    public String getItemName() { return itemName; }
    public long getSpent() { return spent; }
    public long getProfit() { return profit; }
}
