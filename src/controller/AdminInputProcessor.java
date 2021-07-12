package controller;

import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;

public class AdminInputProcessor {
    Scanner scanner = new Scanner(System.in);

    public void run() {
        boolean exit = false;
        while (!exit) {
            System.out.print("Enter command: ");
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

    private void editName(Matcher matcher) {
        if (matcher.find()) {
            int ID = Integer.parseInt(matcher.group(1));
            String newName = matcher.group(2);
            if (Item.findItem(ID) == null)
                System.out.println("Item does not exist. Nothing was changed.");
            else if (newName.equals(Objects.requireNonNull(Item.findItem(ID)).getName()))
                System.out.println("This item already has this name. Nothing was changed.");
            else {
                System.out.println("Item " + Objects.requireNonNull(Item.findItem(ID)).getName()
                + " renamed \"" + newName + "\"." );
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
                System.out.println("Item does not exist. Nothing was changed.");
                return;
            }
            Item item = Item.findItem(ID);
            assert item != null;
            if (newName.equals(item.getName())) {
                changeName = false;
                System.out.println("This item already has this name.");
            }
            if (newCount == item.getInStock()) {
                changeCount = false;
                System.out.println(newCount + " " + item.getName() + " in stock already.");
            }
            else if (newCount < 0) {
                changeCount = false;
                System.out.println("Invalid new count.");
            }
            if (changeName) {
                System.out.println("Item " + item.getName() + " renamed \"" + newName + "\"." );
                Database.getInstance().setName(item, newName);
            }
            if (changeCount) {
                Database.getInstance().setCount(item, newCount);
                System.out.println(newCount + " " + item.getName() + " currently in stock.");
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
                System.out.println("Item does not exist. Nothing was changed.");
                return;
            }
            Item item = Item.findItem(ID);
            assert item != null;
            if (newCount == item.getInStock()) {
                changeCount = false;
                System.out.println(newCount + " " + item.getName() + " in stock already.");
            }
            else if (newCount < 0) {
                changeCount = false;
                System.out.println("Invalid new count.");
            }
            if (newSellPrice == item.getSellingPrice()) {
                changeSellPrice = false;
                System.out.println("Sell Price of " + item.getName() + " is already " + item.getSellingPrice() + ".");
            }
            else if (newSellPrice < 0) {
                changeSellPrice = false;
                System.out.println("Invalid new sell price.");
            }
            if (newBuyPrice == item.getBuyingPrice()) {
                changeBuyPrice = false;
                System.out.println("Buy Price of " + item.getName() + " is already " + item.getBuyingPrice() + ".");
            }
            else if (newSellPrice < 0) {
                changeBuyPrice = false;
                System.out.println("Invalid new buy price.");
            }
            if (newSellPrice < newBuyPrice) {
                changeSellPrice = false;
                changeBuyPrice = false;
                System.out.println("Invalid values. (New selling price cannot be less than new buying price.)");
            }
            if (changeCount) {
                Database.getInstance().setCount(item, newCount);
                System.out.println(newCount + " " + item.getName() + " currently in stock.");
            }
            if (changeSellPrice) {
                Database.getInstance().setSellingPrice(item, newSellPrice);
                System.out.println(item.getName() + " is now being sold for " + newSellPrice + ".");
            }
            if (changeBuyPrice) {
                Database.getInstance().setBuyingPrice(item, newBuyPrice);
                System.out.println(item.getName() + " is now being bought for " + newBuyPrice + ".");
            }
        }
    }

}