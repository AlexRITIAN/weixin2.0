<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Server Status Page</title>
<!-- custom-theme -->
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="" />
<script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false);
		function hideURLbar(){ window.scrollTo(0,1); } </script>
<!-- //custom-theme -->
<link href="css/bootstrap.css" rel="stylesheet" type="text/css" media="all" />
<link href="css/style.css" rel="stylesheet" type="text/css" media="all" />
<!-- js -->
<script type="text/javascript" src="js/jquery-2.1.4.min.js"></script>
<!-- //js -->
<!-- font-awesome-icons -->
<link href="css/font-awesome.css" rel="stylesheet"> 
<!-- //font-awesome-icons -->

<script type="text/javascript" src="js/Ajax/jquery-1.8.3.js"></script>
<script type="text/javascript">
	$(function() {
		$.ajax({
			type : 'post',
			url : '/BServlet',
			data : {
				name : 'restart',
				state : 'BServerStatusFunction'
			},
			dataType : 'json',
			success : function(data) {
				if (data != null) {
					for (var i = 0; i < data.length - 1; i++) {
							$("#server_status").append("<tr><td><input type=\"button\" id=\"build\" value=\"build\" onclick=\"build('"+ data[i].name+"')\">" + "</td><td>" + data[i].color + "</td><td>" + data[i].name + "</td></tr>" ) ; 
					}
				}
			}

		});
	})
</script>

	
<body>
<!-- banner -->
	<div class="banner1">
	<nav class="navbar navbar-default">
			<div class="navbar-header navbar-left">
				<h1><a class="navbar-brand" href="/BServlet?state=index"><span>L</span>enovo</a></h1>
			</div>
		</nav>
		<div class="wthree_banner1_info">
			<div class="container">
				<h3><span>R</span>estart View</h3>
			</div>
		</div>
	</div>
<!-- //banner -->	
<!-- courses -->
	<div class="courses">
		<div class="container"> 
						<table class="table table-striped">
							<thead>
								<tr>
									<th>Build</th>
									<th>Status</th>
									<th>Name</th>
								</tr>
							</thead>
							<tbody id="server_status">
							</tbody>
						</table>
				<div class="clearfix"> </div>
		</div>
	</div>
<script src="js/bars.js"></script>
<!-- footer -->
<!-- 	<div class="footer">
		<div class="container">
			<div class="agile_footer_copy">
				<div class="w3agile_footer_grids">
					<div class="col-md-4 w3agile_footer_grid">
						<h3>Contact Info</h3>
						<ul>
							<li><i class="glyphicon glyphicon-map-marker" aria-hidden="true"></i>1234k Avenue, 4th block, <span>New York City.</span></li>
							<li><i class="glyphicon glyphicon-envelope" aria-hidden="true"></i><a href="mailto:info@example.com">info@example.com</a></li>
							<li><i class="glyphicon glyphicon-earphone" aria-hidden="true"></i>+1234 567 567</li>
						</ul>
					</div>
					<div class="col-md-4 w3agile_footer_grid w3agile_footer_grid1">
						<h3>Navigation</h3>
						<ul>
							<li><span class="glyphicon glyphicon-menu-right" aria-hidden="true"></span><a href="courses.html">Courses</a></li>
							<li><span class="glyphicon glyphicon-menu-right" aria-hidden="true"></span><a href="services.html">Services</a></li>
							<li><span class="glyphicon glyphicon-menu-right" aria-hidden="true"></span><a href="icons.html">Web Icons</a></li>
							<li><span class="glyphicon glyphicon-menu-right" aria-hidden="true"></span><a href="mail.html">Mail Us</a></li>
						</ul>
					</div>
					<div class="clearfix"> </div>
				</div>
			</div>
			<div class="w3_agileits_copy_right_social">
				<div class="col-md-6 agileits_w3layouts_copy_right">
					<p>Copyright &copy; 2017.Company name All rights reserved.</p>
				</div>
				<div class="col-md-6 w3_agile_copy_right">
					<ul class="agileinfo_social_icons">
						<li><a href="#" class="w3_agileits_facebook"><i class="fa fa-facebook" aria-hidden="true"></i></a></li>
						<li><a href="#" class="wthree_twitter"><i class="fa fa-twitter" aria-hidden="true"></i></a></li>
						<li><a href="#" class="agileinfo_google"><i class="fa fa-google-plus" aria-hidden="true"></i></a></li>
						<li><a href="#" class="agileits_pinterest"><i class="fa fa-pinterest-p" aria-hidden="true"></i></a></li>
					</ul>
				</div>
				<div class="clearfix"> </div>
			</div>
		</div>
	</div> -->
<!-- //footer -->
<!-- start-smooth-scrolling -->
<script type="text/javascript" src="js/move-top.js"></script>
<script type="text/javascript" src="js/easing.js"></script>
<script type="text/javascript">
	jQuery(document).ready(function($) {
		$(".scroll").click(function(event){		
			event.preventDefault();
			$('html,body').animate({scrollTop:$(this.hash).offset().top},1000);
		});
	});
</script>
<!-- start-smooth-scrolling -->
<!-- for bootstrap working -->
	<script src="js/bootstrap.js"></script>
<!-- //for bootstrap working -->
<!-- here stars scrolling icon -->
	<script type="text/javascript">
		$(document).ready(function() {
			/*
				var defaults = {
				containerID: 'toTop', // fading element id
				containerHoverID: 'toTopHover', // fading element hover id
				scrollSpeed: 1200,
				easingType: 'linear' 
				};
			*/
								
			$().UItoTop({ easingType: 'easeOutQuart' });
								
			});
	</script>
<!-- //here ends scrolling icon -->

	<script type="text/javascript">
		function build(n){
			$.ajax({
				type : 'post',
				url : '/BServlet',
				data : {
					name : 'build@' + n,
					state : 'BServerBuildFunction'
				},
				dataType : 'json',
				success : function(data) {
					alert(n + "正在构建,稍候刷新网页查看最新信息");
				}

			});
		}
	</script>
</body>
</html>