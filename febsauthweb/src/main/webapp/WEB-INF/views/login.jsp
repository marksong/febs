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
	<c:if test="${not empty param.error}">
		<font color="red">Login error.<br />
		</font>  
    Reason:${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}  
</c:if>
	<form method="POST" action="${ctx }/j_spring_security_check">
		<table>
			<tr>
				<td align="right">Username</td>
				<td><input type="text" name="j_username" /></td>
			</tr>
			<tr>
				<td align="right">Password</td>
				<td><input type="password" name="j_password" /></td>
			</tr>
			<tr>
				<td colspan="2" align="right"><input type="submit"
					value="Login" /> <input type="reset" value="Reset" /></td>
			</tr>
		</table>
	</form>
	<security:authentication property="name" />
</body>
</html>
