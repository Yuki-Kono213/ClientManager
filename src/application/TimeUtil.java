package application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class TimeUtil {
    public static LocalDateTime toLocalDateTime(String date, String format) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date formatDate = sdf.parse(date);
        return LocalDateTime.ofInstant(formatDate.toInstant(), ZoneId.systemDefault());
    }
}
