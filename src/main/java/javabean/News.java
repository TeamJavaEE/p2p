package javabean;

import java.text.SimpleDateFormat;
import java.util.Date;



public class News {
	private String userName;
	
	private int userId;
	private int newsId;
	private String newsName;
	private String newsContent;
	private String newsType;
	private Date newsPublishTime;
	private String newsContentComfirm;
	private String newsPicture;
	private int newsStatus;
	private String newsDateStr;
	
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getNewsId() {
		return newsId;
	}
	public void setNewsId(int newsId) {
		this.newsId = newsId;
	}
	public String getNewsName() {
		return newsName;
	}
	public void setNewsName(String newsName) {
		this.newsName = newsName;
	}
	public String getNewsContent() {
		return newsContent;
	}
	public void setNewsContent(String newsContent) {
		this.newsContent = newsContent;
	}
	public String getNewsType() {
		return newsType;
	}
	public void setNewsType(String newsType) {
		this.newsType = newsType;
	}
	public Date getNewsPublishTime() {
		return newsPublishTime;
	}
	public void setNewsPublishTime(Date newsPublishTime) {
		this.newsPublishTime = newsPublishTime;
	}
	public String getNewsContentComfirm() {
		return newsContentComfirm;
	}
	public void setNewsContentComfirm(String newsContentComfirm) {
		this.newsContentComfirm = newsContentComfirm;
	}
	public String getNewsPicture() {
		return newsPicture;
	}
	public void setNewsPicture(String newsPicture) {
		this.newsPicture = newsPicture;
	}
	public int getNewsStatus() {
		return newsStatus;
	}
	public void setNewsStatus(int newsStatus) {
		this.newsStatus = newsStatus;
	}
	
	
	public String getNewsDateStr() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.newsDateStr = simpleDateFormat.format(newsPublishTime);
		return this.newsDateStr;
	}
	public void setNewsDateStr(String newsDateStr) {
		this.newsDateStr = newsDateStr;
	}
}
