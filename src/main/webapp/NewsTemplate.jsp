<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>${requestScope.news.newsName}</title>
	<link rel="stylesheet" href="./css/HeaderFooter.css">
	<style type="text/css">
		body{
			background-color: #f7f7f7;
		}
		/*用户名样式*/
		.userName{
			color: white;
			line-height: 48px;
			float: right;
		}
		.container{
			margin-top: 78px;
			margin-bottom: 20px;
			margin-right: auto;
			margin-left: auto;
			width: 680px;
			background-color: white;
			padding: 40px 134px 40px 134px;
		}
		.news-title{
		 	line-height: 44px;
		    margin: 28px 0 34px;
		    color: #333333;
		    font-size: 32px;
		    font-weight: normal;
			
		}
		.news-about{
			position: relative;
		    margin-bottom: 36px;
		    height: 48px;
		    border-top: 1px solid #dfdfdf;
		}
		.news-about p{
			line-height: 24px;
		    color: #8e8e8e;
		    font-size: 14px;
		}
		
		.news-comment{
			
		}
		.news-comment-title{
			margin: 40px 0 20px;
		    border-bottom: 1px solid #b9b9b9;
		    height: 28px;
		}
		.comment-span{
			cursor: pointer;
		    display: inline;
		    text-decoration: none;
		    padding: 4px 0;
		    line-height: 26px;
		    font-size: 15px;
		    font-weight: lighter;
		    color: #222;
		    border-bottom: 2px solid #ff8104;
		    margin-right: 20px;
		}
		.mycommt{
			padding: 10px 0 40px;
		}
		.news-comment-left{
		    width: 40px;
		    height: 40px;
		    float: left;
		    border-radius: 50%;
		    background: url('./images/user_n.png') no-repeat 0 0;
		    background-size: 40px 40px;
		    overflow: hidden;
		}
		.news-comment img{
			width: 40px; height: 40px;
		}
		.news-comment-right{
		    width: 618px;
		    float: right;
		    position: relative;
		}
		.news-comment-right-input{
			max-width: 596px;
		    min-width: 596px;
		    width: 596px;
		    height: 46px;
		    position: relative;
		    display: block;
		    font-family: "Microsoft Yahei";
		    font-size: 14px;
		    padding: 5px 10px;
		    background: none repeat scroll 0 0 #ffffff;
		    border: 1px solid #d3d3d3;
		    word-wrap: break-word;
		    line-height: 22px;
		    overflow-y: hidden;
		}
		.news-comment-right button{
			cursor: pointer;
		    width: 54px;
		    height: 24px;
		    line-height: 24px;
		    float: right;
		    background-color: #00a5e8;
		    color: #ffffff;
		    font-size: 12px;
		    margin-top: 10px;
		    text-align: center;
		    border: 0 none;
		}
		.news-comment-right h3{
			height: 14px;
		    line-height: 14px;
		    font-size: 12px;
		    font-weight: 400;
		    color: #00a5eb;
		}
		.news-comment-right a{
			color: #00a5eb;
		}
		.news-comment-right span{
			color: #8C8C8C;
		    margin-left: 15px;
		    font-family: Microsoft YaHei;
		    font-size: 12px;
		}
		.news-comment-content{
			min-height: 20px;
		    line-height: 20px;
		    margin: 16px 0 18px;
		    font-size: 14px;
		    color: #4c4c4c;
		}
		.news-comment-divide{
			clear: both;
		    border-bottom: 1px dashed #d9d9d9;
		    padding-top: 6px;
		}
		.other-commt{
			padding-top: 20px;
		}
		
		.mask{
			position: fixed; 
			width: 100%; 
			height: 100%; 
			top: 0;
			left: 0; 
			right: 0;
			z-index: 10000; 
			background-color:rgba(0,0,0,0.3);
		}
		.maskBox{
			position: fixed; 
			margin-top: -100px;
			margin-left:-300px;
			width: 600px; 
			height: 200px; 
			top: 50%;
			left: 50%; 
			z-index: 20000; 
			background-color:white;
		}
		.editBox{
			margin: 40px;
			font-size: 22px;
			width: 500px;
		}
	</style>
</head>
<body>
	<!-- 顶部导航 -->
	<div class="header" style="z-index: 100;">
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

	
	<div class="container" style="z-index: 100;">
		<h1 class="news-title">${requestScope.news.newsName}</h1>
		<div class="news-about">
			<p>作者：${requestScope.userName}</p>
			<p>发布时间：${requestScope.news.newsPublishTime}</p>
		</div>
		<div>
			${requestScope.news.newsContent}
		</div>
	

			<div class="news-comment">
				<div class="news-comment-title">
					<h2 class="comment-span">评论<span>（<c:out value="${requestScope.totalCommentsNumber}"></c:out>）</span></h2>
				</div>	

				<div class="mycommt">
					<div class="news-comment-left">
						<img src="./images/def_head.jpg">
					</div>
					<div class="news-comment-right">
						<textarea id="comment-text" class="news-comment-right-input"></textarea>
						<button id="btn-comment-submit" class="aqw_pub">发表</button>
					</div>

					<div style="clear: both;"></div>
				</div>


				<!-- 评论 -->
				<!-- <div class="other-commt">
					<div class="news-comment-left">
						
					</div>
					<div class="news-comment-right">
						<h3>
		        		<a href="">chen</a>
		        		<span>10小时前</span>
		                </h3>
		                <div class="news-comment-content">
		                	信心比黄金重要。科技本身就有风险。希望各方多一些宽容，而不是痛打落水狗？中国科技未来的春天还有吗？
		                </div>
					</div>

					<div class="news-comment-divide"></div>
				</div> -->

			

				<c:forEach items="${requestScope.comments}" var="item">
				<div class="other-commt">
					<div class="news-comment-left">
						<img alt="" src="${item.userPicture}">
					</div>
					<div class="news-comment-right">
						<h3>
		        		<span><c:out value=" ${item.userName}"></c:out></span>
		        		<span><c:out value=" ${item.newsCommentTime}"></c:out></span>
		                <c:if test="${sessionScope.user.userType == 2}">
		                <a href="<%=request.getContextPath()%>/CommentManageServlet?type=delete&newsCommentId=${item.newsCommentId}&newsId=${item.newsId}"><button>删除</button></a>
		                </c:if>
		                <c:if test="${sessionScope.user.userId == item.userId}">
		                	<c:if test="${item.edit}">
		                		<button onclick="editComment(${item.newsCommentId}, ${item.newsId})">编辑</button>
		                	</c:if>
		                </c:if>
		                </h3>
		                <div class="news-comment-content">
		                	${item.newsCommentContent}
		                </div>
					</div>

					<div class="news-comment-divide"></div>
				</div>
				</c:forEach>
				
			</div>
	</div>

	<!-- 底部 -->
	<div class="footer" style="z-index: 100;">
		<div>
			<p>专业：软件工程 | 班级：16级卓1 | 学号：201641404147 | 姓名：陈松彬</p>
			<p> QQ：1748681439 电话：13433643869 </p>
		</div>
	</div>
	
	<!-- 模态框 -->
	<div class="mask" style="display: none">
		<div class="maskBox">
			<h3 style="text-align: center;">修改评论</h3>
			<form action="">
				<textarea id="editText" class="editBox"></textarea>
			</form>
			<div style="width:120px; margin: 0 auto;">
					<button id="btn-edit-submit">确认修改</button>
					<button class="btn-cancel">取消修改</button>
			</div>
		</div>
	</div>

<script type="text/javascript">
var editUrl = null;
function editComment(newsCommentId, newsId) {
	var mask = document.getElementsByClassName('mask');
	mask[0].style.display = "block";
	editUrl = "/news/CommentManageServlet?type=edit&newsId=" + newsId + "&newsCommentId=" + newsCommentId + "&newsCommentContent=";
}
 
var btn_edit_submit = document.getElementById('btn-edit-submit');
btn_edit_submit.onclick = function () {
	var text = document.getElementById('editText').value;
	var reg = new RegExp("\n", "g" )
	text = text.replace(reg,"</br>");
	console.log(editUrl+text);
	window.location = editUrl + text;
};

var btn_cancel = document.getElementsByClassName('btn-cancel')[0];
btn_cancel.onclick = function () {
	var mask = document.getElementsByClassName('mask');
	mask[0].style.display = "none";
};

var btn_comment_submit = document.getElementById('btn-comment-submit');
btn_comment_submit.onclick = function () {
	var text = document.getElementById('comment-text').value;
	var reg = new RegExp("\n", "g" )
	text = text.replace(reg,"</br>");
	//console.log(text);
	window.location = "/news/CommentManageServlet?type=submit&newsCommentContent="+text+"&newsId=" + ${requestScope.news.newsId};
	//console.log("/news/CommentManageServlet?type=submit&newsCommentContent="+text+"&newsId=" + ${requestScope.news.newsId});
};
</script>
</body>
</html>