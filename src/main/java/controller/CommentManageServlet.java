package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CommentDao;
import dao.impl.CommentDaoImpl;
import javabean.User;

/**
 * 评论控制器
 */
@WebServlet("/CommentManageServlet")
public class CommentManageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CommentDao commentDao = new CommentDaoImpl();
		String type = request.getParameter("type");
		String newsIdStr = request.getParameter("newsId");
		User user = (User)request.getSession().getAttribute("user");
		if(type==null || newsIdStr==null) {
			request.setAttribute("message", "get参数type为null或者newsId为null");
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		}else {
			if(type.equals("submit")) {
				String newsCommentContent = request.getParameter("newsCommentContent");
				if(newsCommentContent==null) {
					request.setAttribute("message", "get参数newsCommentContent==null 或 newsIdStr==null");
					request.getRequestDispatcher("/message.jsp").forward(request, response);
				} else {
					if(!commentDao.addComment(user.getUserId(), Integer.parseInt(newsIdStr), newsCommentContent)) {
						request.setAttribute("message", "提交评论到数据库出错");
						request.getRequestDispatcher("/message.jsp").forward(request, response);
					}
				}
			} else if(type.equals("edit")) {
				String newsCommentIdStr = request.getParameter("newsCommentId");
				String content = request.getParameter("newsCommentContent");
				if(newsCommentIdStr == null || content==null) {
					request.setAttribute("message", "get参数newsCommentId==null 或者 newsCommentContent==null");
					request.getRequestDispatcher("/message.jsp").forward(request, response);
				} else {
					int newsCommentId = Integer.parseInt(newsCommentIdStr);
					if(!commentDao.updateComment(newsCommentId, content)) {
						request.setAttribute("message", "修改评论到数据库出错");
						request.getRequestDispatcher("/message.jsp").forward(request, response);
					} 
				}
			} else if(type.equals("delete")) {
				if(user.getUserType() != 2) {
					request.setAttribute("message", "只有管理员具有删除评论的权力");
					request.getRequestDispatcher("/message.jsp").forward(request, response);
				}else {
					String newsCommentIdStr = request.getParameter("newsCommentId");
					commentDao.deleteComment(Integer.parseInt(newsCommentIdStr));
				}
			} else {
				request.setAttribute("message", "type参数值有误，只有submit, edit, delete");
				request.getRequestDispatcher("/message.jsp").forward(request, response);
			}
			response.sendRedirect(request.getContextPath()+"/ShowNewsServlet?newsId=" + newsIdStr);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
