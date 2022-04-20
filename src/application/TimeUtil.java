package application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class TimeUtil {
    public static LocalDate toLocalDateTime(String date, String format) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date formatDate = sdf.parse(date);
        return LocalDate.ofInstant(formatDate.toInstant(), ZoneId.systemDefault());
    }
}
