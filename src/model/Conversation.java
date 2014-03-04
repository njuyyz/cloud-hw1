package model;

import java.util.ArrayList;

public class Conversation {

	private ArrayList<Video> videoList;
	private String conversationId;
	
	public ArrayList<Video> getVideoList() {
		return videoList;
	}

	public void setVideoList(ArrayList<Video> videoList) {
		this.videoList = videoList;
	}

	public String getConversationId() {
		return conversationId;
	}

	public void setConversationId(String converstationId) {
		this.conversationId = converstationId;
	}

	public Conversation(){
		
	}
	
	public boolean addVideo(Video v){
		return false;
	}
	
}
