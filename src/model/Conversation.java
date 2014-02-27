package model;

import java.util.ArrayList;

public class Conversation {

	private ArrayList<Video> videoList;
	private String converstationId;
	
	public ArrayList<Video> getVideoList() {
		return videoList;
	}

	public void setVideoList(ArrayList<Video> videoList) {
		this.videoList = videoList;
	}

	public String getConverstationId() {
		return converstationId;
	}

	public void setConverstationId(String converstationId) {
		this.converstationId = converstationId;
	}

	public Conversation(){
		
	}
	
	public boolean addVideo(Video v){
		return false;
	}
	
}
