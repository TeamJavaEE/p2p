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


import dao.impl.UserDaoImpl;
import javabean.User;

/**
 * 设置用户资料控制器
 */
@WebServlet("/UserDataManageServlet")
public class UserDataManageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	// 处理完成编辑新闻任务的请求
	private void doneEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = (User)request.getSession().getAttribute("user");
	
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
				    	String fileName = user.getUserId() + "_" + System.currentTimeMillis() + "_" + item.getName();
				    	user.setUserPicture(request.getServletContext().getContextPath() + "/filedata/user_picture/" + fileName);
				    	item.write(new File(request.getServletContext().getRealPath("/") + "\\filedata\\user_picture\\" + fileName));
			    	}	    	
			    } else { 
			    	if(item.getFieldName().equals("user_password")) {
			    		user.setUserPassword(item.getString("utf-8"));
			    	} else if(item.getFieldName().equals("user_sex")) {
			    		user.setUserSex(item.getString("utf-8"));
			    	} else if(item.getFieldName().equals("user_hobby")) {
			    		user.setUserHobby(item.getString("utf-8"));
			    	}
			    }
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(new UserDaoImpl().updateUser(user)) {
			request.setAttribute("message", "用户信息修改成功");
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		} else {
			request.setAttribute("message", "用户信息修改失败");
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String type = request.getParameter("type");
		if(type != null) {
			if(type.equals("done")) {
				doneEdit(request, response);
			} else {
				request.setAttribute("type", "edit");
				request.getRequestDispatcher("/UserCenter.jsp").forward(request, response);
			}
		} else {
			request.getRequestDispatcher("/UserCenter.jsp").forward(request, response);
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
