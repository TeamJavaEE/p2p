package javabean;

import java.util.Date;

public class Comment {
	private int newsCommentId;
	private int userId;
	private String userName;
	private String userPicture;
	private int newsId;
	private Date newsCommentTime;
	private boolean edit;
	private String newsCommentContent;
	 
	
	
	public int getNewsCommentId() {
		return newsCommentId;
	}
	public void setNewsCommentId(int newsCommentId) {
		this.newsCommentId = newsCommentId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPicture() {
		return userPicture;
	}
	public void setUserPicture(String userPicture) {
		this.userPicture = userPicture;
	}
	public int getNewsId() {
		return newsId;
	}
	public void setNewsId(int newsId) {
		this.newsId = newsId;
	}
	public Date getNewsCommentTime() {
		return newsCommentTime;
	}
	public void setNewsCommentTime(Date newsCommentTime) {
		this.edit = (System.currentTimeMillis() - newsCommentTime.getTime() <= 300000);
		this.newsCommentTime = newsCommentTime;
	}
	public String getNewsCommentContent() {
		return newsCommentContent;
	}
	public void setNewsCommentContent(String newsCommentContent) {
		this.newsCommentContent = newsCommentContent;
	}
	public boolean getEdit() {
		return edit;
	}
	public void setEdit(boolean edit) {
		this.edit = edit;
	}
}
