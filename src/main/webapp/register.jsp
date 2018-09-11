<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>注册</title>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/HeaderFooter.css">
	<style type="text/css">
		.container{
			background-color: #f7f7f7;
			margin: 0 auto;
			height: 600px;
			position: relative;
			z-index: 10;
		}
		.container .registerBox{
			width: 480px;
			/*height: 400px;*/
			background: white;
			position: absolute;
			left: 50%;
			top: 100px;
			margin-left: -240px;
		}
		.container .registerHeader{
			color: gray;
			height: 58px;
			text-align: center;
			line-height: 58px;
			font-size: 20px;
		}
		.container form{
			width: 400px;
			margin: 26px auto;
		}
		.container .text{
			width: 100px;
			height: 38px;
			float: left;
			text-align: right;
			line-height: 38px;
		}
		.container select {
			width: 260px;
			height: 38px;
			border: 0.5px solid lightgray;
			border-radius: 4px;
		}
		.container input{
			float: left;
			font-size: 16px;
			width: 260px;
			height: 38px;
			border: 0.5px solid lightgray;
			border-radius: 4px;
		}
		.container .notice{
			float: left;
			height: 26px;
			width: 400px;
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
		.register-msg{
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

	<!-- 注册 -->
	<div class="container">
		<div class="registerBox">
			<div class="registerHeader">注册</div>
			<hr>
			<form id="RegisterForm" action="<%=request.getContextPath()%>/RegisterServlet" method="POST">
				<div class="text">用户类型：</div>
				<select name="user_type">
					<option value="0" selected>普通用户</option>
					<option value="1">新闻发布员</option>
					<option value="2">管理员</option>
				</select>
				<div class="notice"></div>
				<div>
					<div id="register-user-msg" class="register-msg" style="color: red; display: none;"></div>
					<div class="text">用户名：</div><input id="register-user" type="text" name="user" placeholder="5~15位字母或数字"><br>
				</div>
				<div class="notice"></div>
				<div>
					<div id="register-pw-msg" class="register-msg" style="color: red; display: none;"></div>
					<div class="text">密码：</div><input id="register-pw" type="password" name="pw" placeholder="至少6位"><br>
				</div>
				<div class="notice"></div>
				<div>
					<div id="register-re-pw-msg" class="register-msg" style="color: red; display: none;"></div>
					<div class="text">重新输入：</div><input id="register-re-pw" type="password" name="repw"><br>
				</div>
				<div class="notice"></div>
			</form>
				<button type="submit" id="register-button">注册</button>
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
// 获得注册按钮对象
var register_button = document.getElementById("register-button");
//注册验证
register_button.onclick=function(){
	var isOk = true;
	// 初始化隐藏所有提示框
	var register_msgs = document.getElementsByClassName('register-msg');
	for(var i=0;i<register_msgs.length;++i){
		register_msgs[i].style.display = "none";
	}

	//密码检测
	var registerPw = document.getElementById("register-pw").value;
	var registerRePw = document.getElementById("register-re-pw").value;
	if(registerPw!=registerRePw){
		isOk = false;
		msg="两次输入的密码不一致！";
		register_msgs[2].innerText = msg;
		register_msgs[2].style.display = "block";
	}
	if(registerPw.length<6){
		isOk = false;
		msg="密码长度不符合要求！";
		register_msgs[1].innerText = msg;
		register_msgs[1].style.display = "block";
	}
	//用户名检测
	var registerUser = document.getElementById("register-user").value;
	if(registerUser.length==0 || registerUser.length<5 || registerUser.length>15){
		isOk = false;
		msg="用户名长度不符合要求！";
		register_msgs[0].innerText = msg;
		register_msgs[0].style.display = "block";
	}else{
		var reg = /^[0-9a-zA-Z]+$/;
		if(!reg.test(registerUser)){
			isOk = false;
			msg="用户名只能由字母或数字组成";
			register_msgs[0].innerText = msg;
			register_msgs[0].style.display = "block";
		}
	}

	if(isOk){
		document.getElementById("RegisterForm").submit();
	}
};


</script>
</body>
</html>