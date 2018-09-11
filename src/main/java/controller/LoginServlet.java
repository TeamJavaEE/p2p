package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import dao.UserDao;
import dao.impl.UserDaoImpl;
import javabean.User;

/**
 * 登录控制
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userName = request.getParameter("user");
		String userPassword = request.getParameter("pw");
		User user = new User();
		user.setUserName(userName);
		user.setUserPassword(userPassword);
		UserDao userDao = new UserDaoImpl();
		int rs = userDao.login(user); 
		if(rs == 1) {
			int userType = user.getUserType();
			int userComfirm = user.getUserComfirm();
			int userStatus = user.getUserStatus();
			// 判断是否通过审核
			if((userType == 1 || userType == 2) && (userComfirm == 0 || userComfirm==2)) {
				String message = null;
				if(userComfirm == 0) {
					message = "审核不通过";
				}else {
					message = "该账号还未审核，请联系管理员审核";
				}
				request.setAttribute("message", message);
				request.getRequestDispatcher("message.jsp").forward(request, response);
			} else if(userStatus==0){
				String message = "该账号已被停用，请联系管理员重新启用";
				request.setAttribute("message", message);
				request.getRequestDispatcher("message.jsp").forward(request, response);
			} else {
				request.getSession().setAttribute("user", user);
				request.getRequestDispatcher("/indexServlet").forward(request, response);
			}
		} else if(rs == 0){
			String message = "密码错误！,请重新登录！";
			request.setAttribute("message", message);
			request.getRequestDispatcher("message.jsp").forward(request, response);
		} else {
			String message = "用户名错误，请重新登录！";
			request.setAttribute("message", message);
			request.getRequestDispatcher("message.jsp").forward(request, response);
		}
	}
}
