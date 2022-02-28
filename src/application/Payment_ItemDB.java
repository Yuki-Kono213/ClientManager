
	package application;

	import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
public class Payment_ItemDB {



		
		/**
		 * テーブル名。
		 */
		private static final String TABLE_NAME = "PAYMENT_ITEM";
		

		/**
		 * テスト処理を実行します。
		 * @param args
		 */
		public void UsePaymentItemDataBase(String[] args) {
			
			try{
				// オブジェクトを生成
				create();
				execute(args);
				
			}catch(Throwable t) {
				t.printStackTrace();
			}finally{
				// オブジェクトを破棄
				close();
			}
		}
		
		/**
		 * Connectionオブジェクトを保持します。
		 */
		private Connection _connection;
		
		/**
		 * Statementオブジェクトを保持します。
		 */
		private Statement _statement;
		/**
		 * 構築します。
		 */
		public Payment_ItemDB() {
			_connection = null;
			_statement = null;
		}
		
		
		
		/**
		 * オブジェクトを生成します。
		 */
		public void create()
			throws ClassNotFoundException, SQLException{
			// 下準備
			Class.forName("org.h2.Driver");
			_connection = DriverManager.getConnection("jdbc:h2:~/Client", "sa", "maru9685");
			_statement = _connection.createStatement();
		}
		
		/**
		 * 各種オブジェクトを閉じます。
		 */
		public void close() {
			if(_statement != null) {
				try{
					_statement.close();
				}catch(SQLException e) {
					;
				}
				_statement = null;
			}
			if(_connection != null) {
				try{
					_connection.close();
				}catch(SQLException e) {
					;
				}
				_connection = null;
			}
		}
		
		/**
		 * 実行します。
		 * @param args
		 * @throws SQLException
		 */
		public void execute(String[] args)
			throws SQLException {
			String command = args[0];
			if("select".equals(command)) {
				executeSelect();
			}else if("insert".equals(command)) {
				executeInsert(args[1], Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]));
			}
		}
		
		public ObservableList<String> ReturnPaymentItemList(int payment_ID)
			throws SQLException{
			ObservableList<String> items = FXCollections.observableArrayList();
			ResultSet resultSet = null;
			try {
				create();
				resultSet = _statement.executeQuery("SELECT * FROM " + TABLE_NAME + " WHERE PAYMENT_ID = '"+payment_ID+"'");

				boolean br = resultSet.first();
				if(br == false) {
					return items;
				}
				do{
					String addname = resultSet.getString("NAME");
					items.add(addname);
				}while(resultSet.next());
			}
			catch(Exception ex){

				
			}
			finally {
				close();
				resultSet.close();
			}
		
			
			return items;
		}
		
		
		/**
		 * SELECT処理を実行します。
		 */
		private void executeSelect()
			throws SQLException{
			ResultSet resultSet = _statement.executeQuery("SELECT * FROM " + TABLE_NAME);
			try{
				boolean br = resultSet.first();
				if(br == false) {
					return;
				}
				do{
					String id = resultSet.getString("ID");
					String name = resultSet.getString("NAME");
					String password = resultSet.getString("PASSWORD");
					
					System.out.println("id: " + id + ", name: " + name + ", password: " + password);
				}while(resultSet.next());
			}finally{
				resultSet.close();
			}
		}
		
		/**
		 * INSERT処理を実行します。
		 * @param id
		 * @param name
		 * @param password
		 */
		private void executeInsert(String name, Integer amount, Integer price, Integer payment_id)
			throws SQLException{
			// SQL文を発行
			int updateCount = _statement.executeUpdate("INSERT INTO " + TABLE_NAME + " (NAME,AMOUNT,PRICE,PAYMENT_ID) VALUES ('"+name+"','"+amount+"','"+price+"','"+payment_id+"')");
			System.out.println("Insert: " + updateCount);
		}
		
		
	}


