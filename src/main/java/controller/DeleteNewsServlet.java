package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.NewsDao;
import dao.impl.NewsDaoImpl;
import javabean.User;

/*
 * 删除新闻控制器
 */
@WebServlet("/DeleteNewsServlet")
public class DeleteNewsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String newsIdStr = request.getParameter("newsId");
		if(newsIdStr == null) {
			request.setAttribute("message", "删除新闻，你怎么给我传了一个空的newsId");
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		}
		else {
			NewsDao newsDao = new NewsDaoImpl();
			int newsId = Integer.parseInt(newsIdStr);
			int userId = ((User)request.getSession().getAttribute("user")).getUserId();
			User user= (User)request.getSession().getAttribute("user");
			if(newsDao.isUserCreateNews(userId, newsId) || user.getUserType()==2) {
				if(newsDao.deleteNewsByNewsId(newsId)) {
					request.setAttribute("message", "删除成功");
					request.getRequestDispatcher("/message.jsp").forward(request, response);
				} else {
					request.setAttribute("message", "与数据库连接异常，无法删除");
					request.getRequestDispatcher("/message.jsp").forward(request, response);
				}
			}else {
				request.setAttribute("message", "这个新闻不是你创建的，你是无法删除的ok?请使用我提供的按钮删除，不要给我发不符合要求的请求");
				request.getRequestDispatcher("/message.jsp").forward(request, response);
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
