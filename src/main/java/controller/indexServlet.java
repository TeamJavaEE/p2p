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

/**
 * 渲染首页控制器
 */
@WebServlet("/indexServlet")
public class indexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String types[] = {"政治", "经济", "社会", "国际", "科技", "其他"};
		NewsDao newsDao = new NewsDaoImpl();
		ArrayList<News> arr = null;
		for(int i=0;i<types.length;++i) {
			arr = newsDao.getLatestNewsByNewsType(types[i], 5);
			request.setAttribute("type"+i, arr);
		}
		// 生成最新的新闻数组
		request.setAttribute("latestNews", newsDao.getLatestNews(10));
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
