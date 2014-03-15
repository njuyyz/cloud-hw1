package helper;

import java.util.ArrayList;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.CreateTopicResult;

import model.ConnectDB;

public class NotificationHelper {

	private ArrayList<String> emailList = null;
	private static final String ACCESS_KEY = "AKIAIJPW4VXRPU4ULLPA";
	private static final String SECRET_KEY = "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";

	private AmazonSNSClient sns = null;
	
	private static NotificationHelper instance = null;
	
	
	private NotificationHelper(){
		emailList = ConnectDB.getInstance().getEmailList();
		sns = new AmazonSNSClient(new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY));
	}
	public static NotificationHelper getInstance(){
		if(instance == null){
			instance = new NotificationHelper();
		}
		return instance;
	}
	
	public void subscribe( String email){
		CreateTopicResult ctr = sns.createTopic("try");
		String arn = ctr.getTopicArn();
		sns.subscribe(arn, "email", email);
	}
	
	public void publish(){
		CreateTopicResult ctr = sns.createTopic("try");
		String arn = ctr.getTopicArn();
		sns.publish(arn, "Someone has posted a new video to the website, please log in and see the updated video");
	}
}
