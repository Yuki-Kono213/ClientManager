package application;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class PaymentEdit extends Application {

	List<TextField> inputTextNameList= new ArrayList<TextField>();
	List<TextField> inputTextPriceList= new ArrayList<TextField>();
	List<TextField> inputTextAmountList= new ArrayList<TextField>();
	List<Label> totalMoneyList= new ArrayList<Label>();
	TextField nameTextField = new TextField();
	Label codeLabel = new Label();
	DatePicker arrivalDatePicker = new DatePicker();

	Label periodLabel = new Label();
	
	Label errorLabel = new Label("エラー表示");
	TextField personTextField = new TextField();
	DatePicker departureDatePicker = new DatePicker();
	DatePicker issueDatePicker = new DatePicker();
	TextField memoTextField = new TextField();
	Label saveDirectoryNameLabel = new Label();
	Label saveDirectoryNameLabel2 = new Label();
	public String dateTime = "";
	public String name = "";
	public Integer ID;
	public void start(Stage primaryStage) {
		try {
				GridPane grid = new GridPane();
				grid.setAlignment(Pos.CENTER);
				grid.setHgap(10);
				grid.setVgap(10);
				grid.setPadding(new Insets(25, 25, 25, 25));

				grid.add(new Label("名前"),0,1);
				grid.add(nameTextField,1,1);
				nameTextField.setText(name);


				grid.add(errorLabel,2,1,2,1);
				
				grid.add(codeLabel,4,1);
				
				codeLabel.setText(new CodeCreater().CodeCreate(LocalDateTime.now()));
				
				
				grid.add(new Label("ご到着日"),0,2);
				grid.add(new Label("泊数"),1,2);
				grid.add(new Label("ご出発日"),2,2);
				grid.add(new Label("人数"),3,2);
				grid.add(new Label("発行日"),4,2);
				grid.add(new Label("備考"),5,2);
				
				grid.add(arrivalDatePicker,0,3);
	
				grid.add(periodLabel,1,3);
				

				grid.add(departureDatePicker,2,3);
	
				grid.add(personTextField,3,3); 
				
				
				grid.add(issueDatePicker,4,3);
				
				issueDatePicker.setValue(LocalDate.now());
	
				grid.add(memoTextField,5,3);

				arrivalDatePicker.setOnAction(new EventHandler<ActionEvent>() {
					 
				    @Override
				    public void handle(ActionEvent e) {
					    	CalcPeriod(periodLabel,  departureDatePicker, arrivalDatePicker);
				    }
				});
				departureDatePicker.setOnAction(new EventHandler<ActionEvent>() {
					 
				    @Override
				    public void handle(ActionEvent e) {
					    	CalcPeriod(periodLabel,  departureDatePicker, arrivalDatePicker);
				    }
				});
				
				for(int i =0; i < 8; i++) {
	
					inputTextNameList.add(new TextField());
					grid.add(inputTextNameList.get(i), 1, i + 4);
					grid.add(new Label("利用明細"),0,i + 4);
					inputTextAmountList.add(new TextField());
					grid.add(inputTextAmountList.get(i), 3, i+ 4);
					grid.add(new Label("数量"),2,i+ 4);
					inputTextPriceList.add(new TextField());
					grid.add(inputTextPriceList.get(i), 5, i+ 4);
					grid.add(new Label("金額"),4,i+ 4);
					totalMoneyList.add(new Label());
					totalMoneyList.get(i).setPrefWidth(150);
					grid.add(totalMoneyList.get(i),6,i+4);
				}
				Label saveDirectoryLabel = new Label("保存先");
				grid.add(saveDirectoryLabel, 0, 12);
				
				Button saveDirectoryButton = new Button("保存先変更");
				grid.add(saveDirectoryButton, 0, 14);

				Path filePath = Paths.get("config.txt").toAbsolutePath();
				List<String> textList = Files.readAllLines(filePath);
				Button saveDirectoryButton2 = new Button("保存先変更2");
				grid.add(saveDirectoryButton2, 3, 14);

				Label saveDirectoryLabel2 = new Label("保存先2");
				grid.add(saveDirectoryLabel2, 3, 12);

				saveDirectoryNameLabel = new Label(textList.get(0));
				saveDirectoryNameLabel2 = new Label(textList.get(1));
				
				grid.add(saveDirectoryNameLabel, 0, 13, 3, 1);
				grid.add(saveDirectoryNameLabel2, 3, 13, 3, 1);
				saveDirectoryButton.setOnAction(new EventHandler<ActionEvent>() {
					 
				    @Override
				    public void handle(ActionEvent e) {
					    DirectoryChooser fc = new DirectoryChooser();
					    fc.setTitle("フォルダ選択");
					    // 初期フォルダをホームに
					    fc.setInitialDirectory(new File(System.getProperty("user.home")));
					    File writeFile = fc.showDialog(null);
					    if (writeFile != null) {
					      saveDirectoryNameLabel.setText(writeFile.getPath());
					      try{
					    	  File configFile = new File("./config.txt");
					    	  FileWriter filewriter = new FileWriter(configFile);
					    	  textList.set(0, writeFile.getPath());
						      System.out.println(textList.get(0));
					    	  for(String text:textList) {
						    	  filewriter.write(text +"\n");
					    	  }
					    	  filewriter.close();
					    	}catch(IOException ex){
					    	  System.out.println(ex);
					    	}
					    }
				    }
				});
				saveDirectoryButton2.setOnAction(new EventHandler<ActionEvent>() {
					 
				    @Override
				    public void handle(ActionEvent e) {
					    DirectoryChooser fc = new DirectoryChooser();
					    fc.setTitle("フォルダ選択2");
					    // 初期フォルダをホームに
					    fc.setInitialDirectory(new File(System.getProperty("user.home")));
					    File writeFile = fc.showDialog(null);
					    if (writeFile != null) {
					      saveDirectoryNameLabel2.setText(writeFile.getPath());
					      try{
					    	  File configFile = new File("./config.txt");
					    	  FileWriter filewriter = new FileWriter(configFile);
					    	  textList.set(1, writeFile.getPath());
						      System.out.println(textList.get(1));
					    	  for(String text:textList) {
						    	  filewriter.write(text +"\n");
					    	  }
					    	  filewriter.close();
					    	}catch(IOException ex){
					    	  System.out.println(ex);
					    	}
					    }
				    }
				});
				Button submitBtn = new Button("入力反映");
				submitBtn.setOnAction(new EventHandler<ActionEvent>() {
					
				    @Override
				    public void handle(ActionEvent e) {
				    	SetItemDraw(0);
				    }
				});
				
				
				
				Button submitBtn2 = new Button("入力反映2");
				submitBtn2.setOnAction(new EventHandler<ActionEvent>() {
					 
				    @Override
				    public void handle(ActionEvent e) {

				    	SetItemDraw(1);
				    }
				});
	
	
				Button calcBtn = new Button("計算");
				calcBtn.setOnAction(new EventHandler<ActionEvent>() {
				    @Override
				    public void handle(ActionEvent e) {
				    	for(int i =0; i < inputTextNameList.size(); i++)
				    	{
				    		try {
				    			totalMoneyList.get(i).setText(Integer.toString((Integer.parseInt(inputTextPriceList.get(i).getText()) )));
			
				    		} 
				    		catch (NumberFormatException nfex) {
				    			totalMoneyList.get(i).setText("");
				    		}
				    	}
				    }
				});
				DrawPaymentData(dateTime);
				Scene scene = new Scene(grid, 900, 500);
				primaryStage.setScene(scene);
				grid.add(submitBtn, 1, 14);
				grid.add(submitBtn2, 4, 14);
				grid.add(calcBtn, 6, 1);
				primaryStage.setScene(scene);
				primaryStage.show();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	private void DrawPaymentData(String dateTime) 
	{
		Date payment_Time;
		try 
		{
			if(dateTime != null) {
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");

				payment_Time = formatter.parse(dateTime);
			      int payment_ID = new PaymentDB().ReturnPaymentID(payment_Time.getTime());
			      InputManager  payItems = new PaymentDB().ReturnPaymentBasicData(ID);
			      payItems = new Payment_ItemDB().ReturnPaymentInputManager(payment_ID, payItems);
			      

			  	if(payItems.personCount != 0) {
			  		personTextField.setText(payItems.personCount.toString());
			  	}
			  	if(payItems.departureDateTime != null) {
			  		departureDatePicker.setValue(payItems.departureDateTime); 
			  	}
			  	if(payItems.arrivalDateTime != null) {
			  		arrivalDatePicker.setValue(payItems.arrivalDateTime); 
			  	}
			  	
			  	for(int i =0; i < payItems.nameList.size(); i++) 
			  	{

			  		inputTextNameList.get(i).setText(payItems.nameList.get(i));
			  		inputTextPriceList.get(i).setText(Integer.toString(payItems.priceList.get(i)));
			  		inputTextAmountList.get(i).setText(Integer.toString(payItems.amountList.get(i)));
			  		
			  	}
			}

		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		
	}
	public void SetItemDraw(int index) {
		if(arrivalDatePicker.getValue() == null || departureDatePicker.getValue() == null ||issueDatePicker.getValue() == null) 
    	{

    		
			errorLabel.setText("日付の入力が不正です。");
			return;
    	}
    	else if(personTextField.getText() == "" || nameTextField.getText() == "") 
    	{

			errorLabel.setText("入力されていない欄が利用明細以外に存在します");
			return;
    	}
    	for(int i =0; i < inputTextNameList.size(); i++)
    	{
    		try {
    			totalMoneyList.get(i).setText(Integer.toString((Integer.parseInt(inputTextPriceList.get(i).getText()) * 
    					Integer.parseInt(inputTextAmountList.get(i).getText()))));

    		} 
    		catch (NumberFormatException nfex) {
    			if(inputTextAmountList.get(i).getText() == ""  && inputTextPriceList.get(i).getText() == ""  && inputTextNameList.get(i).getText() == "" ) 
    			{
    				totalMoneyList.get(i).setText("");
    			}
    			else 
    			{
    				errorLabel.setText("入力されていない欄が利用明細に存在します");
    				return;
    			}
    		}
    	}
    	
    	int total = 0;
    	
    	for(Label text : totalMoneyList) 
    	{
    		if(text.getText() != "")
    		{
    			total += Integer.parseInt(text.getText()) ;
    			
    		}
    	}
    	
    	
    	InputManager im = new InputManager();
    	ExcelWriter exw = new ExcelWriter();
		UserDB userDB = new UserDB();
    	for(int i =0; i < inputTextNameList.size(); i++)
    	{
    		try {
    			String name = inputTextNameList.get(i).getText();
    			int price = Integer.parseInt(inputTextPriceList.get(i).getText());
    			int amount = Integer.parseInt(inputTextAmountList.get(i).getText());
    			im.nameList.add(name);
    			im.priceList.add(price);
    			im.amountList.add(amount);
    					
    		} 
    		catch (NumberFormatException nfex) {
    			totalMoneyList.get(i).setText("");
    		}
    	}
		try {
			im.arrivalDateTime = arrivalDatePicker.getValue();
			im.departureDateTime = departureDatePicker.getValue();
			im.issueDateTime = issueDatePicker.getValue();
			im.name = nameTextField.getText();
			im.period = Integer.parseInt(periodLabel.getText());
			im.personCount = Integer.parseInt(personTextField.getText());
			im.memo = memoTextField.getText();
			im.Code = codeLabel.getText();
			im.CodeName = codeLabel.getText() + " " + im.name;
			if(index == 0) {
				im.Directory = saveDirectoryNameLabel.getText();
			}
			else 
			{
				im.Directory = saveDirectoryNameLabel2.getText();
			}
			System.out.println(im.Directory);
			
	    	exw.excelWrite(im);
	    	if(!userDB.AlreadyExistCheck(im.name)) 
	    	{
	    		userDB.UseClientDataBase(new String[] {"insert", im.name, "0","", "1"});
	    	}
	    	int id = userDB.GetClient_ID(im.name);
	    	
	    	PaymentDB pDB = new PaymentDB();
	    	
	    	pDB.UsePaymentDataBase(new String[] {"insert", Integer.toString(id) ,im.memo, Integer.toString(total), Long.toString(new Date().getTime())});
	    	
	    	Payment_ItemDB pIDB = new Payment_ItemDB();

	    	int pid = pDB.GetPaymentID(id);
	    	for(int i = 0; i < inputTextNameList.size(); i++) 
	    	{
	    		if(inputTextNameList.get(i).getText() != "" && inputTextPriceList.get(i).getText() != "" && inputTextAmountList.get(i).getText() != "" )
	    		{
	    			pIDB.UsePaymentItemDataBase(new String[] {"insert", 
	    					inputTextNameList.get(i).getText() ,
	    					inputTextAmountList.get(i).getText(), 
	    					inputTextPriceList.get(i).getText(), 
	    					Integer.toString(pid)});
	    		}
	    		
	    	}
			codeLabel.setText(new CodeCreater().CodeCreate(LocalDateTime.now()));
			errorLabel.setText("入力に成功しました。");
	    	
		} 
		catch (Exception ex) {
			System.out.println(ex);
		}

		
	}
	
	private void CalcPeriod(Label periodLabel, DatePicker departureDatePicker, DatePicker arrivalDatePicker) 
	{
    	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    	if(departureDatePicker.getValue() != null && arrivalDatePicker.getValue() != null) {
			try {
	
				Date departDate = simpleDateFormat.parse(departureDatePicker.getValue().toString());
		    	Date arrivalDate = simpleDateFormat.parse(arrivalDatePicker.getValue().toString());
		    	long departLong = departDate.getTime();
		    	long arrivalLong = arrivalDate.getTime();
		        long one_date_time = 1000 * 60 * 60 * 24;
		        periodLabel.setText(Long.toString((departLong - arrivalLong) / one_date_time)); 
			}
	        catch (ParseException e1) {
				e1.printStackTrace();
			}
    	}
		
	}
	
	public void CalcTotalMoney() {
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
