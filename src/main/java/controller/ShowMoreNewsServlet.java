package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.NewsDao;
import dao.impl.NewsDaoImpl;
import javabean.News;
import javabean.Page;

/**
 * 显示更多新闻控制器
 */
@WebServlet("/ShowMoreNewsServlet")
public class ShowMoreNewsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String newsType = request.getParameter("newsType");
		if(newsType == null) {
			request.setAttribute("message", "新闻类型为空");
			request.getRequestDispatcher("/messsage.jsp").forward(request, response);
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
		Page page = new Page();
		page.setPageSize(pageSize);	
		NewsDao newsDao = new NewsDaoImpl();
		ArrayList<News> arr = newsDao.getLatestNewsByNewsType(newsType);
		page.setNewsList(arr);
		request.setAttribute("page", page.getPage(curPage));
		request.setAttribute("totalPage", page.getTotalPage());
		request.setAttribute("curPage", curPage);
		request.setAttribute("newsType", newsType);
		request.getRequestDispatcher("/MoreNews.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		doGet(request, response);
	}

}
