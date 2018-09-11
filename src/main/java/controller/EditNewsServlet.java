package controller;

import java.io.File;
import java.io.IOException;
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
 * 修改新闻控制器
 */
@WebServlet("/EditNewsServlet")
public class EditNewsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	// 处理完成编辑新闻任务的请求
	private void doneEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String newsIdStr = request.getParameter("newsId");
		if(newsIdStr == null) {
			request.setAttribute("message", "操作失败，由于：传了一个空的newsId");
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		}
		else {
			int newsId = Integer.parseInt(newsIdStr);
			NewsDao newsDao = new NewsDaoImpl();
			News news = newsDao.getNewsByNewsId(newsId);
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
					    	news.setNewsPicture(request.getServletContext().getContextPath() + "/filedata/news_picture/" + fileName);
					    	item.write(new File(request.getServletContext().getRealPath("/") + "\\filedata\\news_picture\\" + fileName));
				    	}	    	
				    } else { 
				    	if(item.getFieldName().equals("news_name")) {
				    		news.setNewsName(item.getString("utf-8"));
				    	} else if(item.getFieldName().equals("editorValue")) {
				    		news.setNewsContent(item.getString("utf-8"));
				    	} else if(item.getFieldName().equals("news_type")) {
				    		news.setNewsType(item.getString("utf-8"));
				    	}
				    }
				}	
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(newsDao.updateNews(news)) {
				request.setAttribute("message", "修改成功");
				request.getRequestDispatcher("/message.jsp").forward(request, response);
			} else {
				request.setAttribute("message", "修改失败");
				request.getRequestDispatcher("/message.jsp").forward(request, response);
			}
		}
	}
	
	// 处理编辑新闻的请求
	private void Edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String newsIdStr = request.getParameter("newsId");
		if(newsIdStr == null) {
			request.setAttribute("message", "修改新闻，你怎么给我传了一个空的newsId");
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		}
		else {
			NewsDao newsDao = new NewsDaoImpl();
			int newsId = Integer.parseInt(newsIdStr);
			int userId = ((User)request.getSession().getAttribute("user")).getUserId();
			if(newsDao.isUserCreateNews(userId, newsId)) {
				News news = newsDao.getNewsByNewsId(newsId);
				String part1 = "<form action='";
				part1 = part1 + request.getServletContext().getContextPath();
				part1 = part1 + "/EditNewsServlet?type=done&newsId=" + news.getNewsId();
				part1 = part1 + "' method='post' enctype='multipart/form-data'>新闻标题<input type='text' name='news_name' value='";
				String part2 = "'><br>选择新闻所属类型<select name='news_type'>";
				String part3 = "</select><br>上传新闻简图<input type='file' name='news_picture'><div><script id='editor' type='text/plain' style='height: 500px;'>";
				String part4 = "</script></div><input class='btn' type='submit' value='修改新闻'></form>";
					
				String newsHtml = part1 + news.getNewsName() + part2;
				String types[] = {"政治", "经济", "社会", "国际", "科技", "其他"};
				for(int i=0;i<types.length;++i) {
					if(types[i].equals(news.getNewsType())) {
						newsHtml += "<option value='" + types[i] + "' selected>" + types[i] + "</option>";
					} else {
						newsHtml += "<option value='" + types[i] + "'>" + types[i] + "</option>";
					}
				}
				newsHtml = newsHtml + part3 + news.getNewsContent() + part4;

				request.setAttribute("newsHtml", newsHtml);
				request.getRequestDispatcher("/NewsManage/PublishNews.jsp").forward(request, response);
				
				
			}else {
				request.setAttribute("message", "这个新闻不是你创建的，你是无法修改的ok?请使用我提供的按钮修改，不要给我发不符合要求的请求");
				request.getRequestDispatcher("/message.jsp").forward(request, response);
			}
		}
	}
	
 	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
 		
 		String type = request.getParameter("type");
 		if(type==null) {
 			Edit(request, response);
 		} else {
 			doneEdit(request, response);
 		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
