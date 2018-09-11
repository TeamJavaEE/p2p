<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>用户审核</title>
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
		.btn{
		    color: red;
			height: 60px;
			padding: 10px 10px;
			font-size: 24px;
		}
		img{
			width: 100px;
			height: 100px;
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
		<table border="1">
			<thead>
			<tr>
			<td>注册时间</td>
			<td>用户名</td>
			<td>用户类型</td>
			<td>操作</td>
			</tr>
			</thead>
			<c:forEach items="${requestScope.newUsers}" var="item">
				<tr>
				<td><c:out value="${item.userRegisterTime}"></c:out></td>
				<td><c:out value="${item.userName}"></c:out></td>
				<td>
					<c:if test="${item.userType == 0}">
					普通用户
					</c:if>
					<c:if test="${item.userType == 1}">
					新闻发布员
					</c:if>
					<c:if test="${item.userType == 2}">
					管理员
					</c:if>
				</td>
				<td>
				<a href="<%=request.getContextPath()%>/CheckNewUser?pass=1&userId=${item.userId}"><button style="color: green;">通过</button></a>
				<a href="<%=request.getContextPath()%>/CheckNewUser?pass=0&userId=${item.userId}"><button style="color: red;">不通过</button></a>	
				</td>
				</tr>
			</c:forEach>
		</table>
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