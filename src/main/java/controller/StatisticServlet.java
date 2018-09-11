package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CommentDao;
import dao.NewsDao;
import dao.impl.CommentDaoImpl;
import dao.impl.NewsDaoImpl;
import javabean.MonthDataRow;
import javabean.Row;

/**
 * 数据统计控制器
 */
@WebServlet("/StatisticServlet")
public class StatisticServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String type = request.getParameter("type");
		if(type==null) {
			request.setAttribute("message", "get请求参数type为空");
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		}
		
		// 自定义排序
		Comparator<Row> c = new Comparator<Row>() {
			@Override
			public int compare(Row o1, Row o2) {
				if((int)o1.getNumber()<(int)o2.getNumber())
					return 1;
				//注意！！返回值必须是一对相反数，否则无效。jdk1.7以后就是这样。
		//		else return 0; //无效
				else return -1;
			}
		};
		ArrayList<Row> rows = null; 
		NewsDao newsDao = new NewsDaoImpl();
		CommentDao commentDao = new CommentDaoImpl();
		
		if(type.equals("0")) {
			ArrayList<MonthDataRow> arr = new ArrayList<MonthDataRow>(12);
			Calendar cale = null;  
	        cale = Calendar.getInstance();  
	        int curYear = cale.get(Calendar.YEAR);  
	        int curMonth = cale.get(Calendar.MONTH) + 1;
	        String start = null;
	        String end = null;
			for(int i=0;i<=11;++i) {
				MonthDataRow monthDataRow = new MonthDataRow();
				int startMonth = curMonth - i;
				int startYear = curYear;
				if(startMonth <= 0) {
					startMonth += 12;
					startYear --;
				}
				int endMonth = startMonth + 1;
				int endYear = startYear;
				if(endMonth >= 13) {
					endMonth -= 12;
					endYear++;
				}
				start = startYear + "-" + startMonth + "-01 00:00:00";
				end = endYear + "-" + endMonth + "-01 00:00:00";
				//System.out.println(start + " " + end);
				monthDataRow.setNewsNumber(newsDao.getDataBetween(start, end));
				monthDataRow.setCommentNumber(commentDao.getDataBetween(start, end));
				monthDataRow.setRowName(startYear + "-" + startMonth);
				arr.add(monthDataRow);
			}
			request.setAttribute("arr", arr);
			request.setAttribute("title", "近一年12个月新闻数量与评论数量统计");
			request.getRequestDispatcher("/StatisticManage/StatisticYear.jsp").forward(request, response);
		}else if(type.equals("1")) {
			
			String time = request.getParameter("time");
			if(time==null) {
				request.setAttribute("message", "get请求参数time为空");
				request.getRequestDispatcher("/message.jsp").forward(request, response);
			}else {
				if(time.equals("year")) {
					rows = newsDao.getLastestNumber("1 year");
					request.setAttribute("title", "近一年新闻数据统计");
				} else if(time.equals("month")) {
					rows = newsDao.getLastestNumber("1 month");
					request.setAttribute("title", "近一月新闻数据统计");		
				} else if(time.equals("week")) {
					rows = newsDao.getLastestNumber("7 day");
					request.setAttribute("title", "近一周新闻数据统计");
				} else if(time.equals("all")) {
					rows = newsDao.getNewsNumber();
					request.setAttribute("title", "全部新闻数据统计");
				}
				rows.sort(c);
				request.setAttribute("rows", rows);
				request.getRequestDispatcher("/StatisticManage/StatisticPage.jsp").forward(request, response);
			}
		}else if(type.equals("2")) {
			
			String time = request.getParameter("time");
			if(time==null) {
				request.setAttribute("message", "get请求参数time为空");
				request.getRequestDispatcher("/message.jsp").forward(request, response);
			}else {
				if(time.equals("year")) {
					rows = commentDao.getLastestNumber("1 year");
					request.setAttribute("title", "近一年评论数据统计");
				} else if(time.equals("month")) {
					rows = commentDao.getLastestNumber("1 month");
					request.setAttribute("title", "近一月评论数据统计");		
				} else if(time.equals("week")) {
					rows = commentDao.getLastestNumber("7 day");
					request.setAttribute("title", "近一周评论数据统计");
				} else if(time.equals("all")) {
					rows = commentDao.getCommentsNumber();
					request.setAttribute("title", "全部评论数据统计");
				}
				rows.sort(c);
				request.setAttribute("rows", rows);
				request.getRequestDispatcher("/StatisticManage/StatisticPage.jsp").forward(request, response);
			}
		} else {
			
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
