package controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import dao.NewsDao;
import dao.impl.NewsDaoImpl;
import javabean.News;
import javabean.User;

/**
 *发布新闻控制
 */
@WebServlet("/PublishNewsServlet")
public class PublishNewsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 判断用户是否具备发布新闻的权限
		String message = null;
		int userType = ((User)request.getSession().getAttribute("user")).getUserType();
		if(userType == 0) {
			message = "普通用户，不具备发布新闻的权限";
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		}
		
		
		String news_name = null;
		String news_type = null;
		String news_picture = null;
		String news_content = null;
		
		// Create a factory for disk-based file items
		DiskFileItemFactory factory = new DiskFileItemFactory();

		// Configure a repository (to ensure a secure temp location is used)
		ServletContext servletContext = this.getServletConfig().getServletContext();
		File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
		factory.setRepository(repository);

		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);
		
		// 解决中文乱码
		upload.setHeaderEncoding("utf-8");
		
		// Parse the request
		try {
			List<FileItem> items = upload.parseRequest(request);
			// Process the uploaded items
			Iterator<FileItem> iter = items.iterator();
			while (iter.hasNext()) {
			    FileItem item = iter.next();
			    if (!item.isFormField()) {
			    	if(!item.getName().equals("")) {
			    		int userId = ((User)request.getSession().getAttribute("user")).getUserId();
				    	String fileName = userId + "_" + System.currentTimeMillis() + "_" + item.getName();
				    	news_picture = request.getServletContext().getContextPath() + "/filedata/news_picture/" + fileName;
				    	item.write(new File(request.getServletContext().getRealPath("/") + "\\filedata\\news_picture\\" + fileName));
			    	}	    	
			    } else { 
			    	if(item.getFieldName().equals("news_name")) {
			    		news_name = item.getString("utf-8");
			    	} else if(item.getFieldName().equals("editorValue")) {
			    		news_content = item.getString("utf-8");
			    	} else if(item.getFieldName().equals("news_type")) {
			    		news_type = item.getString("utf-8");
			    	}
			    }
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(news_name==null || news_picture==null || news_type==null || news_content==null) {
			message = "新闻发布失败，原因：信息不完整！！";
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		} else {
//				System.out.println(news_name);
//				System.out.println(news_type);
//				System.out.println(news_picture);
//				System.out.println(news_content);
			News news = new News();
			news.setNewsName(news_name);
			news.setNewsType(news_type);
			news.setNewsPicture(news_picture);
			news.setNewsContent(news_content);
			int userId = ((User)request.getSession().getAttribute("user")).getUserId();
			news.setUserId(userId);
			news.setNewsPublishTime(new Date());
			news.setNewsStatus(2);
			NewsDao newsDao = new NewsDaoImpl();
			
			if(newsDao.addNews(news)) {
				message = "新闻发布成功，请等待管理员审核通过！";
				request.setAttribute("message", message);
				request.getRequestDispatcher("/message.jsp").forward(request, response);
			} else {
				message = "系统出错，新闻发布失败";
				request.setAttribute("message", message);
				request.getRequestDispatcher("/message.jsp").forward(request, response);
			}
		}	
	}
}
