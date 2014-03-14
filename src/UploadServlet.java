import helper.NotificationHelper;
import helper.TransCoder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.ConnectDB;
import model.Video;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.elastictranscoder.AmazonElasticTranscoderClient;
import com.amazonaws.services.elastictranscoder.model.CreateJobOutput;
import com.amazonaws.services.elastictranscoder.model.CreateJobRequest;
import com.amazonaws.services.elastictranscoder.model.JobInput;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

public class UploadServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static String bucket = "allen.ryan.bucket.1";
	
	public static final String cloudFrontUrl = "rtmp://s1f29zvynq6sfw.cloudfront.net/cfx/st/";

	/**
	 * A client to use to access Amazon S3. Pulls credentials from the
	 * {@code AwsCredentials.properties} file if found on the classpath,
	 * otherwise will attempt to obtain credentials based on the IAM Instance
	 * Profile associated with the EC2 instance on which it is run.
	 */
	private String getBucket() {
		return bucket;
	}

	@Override
	protected void doPost(final HttpServletRequest request,
			final HttpServletResponse response) throws ServletException,
			IOException {
		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(
				UploadServlet.class
						.getResourceAsStream("AwsCredentials.properties")));

		try {

			List<FileItem> items = new ServletFileUpload(
					new DiskFileItemFactory()).parseRequest(request);
			
			if (items.size() == 2) {
				String userId = items.get(1).getString();
				
				InputStream is = items.get(0).getInputStream();
				String key = "" + System.currentTimeMillis() + request.getRemoteAddr() + items.get(0).getName();
				PutObjectRequest putObj = new PutObjectRequest(getBucket(), key, is, new ObjectMetadata());
	            putObj.setCannedAcl(CannedAccessControlList.PublicRead);
				s3.putObject(putObj);
				
				
				
				
//				TransCoder tc = TransCoder.getInstance();
//				String finalFileName = tc.transcode(key);
				
				
				
				
				
				JobInput myjob = new JobInput();
				myjob.setAspectRatio("auto");
				myjob.setContainer("auto");
				myjob.setFrameRate("auto");
				myjob.setInterlaced("auto");

				// the input filename
				myjob.setKey(key);
				myjob.setResolution("auto");

				CreateJobOutput jobout = new CreateJobOutput();

				int indexDot = key.lastIndexOf(".");
				
				String outputFileName = key.substring(0,indexDot)+"_transcoded.mp4";
				// output filename
				jobout.setKey(outputFileName);
				jobout.setPresetId("1351620000001-000060");
				jobout.setRotate("0");
				CreateJobRequest pipejob = new CreateJobRequest();
				pipejob.setInput(myjob);
				pipejob.setOutput(jobout);
				pipejob.setPipelineId("1394256631754-cowxng");

				AmazonElasticTranscoderClient transclient = new AmazonElasticTranscoderClient(
						new PropertiesCredentials(
								UploadServlet.class
										.getResourceAsStream("AwsCredentials.properties")));

				transclient.createJob(pipejob);
				
				
				
				
				
				
				
				
				
				
				request.setAttribute("UploadStatus", "before Video;  ");
				Video v= new Video();
				v.setUsername(userId);
				v.setUrl(cloudFrontUrl+outputFileName);
				v.setVideoName(items.get(0).getName());
				v.setVideoTimestamp(System.currentTimeMillis());
				
				ConnectDB conDB = ConnectDB.getInstance();
				conDB.addConversation(v);
				
				NotificationHelper nh = NotificationHelper.getInstance();
				nh.publish();

			}else{
				request.setAttribute("UploadStatus", ""+items.size());
			}
			response.sendRedirect("index.html");

		} catch (FileUploadException e) {
			throw new ServletException("Cannot parse multipart request.", e);
		} catch (RuntimeException exception) {

			response.getWriter().write("upload failed");
			response.setStatus(500);
		}
	}
}
