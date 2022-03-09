package application;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class CodeCreater 
{
	public String CodeCreate(LocalDateTime time) {
		
		String timeText = Integer.toString(time.getYear()).substring(2,4);

	      System.out.println(String.format("%02d", time.getMonthValue()));
		timeText += String.format("%02d", time.getMonthValue());
		timeText += String.format("%02d", time.getDayOfMonth());
		
		timeText += "-";
		
		try {
			timeText += String.format("%02d", new PaymentDB().CalcTodayCount(LocalDate.now()));
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		
		return timeText;
	}

}
