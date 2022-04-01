package application;

import java.text.DateFormat;

public class DateChecker {
    public static boolean checkDate(String strDate) {
        if (strDate == null || strDate.length() != 10) {
            return false;
        }
        DateFormat format = DateFormat.getDateInstance();
        format.setLenient(false);
        try {
            format.parse(strDate);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
