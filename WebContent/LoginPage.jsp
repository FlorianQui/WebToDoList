<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login Page</title>
</head>

<%
String username = null;
String password = null;

Cookie[] cookies = request.getCookies();
if(cookies != null) {
	for( int i = 0; i < cookies.length; i++) {
		if(cookies[i].getName().equals("username")) {
			request.setAttribute("username", cookies[i].getValue());
		}
		if(cookies[i].getName().equals("password")) {
			request.setAttribute("password", cookies[i].getValue());
		}
	}
}
%>

<body>
	<div>
		<h1>LOGIN PAGE</h1>
		<form action="login" method="post">
			<input type="text" name="username" id="username" value="Username"/>
			<input type="password" name="password" id="password" value="Password"/>
			
			<input type="submit" name="submit_btn" value="LOG IN"/>
		</form>
	</div>
</body>
</html>