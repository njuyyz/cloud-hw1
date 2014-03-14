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
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

public class ReplyServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static String bucket = "allen.ryan.bucket.1";

	public static final String cloudFrontUrl = "rtmp://s1f29zvynq6sfw.cloudfront.net/cfx/st/";

	private String getBucket() {
		return bucket;
	}

	@Override
	protected void doPost(final HttpServletRequest request,
			final HttpServletResponse response) throws ServletException,
			IOException {
		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(
				ReplyServlet.class
						.getResourceAsStream("AwsCredentials.properties")));

		try {

			List<FileItem> items = new ServletFileUpload(
					new DiskFileItemFactory()).parseRequest(request);

			String userId = items.get(1).getString();
			long conversationId = Long.parseLong(items.get(2).getString());
			InputStream is = items.get(0).getInputStream();
			String key = "" + System.currentTimeMillis()
					+ request.getRemoteAddr() + items.get(0).getName();
			PutObjectRequest putObj = new PutObjectRequest(getBucket(), key,
					is, new ObjectMetadata());
			putObj.setCannedAcl(CannedAccessControlList.PublicRead);
			s3.putObject(putObj);
			request.setAttribute("UploadStatus", "before Video;  ");
			Video v = new Video();
			v.setUsername(userId);
			v.setUrl(cloudFrontUrl + key);
			v.setVideoName(items.get(0).getName());
			v.setVideoTimestamp(System.currentTimeMillis());
			v.setConversationId(conversationId);

			ConnectDB conDB = ConnectDB.getInstance();
			conDB.addVideo(v);

//			request.getRequestDispatcher("conversation/?conversationID=" + conversationId)
//					.forward(request, response);
			response.sendRedirect("conversation?conversationID=" + conversationId);

		} catch (FileUploadException e) {
			throw new ServletException("Cannot parse multipart request.", e);
		} catch (RuntimeException exception) {

			response.getWriter().write("upload failed");
			response.setStatus(500);
		}
	}
}