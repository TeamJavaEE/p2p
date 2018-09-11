package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import dao.UserDao;
import javabean.User;
import jdbc.JDBCUtil;

public class UserDaoImpl implements UserDao {

	@Override
	public int login(User user) {
		Connection conn = null;
		String sql = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = JDBCUtil.getConn();
			sql = "select * from user where user_name = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, user.getUserName());
			rs = ps.executeQuery();
			if(rs.next()) {
				String userPassword = rs.getString(3);
				if(userPassword.equals(user.getUserPassword())) {
					user.setUserId(rs.getInt(1));
					user.setUserPassword(userPassword);
					user.setUserPicture(rs.getString(4));
					user.setUserRegisterTime(rs.getTimestamp(5));
					user.setUserSex(rs.getString(6));
					user.setUserHobby(rs.getString(7));
					user.setUserType(rs.getInt(8));
					user.setUserComfirm(rs.getInt(9));
					user.setUserStatus(rs.getInt(10));
					return 1;
				} else {
					return 0;
				}
			} else {
				return 2;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.release(conn, ps, rs);
		}
		return 0;
	}

	
	private boolean isExist(String userName) {
		Connection conn = null;
		String sql = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = JDBCUtil.getConn();
			sql = "select * from user where user_name = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, userName);
			rs = ps.executeQuery();
			if(rs.next()) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.release(conn, ps, rs);
		}
		return false;
	}
	@Override
	public int register(User user) {
		if(isExist(user.getUserName())) {
			return 2;
		}
		Connection conn = null;
		String sql = null;
		Statement st = null;
		int rs = -1;
		try {
			conn = JDBCUtil.getConn();
			sql = "INSERT INTO USER VALUES(NULL,'";
			sql = sql + user.getUserName() + "','";
			sql = sql + user.getUserPassword() + "',NULL,'";
			java.util.Date date = new java.util.Date();
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sql = sql + simpleDateFormat.format(date) + "',NULL,NULL,";
			sql = sql + user.getUserType() + "," + user.getUserComfirm() + ",1);";
			st = conn.createStatement();
			//System.out.println(sql);
			rs = st.executeUpdate(sql);
			if(rs>0) {
				return 1;
			} 
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.release(conn, st);
		}
		return 0;
	}


	@Override
	public String getUserNameByUserId(int userId) {
		Connection conn = null;
		String sql = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String userName = null;
		try {
			conn = JDBCUtil.getConn();
			sql = "select user_name from user where user_id = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			rs = ps.executeQuery();
			if(rs.next()) {
				userName = rs.getString(1);
			} 
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.release(conn, ps, rs);
		}
		return userName;
	}


	@Override
	public boolean updateUser(User user) {
		Connection conn = null;
		String sql = null;
		Statement st = null;
		int rs = -1;
		try {
			conn = JDBCUtil.getConn();
			sql = "update user set user_password='";
			sql = sql + user.getUserPassword() + "',user_picture='";
			sql = sql + user.getUserPicture() + "',user_sex='";
			sql = sql + user.getUserSex() + "',user_hobby='";
			sql = sql + user.getUserHobby() + "' where user_id=" + user.getUserId();
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
	public ArrayList<User> getAllUsers() {
		ArrayList<User> arr = new ArrayList<User>();
		Connection conn = null;
		String sql = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = JDBCUtil.getConn();
			sql = "select * from user";
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()) {
				User user = new User();
				user.setUserId(rs.getInt(1));
				user.setUserName(rs.getString(2));
				user.setUserPicture(rs.getString(4));
				user.setUserRegisterTime(rs.getTimestamp(5));
				user.setUserSex(rs.getString(6));
				user.setUserHobby(rs.getString(7));
				user.setUserType(rs.getInt(8));
				user.setUserComfirm(rs.getInt(9));
				user.setUserStatus(rs.getInt(10));
				arr.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.release(conn, st, rs);
		}
		return arr;
	}


	@Override
	public boolean changeUserStatus(int userId, int status) {
		Connection conn = null;
		String sql = null;
		PreparedStatement ps = null;
		int rs = -1;
		try {
			conn = JDBCUtil.getConn();
			sql = "update user set user_status = ? where user_id = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, status);
			ps.setInt(2, userId);
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
	public boolean deleteUserById(int userId) {
		Connection conn = null;
		String sql = null;
		PreparedStatement ps = null;
		int rs = -1;
		try {
			conn = JDBCUtil.getConn();
			sql = "delete from user where user_id = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
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
	public boolean checkNewUser(int userId, int pass) {
		Connection conn = null;
		String sql = null;
		PreparedStatement ps = null;
		int rs = -1;
		try {
			conn = JDBCUtil.getConn();
			sql = "update user set user_confirm = ? where user_id = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, pass);
			ps.setInt(2, userId);
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
	public ArrayList<User> getUncheckedUser() {
		ArrayList<User> arr = new ArrayList<User>();
		Connection conn = null;
		String sql = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = JDBCUtil.getConn();
			sql = "select user_id, user_name, user_register_time, user_type "
					+ "from user where user_type!=0 "
					+ "and user_confirm = 2;";
			st = conn.createStatement();
			//System.out.println(sql);
			rs = st.executeQuery(sql);
			while(rs.next()) {
				User user = new User();
				user.setUserId(rs.getInt(1));
				user.setUserName(rs.getString(2));
				user.setUserRegisterTime(rs.getTimestamp(3));
				user.setUserType(rs.getInt(4));
				arr.add(user);
			}
			return arr;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.release(conn, st,rs);
		}
		return arr;
	}
}
