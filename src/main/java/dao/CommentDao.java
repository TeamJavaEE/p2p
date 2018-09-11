package dao;

import java.util.ArrayList;

import javabean.Comment;
import javabean.Row;

public interface CommentDao {
	/*
	 * 增加评论 
	 */
	boolean addComment(int userId, int newsId, String content);


	/*
	 * 删除评论
	 */
	boolean deleteComment(int newsCommentId);
	
	/*
	 * 获取新闻评论
	 */
	ArrayList<Comment> getCommentsByNewsId(int newsId);
	
	/*
	 * 更新评论 
	 */
	boolean updateComment(int newsCommentId, String content);

	/*
	 * 获得近期一段时间内，每个用户 的评论数目
	 */
	public ArrayList<Row> getLastestNumber(String interval);	
	
	/*
	 * 获得用户 总的评论数
	 */
	public ArrayList<Row> getCommentsNumber();
	
	/*
	 * 获得一段时间内产生的评论数目
	 */
	public int getDataBetween(String start, String end);
}
