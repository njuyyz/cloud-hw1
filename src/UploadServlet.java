import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.amazonaws.auth.AWSCredentialsProviderChain;
import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

public class UploadServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static String bucket = "elasticbeanstalk-us-east-1-524298820271";

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
			
			if (items.size() == 1) {
				InputStream is = items.get(0).getInputStream();
				String key = "" + System.currentTimeMillis() + request.getRemoteAddr() + items.get(0).getName();
				s3.putObject(new PutObjectRequest(getBucket(), key, is, new ObjectMetadata()));

				request.setAttribute("UploadStatus", "Upload Success");
			}
			request.setAttribute("UploadStatus", "Upload Failed");
			request.getRequestDispatcher("index.html").forward(request, response);

		} catch (FileUploadException e) {
			throw new ServletException("Cannot parse multipart request.", e);
		} catch (RuntimeException exception) {

			response.getWriter().write("upload failed");
			response.setStatus(500);
		}
	}
}
