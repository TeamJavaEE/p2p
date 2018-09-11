package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.impl.NewsDaoImpl;
import javabean.News;
import javabean.User;

/**
 * 审核新发布的新闻
 */
@WebServlet("/CheckNews")
public class CheckNews extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user =(User)request.getSession().getAttribute("user");
		int userType = user.getUserType();
		if(userType != 2) {
			request.setAttribute("message", "没有审核新闻的权限");
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		} else {
			String newsIdStr = request.getParameter("newsId");
			String passStr = request.getParameter("pass");
			if(newsIdStr == null || passStr == null) {
				ArrayList<News> arr = new NewsDaoImpl().getUncheckedNews();
				request.setAttribute("uncheckedNews", arr);
				request.getRequestDispatcher("/NewsManage/CheckNews.jsp").forward(request, response);
			} else {
				int newsId = Integer.parseInt(newsIdStr);
				int pass = Integer.parseInt(passStr);
				if(new NewsDaoImpl().checkNews(newsId, pass)) {
					response.sendRedirect(request.getContextPath() + "/CheckNews");
				} else {
					request.setAttribute("message", "数据库出错");
					request.getRequestDispatcher("/message.jsp").forward(request, response);
				}
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
