package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import dao.CommentDao;
import javabean.Comment;
import javabean.Row;
import jdbc.JDBCUtil;

public class CommentDaoImpl implements CommentDao{
	@Override
	public boolean addComment(int userId, int newsId, String content) {
		Connection conn = null;
		String sql = null;
		PreparedStatement ps = null;
		int rs = -1;
		conn = JDBCUtil.getConn();
		sql = "insert into news_comment values(null,?,?,?,?)";
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			ps.setInt(2, newsId);
			java.util.Date date = new java.util.Date();
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			ps.setString(3, simpleDateFormat.format(date));
			ps.setString(4, content);
			rs = ps.executeUpdate();
			if(rs!=-1) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.release(conn, ps);
		}
		return false;
	}

	@Override
	public boolean deleteComment(int newsCommentId) {
		Connection conn = null;
		String sql = null;
		PreparedStatement ps = null;
		int rs = -1;
		conn = JDBCUtil.getConn();
		sql = "delete from news_comment where news_comment_id=?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, newsCommentId);
			rs = ps.executeUpdate();
			if(rs!=-1) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.release(conn, ps);
		}
		return false;
	}

	@Override
	public ArrayList<Comment> getCommentsByNewsId(int newsId) {
		ArrayList<Comment> arr = new ArrayList<Comment>();
		Connection conn = null;
		String sql = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		conn = JDBCUtil.getConn();
		sql = "select news_comment_id, user.user_id, user_name, user_picture, "
				+ "news_id, news_comment_time, news_comment_content "
				+ "from news_comment, user where "
				+ "user.user_id=news_comment.user_id and news_id=?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, newsId);
			rs = ps.executeQuery();
			while(rs.next()) {
				Comment comment = new Comment();
				comment.setNewsCommentId(rs.getInt(1));
				comment.setUserId(rs.getInt(2));
				comment.setUserName(rs.getString(3));
				comment.setUserPicture(rs.getString(4));
				comment.setNewsId(rs.getInt(5));
				comment.setNewsCommentTime(rs.getTimestamp(6));
				comment.setNewsCommentContent(rs.getString(7));
				arr.add(comment);				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.release(conn, ps, rs);
		}
		return arr;
	}

	@Override
	public boolean updateComment(int newsCommentId, String content) {
		Connection conn = null;
		String sql = null;
		PreparedStatement ps = null;
		int rs = -1;
		conn = JDBCUtil.getConn();
		sql = "update news_comment set news_comment_time=?, news_comment_content = ?"
				+ " where news_comment_id=?";
		try {
			ps = conn.prepareStatement(sql);
			java.util.Date date = new java.util.Date();
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			ps.setString(1, simpleDateFormat.format(date));
			ps.setString(2, content);
			ps.setInt(3, newsCommentId);
			rs = ps.executeUpdate();
			if(rs!=-1) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.release(conn, ps);
		}
		return false;
	}

	@Override
	public ArrayList<Row> getLastestNumber(String interval) {
		ArrayList<Row> arr = new ArrayList<Row>();
		String sql = null;
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = JDBCUtil.getConn();
			sql = "select user_name, count(*) "
					+ "from news_comment, user where news_comment.user_id=user.user_id " 
					+ "and news_comment_time>date_sub(curdate(),interval " + interval + ") "
					+ "group by user.user_id";
			st = conn.createStatement();
			//System.out.println(sql);
			rs = st.executeQuery(sql);
			while(rs.next()) {
				Row row = new Row();
				row.setUserName(rs.getString(1));
				row.setNumber(rs.getInt(2));
				arr.add(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.release(conn, st,rs);
		}
		return arr;
	}

	@Override
	public ArrayList<Row> getCommentsNumber() {
		ArrayList<Row> arr = new ArrayList<Row>();
		String sql = null;
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = JDBCUtil.getConn();
			sql = "select user_name, count(*) "
					+ "from news_comment, user where news_comment.user_id=user.user_id " 
					+ "group by user.user_id";
			st = conn.createStatement();
			//System.out.println(sql);
			rs = st.executeQuery(sql);
			while(rs.next()) {
				Row row = new Row();
				row.setUserName(rs.getString(1));
				row.setNumber(rs.getInt(2));
				arr.add(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.release(conn, st,rs);
		}
		return arr;
	}

	@Override
	public int getDataBetween(String start, String end) {
		String sql = null;
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = JDBCUtil.getConn();
			sql = "select count(*) "
					+ "from news_comment where "
					+ "news_comment_time between '" 
					+ start + "' and '" + end + "'";
			st = conn.createStatement();
			//System.out.println(sql);
			rs = st.executeQuery(sql);
			if(rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.release(conn, st,rs);
		}
		return -1;
	}	
}
