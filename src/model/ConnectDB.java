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
	
	private static long nextConversationId;

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
				+ "', " + 3 + ", " + System.currentTimeMillis() + ", '"
				+ "url2" + "', 'Ben')");

		statement.executeUpdate("insert into cloudTable values ('" + "test.mp4"
				+ "', " + 2 + ", " + System.currentTimeMillis() + ", '"
				+ "url3" + "', 'Ryan')");

		System.out.println("Insert created successfully");
	}

	// print all videos' names
	public void query(ArrayList<Long> a) throws Exception {

		ResultSet rs = statement
				.executeQuery("select username, videoName, videoTimestamp, conversationId, url from cloudTable order by videoTimestamp desc");

		while (rs.next()) {
			// System.out.println(rs.getString(username));
			System.out.print(rs.getLong(conversationId));
			System.out.println(" " + rs.getString(username));
		}
//
		// close all the stuff
		rs.close();
		statement.close();
		connection.close();
	}

	
	public void clearTable() throws Exception {
		statement.execute("TRUNCATE TABLE " + tableName);
		System.out.println("t created successfully");

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

	public Video getVideo(String URL) throws SQLException {
		Video v = new Video();
		
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
		rs.close();
		
		return v;
	}


	public Conversation getConversation(long id) throws SQLException {
		
		Conversation c = new Conversation();
		
		ArrayList<String> urlList = new ArrayList<String>();	
		ResultSet rs = statement
				.executeQuery("select url, conversationId from cloudTable where conversationId = '"
						+ id + "'");

		
		while (rs.next()) {
			urlList.add(rs.getString(url));	
		}
		rs.close();
		System.out.println("urlList size:" + urlList.size());
		
		ArrayList<Video> videoList = new ArrayList<Video>();
		for(int i = 0; i < urlList.size(); i++)	{
			videoList.add(getVideo(urlList.get(i)));
		}
		System.out.println("traverse videolist over");
		c.setVideoList(videoList);
		c.setConversationId(id);
	
		return c;
	}

	/**
	 * get all the videos (top video for each conversation)
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Video> getTopVideos() throws SQLException {
		//Conversation c = new Conversation();
		ArrayList<Video> arr = new ArrayList<Video>();
		
		// sql
		ResultSet rs = statement
				.executeQuery("select * "
						+ "from cloudTable t1 "
						+ "where videoTimestamp in ( "
							+ "select min(videoTimestamp) "
							+ "from cloudTable t2 "
							+ "where t1.conversationId = t2.conversationId "
							+ "group by t2.conversationId) "
						+ "Order by conversationId");
		
		while (rs.next()) {
			//System.out.println(rs.getString(url));
			Video v = new Video();
			v.setConversationId(rs.getLong(conversationId));		
			v.setVideoTimestamp(rs.getLong(videoTimestamp));
					
			v.setUrl(rs.getString(url));
			v.setVideoName(rs.getString(videoName));
			v.setUsername(rs.getString(username));	
			
			arr.add(v);
			
		}
		
		rs.close();
		nextConversationId = 1 + arr.get(arr.size()-1).getConversationId();
		return arr;
	}
	
	
	/**
	 * 
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Conversation> getAllConversations() throws SQLException {
		
		ArrayList<Conversation> array = new ArrayList<Conversation>();
		
		// sql
		ResultSet rs = statement
				.executeQuery("select * "
						+ "from cloudTable t1 "
						+ "where videoTimestamp in ( "
							+ "select min(videoTimestamp) "
							+ "from cloudTable t2 "
							+ "where t1.conversationId = t2.conversationId "
							+ "group by t2.conversationId) "
						+ "Order by conversationId");
		
		ArrayList<Long> arr = new ArrayList<Long>();	
		
		while (rs.next()) {							
			arr.add(rs.getLong(conversationId));			
		}
		rs.close();
		
		Conversation c = new Conversation();
		for(int i = 0; i < arr.size(); i++)	{
			c = getConversation(arr.get(i));
			array.add(c);
		}

		return array;
	}
	
	

	public static void main(String[] args) throws Exception {
		Video video = new Video("url1", "test1.mp4", 1l, System.currentTimeMillis(), "Allen");
			
		Video video2;
		
		ConnectDB db = new ConnectDB();

		db.createTable();
		db.addVideo(video);
		db.insert();

		ArrayList<Long> a = new ArrayList<Long>();

		video2 = db.getVideo("url1");
		
//		System.out.println("this is Conversation 1:");	
//		Conversation c = db.getConversation(1);
//		ArrayList<Video> arr = c.getVideoList();
//		
//		System.out.println(arr.get(0).getUsername());	// Allen
//		System.out.println(arr.get(1).getUsername());	// Ben
//		System.out.println("this is Conversation 2:");
//		
//		c = db.getConversation(2);
//		ArrayList<Video> arr2 = c.getVideoList();	
//		System.out.println(arr2.get(0).getUsername());		
		
		ArrayList<Video> arr = new ArrayList<Video>();
		arr = db.getTopVideos();
		ArrayList<Conversation> array = new ArrayList<Conversation>();
		array = db.getAllConversations();
		
		
		db.query(a); 			// disconnect
		// new ControlRDS().deleteRecord("sample.mp4");
		System.out.println("success!");

	}
}
