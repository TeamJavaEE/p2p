package javabean;

import java.util.ArrayList;

public class Page {
	private ArrayList<News> newsList = null;
	private int pageSize;
	private int totalPage;
	
	public ArrayList<News> getNewsList() {
		return newsList;
	}
	public void setNewsList(ArrayList<News> newsList) {
		this.newsList = newsList;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	
	public ArrayList<News> getPage(int curPage){
		totalPage = newsList.size()/pageSize;
		if(newsList.size()%pageSize != 0) {
			totalPage++;
		}
		if(curPage>pageSize || curPage<0) {
			return null;
		}
		int begin = (curPage-1) * pageSize;
		ArrayList<News> ans = new ArrayList<News>(pageSize);
		int count = 0;
		for(int i=begin;i<newsList.size() && count<pageSize;++i) {
			ans.add(newsList.get(i));
			count++;
		}
		return ans;
	}
}
