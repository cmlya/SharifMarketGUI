package controller;

import model.ConsoleColors;
import static controller.Utils.digitCount;
import static controller.Utils.spaces;

public class Order {
    private final String username;
    private final String date;
    private final int itemID;
    private final int number;
    private final int orderID;
    private final String itemName;

    // constructor
    public Order(String username, String date, int itemID, int number, int orderID, String itemName) {
        this.username = username;
        this.date = date;
        this.itemID = itemID;
        this.number = number;
        this.orderID = orderID;
        this.itemName = itemName;
        Database.getInstance().addOrder(this);
    }

    public static Order findOrder(int orderID) {
        for (Order order : Database.getInstance().getOrders())
            if (order.orderID == orderID)
                return order;
        return null;
    }
/*
    public static void printOrders() {
        int orderIDLength = 8;
        int maxUsernameLength = "username".length();
        int dateLength = 19;
        int maxLengthName = ("ITEM".length());
        int maxCount = 99999;
        for (Order order : Database.getInstance().getOrders()) {
            if (order.username > maxUsernameLength)
                maxUsernameLength = order.username;
            if (order.itemName.length() > maxLengthName)
                maxLengthName = order.itemName.length();
            if (order.number > maxCount)
                maxCount = order.number;
        }
        System.out.println(ConsoleColors.PURPLE + "NEW ORDERS:" + ConsoleColors.RESET);
        String header = "ORDER ID" + spaces("ORDER ID", orderIDLength) +
                "USER ID" + spaces("USER ID", digitCount(maxUsernameLength)) +
                "DATE" + spaces("DATE", dateLength) + "ITEM" + spaces("ITEM", maxLengthName) +
                "COUNT" + spaces("COUNT", digitCount(maxCount));
        System.out.println(header);
        for (int i = 0; i < header.length(); i++)
            System.out.print(ConsoleColors.BLUE_BRIGHT + "-" + ConsoleColors.RESET);
        System.out.println();
        for (Order order : Database.getInstance().getOrders()) {
            System.out.println(order.orderID + spaces(String.valueOf(order.orderID), 8) +
                    order.username + spaces(String.valueOf(order.username), digitCount(maxUsernameLength)) +
                    order.date + spaces(order.date, dateLength) +
                    order.itemName + spaces(order.itemName, maxLengthName) +
                    order.number);
        }
    }

    public static void printHistory() {
        int orderIDLength = 8;
        int maxUserID = 9999999;
        int dateLength = 19;
        int maxLengthName = ("ITEM".length());
        int maxCount = 99999;
        for (Order order : Database.getInstance().getOrderHistory()) {
            if (order.username > maxUserID)
                maxUserID = order.username;
            if (order.itemName.length() > maxLengthName)
                maxLengthName = order.itemName.length();
            if (order.number > maxCount)
                maxCount = order.number;
        }
        System.out.println(ConsoleColors.BLUE + "ORDER HISTORY:" + ConsoleColors.RESET);
        String header = "ORDER ID" + spaces("ORDER ID", orderIDLength) +
                "USER ID" + spaces("USER ID", digitCount(maxUserID)) +
                "DATE" + spaces("DATE", dateLength) + "ITEM" + spaces("ITEM", maxLengthName) +
                "COUNT" + spaces("COUNT", digitCount(maxCount));
        System.out.println(header);
        for (int i = 0; i < header.length(); i++)
            System.out.print(ConsoleColors.YELLOW_BOLD_BRIGHT + "-" + ConsoleColors.RESET);
        System.out.println();
        for (Order order : Database.getInstance().getOrderHistory()) {
            System.out.println(order.orderID + spaces(String.valueOf(order.orderID), 8) +
                    order.username + spaces(String.valueOf(order.username), digitCount(maxUserID)) +
                    order.date + spaces(order.date, dateLength) +
                    order.itemName + spaces(order.itemName, maxLengthName) +
                    order.number);
        }
    }
*/
    public String getUsername() { return username; }
    public int getItemID() { return itemID; }
    public int getNumber() { return number; }
}
