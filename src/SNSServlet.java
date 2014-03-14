import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.amazonaws.auth.AWSCredentialsProviderChain;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.CreateTopicResult;
import com.amazonaws.services.sns.model.GetTopicAttributesResult;
import com.amazonaws.services.sns.model.PublishResult;
import com.amazonaws.services.sns.model.SubscribeResult;


public class SNSServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String ACCESS_KEY = "AKIAIJPW4VXRPU4ULLPA";
	private static final String SECRET_KEY = "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";

	protected void doGet(final HttpServletRequest request,
			final HttpServletResponse response) throws ServletException,
			IOException {
		
		
		AmazonSNSClient sns = new AmazonSNSClient(new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY));
		
		CreateTopicResult ctr = sns.createTopic("try");
		
		
				
		String arn = ctr.getTopicArn();
		GetTopicAttributesResult s = sns.getTopicAttributes(arn);
		
		SubscribeResult scr = sns.subscribe(arn, "email", "allen3wsy@gmail.com");
		PublishResult pr = sns.publish(arn, "testMsg");
		response.getWriter().write("Success");
		response.setStatus(200);
		
	}
	
	
}
