package controller;

import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;
import static controller.Utils.date;
import static controller.Utils.randomCode;

public class UserInputProcessor {
    Scanner scanner = new Scanner(System.in);

    public void run(){
        boolean exit = false;
        while (!exit) {
            System.out.print("Enter command: ");
            exit = runCommand(scanner.nextLine());
        }
    }

    private boolean runCommand(String input) {
        if (input.equals("exit")) {
            Database.getInstance().setCurrentCustomer(null);
            Database.write();
            return true;
        }
        Database.write();
        return false;
    }

//    private void login() {
//            int ID = 0; //TODO
//            if (Customer.findCustomer(ID) == null) {
//                System.out.println("Customer with this username does not exist"); // TODO offer signup
//                String password = scanner.nextLine();
//                System.out.println("Confirm password: ");
//                String testString = scanner.nextLine();
//                while (!password.equals(testString)) {
//                    System.out.println("Passwords do not match. Choose a password: ");
//                    password = scanner.nextLine();
//                    System.out.println("Confirm password: ");
//                    testString = scanner.nextLine();
//                }
//                new Customer(ID, password);
//                System.out.println("Account created successfully!");
//            }
//            else {
//                String password = Objects.requireNonNull(Customer.findCustomer(ID)).getPassword();
//                System.out.println("Enter password: ");
//                String attempt = scanner.nextLine();
//                int limit = 3;
//                while (limit > 0 && !attempt.equals(password)) {
//                    System.out.println(ConsoleColors.RED_BACKGROUND + "Incorrect password. Try again." +
//                            ConsoleColors.RESET);
//                    attempt = scanner.nextLine();
//                    limit--;
//                    if (limit == 1) {
//                        System.out.println("Out of attempts. You have been restricted.");
//                        return;
//                    }
//                }
//                System.out.println("Welcome back.");
//            }
//            Database.getInstance().setCurrentCustomer(Customer.findCustomer(ID));
//    }

    private void order(Matcher matcher) {
        if (matcher.find()) {
            int itemID = Integer.parseInt(matcher.group(1));
            int count = Integer.parseInt(matcher.group(2));
            int orderID = randomCode();
            String itemName = Objects.requireNonNull(Item.findItem(itemID)).getName();
            new Order(Database.getInstance().getCurrentCustomer().getUsername(), date(), itemID, count, orderID, itemName);
            Database.getInstance().setCount(Objects.requireNonNull(Item.findItem(itemID)), Objects.requireNonNull(Item.findItem(itemID)).getInStock() - count);
        }
    }

}
