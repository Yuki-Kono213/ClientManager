package application;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class InputManager {

	public String name;
	public List<String> nameList = new ArrayList<String>();
	public List<Integer> priceList = new ArrayList<Integer>();
	public List<Integer> amountList = new ArrayList<Integer>();
	public LocalDate departureDateTime;
	public LocalDate arrivalDateTime;
	public LocalDate issueDateTime;
	public Integer period;
	public Integer personCount;
	public String memo;
	
	public String CodeName;
	public String Code;
}
