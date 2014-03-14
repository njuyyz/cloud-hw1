import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.services.elastictranscoder.AmazonElasticTranscoderClient;
import com.amazonaws.services.elastictranscoder.model.CreateJobOutput;
import com.amazonaws.services.elastictranscoder.model.CreateJobRequest;
import com.amazonaws.services.elastictranscoder.model.JobInput;


public class Transcoder {

	 CreateJobRequest pipejob = new CreateJobRequest();
     
     JobInput myjob = new JobInput();
     myjob.setAspectRatio("auto");
     myjob.setContainer("auto");
     myjob.setFrameRate("auto");
     myjob.setInterlaced("auto");
     
     // the input filename
     myjob.setKey("sample_mpeg4.mp4");
     myjob.setResolution("auto");
     
     CreateJobOutput jobout = new CreateJobOutput();
     
     // output filename
     jobout.setKey("successful.mp4");
     jobout.setPresetId("1351620000001-000060");
     jobout.setRotate("0");
     
     pipejob.setInput(myjob);
     pipejob.setOutput(jobout);
     pipejob.setPipelineId("1394256631754-cowxng");
     
     AmazonElasticTranscoderClient transclient = new AmazonElasticTranscoderClient(new ClasspathPropertiesFileCredentialsProvider());
     
     transclient.createJob(pipejob);

}
