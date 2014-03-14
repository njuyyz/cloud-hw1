package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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

	private static long nextConversationId = 1;

	private static ConnectDB instance = null;

	private ConnectDB() {

		String url_connection = "jdbc:mysql://mydbinstance.co9r6pzgikbx.us-east-1.rds.amazonaws.com:3306/";

		String dbName = "mydb";
		String user = "awsuser";
		String password = "mypassword";

		String driver = "com.mysql.jdbc.Driver";

		try {
			Class.forName(driver).newInstance();
			connection = DriverManager.getConnection(url_connection + dbName,
					user, password);
			statement = connection.createStatement();
			System.out.println(connection.getCatalog());

			createTable();

		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static ConnectDB getInstance() {
		if (instance == null) {
			instance = new ConnectDB();
		}
		return instance;
	}

	public void createTable() {

		// try {
		// statement.execute("DROP TABLE IF EXISTS " + tableName);
		// } catch (SQLException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// System.out.println("Create a new table: " + tableName);

		// // I chose int for JAVA's (long)!!!
		try {
			statement
					.execute("create table if not exists "
							+ tableName
							+ " (videoName varchar(32), conversationId bigint, videoTimestamp bigint, url varchar(100), username varchar(32))");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Table created successfully");

		// email table
		try {
			statement
					.execute("create table if not exists email (email varchar(32))");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Email Table created successfully");

	}

	/**
	 * insert email into RDS
	 */
	public void insertEmail(String email)	{
		
		// insert email tuple
		// email String:
		try {
			statement.executeUpdate("insert into email values ('" + email + "')");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	/**
	 * return all the emails
	 * @return
	 */
	public ArrayList<String> getEmailList() {
		ArrayList<String> array = new ArrayList<String>();

		// sql
		ResultSet rs;

		try {
			rs = statement.executeQuery("select * from email");

			String s = new String();
			while (rs.next()) {
				s = rs.getString("email");
				array.add(s);

			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return array;
	}

	public void insert() {

		try {
			statement
					.executeUpdate("insert into cloudTable values ('"
							+ "test.mp4" + "', " + 3 + ", "
							+ System.currentTimeMillis() + ", '" + "url2"
							+ "', 'Ben')");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			statement.executeUpdate("insert into cloudTable values ('"
					+ "test.mp4" + "', " + 2 + ", "
					+ System.currentTimeMillis() + ", '" + "url3"
					+ "', 'Ryan')");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Insert created successfully");
	}

	// print all videos' names
	public void query(ArrayList<Long> a) {

		ResultSet rs = null;
		try {
			rs = statement
					.executeQuery("select username, videoName, videoTimestamp, conversationId, url from cloudTable order by videoTimestamp desc");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			while (rs.next()) {
				// System.out.println(rs.getString(username));
				System.out.print(rs.getLong(conversationId));
				System.out.println(" " + rs.getString(username));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//
		// close all the stuff
		try {
			rs.close();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void clearTable() throws Exception {
		statement.execute("TRUNCATE TABLE " + tableName);
		System.out.println("t created successfully");

	}

	/**
	 * delete a video from the table
	 * 
	 * @param v
	 * @throws SQLException
	 */
	public void deleteVideo(Video v) {
		v.getUrl();

		try {
			statement.executeUpdate("delete from cloudTable where url = '"
					+ v.getUrl() + "'");
			statement.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void addConversation(Video v) {

		// race condition?
		v.setConversationId(nextConversationId++);
		addVideo(v);

	}

	public String addVideo(Video v) {

		String url = v.getUrl();
		String videoName = v.getVideoName();
		long conversationId = v.getConversationId();
		long videoTimestamp = v.getVideoTimestamp();
		String username = v.getUsername();
		String status = url;
		try {
			statement.executeUpdate("insert into cloudTable values ('"
					+ videoName + "', " + conversationId + ", "
					+ videoTimestamp + ", '" + url + "', '" + username + "')");
			return status + " success";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return status + e.getMessage();
		}
	}

	public Video getVideo(String URL) {
		Video v = new Video();

		ResultSet rs;
		try {
			rs = statement
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
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
		ArrayList<Video> videoList = new ArrayList<Video>();

		for (int i = 0; i < urlList.size(); i++) {
			videoList.add(getVideo(urlList.get(i)));
		}

		c.setVideoList(videoList);
		c.setConversationId(id);

		return c;
	}

	/**
	 * get all the videos (top video for each conversation)
	 * 
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Video> getTopVideos() {
		// Conversation c = new Conversation();
		ArrayList<Video> arr = new ArrayList<Video>();

		// sql
		ResultSet rs;
		try {
			rs = statement.executeQuery("select * " + "from cloudTable t1 "
					+ "where videoTimestamp in ( "
					+ "select min(videoTimestamp) " + "from cloudTable t2 "
					+ "where t1.conversationId = t2.conversationId "
					+ "group by t2.conversationId) "
					+ "Order by conversationId");

			while (rs.next()) {
				// System.out.println(rs.getString(url));
				Video v = new Video();
				v.setConversationId(rs.getLong(conversationId));
				v.setVideoTimestamp(rs.getLong(videoTimestamp));

				v.setUrl(rs.getString(url));
				v.setVideoName(rs.getString(videoName));
				v.setUsername(rs.getString(username));

				arr.add(v);

			}

			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		nextConversationId = 1 + arr.get(arr.size() - 1).getConversationId();
		return arr;
	}

	/**
	 * 
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Conversation> getAllConversations() {

		ArrayList<Conversation> array = new ArrayList<Conversation>();

		// sql
		ResultSet rs;
		try {
			rs = statement.executeQuery("select * " + "from cloudTable t1 "
					+ "where videoTimestamp in ( "
					+ "select min(videoTimestamp) " + "from cloudTable t2 "
					+ "where t1.conversationId = t2.conversationId "
					+ "group by t2.conversationId) "
					+ "Order by conversationId");

			ArrayList<Long> arr = new ArrayList<Long>();

			while (rs.next()) {
				arr.add(rs.getLong(conversationId));
			}
			rs.close();

			Conversation c = new Conversation();
			for (int i = 0; i < arr.size(); i++) {
				c = getConversation(arr.get(i));
				array.add(c);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		nextConversationId = 1 + array.get(array.size() - 1)
				.getConversationId();

		return array;
	}

	// public static void main(String[] args){
	// Video video = new Video("url1", "test1.mp4", 1l,
	// System.currentTimeMillis(), "Allen");
	//
	// Video video2;
	//
	// ConnectDB db = new ConnectDB();
	//
	// db.createTable();
	// db.addVideo(video);
	// db.insert();
	//
	// ArrayList<Long> a = new ArrayList<Long>();
	//
	// video2 = db.getVideo("url1");
	//
	// // System.out.println("this is Conversation 1:");
	// // Conversation c = db.getConversation(1);
	// // ArrayList<Video> arr = c.getVideoList();
	// //
	// // System.out.println(arr.get(0).getUsername()); // Allen
	// // System.out.println(arr.get(1).getUsername()); // Ben
	// // System.out.println("this is Conversation 2:");
	// //
	// // c = db.getConversation(2);
	// // ArrayList<Video> arr2 = c.getVideoList();
	// // System.out.println(arr2.get(0).getUsername());
	//
	// ArrayList<Video> arr = new ArrayList<Video>();
	// arr = db.getTopVideos();
	// ArrayList<Conversation> array = new ArrayList<Conversation>();
	// array = db.getAllConversations();
	//
	//
	// db.query(a); // disconnect
	// // db.deleteRecord("url....");
	// System.out.println("success!");
	//
	// }
}
