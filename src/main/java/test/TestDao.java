package test;


import java.util.Date;

import org.junit.Test;

import dao.NewsDao;
import dao.UserDao;
import dao.impl.CommentDaoImpl;
import dao.impl.NewsDaoImpl;
import dao.impl.UserDaoImpl;
import javabean.News;
import javabean.Page;
import javabean.User;

public class TestDao {
	
	@Test
	public void testLogin() {
		User user = new User();
		user.setUserName("adin");
		user.setUserPassword("13abc");
		UserDao dao = new UserDaoImpl();
		System.out.println(dao.login(user));
		System.out.println(user.getUserId());
		System.out.println(user.getUserName());
		System.out.println(user.getUserPassword());
		System.out.println(user.getUserPicture());
		System.out.println(user.getUserRegisterTime());
		System.out.println(user.getUserSex());
		System.out.println(user.getUserHobby());
		System.out.println(user.getUserType());
		System.out.println(user.getUserComfirm());
		System.out.println(user.getUserStatus());
	}
	
	@Test
	public void testRegister() {
		UserDao dao = new UserDaoImpl();
		User user = new User();
		user.setUserName("haibin");
		user.setUserPassword("123456");
		user.setUserType(0);
		System.out.println(dao.register(user));
	}
	
	@Test
	public void testNewsDao() {
		NewsDao newsDao = new NewsDaoImpl();
		News news = new News();
		news.setNewsName("新闻4");
		news.setNewsType("经济");
		news.setNewsPicture("图片路径");
		news.setNewsContent("新闻内容");
		int userId = 8;
		news.setUserId(userId);
		news.setNewsPublishTime(new Date());
		news.setNewsStatus(2);
		newsDao.addNews(news);
	}
	
	@Test
	public void testNewsDaoGetNews() {
		Page page = new Page();
		page.setPageSize(5);
		NewsDao newsDao = new NewsDaoImpl();
		newsDao.getNewsPageByUserId(page, 8);
		System.out.println(page.getPage(1));
	}
	
	@Test
	public void testNewsHtml() {
		String part1 = "<form action='<%=request.getContextPath()%>/EditNewsServlet' method='post' enctype='multipart/form-data'>新闻标题<input type='text' name='news_name' value='";
		String part2 = "'><br>选择新闻所属类型<select name='news_type'>";
		String part3 = "</select><br>上传新闻简图<input type='file' name='news_picture'><div><script id='editor' type='text/plain' style='height: 500px;'>";
		String part4 = "</script></div><input class='btn' type='submit' value='修改新闻'></form>";
		
		
		String html = part1 + "新闻标题" + part2;
		String types[] = {"政治", "经济", "社会", "国际", "科技", "其他"};
		String newsType = "经济";
		for(int i=0;i<types.length;++i) {
			if(types[i].equals(newsType)) {
				html += "<option value='" + types[i] + "' selected>" + types[i] + "</option>";
			} else {
				html += "<option value='" + types[i] + "'>" + types[i] + "</option>";
			}
		}
		html = html + part3 + "新闻内日哦那个" + part4;
		System.out.println(html);
	}
	
	@Test
	public void testCommentDao() {
		new CommentDaoImpl().addComment(8, 4, "第一个评论");
	}
}
