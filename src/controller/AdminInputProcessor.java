package controller;

import model.ConsoleColors;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;
import static controller.Utils.randomCode;

public class AdminInputProcessor {
    Scanner scanner = new Scanner(System.in);

    public void run() {
        boolean exit = false;
        while (!exit) {
            System.out.print(ConsoleColors.GREEN_BOLD_BRIGHT + "Enter command: " + ConsoleColors.RESET);
            exit = runCommand(scanner.nextLine());
        }
    }

    private boolean runCommand(String input) {
        if (input.equals("exit")) {
            Database.write();
            return true;
        }
        Database.write();
        return false;
    }

    private void logout(Matcher matcher) {
        if (matcher.find()) {
            System.out.println("Admin logged out successfully.");
        }
    }

    private void newItem(Matcher matcher) {
        if (matcher.find()) {
            String name = matcher.group(1);
            int inStock = Integer.parseInt(matcher.group(2));
            int sellingPrice = Integer.parseInt(matcher.group(3));
            int buyingPrice = Integer.parseInt(matcher.group(4));
            if (Item.findItemByName(name) != null) {
                System.out.println("Item with this name already exists. Item was not added.");
                return;
            }
            if (buyingPrice > sellingPrice) {
                System.out.println("Selling price cannot be less than buying price. Item was not added.");
                return;
            }
            int ID = randomCode();
            new Item(name, ID, buyingPrice, sellingPrice, inStock);
            System.out.println("Item added successfully. Item ID: " + ID);
        }
    }

    private void allItems(Matcher matcher) { if (matcher.find()) Item.printAll(); }

    private void itemsInStock(Matcher matcher) { if (matcher.find()) Item.printInStock(); }

    private void outOfStock(Matcher matcher) { if (matcher.find()) Item.printOutOfStock(); }

    private void remove(Matcher matcher) {
        if (matcher.find()) {
            int ID = Integer.parseInt(matcher.group(1));
            if (Item.findItem(ID) == null)
                System.out.println(ConsoleColors.RED_BRIGHT + "Item does not exist. Nothing was removed." + ConsoleColors.RESET);
            else {
                Item item = Item.findItem(ID);
                assert item != null;
                System.out.println("Item " + ConsoleColors.CYAN_BRIGHT + item.getName() +
                        ConsoleColors.RESET + " removed successfully");
                Database.getInstance().addDeletedItem(item);
                Database.getInstance().removeItem(item);
            }
        }
    }

    private void editName(Matcher matcher) {
        if (matcher.find()) {
            int ID = Integer.parseInt(matcher.group(1));
            String newName = matcher.group(2);
            if (Item.findItem(ID) == null)
                System.out.println(ConsoleColors.BLUE + "Item does not exist. Nothing was changed." + ConsoleColors.RESET);
            else if (newName.equals(Objects.requireNonNull(Item.findItem(ID)).getName()))
                System.out.println(ConsoleColors. BLUE +
                        "This item already has this name. Nothing was changed." + ConsoleColors.RESET);
            else {
                System.out.println("Item " + ConsoleColors.CYAN_BRIGHT + Objects.requireNonNull(Item.findItem(ID)).getName() + ConsoleColors.RESET
                + " renamed \"" + ConsoleColors.CYAN_BRIGHT + newName + ConsoleColors.RESET + "\"." );
                Database.getInstance().setName(Objects.requireNonNull(Item.findItem(ID)), newName);
            }
        }
    }

    private void editNameCount(Matcher matcher) {
        if (matcher.find()) {
            int ID = Integer.parseInt(matcher.group(1));
            String newName = matcher.group(2);
            int newCount = Integer.parseInt(matcher.group(3));
            boolean changeName = true;
            boolean changeCount = true;
            if (Item.findItem(ID) == null) {
                System.out.println(ConsoleColors.BLUE + "Item does not exist. Nothing was changed." + ConsoleColors.RESET);
                return;
            }
            Item item = Item.findItem(ID);
            assert item != null;
            if (newName.equals(item.getName())) {
                changeName = false;
                System.out.println(ConsoleColors.BLUE + "This item already has this name." + ConsoleColors.RESET);
            }
            if (newCount == item.getInStock()) {
                changeCount = false;
                System.out.println(ConsoleColors.BLUE + newCount + ConsoleColors.CYAN_BRIGHT + " " + item.getName() +
                        ConsoleColors. BLUE + " in stock already." + ConsoleColors.RESET);
            }
            else if (newCount < 0) {
                changeCount = false;
                System.out.println(ConsoleColors. BLUE + "Invalid new count." + ConsoleColors.RESET);
            }
            if (changeName) {
                System.out.println("Item " + ConsoleColors.CYAN_BRIGHT + item.getName() + ConsoleColors.RESET
                        + " renamed \"" + ConsoleColors.CYAN_BRIGHT + newName + ConsoleColors.RESET + "\"." );
                Database.getInstance().setName(item, newName);
            }
            if (changeCount) {
                Database.getInstance().setCount(item, newCount);
                System.out.println(ConsoleColors.CYAN_BRIGHT + newCount + " " + item.getName() + ConsoleColors.RESET +
                        " currently in stock.");
            }
        }
    }

    private void editSPBPCount(Matcher matcher) {
        if (matcher.find()) {
            int ID = Integer.parseInt(matcher.group(1));
            int newSellPrice = Integer.parseInt(matcher.group(2));
            int newBuyPrice = Integer.parseInt(matcher.group(3));
            int newCount = Integer.parseInt(matcher.group(4));
            boolean changeSellPrice = true;
            boolean changeBuyPrice = true;
            boolean changeCount = true;
            if (Item.findItem(ID) == null) {
                System.out.println(ConsoleColors.BLUE + "Item does not exist. Nothing was changed." + ConsoleColors.RESET);
                return;
            }
            Item item = Item.findItem(ID);
            assert item != null;
            if (newCount == item.getInStock()) {
                changeCount = false;
                System.out.println(ConsoleColors.BLUE + newCount + ConsoleColors.CYAN_BRIGHT + " " + item.getName() +
                        ConsoleColors. BLUE + " in stock already." + ConsoleColors.RESET);
            }
            else if (newCount < 0) {
                changeCount = false;
                System.out.println(ConsoleColors. BLUE + "Invalid new count." + ConsoleColors.RESET);
            }
            if (newSellPrice == item.getSellingPrice()) {
                changeSellPrice = false;
                System.out.println(ConsoleColors.BLUE + "Sell Price of" + ConsoleColors.CYAN_BRIGHT + " " + item.getName() +
                        ConsoleColors. BLUE + " is already " + item.getSellingPrice() + "." + ConsoleColors.RESET);
            }
            else if (newSellPrice < 0) {
                changeSellPrice = false;
                System.out.println(ConsoleColors. BLUE + "Invalid new sell price." + ConsoleColors.RESET);
            }
            if (newBuyPrice == item.getBuyingPrice()) {
                changeBuyPrice = false;
                System.out.println(ConsoleColors.BLUE + "Buy Price of" + ConsoleColors.CYAN_BRIGHT + " " + item.getName() +
                        ConsoleColors. BLUE + " is already " + item.getBuyingPrice() + "." + ConsoleColors.RESET);
            }
            else if (newSellPrice < 0) {
                changeBuyPrice = false;
                System.out.println(ConsoleColors. BLUE + "Invalid new buy price." + ConsoleColors.RESET);
            }
            if (newSellPrice < newBuyPrice) {
                changeSellPrice = false;
                changeBuyPrice = false;
                System.out.println(ConsoleColors.RED +
                        "Invalid values. (New selling price cannot be less than new buying price.)" + ConsoleColors.RESET);
            }
            if (changeCount) {
                Database.getInstance().setCount(item, newCount);
                System.out.println(ConsoleColors.CYAN_BRIGHT + newCount + " " + item.getName() + ConsoleColors.RESET +
                        " currently in stock.");
            }
            if (changeSellPrice) {
                Database.getInstance().setSellingPrice(item, newSellPrice);
                System.out.println(item.getName() + " is now being sold for " + ConsoleColors.CYAN_BRIGHT + newSellPrice
                + ConsoleColors.RESET + ".");
            }
            if (changeBuyPrice) {
                Database.getInstance().setBuyingPrice(item, newBuyPrice);
                System.out.println(item.getName() + " is now being bought for " + ConsoleColors.CYAN_BRIGHT + newBuyPrice
                + ConsoleColors.RESET + ".");
            }
        }
    }

    private void checkout(Matcher matcher) {
        if (matcher.find()) {
            int orderID = Integer.parseInt(matcher.group(1));
            if (Order.findOrder(orderID) == null) {
                System.out.println("Order with this ID does not exit.");
                return;
            }
            Order order = Order.findOrder(orderID);
            Item item;
            assert order != null;
            if (Item.findItem(order.getItemID()) == null)
                item = Item.findDeletedItem(order.getItemID());
            else item = Item.findItem(order.getItemID());
            assert item != null;
            Item.updateSales(item, order.getNumber(), item.getSellingPrice(), item.getBuyingPrice());
            Database.getInstance().addOrderHistory(order);
            Database.getInstance().removeOrder(order);
            System.out.println("Order checked out.");
        }
    }

    private void newOrders(Matcher matcher) {
        //Order.printOrders();
    }

    private void history(Matcher matcher) {
        //Order.printHistory();
    }

    private void calculateProfit(Matcher matcher) {
        if (matcher.find()) {
            int profit = 0;
            for (Item item : Database.getInstance().getItems())
                profit += item.getItemProfit();
            for (Item item : Database.getInstance().getDeletedItems())
                profit += item.getItemProfit();
            System.out.println("Total profit: " + ConsoleColors.CYAN_BRIGHT + profit + ConsoleColors.RESET);
        }
    }

    private void calculateItemProfit(Matcher matcher) {
        if(matcher.find()) {
            int itemID = Integer.parseInt(matcher.group(1));
            if (Item.findItem(itemID) == null && Item.findDeletedItem(itemID) == null){
                System.out.println("No item associated with this ID.");
                return;
            }
            if (Item.findItem(itemID) == null) {
                System.out.println("Profit from " + ConsoleColors.CYAN_BRIGHT + Objects.requireNonNull(Item.findDeletedItem(itemID)).getName() +
                       ConsoleColors.RESET + ": " + Objects.requireNonNull(Item.findDeletedItem(itemID)).getItemProfit());
            }
            else System.out.println("Profit from " + ConsoleColors.CYAN_BRIGHT + Objects.requireNonNull(Item.findItem(itemID)).getName() +
                    ConsoleColors.RESET + ": " + Objects.requireNonNull(Item.findItem(itemID)).getItemProfit());
        }
    }

    private void sales(Matcher matcher) {
        if (matcher.find()) {
            for (Item item : Database.getInstance().getItems()) {
                System.out.println(ConsoleColors.CYAN_UNDERLINED + item.getName() +
                        " Sales:" + ConsoleColors.RESET + "\nOrders: " + ConsoleColors.CYAN_BRIGHT + item.getOrdersIn() + ConsoleColors.RESET +
                        "\tNumber Sold: " + ConsoleColors.CYAN_BRIGHT + item.getNumberSold() + ConsoleColors.RESET +
                        "\nMoney Received: " + ConsoleColors.CYAN_BRIGHT + item.getMoneyMadeFrom() + ConsoleColors.RESET +
                        "\tProfit: " + ConsoleColors.CYAN_BRIGHT + item.getItemProfit() + ConsoleColors.RESET);
            }
            for (Item item : Database.getInstance().getDeletedItems()) {
                System.out.println(ConsoleColors.CYAN_UNDERLINED + item.getName() +
                        " Sales:" + ConsoleColors.RESET + "\nOrders: " + ConsoleColors.CYAN_BRIGHT + item.getOrdersIn() + ConsoleColors.RESET +
                        "\tNumber Sold: " + ConsoleColors.CYAN_BRIGHT + item.getNumberSold() + ConsoleColors.RESET +
                        "\nMoney Received: " + ConsoleColors.CYAN_BRIGHT + item.getMoneyMadeFrom() + ConsoleColors.RESET +
                        "\tProfit: " + ConsoleColors.CYAN_BRIGHT + item.getItemProfit() + ConsoleColors.RESET);
            }
        }
    }

    private void itemSales(Matcher matcher) {
        if (matcher.find()) {
            int itemID = Integer.parseInt(matcher.group(1));
            if (Item.findItem(itemID) == null && Item.findDeletedItem(itemID) == null){
                System.out.println("No item associated with this ID.");
                return;
            }
            Item item;
            if (Item.findItem(itemID) == null)
                item = Item.findDeletedItem(itemID);
            else item = Item.findItem(itemID);
            assert item != null;
            System.out.println(ConsoleColors.CYAN_UNDERLINED + item.getName() +
                    " Sales:" + ConsoleColors.RESET + "\nOrders: " + ConsoleColors.CYAN_BRIGHT + item.getOrdersIn() + ConsoleColors.RESET +
                    "\tNumber Sold: " + ConsoleColors.CYAN_BRIGHT + item.getNumberSold() + ConsoleColors.RESET +
                    "\nMoney Received: " + ConsoleColors.CYAN_BRIGHT + item.getMoneyMadeFrom() + ConsoleColors.RESET +
                    "\tProfit: " + ConsoleColors.CYAN_BRIGHT + item.getItemProfit() + ConsoleColors.RESET);
        }
    }
}