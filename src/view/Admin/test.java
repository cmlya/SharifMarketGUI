package view.Admin;

public class test {
    public static void main(String[] args) {
        String s = "25425o8";
        if (s.matches(".*[a-z].*")) System.out.println("Valid");
        else System.out.println("Invalid");
    }
}
