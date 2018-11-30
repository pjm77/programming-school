<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" href="css/style.css">
	<title>User add or edit</title>
</head>
<body>
<div class="content">
	<h3>Add / edit user</h3>
	<form action="addedituser" method="post">
		<input type="hidden" name="id" value="${userId}"/>
		User name: <input type="text" name="name" value="${userName}"/><br>
		User email: <input type="text" name="email" value="${userEmail}"/><br>
		User password: <input type="text" name="password"/><br>
		Group Id: <input type="number" name="group_id" min="1" step="1" value="${userGroup_id}"/><br>
		<br>
		<input type="submit" value="${button}"/>
	</form>
</div>
</body>
</html>