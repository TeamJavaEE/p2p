package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.impl.UserDaoImpl;
import javabean.User;

/**
 * 审核新用户
 */
@WebServlet("/CheckNewUser")
public class CheckNewUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String passStr = request.getParameter("pass");
		String userIdStr = request.getParameter("userId");
		if(passStr == null || userIdStr == null) {
			ArrayList<User> arr = new UserDaoImpl().getUncheckedUser();
			request.setAttribute("newUsers", arr);
			request.getRequestDispatcher("/UserManage/CheckNewUser.jsp").forward(request, response);
		}else {
			int userId = Integer.parseInt(userIdStr);
			int pass = Integer.parseInt(passStr);
			if(new UserDaoImpl().checkNewUser(userId, pass)) {
				response.sendRedirect(request.getContextPath() + "/CheckNewUser");
			}else {
				request.setAttribute("message", "数据库出错");
				request.getRequestDispatcher("/message.jsp").forward(request, response);
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
