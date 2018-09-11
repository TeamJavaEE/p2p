<%@page import="javabean.News"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>新闻系统首页chsobin</title>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/HeaderFooter.css">
	<style type="text/css">

		body{
			background-color: #f7f7f7;
		}

		
		.container{
			width: 1024px;
			margin: 60px auto;
			position: relative;
		}
		
		/*新闻分类标题样式：例如社会、经济、*/
		.tag{
			color: gray;
			border-left: 6px solid red;
			font-size: 20px;
			line-height: 28px;
			height: 28px;
			margin-bottom: 8px;
			padding-left: 8px;
		}

		/*新闻分类框相关样式*/
		.topic{
			margin-top: 20px;
			width: 738px;
			float: left;
		}
		.topicLeft{
			width: 220px;
			float: left;
			background-color: white;
			border: 1px solid lightgray;
			margin-right: 20px;
		}
		.topicLeft img{
			width: 220px;
			height: 120px;
		}
		.newsTitle{
			padding: 10px;
			text-align: center;
		}
		.topicText{
			float: left;
		}
		.topicText ul{
			list-style:square inside url('<%=request.getContextPath()%>/images/arrow.gif');
			width: 460px;
			background-color: white;
			border-top: 1px solid lightgray;
			border-left: 1px solid lightgray;
			border-right: 2px solid lightgray;
			border-bottom: 2px solid lightgray;
		}
		.topicText li{
			font-family: '宋体';
			font-size: 15px;
			padding: 10px 10px;
		}



		/*最新新闻相关样式*/
		.rightNav{
			float: right;
			width: 250px;
		}
		.latestNews{
			width: 250px;
		}
		.latestNews ul{
			background-color: white;
			border-top: 1px solid lightgray;
			border-left: 1px solid lightgray;
			border-right: 2px solid lightgray;
			border-bottom: 2px solid lightgray;
		}
		.latestNews li{
			font-family: '宋体';
			font-size: 16px;
			border-bottom: 1px solid lightgray;
			padding: 8px 10px;
		}
		/*用户名样式*/
		.userName{
			color: white;
			line-height: 48px;
			float: right;
		}
	</style>
</head>
<body>
	<!-- 顶部导航 -->
	<div class="header">
		<div class="headerContainer">
			<div class="title">
				<a href="<%=request.getContextPath()%>/indexServlet">新闻</a>	
			</div>
			<div class="subhead">
				<a href="<%=request.getContextPath()%>/UserDataManageServlet">个人中心</a>
			</div>
			<c:if test="${not empty sessionScope.user }">
				<c:if test="${sessionScope.user.userType == 1}">
					<div class="subhead">
						<a href="<%=request.getContextPath()%>/NewsManageServlet">新闻管理</a>
					</div>
				</c:if>
				<c:if test="${sessionScope.user.userType == 2}">
					<div class="subhead">
						<a href="<%=request.getContextPath()%>/NewsManageServlet">新闻管理</a>
					</div>
					<div class="subhead">
						<a href="<%=request.getContextPath()%>/UserManageServlet">用户管理</a>
					</div>
					<div class="subhead">
						<a href="<%=request.getContextPath()%>/StatisticManage/Statistic.jsp">数据统计</a>
					</div>
				</c:if>
			</c:if>
			
			<a href="<%=request.getContextPath()%>/register.jsp"><button class="register">注册</button></a>		
			<c:if test="${empty sessionScope.user }">
			<a href="<%=request.getContextPath()%>/login.jsp"><button class="login">登陆</button></a>
			</c:if>
			<c:if test="${not empty sessionScope.user }">
				<div class="userName"><a href="<%=request.getContextPath()%>/LogoutServlet" title="注销登录">${sessionScope.user.userName}</a></div>
			</c:if>
		</div>
	</div>
	<!-- 顶部导航 -->
	

	<div class="container">

		<div class="rightNav">
			<!-- 最新 -->
			<div class="latestNews">
				<div class="tag">
					最新
				</div>
				<ul>
					<%
						ArrayList<News> arr = null;
				 		arr = (ArrayList<News>)request.getAttribute("latestNews");	
			 			if(arr!=null){
			 				for(int i=0;i<arr.size();++i){
			 					String url = request.getServletContext().getContextPath() + "/ShowNewsServlet?newsId=" + arr.get(i).getNewsId();
			 					out.print("<li><a href='" + url + "'>" + arr.get(i).getNewsName() + "</a></li>");
			 				}
			 			}
			 		%>
				</ul>
			</div>
		</div>


		<!-- <div class="topic">
			 <div class="tag">
			 	精选
			 </div>
			 <div class="topicLeft">
			 	<a href="">
			 		<img src="./images/a.jpg" alt="">
					<div class="newsTitle">
						东莞理工学院博士后创新实践基地博士后招聘公告
					</div>
			 	</a>
			 </div>
			  <div class="topicLeft">
			 	<a href="">
			 		<img src="./images/a.jpg" alt="">
					<div class="newsTitle">
						东莞理工学院博士后创新实践基地博士后招聘公告
					</div>
			 	</a>
			 </div>
			  <div class="topicLeft">
			 	<a href="">
			 		<img src="./images/a.jpg" alt="">
					<div class="newsTitle">
						东莞理工学院博士后创新实践基地博士后招聘公告
					</div>
			 	</a>
			 </div>
		
		
			 <div style="clear: both;"></div>
		</div> -->

		<div class="topic">
			 <div class="tag">
			 	政治
			 </div>
			 <div class="topicLeft">
			 	<%
			 		News news = null;
			 		arr = (ArrayList<News>)request.getAttribute("type0");
		 			if(arr != null && arr.size()>0){
		 				news = arr.get(0);
		 				String url = request.getServletContext().getContextPath() + "/ShowNewsServlet?newsId=" + news.getNewsId();
		 				out.print("<a href='" + url + "'>");
		 				out.print("<img src='" + news.getNewsPicture() + "' alt='图片！error'>");
		 				out.print("<div class='newsTitle'>" + news.getNewsName() + "</div></a>");
		 			}
			 	%>
			 </div>
		
			 <div class="topicText">
			 	<ul>
			 		<%
			 			if(arr!=null){
			 				for(int i=1;i<arr.size();++i){
			 					String url = request.getServletContext().getContextPath() + "/ShowNewsServlet?newsId=" + arr.get(i).getNewsId();
			 					out.print("<li><a href='" + url + "'>" + arr.get(i).getNewsName() + "</a></li>");
			 				}
			 			}
			 		%>
					<li><a href="<%=request.getContextPath()%>/ShowMoreNewsServlet?newsType=政治">更多...</a></li>
			 	</ul>
			 </div>
			 <div style="clear: both;"></div>
		</div>
		
		<div class="topic">
			 <div class="tag">
			 	经济
			 </div>
			 <div class="topicLeft">
			 	<%	
			 		arr = (ArrayList<News>)request.getAttribute("type1");
				 	if(arr != null && arr.size()>0){		
		 				news = arr.get(0);
		 				String url = request.getServletContext().getContextPath() + "/ShowNewsServlet?newsId=" + news.getNewsId();
		 				out.print("<a href='" + url + "'>");
		 				out.print("<img src='" + news.getNewsPicture() + "' alt='图片！error'>");
		 				out.print("<div class='newsTitle'>" + news.getNewsName() + "</div></a>");
		 			}
			 	%>
			 </div>
		
			 <div class="topicText">
			 	<ul>
			 		<%
			 			if(arr!=null){
			 				for(int i=1;i<arr.size();++i){
			 					String url = request.getServletContext().getContextPath() + "/ShowNewsServlet?newsId=" + arr.get(i).getNewsId();
			 					out.print("<li><a href='" + url + "'>" + arr.get(i).getNewsName() + "</a></li>");
			 				}
			 			}
			 		%>
			 		<li><a href="<%=request.getContextPath()%>/ShowMoreNewsServlet?newsType=经济">更多...</a></li>
			 	</ul> 	
			 </div>
			 <div style="clear: both;"></div>
		</div>
		
		<div class="topic">
			 <div class="tag">
			 	社会
			 </div>
			 <div class="topicLeft">
			 	<%	
			 		arr = (ArrayList<News>)request.getAttribute("type2");
				 	if(arr != null && arr.size()>0){		
		 				news = arr.get(0);
		 				String url = request.getServletContext().getContextPath() + "/ShowNewsServlet?newsId=" + news.getNewsId();
		 				out.print("<a href='" + url + "'>");
		 				out.print("<img src='" + news.getNewsPicture() + "' alt='图片！error'>");
		 				out.print("<div class='newsTitle'>" + news.getNewsName() + "</div></a>");
		 			}
			 	%>
			 </div>
		
			 <div class="topicText">
			 	<ul>
			 		<%
			 			if(arr!=null){
			 				for(int i=1;i<arr.size();++i){
			 					String url = request.getServletContext().getContextPath() + "/ShowNewsServlet?newsId=" + arr.get(i).getNewsId();
			 					out.print("<li><a href='" + url + "'>" + arr.get(i).getNewsName() + "</a></li>");
			 				}
			 			}
			 		%>
			 		<li><a href="<%=request.getContextPath()%>/ShowMoreNewsServlet?newsType=社会">更多...</a></li>
			 	</ul>
			 </div>
			 <div style="clear: both;"></div>
		</div>

		<div class="topic">
			 <div class="tag">
			 	国际
			 </div>
			 <div class="topicLeft">
			 	<%	
			 		arr = (ArrayList<News>)request.getAttribute("type3");
				 	if(arr != null && arr.size()>0){		
		 				news = arr.get(0);
		 				String url = request.getServletContext().getContextPath() + "/ShowNewsServlet?newsId=" + news.getNewsId();
		 				out.print("<a href='" + url + "'>");
		 				out.print("<img src='" + news.getNewsPicture() + "' alt='图片！error'>");
		 				out.print("<div class='newsTitle'>" + news.getNewsName() + "</div></a>");
		 			}
			 	%>
			 </div>
		
			 <div class="topicText">
			 	<ul>
			 		<%
			 			if(arr!=null){
			 				for(int i=1;i<arr.size();++i){
			 					String url = request.getServletContext().getContextPath() + "/ShowNewsServlet?newsId=" + arr.get(i).getNewsId();
			 					out.print("<li><a href='" + url + "'>" + arr.get(i).getNewsName() + "</a></li>");
			 				}
			 			}
			 		%>
			 		<li><a href="<%=request.getContextPath()%>/ShowMoreNewsServlet?newsType=国际">更多...</a></li>
			 	</ul>
			 </div>
			 <div style="clear: both;"></div>
		</div>

		<div class="topic">
			 <div class="tag">
			 	科技
			 </div>
			 <div class="topicLeft">
			 	<%	
			 		arr = (ArrayList<News>)request.getAttribute("type4");
				 	if(arr != null && arr.size()>0){		
		 				news = arr.get(0);
		 				String url = request.getServletContext().getContextPath() + "/ShowNewsServlet?newsId=" + news.getNewsId();
		 				out.print("<a href='" + url + "'>");
		 				out.print("<img src='" + news.getNewsPicture() + "' alt='图片！error'>");
		 				out.print("<div class='newsTitle'>" + news.getNewsName() + "</div></a>");
		 			}
			 	%>
			 </div>
		
			 <div class="topicText">
			 	<ul>
			 		<%
			 			if(arr!=null){
			 				for(int i=1;i<arr.size();++i){
			 					String url = request.getServletContext().getContextPath() + "/ShowNewsServlet?newsId=" + arr.get(i).getNewsId();
			 					out.print("<li><a href='" + url + "'>" + arr.get(i).getNewsName() + "</a></li>");
			 				}
			 			}
			 		%>
			 		<li><a href="<%=request.getContextPath()%>/ShowMoreNewsServlet?newsType=科技">更多...</a></li>
			 	</ul>
			 </div>
			 <div style="clear: both;"></div>
		</div>

		<div class="topic">
			 <div class="tag">
			 	其他
			 </div>
			 <div class="topicLeft">
			 	<%	
			 		arr = (ArrayList<News>)request.getAttribute("type5");
				 	if(arr != null && arr.size()>0){		
		 				news = arr.get(0);
		 				String url = request.getServletContext().getContextPath() + "/ShowNewsServlet?newsId=" + news.getNewsId();
		 				out.print("<a href='" + url + "'>");
		 				out.print("<img src='" + news.getNewsPicture() + "' alt='图片！error'>");
		 				out.print("<div class='newsTitle'>" + news.getNewsName() + "</div></a>");
		 			}
			 	%>
			 </div>
		
			 <div class="topicText">
			 	<ul>
			 		<%
			 			if(arr!=null){
			 				for(int i=1;i<arr.size();++i){
			 					String url = request.getServletContext().getContextPath() + "/ShowNewsServlet?newsId=" + arr.get(i).getNewsId();
			 					out.print("<li><a href='" + url + "'>" + arr.get(i).getNewsName() + "</a></li>");
			 				}
			 			}
			 		%>
					<li><a href="<%=request.getContextPath()%>/ShowMoreNewsServlet?newsType=其他">更多...</a></li>
			 	</ul>
			 </div>
			 <div style="clear: both;"></div>
		</div>


		<div style="clear: both;"></div>
	</div>
	
	
	
	<!-- 底部 -->
	<div class="footer">
		<div>
			<p>专业：软件工程 | 班级：16级卓1 | 学号：201641404147 | 姓名：陈松彬</p>
			<p> QQ：1748681439 电话：13433643869 </p>
		</div>
	</div>
</body>
</html>