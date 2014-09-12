<%@ page isErrorPage="true" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib tagdir="/WEB-INF/tags/template" prefix="template" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<template:page title="Schedule">	
	<c:set var="error_code" value="${requestScope['javax.servlet.error.status_code']}" />
	<c:if test="${error_code == '404'}">
		<h1><fmt:message key="error.error"/>: <c:out value="${error_code}"/></h1>
		<h3>URI <c:out value="${requestScope['javax.servlet.forward.request_uri']}"/> <fmt:message key="error.404"/></h3>
	</c:if>
	<c:if test="${error_code == '500'}">
		<h1><fmt:message key="error.error"/>: <c:out value="${error_code}"/></h1>
		<h3><fmt:message key="error.500" /></h3>
	</c:if>
</template:page>