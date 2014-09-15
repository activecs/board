<%@ include file="/WEB-INF/jspf/taglibs.jspf"%>
<%@ page isErrorPage="true" %>

<template:page title="Error">
	<div class="col-md-8">
		<c:set var="error_code" value="${requestScope['javax.servlet.error.status_code']}" />
		<c:if test="${error_code == '404'}">
			<h1><fmt:message key="error.error"/>: <c:out value="${error_code}"/></h1>
			<h3>URI <c:out value="${requestScope['javax.servlet.forward.request_uri']}"/> <fmt:message key="error.404"/></h3>
		</c:if>
		<c:if test="${error_code == '500'}">
			<h1><fmt:message key="error.error"/>: <c:out value="${error_code}"/></h1>
			<h3><fmt:message key="error.500"/></h3>
		</c:if>
	</div>
</template:page>
