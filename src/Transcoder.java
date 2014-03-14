import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.services.elastictranscoder.AmazonElasticTranscoderClient;
import com.amazonaws.services.elastictranscoder.model.CreateJobOutput;
import com.amazonaws.services.elastictranscoder.model.CreateJobRequest;
import com.amazonaws.services.elastictranscoder.model.JobInput;

public class Transcoder {

	CreateJobRequest pipejob = new CreateJobRequest();
	private static Transcoder instance = null;
	
	// private constructor
	private Transcoder()	{
	}
	
	public static Transcoder getInstance()	{
		if(instance == null)
			instance = new Transcoder();
		return instance;
	}
	
	// exexute the transcoding function
	public String transcode(String input) {
				
		JobInput myjob = new JobInput();
		myjob.setAspectRatio("auto");
		myjob.setContainer("auto");
		myjob.setFrameRate("auto");
		myjob.setInterlaced("auto");

		// the input filename
		myjob.setKey(input);
		myjob.setResolution("auto");

		CreateJobOutput jobout = new CreateJobOutput();

		int indexDot = input.lastIndexOf(".");
		
		String output = input.substring(0,indexDot)+"_transcoded.mp4";
		// output filename
		jobout.setKey(output);
		jobout.setPresetId("1351620000001-000060");
		jobout.setRotate("0");

		pipejob.setInput(myjob);
		pipejob.setOutput(jobout);
		pipejob.setPipelineId("1394256631754-cowxng");

		AmazonElasticTranscoderClient transclient = new AmazonElasticTranscoderClient(
				new ClasspathPropertiesFileCredentialsProvider());

		transclient.createJob(pipejob);
		
		return output;
	}
	
	

}
