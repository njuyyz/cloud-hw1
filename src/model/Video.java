package model;

public class Video {

	private String url;
	private String videoName;
	private long conversationId;
	private long videoTimestamp;
	private String username;
	
	public Video()	{
		
	}
	
	public Video(String url, String videoName, long conversationId, long videoTimeStamp, String username)	{
		this.url = url;
		this.videoName = videoName;
		this.conversationId = conversationId;
		this.videoTimestamp = videoTimeStamp;
		this.username = username;
	}
	
	public long getConversationId() {
		return conversationId;
	}
	public void setConversationId(long conversationId) {
		this.conversationId = conversationId;
	}
	public long getVideoTimestamp() {
		return videoTimestamp;
	}
	public void setVideoTimestamp(long videoTimestamp) {
		this.videoTimestamp = videoTimestamp;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getVideoName() {
		return videoName;
	}
	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}

}
