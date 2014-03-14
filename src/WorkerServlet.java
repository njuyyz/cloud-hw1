import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.ConnectDB;
import model.Conversation;
/**
 * An example Amazon Elastic Beanstalk Worker Tier application. This example
 * requires a Java 7 (or higher) compiler.
 */
public class WorkerServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	
	private ArrayList<Conversation> retreiveConversations() {
		ConnectDB conDB = ConnectDB.getInstance();
		return conDB.getAllConversations();
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			ArrayList<Conversation> conList = retreiveConversations();
			request.setAttribute("conversationList", conList);
			request.getRequestDispatcher("index2.jsp")
					.forward(request, response);

			response.setStatus(200);
		} catch (RuntimeException exception) {

			response.setStatus(500);
			try (PrintWriter writer = new PrintWriter(
					response.getOutputStream())) {
				exception.printStackTrace(writer);
			}
		}
	}

	/**
	 * This method is invoked to handle POST requests from the local SQS daemon
	 * when a work item is pulled off of the queue. The body of the request
	 * contains the message pulled off the queue.
	 */
	@Override
	protected void doPost(final HttpServletRequest request,
			final HttpServletResponse response) throws ServletException,
			IOException {
		doGet(request,response);
	}

}
