package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.NewsDao;
import dao.impl.NewsDaoImpl;
import javabean.Page;
import javabean.User;

/**
 * 新闻管理界面的控制器，控制显示所有新闻或者用户的新闻
 */
@WebServlet("/NewsManageServlet")
public class NewsManageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		Page page = new Page();
		page.setPageSize(pageSize);
		int userType = ((User)request.getSession().getAttribute("user")).getUserType();
		if(userType==2) {
			NewsDao newsDao = new NewsDaoImpl();
			newsDao.getNewsPage(page);
			request.setAttribute("page", page.getPage(curPage));
			request.setAttribute("totalPage", page.getTotalPage());
			request.setAttribute("curPage", curPage);
			request.getRequestDispatcher("/NewsManage/NewsManage.jsp").forward(request, response);
		}else if(userType==1) {
			NewsDao newsDao = new NewsDaoImpl();
			int userId = ((User)request.getSession().getAttribute("user")).getUserId();
			newsDao.getNewsPageByUserId(page, userId);
			request.setAttribute("page", page.getPage(curPage));
			request.setAttribute("totalPage", page.getTotalPage());
			request.setAttribute("curPage", curPage);
			request.getRequestDispatcher("/NewsManage/NewsManage.jsp").forward(request, response);
		}else {
			String message = "普通用户，不具备新闻管理的权限";
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
