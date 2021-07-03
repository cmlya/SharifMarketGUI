package controller;

import static controller.Database.write;

public class Customer {
    private final String username;
    private String password;
    private long wallet = 1000000; // initial money

    public Customer(String username, String password) {
        this.username = username;
        this.password = password;
        Database.getInstance().addCustomer(this);
    }

    public static Customer findCustomer(String username) {
        for (Customer customer : Database.getInstance().getCustomers())
            if (customer.getUsername().equals(username))
                return customer;
        return null;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public void setPassword(String password) {
        this.password = password;
        write();
    }

    public long getWallet() { return wallet; }
    public void setWallet(long wallet) { this.wallet = wallet; }
}
