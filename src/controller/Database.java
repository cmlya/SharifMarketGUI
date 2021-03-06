package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Database {
    private final ArrayList<Customer> customers = new ArrayList<>();
    private final ArrayList<Item> items = new ArrayList<>();
    private final ArrayList<Item> deletedItems = new ArrayList<>();
    private final ArrayList<Order> orders = new ArrayList<>();
    private final ArrayList<Order> orderHistory = new ArrayList<>();
    private Customer currentCustomer;

    static Database database;

    public static Database getInstance() {
        if(database == null) read();
        return database;
    }

    private Database() { }

    private static void read(){
        Gson gson = new Gson();
        try {
            FileReader fileReader = new FileReader("database.json");
            database = gson.fromJson(fileReader, Database.class);
        }
        catch (FileNotFoundException e) { e.printStackTrace(); }
    }

    public static void write(){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter("database.json")){
            gson.toJson(database, writer);
            writer.flush();
        }
        catch (IOException e) { e.printStackTrace(); }
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
        write();
    }
    public void addItem(Item item) {
        items.add(item);
        write();
    }
    public void setCurrentCustomer(Customer currentCustomer) {
        this.currentCustomer = currentCustomer;
        write();
    }
    public void removeItem(Item item) {
        items.remove(item);
        write();
    }
    public void addDeletedItem(Item item) {
        deletedItems.add(item);
        write();
    }
    public void setCount(Item item, int newCount) {
        item.setInStock(newCount);
        write();
    }
    public void setWallet(Customer customer, long newWallet) {
        customer.setWallet(newWallet);
        write();
    }
    public void addOrder(Order order) {
        orders.add(order);
        write();
    }
    public void removeOrder(Order order) {
        orders.remove(order);
        write();
    }
    public void addOrderHistory(Order order) { orderHistory.add(order); }
    public Customer getCurrentCustomer() { return currentCustomer; }
    public ArrayList<Item> getItems() { return items; }
    public ArrayList<Item> getDeletedItems() { return deletedItems; }
    public ArrayList<Order> getOrders() { return orders; }
    public ArrayList<Customer> getCustomers() { return customers; }
    public ArrayList<Order> getOrderHistory() { return orderHistory; }
}
