package model;

/*
 * Copyright 2012-2014 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.CreateTableResult;
import com.amazonaws.services.dynamodbv2.model.DescribeTableRequest;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import com.amazonaws.services.dynamodbv2.model.TableDescription;
import com.amazonaws.services.dynamodbv2.model.TableStatus;

/**
 * This sample demonstrates how to perform a few simple operations with the
 * Amazon DynamoDB service.
 */
public class DynamoDB {

	/*
	 * Important: Be sure to fill in your AWS access credentials in the
	 * AwsCredentials.properties file before you try to run this sample.
	 * http://aws.amazon.com/security-credentials
	 */

	static AmazonDynamoDBClient dynamoDB;
	static String tableName = "video_table";
	
	private DynamoDB(){
		
	}
	
	
	public static AmazonDynamoDBClient getInstance(){
		if(dynamoDB == null){
			try {
				init();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return dynamoDB;
		}
		else{
			return dynamoDB;
		}
	}

	/**
	 * The only information needed to create a client are security credentials
	 * consisting of the AWS Access Key ID and Secret Access Key. All other
	 * configuration, such as the service endpoints, are performed
	 * automatically. Client parameters, such as proxies, can be specified in an
	 * optional ClientConfiguration object when constructing a client.
	 * 
	 * @see com.amazonaws.auth.BasicAWSCredentials
	 * @see com.amazonaws.auth.PropertiesCredentials
	 * @see com.amazonaws.ClientConfiguration
	 */
	public static void init() throws Exception {
		/*
		 * This credentials provider implementation loads your AWS credentials
		 * from a properties file at the root of your classpath.
		 */
		dynamoDB = new AmazonDynamoDBClient(
				new ClasspathPropertiesFileCredentialsProvider());
		Region usEast1 = Region.getRegion(Regions.US_EAST_1);
		dynamoDB.setRegion(usEast1);
	}

	public static void createTable() {
		try {

			// Create a table with a primary hash key named 'name', which holds
			// a string
			CreateTableRequest createTableRequest = new CreateTableRequest()
					.withTableName(tableName)
					.withKeySchema(
							new KeySchemaElement().withAttributeName("name")
									.withKeyType(KeyType.HASH))
					.withAttributeDefinitions(
							new AttributeDefinition().withAttributeName("name")
									.withAttributeType(ScalarAttributeType.S))
					.withProvisionedThroughput(
							new ProvisionedThroughput().withReadCapacityUnits(
									1L).withWriteCapacityUnits(1L));
			TableDescription createdTableDescription = dynamoDB.createTable(
					createTableRequest).getTableDescription();
			System.out.println("Created Table: " + createdTableDescription);

			// Wait for it to become active
			waitForTableToBecomeAvailable(tableName);

			// Describe our new table
			DescribeTableRequest describeTableRequest = new DescribeTableRequest()
					.withTableName(tableName);
			TableDescription tableDescription = dynamoDB.describeTable(
					describeTableRequest).getTable();
			System.out.println("Table Description: " + tableDescription);

			ArrayList<AttributeDefinition> attributeDefinitions = new ArrayList<AttributeDefinition>();
			attributeDefinitions.add(new AttributeDefinition()
					.withAttributeName("videoUrl").withAttributeType("S"));
			attributeDefinitions.add(new AttributeDefinition()
					.withAttributeName("videoName").withAttributeType("S"));
			attributeDefinitions
					.add(new AttributeDefinition().withAttributeName(
							"conversationId").withAttributeType("S"));

			ArrayList<KeySchemaElement> ks = new ArrayList<KeySchemaElement>();
			ks.add(new KeySchemaElement().withAttributeName("videoUrl")
					.withKeyType(KeyType.HASH));

			ProvisionedThroughput provisionedThroughput = new ProvisionedThroughput()
					.withReadCapacityUnits(10L).withWriteCapacityUnits(10L);

			CreateTableRequest request = new CreateTableRequest()
					.withTableName(tableName)
					.withAttributeDefinitions(attributeDefinitions)
					.withKeySchema(ks)
					.withProvisionedThroughput(provisionedThroughput);

			CreateTableResult result = dynamoDB.createTable(request);

			System.out.println(result);

		} catch (AmazonServiceException ase) {
			System.out
					.println("Caught an AmazonServiceException, which means your request made it "
							+ "to AWS, but was rejected with an error response for some reason.");
			System.out.println("Error Message:    " + ase.getMessage());
			System.out.println("HTTP Status Code: " + ase.getStatusCode());
			System.out.println("AWS Error Code:   " + ase.getErrorCode());
			System.out.println("Error Type:       " + ase.getErrorType());
			System.out.println("Request ID:       " + ase.getRequestId());
		} catch (AmazonClientException ace) {
			System.out
					.println("Caught an AmazonClientException, which means the client encountered "
							+ "a serious internal problem while trying to communicate with AWS, "
							+ "such as not being able to access the network.");
			System.out.println("Error Message: " + ace.getMessage());
		}
	}

	public static void addVideo(Video v) {
		// Add an item
		String videoName = v.getVideoName();
		String videoUrl = v.getUrl();
		String conversationId = v.getconversationId();

		Map<String, AttributeValue> item = newItem(videoName, videoUrl,
				conversationId);
		PutItemRequest putItemRequest = new PutItemRequest(tableName, item);
		PutItemResult putItemResult = dynamoDB.putItem(putItemRequest);
		System.out.println("Result: " + putItemResult);
	}

	public static Conversation getConversation(String conversationId) {
		// Scan items for movies with a year attribute greater than 1985
		HashMap<String, Condition> filter = new HashMap<String, Condition>();
		Condition condition = new Condition().withComparisonOperator(
				ComparisonOperator.GT.toString()).withAttributeValueList(
				new AttributeValue(conversationId));
		filter.put("conversationId", condition);
		QueryRequest queryRequest = new QueryRequest(tableName)
				.withKeyConditions(filter);
		QueryResult queryResult = dynamoDB.query(queryRequest);

		Conversation conversation = new Conversation();
		conversation.setConverstationId(conversationId);
		ArrayList<Video> videoList = new ArrayList<Video>();
		for (Map<String, AttributeValue> item : queryResult.getItems()) {
			Video video = new Video();
			video.setconversationId(conversationId);
			video.setUrl(item.get("videoUrl").toString());
			video.setVideoName(item.get("videoName").toString());

			videoList.add(video);
		}
		conversation.setVideoList(videoList);
		return conversation;
	}

	private static Map<String, AttributeValue> newItem(String videoName,
			String videoUrl, String conversationId) {
		Map<String, AttributeValue> item = new HashMap<String, AttributeValue>();
		item.put("videoName", new AttributeValue(videoName));
		item.put("videoUrl", new AttributeValue(videoUrl));
		item.put("conversationId", new AttributeValue(conversationId));

		return item;
	}

	private static void waitForTableToBecomeAvailable(String tableName) {
		System.out.println("Waiting for " + tableName + " to become ACTIVE...");

		long startTime = System.currentTimeMillis();
		long endTime = startTime + (10 * 60 * 1000);
		while (System.currentTimeMillis() < endTime) {
			try {
				Thread.sleep(1000 * 20);
			} catch (Exception e) {
			}
			try {
				DescribeTableRequest request = new DescribeTableRequest()
						.withTableName(tableName);
				TableDescription tableDescription = dynamoDB.describeTable(
						request).getTable();
				String tableStatus = tableDescription.getTableStatus();
				System.out.println("  - current state: " + tableStatus);
				if (tableStatus.equals(TableStatus.ACTIVE.toString()))
					return;
			} catch (AmazonServiceException ase) {
				if (ase.getErrorCode().equalsIgnoreCase(
						"ResourceNotFoundException") == false)
					throw ase;
			}
		}

		throw new RuntimeException("Table " + tableName + " never went active");
	}

}
