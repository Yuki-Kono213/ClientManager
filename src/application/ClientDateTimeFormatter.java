package application;

	import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
	// ...
	public class ClientDateTimeFormatter{
	private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu-MM-dd")
	                .withResolverStyle(ResolverStyle.STRICT);
	public static boolean checkDate(String strDate) {
	        if (strDate == null || strDate.length() != 10) {
	                return false;
	        }
	        try {
	        	System.out.println(strDate);
	                LocalDate.parse(strDate, dtf);
	                return true;
	        } catch (DateTimeException e) {
	                return false;
	        }
	}
	
}

