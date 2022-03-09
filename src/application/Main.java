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

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
				GridPane grid = new GridPane();
				grid.setAlignment(Pos.CENTER);
				grid.setHgap(10);
				grid.setVgap(10);
				grid.setPadding(new Insets(25, 25, 25, 25));

				grid.add(new Label("名前"),0,1);
				TextField nameTextField = new TextField();
				grid.add(nameTextField,1,1);
				
				Label codeLabel = new Label();
				grid.add(codeLabel,4,1);
				
				codeLabel.setText(new CodeCreater().CodeCreate(LocalDateTime.now()));
				
				
				grid.add(new Label("ご到着日"),0,2);
				grid.add(new Label("泊数"),1,2);
				grid.add(new Label("ご出発日"),2,2);
				grid.add(new Label("人数"),3,2);
				grid.add(new Label("発行日"),4,2);
				grid.add(new Label("備考"),5,2);
				
				DatePicker arrivalDatePicker = new DatePicker();
				grid.add(arrivalDatePicker,0,3);
	
				Label periodLabel = new Label();
				grid.add(periodLabel,1,3);
				
				DatePicker departureDatePicker = new DatePicker();

				grid.add(departureDatePicker,2,3);
	
				TextField personTextField = new TextField();
				grid.add(personTextField,3,3);
				
				DatePicker issueDatePicker = new DatePicker();
				grid.add(issueDatePicker,4,3);
				
				issueDatePicker.setValue(LocalDate.now());
	
				TextField memoTextField = new TextField();
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
				
				List<TextField> inputTextNameList= new ArrayList<TextField>();
				List<TextField> inputTextPriceList= new ArrayList<TextField>();
				List<TextField> inputTextAmountList= new ArrayList<TextField>();
				List<Label> totalMoneyList= new ArrayList<Label>();
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
				
				Button saveDirectoryButton = new Button("保存先変更");
				grid.add(saveDirectoryButton, 0, 14);

				Path filePath = Paths.get("./config.txt").toAbsolutePath();
				String text = Files.readString(filePath);
				grid.add(saveDirectoryLabel, 0, 12);
				Label saveDirectoryNameLabel = new Label(text);
				grid.add(saveDirectoryNameLabel, 0, 13, 3, 1);
				saveDirectoryButton.setOnAction(new EventHandler<ActionEvent>() {
					 
				    @Override
				    public void handle(ActionEvent e) {
					    DirectoryChooser fc = new DirectoryChooser();
					    fc.setTitle("フォルダ選択");
					    // 初期フォルダをホームに
					    fc.setInitialDirectory(new File(System.getProperty("user.home")));
					    File writeFile = fc.showDialog(null);
					    if (writeFile != null) {
					      System.out.println(text);
					      saveDirectoryNameLabel.setText(writeFile.getPath());
					      try{
					    	  File configFile = new File("./src/application/config.txt");
					    	  FileWriter filewriter = new FileWriter(configFile);
					    	  filewriter.write(writeFile.getPath());
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
					    	
			    		} 
			    		catch (Exception ex) {
			    			System.out.println(ex);
			    		}
	
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
				
				Button clientBtn = new Button("顧客データ表示");
				clientBtn.setOnAction(new EventHandler<ActionEvent>() {
				    @Override
				    public void handle(ActionEvent e) {
				    	ClientWindow cw = new ClientWindow();
				    	try {
							cw.start(new Stage());
						} catch (ClassNotFoundException e1) {
							// TODO 自動生成された catch ブロック
							e1.printStackTrace();
						}
				    }
				});
				Scene scene = new Scene(grid, 900, 500);
				primaryStage.setScene(scene);
				grid.add(submitBtn, 1, 14);
				grid.add(calcBtn, 2, 14);
				grid.add(clientBtn, 3, 14);
				primaryStage.setScene(scene);
				primaryStage.show();
			} catch(Exception e) {
				e.printStackTrace();
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
