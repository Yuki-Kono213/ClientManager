package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**https://dev.mysql.com/
 * ユーザデータベースへのアクセステストクラス。
 */
public class UserDB {
	
	/**
	 * テーブル名。
	 */
	private static final String TABLE_NAME = "CLIENT";

	/**
	 * テスト処理を実行します。
	 * @param args
	 */
	public void UseClientDataBase (String[] args) {
		
		try{
			// オブジェクトを生成
			create();
			
			// データ操作
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

	private static int client_ID;
	
	public int GetClient_ID() throws SQLException 
	{
		ResultSet resultSetID = null;
		try {
			create();
			String SQLID = "SELECT ID FROM " + TABLE_NAME +" LAST_INSERT_ID";
			resultSetID = _statement.executeQuery(SQLID);
			resultSetID.last();
			System.out.println(resultSetID);
			client_ID = resultSetID.getInt("ID");
		}
		catch(Exception ex)
		{
			
		}finally{
			resultSetID.close();
			close();
		}
		return client_ID;
		
	}
	public int GetClient_ID(String name) throws SQLException 
	{
		ResultSet resultSetID = null;
		try {
			create();
			String SQLID = "SELECT ID FROM " + TABLE_NAME +" WHERE NAME = '"+name+"'";
			resultSetID = _statement.executeQuery(SQLID);
			resultSetID.first();
			System.out.println(resultSetID);
			client_ID = resultSetID.getInt("ID");
		}
		catch(Exception ex)
		{
			
		}finally{
			resultSetID.close();
			close();
		}
		return client_ID;
		
	}
	/**
	 * 構築します。
	 */
	public UserDB() {
		_connection = null;
		_statement = null;
	}
	
	
	public ObservableList<String> ReturnUserName(String name) throws ClassNotFoundException, SQLException 
	{
		ObservableList<String> items =FXCollections.observableArrayList();
		ResultSet resultSet = null;
		try {
			create();
			String SQL;
			if(name != "") {
				SQL = "SELECT * FROM " + TABLE_NAME + " WHERE NAME = '"+name+"'";
			}
			else 
			{
				SQL = "SELECT * FROM " + TABLE_NAME;
			}
			resultSet = _statement.executeQuery(SQL);
			boolean br = resultSet.first();
			if(br == false) {
				return items;
			}
			do{
				String addname = resultSet.getString("NAME") ;
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
	 * オブジェクトを生成します。
	 */
	public void create()
		throws ClassNotFoundException, SQLException{
		// 下準備
		Class.forName("org.h2.Driver");
		_connection = DriverManager.getConnection("jdbc:h2:./Client", "sa", "maru9685");
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

	public boolean AlreadyExistCheck(String name) throws SQLException, ClassNotFoundException 
	{
		create();
		String SQL = "SELECT * FROM CLIENT WHERE NAME = '"+name+"' LIMIT 1";
		ResultSet resultSet = _statement.executeQuery(SQL);
		boolean exist = false;
		try{
			exist = resultSet.first();
			System.out.println(exist);
			if(exist == false) {
				System.out.println("存在しません");
			}
			else 
			{
				String SQLPlus = "UPDATE  "+ TABLE_NAME +" SET REPEAT = REPEAT + 1 WHERE NAME = '"+name+"'";
				_statement.executeUpdate(SQLPlus);
				System.out.println("存在します");
				String SQLID = "SELECT ID FROM " + TABLE_NAME +" WHERE NAME = '"+name+"'";
				ResultSet resultSetID = _statement.executeQuery(SQLID);
				try {
					resultSetID.first();
					System.out.println(resultSetID);
					client_ID = resultSetID.getInt("ID");
				}
				catch(Exception ex)
				{

				      System.out.println(ex);
				}finally{
					resultSetID.close();
				}

			}
			
		}
		catch(Exception e) 
		{
			System.out.println(e);
		
		}finally{
			resultSet.close();
		}
		return exist;
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
			executeInsert(args[1], Integer.parseInt(args[2]), args[3], Integer.parseInt(args[4]));
		}else if("update".equals(command)) {
			executeUpdate(args[1], args[2], args[3]);
		}else if("delete".equals(command)) {
			executeDelete(args[1]);
		}
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
	private void executeInsert(String name, Integer age, String memo, Integer repeat)
		throws SQLException{
		// SQL文を発行
		int updateCount = _statement.executeUpdate("INSERT INTO " + TABLE_NAME + " (NAME,AGE,MEMO,REPEAT) VALUES ('"+name+"','"+age+"','"+memo+"','"+repeat+"')");
		System.out.println("Insert: " + updateCount);
	}
	
	/**
	 * UPDATE処理を実行します。
	 * @param id
	 * @param name
	 * @param password
	 */
	private void executeUpdate(String id, String name, String password)
		throws SQLException{
		// SQL文を発行
		int updateCount = _statement.executeUpdate("UPDATE " + TABLE_NAME + " SET NAME='"+name+"', PASSWORD='"+password+"' WHERE ID='" + id + "'");
		System.out.println("Update: " + updateCount);
	}
	
	/**
	 * DELETE処理を実行します。
	 * @param id
	 */
	private void executeDelete(String id)
		throws SQLException{
		// SQL文を発行
		int updateCount = _statement.executeUpdate("DELETE FROM " + TABLE_NAME + " WHERE ID='" + id + "'");
		System.out.println("Delete: " + updateCount);
	}
	
}
