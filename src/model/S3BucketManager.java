package model;
import java.io.File;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;


public class S3BucketManager {
	AmazonS3Client s3;
	String 	bucket_name = null;
	
	public S3BucketManager(AmazonS3Client s3Instance, String bucketName)
	{
		this.s3 = s3Instance;
		this.bucket_name = bucketName;
	}

	public void createBucket()
	{
		//create bucket
		s3.createBucket(bucket_name);
	}
	
	public void putObject(String key, File file)
	{		
		try {
			//put object - bucket, key, value(file)
			System.out.println("Putting object on S3");
			s3.putObject(new PutObjectRequest(bucket_name, key, file).withCannedAcl(CannedAccessControlList.PublicRead));
			System.out.println("Done");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
