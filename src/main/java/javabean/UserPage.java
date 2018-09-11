package javabean;

import java.util.ArrayList;

public class UserPage {
	private ArrayList<User> UserList = null;
	private int pageSize;
	private int totalPage;
	
	public ArrayList<User> getUserList() {
		return UserList;
	}
	public void setUserList(ArrayList<User> UserList) {
		this.UserList = UserList;
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
	
	public ArrayList<User> getPage(int curPage){
		totalPage = UserList.size()/pageSize;
		if(UserList.size()%pageSize != 0) {
			totalPage++;
		}
		if(curPage>pageSize || curPage<0) {
			return null;
		}
		int begin = (curPage-1) * pageSize;
		ArrayList<User> ans = new ArrayList<User>(pageSize);
		int count = 0;
		for(int i=begin;i<UserList.size() && count<pageSize;++i) {
			ans.add(UserList.get(i));
			count++;
		}
		return ans;
	}
}
