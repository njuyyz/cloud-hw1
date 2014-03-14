import helper.NotificationHelper;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.ConnectDB;


public class SubscribeServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	protected void doPost(final HttpServletRequest request,
			final HttpServletResponse response) throws ServletException,
			IOException {
		String email= (String) request.getParameter("email");
		
		NotificationHelper nh = NotificationHelper.getInstance();
		nh.subscribe(email);
		
		response.sendRedirect("index.html");
	}
	protected void doGet(final HttpServletRequest request,
			final HttpServletResponse response) throws ServletException,
			IOException {
		doPost(request,response);
	}
}
