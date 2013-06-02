<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%> 
<%@ taglib prefix="common" uri="common-tags"%> 
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<script type="text/javascript">
		function backForm() {
			location.href = '${ctx}/auth/user';
		}
	</script>
</head>

<body>
	<div>
		<ul class="breadcrumb">
			<li>
				<a href="${ctx}"><spring:message code="system.home"/></a> <span class="divider">/</span>
			</li>
			<li>
				<a href="<c:url value="/auth/user" />"><spring:message code="auth.user" /></a> <span class="divider">/</span>
			</li>
			<li>
				<a href="<c:url value="/auth/user/edit?filterId=${item.id}" />"><spring:message code="label.edit" /></a>
			</li>
		</ul>
	</div>
	<div class="row-fluid sortable">
		<div class="box span12">
			<div class="box-header well" data-original-title>
				<h2><i class="icon-edit"></i> <spring:message code="label.edit" /><spring:message code="auth.user" /></h2>
			</div>
			<div class="box-content">
				<form id="editForm" action="${ctx}/auth/user/edit" method="post" class="form-horizontal">
					<input type="hidden" value="${filterId}" name="filterId" />
				  <fieldset>
					<div class="control-group">
					  <label class="control-label" for="username"><spring:message code="auth.user.username" /> </label>
					  <div class="controls">
					  	<input type="text" name="username" class="input-xlarge" value="${entity.username }" />
					  </div>
					</div>
					<div class="control-group">
					  <label class="control-label" for="password"><spring:message code="auth.user.password" /> </label>
					  <div class="controls">
					  	<input type="text" name="password" class="input-xlarge" value="${entity.password }" />
					  </div>
					</div>
					<div class="control-group">
					  <label class="control-label" for="email"><spring:message code="auth.user.email" /> </label>
					  <div class="controls">
					  	<input type="text" name="email" class="input-xlarge" value="${entity.email }" />
					  </div>
					</div>
					<div class="control-group">
					  <label class="control-label" for="nickName"><spring:message code="auth.user.nickName" /> </label>
					  <div class="controls">
					  	<input type="text" name="nickName" class="input-xlarge" value="${entity.nickName }" />
					  </div>
					</div>
					<div class="form-actions">
					  <button type="submit" class="btn btn-primary"><spring:message code="label.submit" /></button>
					  <button type="reset" class="btn"><spring:message code="label.reset" /></button>
					  <button type="button" class="btn" onclick="backForm()"><spring:message code="label.back" /></button>
					</div>
				  </fieldset>
				</form>  

			</div>
		</div><!--/span-->

	</div><!--/row-->
</body>
</html>
