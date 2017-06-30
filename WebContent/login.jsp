<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head lang="en">
<meta charset="UTF-8">
<title>login</title>
</head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" type="text/css" href="css/login/css/style.css">
<link rel="stylesheet" type="text/css" href="css/login/css/theme.css">
<link rel="stylesheet" type="text/css"
	href="css/login/css/jquery.mobile-1.3.2.min.css">
<script src="js/Ajax/jquery-1.8.3.js"></script>
<script type="text/javascript">
	$(function() {
		$("#login").click(function(){
			
			$.ajax({
			type : 'post',
			url : '/LoginServlet',
			data : {
				user : $("#user").val(),
				password : $("#password").val()
			},

			dataType : 'text',
			success : function(data) {
				if (data == '0') {
					window.location.href = "http://ttweixin.duapp.com/BServlet?state=index";
				}else{
					$("#error").text("用户名/密码错误!!!");
				}

			}

		})});
	})
</script>
<body>
	<div data-role="page">
		<div data-role="content" class="login-wrap">
			<div class="login-input-box">
				<div class="logo">
                	<p id="error"></p>
            	</div>
				<div class="line1">
					<img src="image/login/images/user.png"> <input type="text"
						class="user" value="用户名" onfocus="if(value=='用户名') {value=''}"
						onblur="if (value=='') {value='用户名'}" data-role="none" id="user">
				</div>
				<div class="line2">
					<img src="image/login/images/lock.png"> <input type="text"
						class="lock" value="密码" onfocus="if(value=='密码') {value=''}"
						onblur="if (value=='') {value='密码'}" data-role="none"
						id="password">
				</div>
				<div class="chose">
					<!-- <a href="register.html" data-ajax="false" class="register" data-role="none" data-transition="slide">注册</a> -->
					<!-- <a href="forget.html"data-ajax="false"  class="forgive" data-role="none">忘记密码</a> -->
					<!-- <a class="login" href="" data-ajax="false" data-role="none"
						data-transition="slide" id="login">登陆</a> -->
					<input type="button" id="login" value="登录">
				</div>
			</div>
		</div>
		<div
			style="text-align: center; margin: 10px 0; font: normal 14px/24px 'MicroSoft YaHei';">
		</div>
	</div>
	<script src="js/login/js/jquery-1.8.3.min.js"></script>
	<script src="js/login/js/jquery.mobile-1.3.2.min.js"></script>

</body>
</html>
