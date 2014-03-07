package model;

import java.util.ArrayList;

public class Conversation {

	private ArrayList<Video> videoList;
	private long conversationId;
	
	
	public ArrayList<Video> getVideoList() {
		return videoList;
	}

	public void setVideoList(ArrayList<Video> videoList) {
		this.videoList = videoList;
	}

	public long getConversationId() {
		return conversationId;
	}

	public void setConversationId(long converstationId) {
		this.conversationId = converstationId;
	}
	
	public boolean addVideo(Video v){
		return false;
	}
	
}
