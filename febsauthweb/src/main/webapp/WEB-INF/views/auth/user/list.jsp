<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%> 
<%@ taglib prefix="common" uri="common-tags"%> 
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<script type="text/javascript">
		function insertForm(filterId) {
			$('#filterId').val('');
			$('#listForm').attr('action', '<s:url namespace="/admin/authority/auth" action="insert" />').submit();
		}
		function updateForm(filterId) {
			$('#filterId').val(filterId);
			$('#listForm').attr('action', '<s:url namespace="/admin/authority/auth" action="update" />').submit();
		}
		function showForm(filterId) {
			$('#filterId').val(filterId);
			$('#listForm').attr('action', '<s:url namespace="/admin/authority/auth" action="show" />').submit();
		}
		function deleteForm(filterId) {
			$('#filterId').val(filterId);
			$('#listForm').attr('action', '<s:url namespace="/admin/authority/auth" action="delete" />').submit();
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
				<a href="<c:url value="/auth/user" />"><spring:message code="auth.user" /></a>
			</li>
		</ul>
	</div>
	<form id="listForm" action="<c:url value="/auth/user" />">
	<div class="row-fluid sortable">		
		<div class="box span12">
			<div class="box-header well" data-original-title>
				<h2><i class="icon-eye-open"></i> <spring:message code="auth.user" /></h2>
			</div>
			<div class="box-content">
				<div class="row-fluid">
					<div class="span6">
						<div class="dataTables_filter" id="DataTables_Table_0_filter">
							<label>
								<a class="btn btn-warning" href="${ctx}/auth/user/add">
									<i class="icon-edit icon-white"></i>  
									<spring:message code="label.create" />                                             
								</a>
								<spring:message code="label.search" />: <input type="text" >
							</label>
						</div>
					</div>
				</div>
				<table class="table table-striped table-bordered bootstrap-datatable">
				  <thead>
					  <tr>
						  <th><spring:message code="auth.user.username" /></th>
						  <th><spring:message code="auth.user.password" /></th>
						  <th><spring:message code="auth.user.email" /></th>
						  <th><spring:message code="auth.user.nickName" /></th>
						  <th><spring:message code="field.createTime" /></th>
						  <th><spring:message code="field.status" /></th>
						  <th><spring:message code="label.operate" /></th>
					  </tr>
				  </thead>   
				  <tbody>
				  	<c:forEach items="${page.items}" var="item">
					<tr>
						<td class="center">${item.username}</td>
						<td class="center">${item.password}</td>
						<td class="center">${item.email}</td>
						<td class="center">${item.nickName}</td>
						<td class="center"><fmt:formatDate value="${item.createTime}" pattern="yyyy/MM/dd HH:mm:ss" /></td>
						<td class="center">
							<span class="label label-success">Active</span>
						</td>
						<td class="center">
							<a class="btn btn-success" href="${ctx }/auth/user/${item.id}">
								<i class="icon-zoom-in icon-white"></i>  
								<spring:message code="label.show" />                                           
							</a>
							<a class="btn btn-info" href="${ctx }/auth/user/edit?filterId=${item.id}">
								<i class="icon-edit icon-white"></i>  
								<spring:message code="label.edit" />                                             
							</a>
							<a class="btn btn-danger" href="${ctx }/auth/user/${item.id}/delete">
								<i class="icon-trash icon-white"></i> 
								<spring:message code="label.delete" /> 
							</a>
						</td>
					</tr>
					</c:forEach>
				  </tbody>
			  </table>            
			  <common:page formId="listForm" />
			</div>
		</div><!--/span-->
	</div><!--/row-->
	</form>
</body>
</html>