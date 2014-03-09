import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.ConnectDB;
import model.Conversation;
import model.Video;
/**
 * An example Amazon Elastic Beanstalk Worker Tier application. This example
 * requires a Java 7 (or higher) compiler.
 */
public class WorkerServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	
	private ArrayList<Conversation> retreiveConversations() {
//		ArrayList<Conversation> list = new ArrayList<Conversation>();
//		for (int i = 0; i < 7; i++) {
//			Conversation c = new Conversation();
//			c.setConversationId(i + 1l);
//			
//			ArrayList<Video> videoList = new ArrayList<Video>();
//			Video v = new Video();
//			v.setConversationId(1L);
//			v.setUrl("http://d3gflyc7e9bpot.cloudfront.net/sample_mpeg4.mp4");
//			v.setVideoName("My Video");
//			Video v2 = new Video();
//			v2.setConversationId(1L);
//			v2.setUrl("http://d3gflyc7e9bpot.cloudfront.net/sample_mpeg4.mp4");
//			v2.setVideoName("My Video");
//			
//
//			videoList.add(v);
//			videoList.add(v2);
//			c.setVideoList(videoList);
//			list.add(c);
//		}

		ConnectDB conDB = ConnectDB.getInstance();
		return conDB.getAllConversations();
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			// response.getWriter().write("columbia university: yes");
			// Signal to beanstalk that processing was successful so this work
			// item should not be retried.
			ArrayList<Conversation> conList = retreiveConversations();
			request.setAttribute("conversationList", conList);
			request.getRequestDispatcher("index2.jsp")
					.forward(request, response);

			response.setStatus(200);
		} catch (RuntimeException exception) {

			// Signal to beanstalk that something went wrong while processing
			// the request. The work request will be retried several times in
			// case the failure was transient (eg a temporary network issue
			// when writing to Amazon S3).

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
