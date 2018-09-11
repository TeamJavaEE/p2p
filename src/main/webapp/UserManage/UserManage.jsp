<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>用户管理</title>
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
			cursor: pointer;
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
		<c:if test="${sessionScope.user.userType == 2}">
			<a href="<%=request.getContextPath()%>/CheckNewUser"><button class="btn">审核新用户</button></a>
		</c:if>
		
		<c:if test="${not empty requestScope.page}">
				<table border="1">
					<thead>
					<tr>
					<td>头像</td>
					<td>注册时间</td>
					<td>用户名</td>
					<td>用户类型</td>
					<td>性别</td>
					<td>爱好</td>
					<td>账号状态</td>
					<td>操作</td>
					<td>审核状态</td>
					<td>删除</td>
					</tr>
					</thead>
					<c:forEach items="${requestScope.page}" var="item">
						<tr>
						<td><img alt="头像无法获取" src="${item.userPicture}"></td>
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
						<td><c:out value="${item.userSex}"></c:out></td>
						<td><c:out value="${item.userHobby}"></c:out></td>
						
						<c:if test="${item.userStatus == 0}">
						<td><span style="color: red;">停用</span></td>
						<td><a href="<%=request.getContextPath()%>/ChangeUserStatusServlet?userId=${item.userId}&status=1&pageSize=5&curPage=${requestScope.curPage}"><button>启用</button></a></td>
						</c:if>
						<c:if test="${item.userStatus == 1}">
						<td><span style="color: green;">开启</span></td>
						<td><a href="<%=request.getContextPath()%>/ChangeUserStatusServlet?userId=${item.userId}&status=0&pageSize=5&curPage=${requestScope.curPage}"><button>停用</button></a></td>
						</c:if>
						
						<c:if test="${item.userComfirm == 1}">
							<td style="color: green;">审核通过</td>
						</c:if>
						<c:if test="${item.userComfirm == 0}">
							<td style="color: red;">审核不通过</td>
						</c:if>
						<c:if test="${item.userComfirm == 2}">
							<td style="color: blue;">审核中</td>
						</c:if>
						
						<td><a href="<%=request.getContextPath()%>/deletUserServlet?userId=${item.userId}&pageSize=5&curPage=${requestScope.curPage}"><button>删除</button></a></td>
						</tr>
					</c:forEach>
				</table>
				<h4>总共  ${ requestScope.totalPage } 页</h4>
				<h4>当前第  ${ requestScope.curPage } 页 </h4>
				<c:if test="${requestScope.curPage != 1}">
					<a href="<%=request.getContextPath()%>/UserManageServlet?pageSize=5&curPage=${requestScope.curPage-1}"><button>上一页</button></a>
				</c:if>
				
				<c:if test="${requestScope.curPage != requestScope.totalPage}">
					<a href="<%=request.getContextPath()%>/UserManageServlet?pageSize=5&curPage=${requestScope.curPage+1}"><button>下一页</button></a>
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