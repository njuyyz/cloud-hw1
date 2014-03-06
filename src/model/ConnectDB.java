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
	private final static String tableName = "videoTable";
	
	private final static String videoName = "name";
	private final static String videoRating = "rating";
	private final static String videoNumOfRating = "count";
	private final static String videoTimestamp = "time";
	private final static String uploaduser = "user";
	
	public ConnectDB() throws Exception{
		String url = "jdbc:mysql://mydbinstance.co9r6pzgikbx.us-east-1.rds.amazonaws.com:3306/";
		String dbName = "mydbinstance";
		String username = "awsuser";
		String password = "mypassword"; 

		connection =  DriverManager.getConnection(url + dbName, username, password);
		statement =  connection.createStatement();
	}	
	
	
	public void createTable() throws SQLException	{
		statement.execute("DROP TABLE IF EXISTS " + tableName);
		
		System.out.println("Create a new table: " + tableName);
		
		statement.execute("create table " + tableName + " (" + videoName + " varchar(32), " + videoRating
			+ " float, " + videoNumOfRating + " int, time varchar(32), "+ uploaduser + " varchar(32))");
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
		
		ConnectDB db = new ConnectDB();
		db.createTable();
		
		new ConnectDB().insert();
		
		ArrayList<String> a = new ArrayList<String>();
		new ConnectDB().query(a) ;
		
		//new ControlRDS().deleteRecord("ke_976238.mp4");
		System.out.println(a.size());
	}
}

