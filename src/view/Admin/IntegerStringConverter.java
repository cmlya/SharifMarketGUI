package view.Admin;

import javafx.util.StringConverter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IntegerStringConverter extends StringConverter<Integer> {
    int n;
    public IntegerStringConverter(int n) { this.n = n; }

    public Integer fromString(String var1) {
        if (var1 == null) return n;
        else {
            var1 = var1.trim();
            Pattern pattern = Pattern.compile("^\\d+$");
            Matcher matcher = pattern.matcher(var1);
            if (matcher.find())
                return var1.length() < 1 ? null : Integer.valueOf(var1);
            else return n;
        }
    }

    public String toString(Integer var1) { return var1 == null ? "" : Integer.toString(var1); }
}
