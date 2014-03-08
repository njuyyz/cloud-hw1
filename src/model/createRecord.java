package model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;


public class createRecord extends HttpServlet {
	
	public createRecord() {
		super();

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String r = request.getParameter("number");
		String objectName = request.getParameter("video");
		int rating = Integer.parseInt(r);
		AWSCredentials credentials = new PropertiesCredentials(getClass()
				.getClassLoader().getResourceAsStream(
						"AwsCredentials.properties"));
		String url = "jdbc:mysql://mysqlinstance.crlqbyekgddu.us-east-1.rds.amazonaws.com:3306/";
		String userName = "company6998";
		String password = "company6998";
		String dbName = "db";
		String driver = "com.mysql.jdbc.Driver";
		try {
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
		try {
			Connection connection = DriverManager.getConnection(url + dbName,
					userName, password);
			Statement stmt = connection.createStatement();
			java.util.Date date = new java.util.Date();
			// System.out.println("insert into video values ('" + objectName +
			// "','" + new Timestamp(date.getTime()) +
			// "'," + rating + ", 1)");
			stmt.executeUpdate("insert into video values ('" + objectName
					+ "','" + new Timestamp(date.getTime()) + "'," + rating
					+ ", 1)");

			stmt.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.sendRedirect("index.jsp");

	}

}