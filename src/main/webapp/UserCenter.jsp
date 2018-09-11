<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>个人中心</title>
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
			height: 40px;
			padding: 10px 10px;
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
	
	<div class="container" style="height: 450px;">
		<c:if test="${not empty requestScope.type }">
			<form id="doneForm" action="<%=request.getContextPath()%>/UserDataManageServlet?type=done" method="post" enctype="multipart/form-data">
				修改密码：<input id='pw' type="password" name="user_password" placeholder="至少6位"><br>
				再输一次：<input id='repw' type="password" name="user_password_repeat" placeholder="至少6位"><br>
				性别：
				<select name="user_sex">
					<c:if test="${sessionScope.user.userSex == '男'}">
						<option value="男" selected>男</option>
						<option value="女">女</option>
					</c:if>
					<c:if test="${empty sessionScope.user.userSex}">
						<option value="男" selected>男</option>
						<option value="女">女</option>
					</c:if>
					<c:if test="${sessionScope.user.userSex == '女'}">
						<option value="男">男</option>
						<option value="女" selected>女</option>
					</c:if>
				</select>
				<br>
				上传新头像<input type="file" name="user_picture">
				<c:if test="${sessionScope.user.userType == 0}">
				爱好：<input type="text" name='user_hobby'>
				</c:if>
				<br>   
			</form>	
			<button id='doneBtn' class="btn" type='submit'>确认修改</button>
		</c:if>
		<c:if test="${empty requestScope.type}">
			<a href="<%=request.getContextPath()%>/UserDataManageServlet?type=edit"><button class="btn">修改个人信息</button></a>
			<br>
			<img alt="还未上传头像" src="${sessionScope.user.userPicture}">
			<br>
			用户名：${sessionScope.user.userName}
			<br>
			用户类型：
			<c:if test="${sessionScope.user.userType == 0}">普通用户</c:if>
			<c:if test="${sessionScope.user.userType == 1}">新闻发布员</c:if>
			<c:if test="${sessionScope.user.userType == 2}">管理员</c:if>
			<br>
			注册时间：${sessionScope.user.userRegisterTime}
			<br>
			性别：${sessionScope.user.userSex}
			<c:if test="${sessionScope.user.userType == 0}">
			<br>
			爱好：${sessionScope.user.userHobby}
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
<script type="text/javascript">
var doneBtn = document.getElementById("doneBtn");
if(doneBtn){
	doneBtn.onclick=function () {
		var isOk = true;
		var password = document.getElementById("pw").value;
		var passwordRepeat = document.getElementById("repw").value;
		if(password.length<6){
			isOk = false;
			alert("密码长度不符合要求！");
		}
		if(password != passwordRepeat){
			isOk = false;
			alert("两次输入的密码不一致！");
		}
		if(isOk){
			document.getElementById("doneForm").submit();	
		}
	};
}
</script>
</html>