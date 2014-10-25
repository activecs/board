<%@ include file="/WEB-INF/jspf/taglibs.jspf"%>

<c:set var="error_code" value="${pageContext.response.status}" />
<template:page title="Error ${error_code}">
	<div class="col-md-8">
		<h1>
		  <spring:message code="error.error"/>: ${error_code}
		</h1>
		<c:if test="${error_code == '404'}">
		    <c:url value="${requestUri}" var="requestUrl" />
			<h3>
			     URI <a href="${requestUrl}">${requestUrl}</a> <spring:message code="error.404"/>
			</h3>
		</c:if>
		<c:if test="${error_code == '500'}">
			<h3><spring:message code="error.500"/></h3>
		</c:if>
		
		 <!--
            Failed URL: ${requestUri}
            Exception:  ${exception.message}
            <c:forEach items="${exception.stackTrace}" var="ste">    
                ${ste} 
            </c:forEach>
        -->
		
	</div>
</template:page>
