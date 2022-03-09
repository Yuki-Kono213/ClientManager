package application;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ClientWindow extends Application {
	@Override
	public void start(Stage primaryStage) throws ClassNotFoundException {
		try {
			GridPane grid = new GridPane();
			grid.setAlignment(Pos.CENTER);
			grid.setHgap(10);
			grid.setVgap(10);
			grid.setPadding(new Insets(25, 25, 25, 25));

			grid.add(new Label("名前"), 0, 1);
			TextField nameTextField = new TextField();
			grid.add(nameTextField, 1, 1);

			List<Label> itemList= new ArrayList<Label>();
			for(int i =0; i < 8; i++) {
				
				itemList.add(new Label());
				grid.add(itemList.get(i), 2, i+3);
			}
			ListView<String> clientList = new ListView<>();
			ObservableList<String> items = FXCollections.observableArrayList(new UserDB().ReturnUserName(""));
			clientList.setItems(items);
			nameTextField.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					ObservableList<String> items = null;
					try {
						items = FXCollections.observableArrayList(new UserDB().ReturnUserName(nameTextField.getText()));
					} catch (ClassNotFoundException e1) {
						// TODO 自動生成された catch ブロック
						e1.printStackTrace();
					} catch (SQLException e1) {
						// TODO 自動生成された catch ブロック
						e1.printStackTrace();
					}
					clientList.setItems(items);
					clientList.refresh();
				}
			});

			ListView<String> paymentList = new ListView<>();
			
			clientList.getSelectionModel().selectedItemProperty()
					.addListener((ObservableValue<? extends String> ov, String old_val, String new_val) -> {
						int paymentClient_ID;
						try {
							paymentClient_ID = new UserDB().GetClient_ID(new_val);
							ObservableList<String> payItems = FXCollections
									.observableArrayList(new PaymentDB().ReturnPaymentList(paymentClient_ID));
							paymentList.setItems(payItems);
						} catch (ClassNotFoundException e) {
							// TODO 自動生成された catch ブロック
							e.printStackTrace();
						} catch (SQLException e) {
							// TODO 自動生成された catch ブロック
							e.printStackTrace();
						}
					});

			paymentList.getSelectionModel().selectedItemProperty()
			.addListener((ObservableValue<? extends String> ov, String old_val, String new_val) -> {
				Date payment_Time;
				try 
				{
					if(new_val != null) {
						SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
	
						payment_Time = formatter.parse(new_val);
					      int payment_ID = new PaymentDB().ReturnPaymentID(payment_Time.getTime());
						ObservableList<String> payItems = FXCollections
								.observableArrayList(new Payment_ItemDB().ReturnPaymentItemList( payment_ID));

						for(int i = 0; i < itemList.size(); i++) 
						{
							itemList.get(i).setText("");
							if(i < payItems.size()) {
								itemList.get(i).setText(payItems.get(i));
							}
							else if(i == payItems.size()) 
							{
								itemList.get(i).setText(new PaymentDB().ReturnPaymentPrice(payment_ID));
							}
						}
					}

				} catch (Exception e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}
			});
			grid.add(clientList, 1, 2);
			grid.add(paymentList, 2, 2);

			Scene scene = new Scene(grid, 900, 500);
			primaryStage.setScene(scene);
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch (Exception ex) {

		}
	}

	public void actionPerformed(ActionEvent e) {
	}
}