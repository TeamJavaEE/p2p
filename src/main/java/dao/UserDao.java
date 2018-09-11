package dao;

import java.util.ArrayList;

import javabean.User;

public interface UserDao {
	/*
	 * 登录，
	 * 如果用户与密码匹配，返回1
	 * 如果用户与密码不匹配，返回0
	 * 若果用户名不存在，返回2
	 */
	int login(User user);
	
	/*
	 * 注册新用户
	 * 如果用户名已经存在，返回2
	 * 若果注册成功，返回1
	 * 其他异常返回0
	 */
	int register(User user);

	/*
	 * 查询用户名
	 */
	String getUserNameByUserId(int userId);
	
	/*
	 * 更新用户信息
	 */
	boolean updateUser(User user);
	
	/*
	 * 得到所有用户信息
	 */
	ArrayList<User> getAllUsers();
	
	/*
	 * 更改用户状态
	 */
	boolean changeUserStatus(int userId, int status);
	
	/*
	 * 通过用户ID删除用户
	 */
	boolean deleteUserById(int userId);
	
	/*
	 * 设置审核是否通过
	 */
	boolean checkNewUser(int userId, int pass);
	
	/*
	 * 获取未审核得用户
	 */
	ArrayList<User> getUncheckedUser();
}
