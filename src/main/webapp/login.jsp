<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>登陆</title>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/HeaderFooter.css">
	<style type="text/css">
		.container{
			background-color: #f7f7f7;
			margin: 0 auto;
			height: 600px;
			position: relative;
			z-index: 10;
		}
		.container .loginBox{
			width: 480px;
			/*height: 300px;*/
			background: white;
			position: absolute;
			left: 50%;
			top: 100px;
			margin-left: -240px;
		}
		.container .loginHeader{
			color: gray;
			height: 58px;
			text-align: center;
			line-height: 58px;
			font-size: 20px;
		}
		.container form{
			width: 300px;
			margin: 26px auto;
		}
		.container input{
			font-size: 16px;
			width: 300px;
			height: 38px;
			border: 0.5px solid lightgray;
			border-radius: 4px;
		}
		.container .notice{
			height: 26px;
			width: 300px;
			margin: 0 auto;
	/*		background-color: red;*/
		}
		.container button{
			color: gray;
			display: block;
			margin: 0px auto;
			width: 80px;
			height: 34px;
			cursor: pointer;
		}
		.msgbox{
			float: right;
			margin-right: 40px;
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

	<!-- 登陆 -->
	<div class="container">
		<div class="loginBox">
			<div class="loginHeader">登陆</div>
			<hr>
			<form id="LoginForm" action="<%=request.getContextPath()%>/LoginServlet" method="POST">
				<div class="msgbox" style="color: red; display: none;"></div>
				<input id="userName" type="text" name="user" placeholder="用户名(5~15位字母或数字)">
				<div class="notice"></div>
				<div class="msgbox" style="color: red; display: none;"></div>
				<input id="password" type="password" name="pw" placeholder="密码(至少6位)">
				<div class="notice"></div>
			</form>
			<button type="submit" id="login-button">确认</button>
		</div>
	</div>

	<!-- 底部 -->
	<div class="footer">
		<div>
			<p>专业：软件工程 | 班级：16级卓1 | 学号：201641404147 | 姓名：陈松彬</p>
			<p> QQ：1748681439 电话：13433643869 </p>
		</div>
	</div>
<script type="text/javascript">
// 获得登录按钮对象
var login_button = document.getElementById("login-button");
login_button.onclick=function () {
	var isOk = true;
	// 初始化隐藏所有提示框
	var msgboxs = document.getElementsByClassName('msgbox');
	for(var i=0;i<msgboxs.length;++i){
		msgboxs[i].style.display = "none";
	}
	//密码检测
	var password = document.getElementById("password").value;
	if(password.length<6){
		isOk = false;
		msg="密码长度不符合要求！";
		msgboxs[1].innerText = msg;
		msgboxs[1].style.display = "block";
	}
	//用户名检测
	var userName = document.getElementById("userName").value;
	if(userName.length==0 || userName.length<5 || userName.length>15){
		isOk = false;
		msg="用户名长度不符合要求！";
		msgboxs[0].innerText = msg;
		msgboxs[0].style.display = "block";
	}else{
		var reg = /^[0-9a-zA-Z]+$/;
		if(!reg.test(userName)){
			isOk = false;
			msg="用户名只能由字母或数字组成";
			msgboxs[0].innerText = msg;
			msgboxs[0].style.display = "block";
		}
	}
	if(isOk){
		document.getElementById("LoginForm").submit();
	}
}

</script>
</body>
</html>