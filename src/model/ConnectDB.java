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
	private static String videoName = "videoName";
	private static String username = "username";
	private static String videoTimestamp = "videoTimestamp";
	private static String conversationId = "conversationId";
	private String url = "url";

	public ConnectDB() throws SQLException, InstantiationException,
			IllegalAccessException, ClassNotFoundException {

		String url_connection = "jdbc:mysql://mydbinstance.co9r6pzgikbx.us-east-1.rds.amazonaws.com:3306/";

		String dbName = "mydb";
		String user = "awsuser";
		String password = "mypassword";

		String driver = "com.mysql.jdbc.Driver";

		Class.forName(driver).newInstance();
		connection = DriverManager.getConnection(url_connection + dbName, user,
				password);
		statement = connection.createStatement();
		System.out.println(connection.getCatalog());

	}

	public void createTable() throws SQLException {

		statement.execute("DROP TABLE IF EXISTS " + tableName);
		System.out.println("Create a new table: " + tableName);

		// // I chose int for JAVA's (long)!!!
		statement
				.execute("create table "
						+ tableName
						+ " (videoName varchar(32), conversationId bigint, videoTimestamp bigint, url varchar(100), username varchar(32))");
		System.out.println("Table created successfully");

	}

	public void insert() throws Exception {

		statement.executeUpdate("insert into cloudTable values ('" + "test.mp4"
				+ "', " + 1 + ", " + System.currentTimeMillis() + ", '"
				+ "http://22222" + "', 'Ben')");

		statement.executeUpdate("insert into cloudTable values ('" + "test.mp4"
				+ "', " + 2 + ", " + System.currentTimeMillis() + ", '"
				+ "http://22222" + "', 'Ryan')");

		System.out.println("Insert created successfully");
	}

	public void query(ArrayList<String> a) throws Exception {

		ResultSet rs = statement
				.executeQuery("select username, videoName, videoTimestamp, conversationId, url from cloudTable order by videoTimestamp desc");

		while (rs.next()) {
			// System.out.println(rs.getString(username));
			a.add(rs.getString(username));
		}

		for (String s : a) {
			System.out.println(s);
		}

		// close all the stuff
		rs.close();
		statement.close();
		connection.close();
	}

	public void clearTable() throws Exception {

		statement.execute("TRUNCATE TABLE " + tableName);
		System.out.println("t created successfully");

	}



	public Video getVideo(String URL) throws SQLException {
		Video v = new Video();

		System.out.println("***********");
		
		ResultSet rs = statement
				.executeQuery("select username, videoName, videoTimestamp, conversationId, url from cloudTable where url = '"
						+ URL + "'");

		while (rs.next()) {
	
			// System.out.println(rel.getString("name"));
				
			v.setConversationId(rs.getLong(conversationId));
			
			
			v.setVideoTimestamp(rs.getLong(videoTimestamp));
			
			
			v.setUrl(rs.getString(url));
			v.setVideoName(rs.getString(videoName));
			v.setUsername(rs.getString(username));
			
		}

		System.out.println("***********");
		return v;
	}

	public void addVideo(Video v) throws SQLException {

		String url = v.getUrl();
		String videoName = v.getVideoName();
		long conversationId = v.getConversationId();
		long videoTimestamp = v.getVideoTimestamp();
		String username = v.getUsername();


		
		statement.executeUpdate("insert into cloudTable values ('" + videoName
				+ "', " + conversationId + ", " + videoTimestamp + ", '" + url
				+ "', '" + username + "')");
	}

	public Conversation getConversation(long conversationId) {
		return null;
	}

	public ArrayList<Conversation> getAllConversation() {
		return null;
	}

	public static void main(String[] args) throws Exception {
		Video video = new Video("url1", "test1", 1l, System.currentTimeMillis(), "Allen");
			
		Video video2;
		
		ConnectDB db = new ConnectDB();

		db.createTable();
		db.addVideo(video);
		db.insert();

		ArrayList<String> a = new ArrayList<String>();

		video2 = db.getVideo("url1");
		
		System.out.println("*****");
		System.out.println("this is video getting from the DB:");
		System.out.println(video2.getUsername());
		System.out.println(video2.getVideoTimestamp());
		System.out.println(video2.getUrl());
		System.out.println("*****");
				
		db.query(a); // disconnect
		// new ControlRDS().deleteRecord("sample.mp4");
		System.out.println(a.size());
		
	}
}
