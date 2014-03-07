import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Conversation;
import model.Video;

import com.amazonaws.auth.AWSCredentialsProviderChain;
import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;

/**
 * An example Amazon Elastic Beanstalk Worker Tier application. This example
 * requires a Java 7 (or higher) compiler.
 */
public class WorkerServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Charset UTF_8 = Charset.forName("UTF-8");

	
	/**
	 * A client to use to access Amazon S3. Pulls credentials from the
	 * {@code AwsCredentials.properties} file if found on the classpath,
	 * otherwise will attempt to obtain credentials based on the IAM Instance
	 * Profile associated with the EC2 instance on which it is run.
	 */
	private final AmazonS3Client s3 = new AmazonS3Client(
			new AWSCredentialsProviderChain(
					new InstanceProfileCredentialsProvider(),
					new ClasspathPropertiesFileCredentialsProvider()));

	private ArrayList<Conversation> retreiveConversations() {
		ArrayList<Conversation> list = new ArrayList<Conversation>();
		for (int i = 0; i < 7; i++) {
			Conversation c = new Conversation();
			c.setConversationId(i + 1l);
			
			ArrayList<Video> videoList = new ArrayList<Video>();
			Video v = new Video();
			v.setConversationId(1L);
			v.setUrl("http://d3gflyc7e9bpot.cloudfront.net/sample_mpeg4.mp4");
			v.setVideoName("My Video");
			
			
			videoList.add(v);
			c.setVideoList(videoList);
			list.add(c);
		}
		return list;
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
			request.getRequestDispatcher("Index.jsp")
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
