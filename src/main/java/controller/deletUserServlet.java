package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.impl.UserDaoImpl;
import javabean.User;

/**
 * 删除用户控制器
 */
@WebServlet("/deletUserServlet")
public class deletUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = (User)request.getSession().getAttribute("user");
		if(user.getUserType() != 2) {
			request.setAttribute("message", "只有管理员才具有删除用户的权限");
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		} else {
			String userIdStr = request.getParameter("userId");
			String pageSizeStr = request.getParameter("pageSize");
			String curPageStr = request.getParameter("curPage");
			if(userIdStr == null || pageSizeStr == null || curPageStr == null) {
				request.setAttribute("message", "get请求缺少参数");
				request.getRequestDispatcher("/message.jsp").forward(request, response);
			}else {
				int userId = Integer.parseInt(userIdStr);
				if(new UserDaoImpl().deleteUserById(userId)) {
					response.sendRedirect(request.getContextPath()+"/UserManageServlet?pageSize="+pageSizeStr+"&curPage="+curPageStr);
				}else {
					request.setAttribute("message", "在连接数据过程中出错，删除失败");
					request.getRequestDispatcher("/message.jsp").forward(request, response);
				}
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
