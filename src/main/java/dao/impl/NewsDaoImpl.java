package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import dao.NewsDao;
import javabean.News;
import javabean.Page;
import javabean.Row;
import jdbc.JDBCUtil;

public class NewsDaoImpl implements NewsDao {

	@Override
	public boolean addNews(News news) {
		Connection conn = null;
		String sql = null;
		Statement st = null;
		int rs = -1;
		try {
			conn = JDBCUtil.getConn();
			sql = "INSERT INTO NEWS VALUES("; 
			sql = sql + news.getUserId() + ",NULL,'";
			sql = sql + news.getNewsName() + "','";
			sql = sql + news.getNewsContent() + "','";
			sql = sql + news.getNewsType() + "','";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sql = sql + simpleDateFormat.format(news.getNewsPublishTime()) + "',NULL,'";
			sql = sql + news.getNewsPicture() + "',";
			sql = sql + news.getNewsStatus() + ")";
			st = conn.createStatement();
			//System.out.println(sql);
			rs = st.executeUpdate(sql);
			if(rs>0) {
				return true;
			} 
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.release(conn, st);
		}
		return false;
	}

	@Override
	public Page getNewsPage(Page page) {
		Connection conn = null;
		String sql = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = JDBCUtil.getConn();
			sql = "select news_id, news_name, news_type, news_publish_time, news_status, user_name "
					+ "from news, user "
					+ "where news.user_id=user.user_id";
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			ArrayList<News> newsList = new ArrayList<News>();
			while(rs.next()) {
				News news = new News();
				news.setNewsId(rs.getInt(1));
				news.setNewsName(rs.getString(2));
				news.setNewsType(rs.getString(3));
				news.setNewsPublishTime(rs.getTimestamp(4));
				news.setNewsStatus(rs.getInt(5));
				news.setUserName(rs.getString(6));
				newsList.add(news);
			} 
			page.setNewsList(newsList);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.release(conn, st, rs);
		}
		return page;
	}

	@Override
	public Page getNewsPageByUserId(Page page, int userId) {
		Connection conn = null;
		String sql = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = JDBCUtil.getConn();
			sql = "select news_id, news_name, news_type, news_publish_time, news_status, user_name "
					+ "from news, user "
					+ "where news.user_id=user.user_id and news.user_id = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			rs = ps.executeQuery();
			ArrayList<News> newsList = new ArrayList<News>();
			while(rs.next()) {
				News news = new News();
				news.setNewsId(rs.getInt(1));
				news.setNewsName(rs.getString(2));
				news.setNewsType(rs.getString(3));
				news.setNewsPublishTime(rs.getTimestamp(4));
				news.setNewsStatus(rs.getInt(5));
				news.setUserName(rs.getString(6));
				newsList.add(news);
			} 
			page.setNewsList(newsList);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.release(conn, ps, rs);
		}
		return page;
	}

	@Override
	public boolean deleteNewsByNewsId(int newsId) {
		Connection conn = null;
		String sql = null;
		PreparedStatement ps = null;
		int rs = -1;
		try {
			conn = JDBCUtil.getConn();
			sql = "delete from news where news_id = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, newsId);
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
	public boolean isUserCreateNews(int userId, int newsId) {
		Connection conn = null;
		String sql = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = JDBCUtil.getConn();
			sql = "select * from news where user_id = ? and news_id = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			ps.setInt(2, newsId);
			rs = ps.executeQuery();
			if(rs.next()) {
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
	public News getNewsByNewsId(int newsId) {
		News news = null;
		Connection conn = null;
		String sql = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = JDBCUtil.getConn();
			sql = "select * from news where news_id = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, newsId);
			rs = ps.executeQuery();
			if(rs.next()) {
				news = new News();
				news.setUserId(rs.getInt(1));
				news.setNewsId(rs.getInt(2));
				news.setNewsName(rs.getString(3));
				news.setNewsContent(rs.getString(4));
				news.setNewsType(rs.getString(5));
				news.setNewsPublishTime(rs.getTimestamp(6));
				news.setNewsContentComfirm(rs.getString(7));
				news.setNewsPicture(rs.getString(8));
				news.setNewsStatus(rs.getInt(9));
			} 
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.release(conn, ps, rs);
		}
		return news;
	}

	@Override
	public boolean updateNews(News news) {
		Connection conn = null;
		String sql = null;
		Statement st = null;
		int rs = -1;
		try {
			conn = JDBCUtil.getConn();
			sql = "update news set news_name='";
			sql = sql + news.getNewsName() + "',news_content='";
			sql = sql + news.getNewsContent() + "',news_picture='";
			sql = sql + news.getNewsPicture() + "',news_status=2, news_type='";
			sql = sql + news.getNewsType();
			sql = sql + "' where news_id=" + news.getNewsId();
			//System.out.println(sql);
			st = conn.createStatement();
			rs = st.executeUpdate(sql);
			if(rs!=-1) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.release(conn, st);
		}
		return false;
	}

	@Override
	public ArrayList<News> getLatestNewsByNewsType(String newsType, int number) {
		ArrayList<News> arr = new ArrayList<News>();
		Connection conn = null;
		String sql = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = JDBCUtil.getConn();
			if(number>0) {
				sql = "select news_id, news_name, news_picture, news_publish_time, news_status from news where news_status=1 and news_type=? ORDER BY news_id DESC LIMIT ?";
				ps = conn.prepareStatement(sql);
				ps.setString(1, newsType);
				ps.setInt(2, number);
				rs = ps.executeQuery();
				while(rs.next()) {
					News news = new News();
					news.setNewsId(rs.getInt(1));
					news.setNewsName(rs.getString(2));
					news.setNewsPicture(rs.getString(3));
					news.setNewsPublishTime(rs.getTimestamp(4));
					news.setNewsStatus(rs.getInt(5));
					arr.add(news);
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.release(conn, ps, rs);
		}
		return arr;
	}

	@Override
	public ArrayList<News> getLatestNewsByNewsType(String newsType) {
		ArrayList<News> arr = new ArrayList<News>();
		Connection conn = null;
		String sql = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = JDBCUtil.getConn();
			sql = "select news_id, news_name, news_picture, news_publish_time, news_status from news where news_status=1 and news_type=? ORDER BY news_id DESC";
			ps = conn.prepareStatement(sql);
			ps.setString(1, newsType);
			rs = ps.executeQuery();
			while(rs.next()) {
				News news = new News();
				news.setNewsId(rs.getInt(1));
				news.setNewsName(rs.getString(2));
				news.setNewsPicture(rs.getString(3));
				news.setNewsPublishTime(rs.getTimestamp(4));
				news.setNewsStatus(rs.getInt(5));
				arr.add(news);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.release(conn, ps, rs);
		}
		return arr;
	}

	@Override
	public ArrayList<News> getLatestNews(int number) {
		ArrayList<News> arr = new ArrayList<News>();
		Connection conn = null;
		String sql = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = JDBCUtil.getConn();
			if(number>0) {
				sql = "select news_id, news_name, news_picture, news_publish_time, news_status from news where news_status=1 ORDER BY news_id DESC LIMIT ?";
				ps = conn.prepareStatement(sql);
				ps.setInt(1, number);
				rs = ps.executeQuery();
				while(rs.next()) {
					News news = new News();
					news.setNewsId(rs.getInt(1));
					news.setNewsName(rs.getString(2));
					news.setNewsPicture(rs.getString(3));
					news.setNewsPublishTime(rs.getTimestamp(4));
					news.setNewsStatus(rs.getInt(5));
					arr.add(news);
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.release(conn, ps, rs);
		}
		return arr;
	}

	@Override
	public ArrayList<News> getUncheckedNews() {
		ArrayList<News> arr = new ArrayList<News>();
		Connection conn = null;
		String sql = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = JDBCUtil.getConn();
			sql = "select user_name, news_id, news_name, news_type, news_publish_time, news_picture "
					+ "from news, user where news.user_id=user.user_id " 
					+ "and news_status=2;";
			st = conn.createStatement();
			//System.out.println(sql);
			rs = st.executeQuery(sql);
			while(rs.next()) {
				News news = new News();
				news.setUserName(rs.getString(1));
				news.setNewsId(rs.getInt(2));
				news.setNewsName(rs.getString(3));
				news.setNewsType(rs.getString(4));
				news.setNewsPublishTime(rs.getTimestamp(5));
				news.setNewsPicture(rs.getString(6));
				arr.add(news);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.release(conn, st,rs);
		}
		return arr;
	}

	@Override
	public boolean checkNews(int newsId, int pass) {
		Connection conn = null;
		String sql = null;
		PreparedStatement ps = null;
		int rs = -1;
		try {
			conn = JDBCUtil.getConn();
			sql = "update news set news_status = ? where news_id = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, pass);
			ps.setInt(2, newsId);
			rs = ps.executeUpdate();
			if(rs>0) {
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
					+ "from news, user where news.user_id=user.user_id " 
					+ "and news_publish_time>date_sub(curdate(),interval " + interval + ") "
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
	public ArrayList<Row> getNewsNumber() {
		ArrayList<Row> arr = new ArrayList<Row>();
		String sql = null;
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = JDBCUtil.getConn();
			sql = "select user_name, count(*) "
					+ "from news, user where news.user_id=user.user_id " 
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
					+ "from news where "
					+ "news_publish_time between '" 
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
