package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ConnectDB {
	static private Connection connection = null;
	static private Statement statement = null;
	
	private static String tableName = "cloudTable";
	
	// for the video class
	private static String videoName = "name";
	private static String uploaduser = "user";
	private static long videoTimestamp = System.currentTimeMillis();
	private long conversationId = 1l;
	private String url;
	
	static String unicode = "?useUnicode=yes&characterEncoding=UTF-8";
	
	public ConnectDB() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException{
			
//		String url = "jdbc:mysql://mydb.co9r6pzgikbx.us-east-1.rds.amazonaws.com:3306/employees?user=awsuser&password=mypassword";
	
		String url = "jdbc:mysql://mydbinstance.co9r6pzgikbx.us-east-1.rds.amazonaws.com:3306/";

		String dbName = "mydb";
		String username = "awsuser";
		String password = "mypassword"; 

		String driver = "com.mysql.jdbc.Driver";

		Class.forName(driver).newInstance();
		connection =  DriverManager.getConnection(url + dbName, username, password);
		//Statement st = connection.createStatement();
		
	}	
	
	public void createTable() throws SQLException	{
		statement.execute("DROP TABLE IF EXISTS " + tableName);
		
		System.out.println("Create a new table: " + tableName);
		
		// I chose int for JAVA's (long)!!!
		statement.execute("create table " + tableName + " (" + videoName + " varchar(32), " + conversationId
			+ " int, " + videoTimestamp + " int, url varchar(100), "+ uploaduser + " varchar(32))");
		
		System.out.println("Table created successfully");
	}
	
	public void insert() throws Exception{
		DateFormat df2=new SimpleDateFormat("yyyy-MM-dd$hh:mm:ss");
		statement.executeUpdate("insert into cloudTable values ('" + "ke_325543.mp4" + "'," + 14 +
				"," + 2 + ", '" + df2.format(new Date()) + "', 'keliao')");
		System.out.println("Insert created successfully");
	}
	
	public void query(ArrayList<String> a) throws Exception{
		//ArrayList<String> a =new ArrayList<String>();
		ResultSet rel = statement.executeQuery("select name, count, rating from cloudTable order by rating desc");
		while(rel.next()) {
			//System.out.println(rel.getString("name"));
			a.add(rel.getString("count"));
		}
		for(String s:a){
			System.out.println(s);
		}
		rel.close();
		statement.close();
		connection.close();
	}
	
	public void clearTable() throws Exception{
		
		statement.execute("TRUNCATE TABLE "+tableName);
			System.out.println("t created successfully");
	}
	
	public Video getVideo(String url){
		return null;
	}
	
	public boolean addVideo(Video v){
		return false;
	}
	
	public Conversation getConversation(long conversationId){
		return null;
	}
	
	public ArrayList<Conversation> getAllConversation(){
		return null;
	}
	
	public static void main(String[] args) throws Exception{
		
		System.out.println("fuck");
		ConnectDB db = new ConnectDB();
	}
}

