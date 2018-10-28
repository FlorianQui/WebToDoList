<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<link rel="stylesheet" href="css/style.css">

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login Page</title>
</head>

<body>
	<div class="login-form-1">
		<c:set var="fail" value="${ loginfailed }"/>
		<c:if test="${ fail == true }">
			<h1>LOG IN FAILED !</h1>
		</c:if>
		
		<h1>LOG IN</h1><br>
		<form class="login-main" action="login" method="post">
			<input type="text" name="username" id="username" value="${ u_name }"/> <br>
			<input type="password" name="password" id="password" value="${ u_pass }"/> <br>
			
			<input type="submit" name="submit_btn" value="LOG IN"/>
			<input type="submit" name="submit_btn" value="REGISTER"/>
		</form>
	</div>
</body>
</html>