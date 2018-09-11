package dao;

import java.util.ArrayList;


import javabean.News;
import javabean.Page;
import javabean.Row;

public interface NewsDao {
	
	/*
	 * 添加新闻 
	 */
	boolean addNews(News news);
	
	/*
	 * 得到一页新闻的内容
	 */
	Page getNewsPage(Page page);
	
	/*
	 * 根据用户id得到该用户发布的一页新闻
	 */
	Page getNewsPageByUserId(Page page, int userId);
	
	/*
	 * 根据新闻id删除新闻
	 */
	boolean deleteNewsByNewsId(int newsId);
	
	/*
	 * 判断新闻与作者是否匹配，防止删除掉别人的新闻
	 */
	boolean isUserCreateNews(int userId, int newsId);
	
	/*
	 * 根据新闻id得到新闻内容
	 */
	News getNewsByNewsId(int newsId);
	
	/*
	 * 跟新新闻
	 */
	boolean updateNews(News news);
	
	/*
	 * 根据类型获得新闻
	 */
	ArrayList<News> getLatestNewsByNewsType(String newsType, int number);
	ArrayList<News> getLatestNewsByNewsType(String newsType);
	
	/*
	 * 得到最新的新闻
	 */
	ArrayList<News> getLatestNews(int number);
	
	/*
	 * 得到未审核的新闻
	 */
	ArrayList<News> getUncheckedNews();
	
	/*
	 * 审核新闻
	 */
	boolean checkNews(int newId, int pass);
	
	
	/*
	 * 获得近期一段时间内，每个发布员 发布的新闻的数量
	 */
	public ArrayList<Row> getLastestNumber(String interval);	
	
	/*
	 * 获得每个发布员 发布了多少新闻
	 */
	public ArrayList<Row> getNewsNumber();
	
	/*
	 * 获得段时间内的新闻数量
	 */
	int getDataBetween(String start, String end);
}

