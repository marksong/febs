<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%> 
<%@ taglib prefix="common" uri="/WEB-INF/tld/common-tags.tld"%>
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
				<a href="<c:url value="/auth/user/${entity.id }" />"><spring:message code="label.show" /></a>
			</li>
		</ul>
	</div>
	<div class="row-fluid sortable">
		<div class="box span12">
			<div class="box-header well" data-original-title>
				<h2><i class="icon-edit"></i> <spring:message code="label.show" /><spring:message code="auth.user" /></h2>
			</div>
			<div class="box-content">
				<form class="form-horizontal">
				  <fieldset>
					<div class="control-group">
					  <label class="control-label"><spring:message code="auth.user.username" /> </label>
					  <div class="controls">
					  	 <span class="content">${entity.username}</span>
					  </div>
					</div>
					<div class="control-group">
					  <label class="control-label"><spring:message code="auth.user.password" /> </label>
					  <div class="controls">
					  	<span class="content">${entity.password}</span>
					  </div>
					</div>
					<div class="control-group">
					  <label class="control-label"><spring:message code="auth.user.email" /> </label>
					  <div class="controls">
					  	<span class="content">${entity.email}</span>
					  </div>
					</div>
					<div class="control-group">
					  <label class="control-label" for="authName"><spring:message code="auth.user.nickName" /> </label>
					  <div class="controls">
					  	<span class="content">${entity.nickName}</span>
					  </div>
					</div>
					<div class="form-actions">
					  <button type="button" class="btn" onclick="backForm()"><spring:message code="label.back" /></button>
					</div>
				  </fieldset>
				</form>   

			</div>
		</div><!--/span-->

	</div><!--/row-->
</body>
</html>
