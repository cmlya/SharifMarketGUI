package controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class Utils {

    public static String date() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        return dateTimeFormatter.format(LocalDateTime.now());
    }

    public static int randomCode() {
        while (true) {
            Random rnd = new Random();
            int random = rnd.nextInt(999999);
            if (Item.findItem(Integer.parseInt(String.format("%06d", random))) == null
            && Order.findOrder(Integer.parseInt(String.format("%06d", random))) == null
            && Item.findDeletedItem(Integer.parseInt(String.format("%06d", random))) == null)
                return Integer.parseInt(String.format("%06d", random));
        }
    }

}
