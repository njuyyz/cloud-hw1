import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.ConnectDB;
import model.Conversation;

public class ConversationServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			long conversationId = Long.parseLong(request
					.getParameter("conversationID"));
//			long conversationId = 15;
			ConnectDB condb = ConnectDB.getInstance();
			Conversation conversation = condb.getConversation(conversationId);

			request.setAttribute("conversation", conversation);
			request.getRequestDispatcher("Conversation.jsp").forward(request,
					response);
			response.setStatus(200);
		} catch (RuntimeException exception) {

			response.setStatus(500);
			try (PrintWriter writer = new PrintWriter(
					response.getOutputStream())) {
				exception.printStackTrace(writer);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
