<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%request.setAttribute("webPath", request.getContextPath());%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>This is SendMessage Page :)</h1>
	<form action="${webPath }/SendServlet" method="get" name="from1">
		<input type="radio" name="tagetType" value=1>用户
		<input type="radio" name="tagetType" value=2>部门
		<input type="radio" name="tagetType" value=3>标签
		<br>
		<input type="radio" name="taget" value=1>全体
		<input type="radio" name="taget" value=2>自选
		<br>
		<input type="text" name="tagetID">
		<br>
		<input type="text" name="message">
		<br>
		<input type="submit" value="submit">
		<input type="reset" value="reset">	
	</form>
</body>
</html>