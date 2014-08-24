<%@ page isErrorPage="true" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>Error</title>
	<%@ include file="/WEB-INF/jspf/head.jspf"%>
</head>
<body>
	<!-- Header =============================== -->
	<header>
		<%@ include file="/WEB-INF/jspf/header.jspf"%>
	</header>
	<!-- Header end =============================== -->
	
	<c:set var="error_code" value="${requestScope['javax.servlet.error.status_code']}" />
	<c:if test="${error_code == '404'}">
		<h1><fmt:message key="error.error"/>: <c:out value="${error_code}"/></h1>
		<h3>URI <c:out value="${requestScope['javax.servlet.forward.request_uri']}"/> <fmt:message key="error.404"/></h3>
	</c:if>
	<c:if test="${error_code == '500'}">
		<h1><fmt:message key="error.error"/>: <c:out value="${error_code}"/></h1>
		<h3><fmt:message key="error.500" /></h3>
	</c:if>
	<!-- Footer =============================== -->
	<footer>
		<%@ include file="/WEB-INF/jspf/footer.jspf"%>
	</footer>
	<!-- Footer end =============================== -->
</body>
</html>