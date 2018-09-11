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
 * 注册控制器
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
   		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userTypeStr = request.getParameter("user_type");
		String userName = request.getParameter("user");
		String userPassword = request.getParameter("pw");
		String message = null;
		if(userTypeStr==null || userName==null || userPassword==null) {
			message = "注册失败，由于用户类型，用户名，密码信息不齐全！！！";
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		} else {
			int userType = Integer.parseInt(userTypeStr);
			User user = new User();
			user.setUserName(userName);
			user.setUserPassword(userPassword);
			user.setUserType(userType);
			UserDao userDao = new UserDaoImpl();
			if(userType == 0) {
				user.setUserComfirm(1);
			}else {
				user.setUserComfirm(2);
			}
			int rs = userDao.register(user);
			if(rs == 2) {
				message = "注册失败，该用户名已被注册，请更换用户名重新注册！！！";
				request.setAttribute("message", message);
				request.getRequestDispatcher("/message.jsp").forward(request, response);
			} else if(rs == 1) {
				message = "注册成功，hello!";
				request.setAttribute("message", message);
				request.getRequestDispatcher("/message.jsp").forward(request, response);
			} else {
				message = "注册失败，异常";
				request.setAttribute("message", message);
				request.getRequestDispatcher("/message.jsp").forward(request, response);
			}
		}
	}

}
