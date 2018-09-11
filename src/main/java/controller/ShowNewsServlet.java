package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CommentDao;
import dao.impl.CommentDaoImpl;
import dao.impl.NewsDaoImpl;
import dao.impl.UserDaoImpl;
import javabean.Comment;
import javabean.News;


@WebServlet("/ShowNewsServlet")
public class ShowNewsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String newsIdStr = request.getParameter("newsId");
		if(newsIdStr==null) {
			request.setAttribute("message", "出错：原因：newsId为空");
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		}else {
			int newsId = Integer.parseInt(newsIdStr);
			News news = new NewsDaoImpl().getNewsByNewsId(newsId);
			request.setAttribute("news", news);
			String userName = new UserDaoImpl().getUserNameByUserId(news.getUserId());
			request.setAttribute("userName", userName);
			
			// 获取评论数据
			CommentDao commentDao = new CommentDaoImpl();
			ArrayList<Comment> comments = commentDao.getCommentsByNewsId(newsId);
			request.setAttribute("totalCommentsNumber", comments.size());
			request.setAttribute("comments", comments);
			request.getRequestDispatcher("/NewsTemplate.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
