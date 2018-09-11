package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserDao;
import dao.impl.UserDaoImpl;
import javabean.User;
import javabean.UserPage;

/**
 * 用户管理控制器
 */
@WebServlet("/UserManageServlet")
public class UserManageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = (User)request.getSession().getAttribute("user");
		if(user.getUserType() != 2) {
			request.setAttribute("message", "用户权限不够，无法管理用户");
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		}
		String pageSizeStr = request.getParameter("pageSize");
		String curPageStr = request.getParameter("curPage");
		int pageSize;
		int curPage;
		if(pageSizeStr == null || curPageStr == null) {
			pageSize = 5;
			curPage = 1;
		}else {
			pageSize = Integer.parseInt(pageSizeStr);
			curPage = Integer.parseInt(curPageStr);
		}
		UserPage userPage = new UserPage();
		userPage.setPageSize(pageSize);
		UserDao userDao = new UserDaoImpl();
		ArrayList<User> arr = new ArrayList<User>();
		arr = userDao.getAllUsers();
		userPage.setUserList(arr);
		request.setAttribute("page", userPage.getPage(curPage));
		request.setAttribute("curPage", curPage);
		request.setAttribute("totalPage", userPage.getTotalPage());
		request.getRequestDispatcher("/UserManage/UserManage.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
