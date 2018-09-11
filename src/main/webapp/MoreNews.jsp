<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>${requestScope.newsType}类新闻</title>
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
		/*用户名样式*/
		.userName{
			color: white;
			line-height: 48px;
			float: right;
		}	
		.newsTable{
			margin: 20px;	
		}
		.newsTable thead{
			font-size: 20px;
		}
		.newsTable .newsTime{
			width:200px;
		}
		.newsTable tr{
			line-height: 40px;
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
	
	
	<div class="container" style="height: 450px;">
		<h1>${requestScope.newsType}类新闻</h1>
		<c:if test="${not empty requestScope.page}">
				<table class="newsTable">
					<thead>
					<tr>
					<td class="newsTime">发布时间</td>
					<td>标题</td>
					</tr>
					</thead>
					<c:forEach items="${requestScope.page}" var="item">
						<tr>
						<td class="newsTime"><c:out value="${item.newsDateStr}"></c:out></td>
						<td><a href="<%=request.getContextPath()%>/ShowNewsServlet?newsId=${item.newsId}">${item.newsName}</a></td>
						</tr>
					</c:forEach>
				</table>
				<h4>总共  ${ requestScope.totalPage } 页</h4>
				<h4>当前第  ${ requestScope.curPage } 页 </h4>
				<c:if test="${requestScope.curPage != 1}">
					<a href="<%=request.getContextPath()%>/ShowMoreNewsServlet?newsType=${requestScope.newsType}&pageSize=5&curPage=${requestScope.curPage-1}"><button>上一页</button></a>
				</c:if>
				
				<c:if test="${requestScope.curPage != requestScope.totalPage}">
					<a href="<%=request.getContextPath()%>/ShowMoreNewsServlet?newsType=${requestScope.newsType}&pageSize=5&curPage=${requestScope.curPage+1}"><button>下一页</button></a>
				</c:if>
		</c:if>
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