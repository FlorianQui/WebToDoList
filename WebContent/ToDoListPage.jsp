<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<link rel="stylesheet" href="css/style.css">

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ToDoList Page</title>
</head>
<body>	
	<div> 
		<div class="user">
			<p> ${ u_name } </p>
			<p> ${ u_role } </p>
			
			<br>
			<br>
		</div>
		<ul>
			<c:forEach var="ToDo" items="${ todo_list }">
				<c:url var="Edit" value="edit">
					<c:param name="toDoId" value="${ ToDo.getId() }"/>
				</c:url>
				<c:url var="Remove" value="remove">
					<c:param name="toDoId" value="${ ToDo.getId() }"/>
				</c:url>
				
				<c:url var="Add" value="add"/>
				
				<li>
					${ ToDo.getDescription() }
					<c:set var="r" value="${ role }"/>
					<c:if test="${ r == 'Instructor' }">
					<table>
						<tr>
						<td>
							<a href="${ Edit }">
								<button>Edit</button>
							</a>
						</td>
						<td>
							<a href="${ Remove }">
								<button>Remove</button>
							</a>
						</td>
					</table>
					</c:if> 
				</li>
			</c:forEach>
		</ul>
					
		<c:set var="r" value="${ role }"/>
		<c:if test="${ r == 'Instructor' }">
			<a href="${ Add }">
				<button>Add</button>
			</a>
		</c:if> 	
	</div>
</body>
</html>