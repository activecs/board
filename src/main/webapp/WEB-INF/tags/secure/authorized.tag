<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ attribute name="roles" required="true"%>

<c:set var="contains" value="false" />

<c:forEach var="role" items="${fn:split(roles,',')}">
    <security:authorize access="hasRole('${role}')">
        <c:set var="contains" value="true" />
    </security:authorize>
</c:forEach>

<c:if test="${contains}">
    <jsp:doBody />
</c:if>