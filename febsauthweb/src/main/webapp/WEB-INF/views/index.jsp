<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Message List</title>
</head>
<body>
<h2>Welcome! <security:authentication property="name" /></h2>
<security:authentication property="authorities" var="authorities" />
<ul>
	<c:forEach items="${authorities}" var="authority">
		<li>${authority.authority}</li>
	</c:forEach>
</ul>
<hr />
<c:forEach items="${messages}" var="message">
	<table>
		<security:authorize ifAllGranted="ROLE_ADMIN,ROLE_USER">
			<tr>
				<td>Author</td>
				<td>${message.author}</td>
			</tr>
		</security:authorize>
		<tr>
			<td>Title</td>
			<td>${message.title}</td>
		</tr>
		<tr>
			<td>Body</td>
			<td>${message.body}</td>
		</tr>
	</table>
	<hr />
</c:forEach>
<a href="${ctx }/j_spring_security_logout">Logout</a>
</body>
</html>