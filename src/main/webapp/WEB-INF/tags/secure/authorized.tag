<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ attribute name="roles" required="true"%>

<c:if test="${not empty sessionScope.user && not empty sessionScope.user.role}">
	<c:set var="userRole" value="${sessionScope.user.role}"/>
	<c:set var="contains" value="false" />
	<c:forEach var="role" items="${fn:split(roles,',')}">
		<c:if test="${userRole eq role}">
			<c:set var="contains" value="true" />
		</c:if>
	</c:forEach>
	<c:if test="${contains}">
		<jsp:doBody />
	</c:if>
</c:if>