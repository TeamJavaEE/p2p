package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import dao.impl.UserDaoImpl;

/**
 * 启用，停用用户
 */
@WebServlet("/ChangeUserStatusServlet")
public class ChangeUserStatusServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String status = request.getParameter("status");
		String pageSizeStr = request.getParameter("pageSize");
		String curPageStr = request.getParameter("curPage");
		String userIdStr = request.getParameter("userId");
		if(status == null || pageSizeStr == null || curPageStr == null) {
			request.setAttribute("message", "get请求缺少参数");
		}else {
			int userId = Integer.parseInt(userIdStr);
			new UserDaoImpl().changeUserStatus(userId, Integer.parseInt(status));
			response.sendRedirect(request.getContextPath()+"/UserManageServlet?pageSize="+pageSizeStr+"&curPage="+curPageStr);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
