<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div>
		<div>
			<p> ToDo number : ${ todo.getId() }</p>
		</div>
		<form action="edit" method="post">
			<input type="text" name="description" id="description" value="${ todo.getDescription() }">
			
			<input type="submit" name="submit_btn" value="Update"/>
		</form>
	</div>
</body>
</html>