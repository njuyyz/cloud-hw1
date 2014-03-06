package model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;


public class RDS {
	static Connection connection;

	private void init() throws SQLException {
		try {
			String url = "mydbinstance.co9r6pzgikbx.us-east-1.rds.amazonaws.com:3306/";
			String dbName = "mydbinstance";
			String userName = "awsuser";
			String password = "mypassword";
			String driver = "com.mysql.jdbc.Driver";
			connection = DriverManager.getConnection(url + dbName, userName,
					password);

			Class.forName(driver).newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public ArrayList<String> listVideo(ArrayList<String> a) throws SQLException {
		init();
		Statement stmt = connection.createStatement();
		try {
			ResultSet rel = stmt
					.executeQuery("select name, rating from cloudTable order by rating desc");
			while (rel.next()) {
				// System.out.println(rel.getString("name"));
				a.add(rel.getString("name"));
			}
			rel.close();
			stmt.close();
			connection.close();
			return a;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	

	// protected void doPost(HttpServletRequest request,
	// HttpServletResponse response) throws ServletException, IOException {
	//
	// String r = request.getParameter("number");
	// String objectName = request.getParameter("video");
	// AWSCredentials credentials = new PropertiesCredentials(getClass()
	// .getClassLoader().getResourceAsStream(
	// "AwsCredentials.properties"));
	//
	// String url =
	// "jdbc:mysql://mydbinstance.co9r6pzgikbx.us-east-1.rds.amazonaws.com:3306/";
	// String userName = "company6998";
	// String password = "company6998";
	// String dbName = "db";
	// String driver = "com.mysql.jdbc.Driver";
	//
	// try {
	// Class.forName(driver).newInstance();
	// } catch (InstantiationException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (IllegalAccessException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (ClassNotFoundException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// try {
	// connection = DriverManager.getConnection(url + dbName,
	// userName, password);
	// Statement stmt = connection.createStatement();
	// java.util.Date date = new java.util.Date();
	// // System.out.println("insert into video values ('" + objectName +
	// // "','" + new Timestamp(date.getTime()) +
	// // "'," + rating + ", 1)");
	// stmt.executeUpdate("insert into video values ('" + objectName
	// + "','" + new Timestamp(date.getTime()) + "'," + rating
	// + ", 1)");
	//
	// stmt.close();
	// connection.close();
	// } catch (SQLException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// response.sendRedirect("index.jsp");
	//
	// }

}
