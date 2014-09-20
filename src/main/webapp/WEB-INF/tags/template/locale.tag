<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="loc" value="${not empty sessionScope['javax.servlet.jsp.jstl.fmt.locale.session'] ? sessionScope['javax.servlet.jsp.jstl.fmt.locale.session'] : pageContext.request.locale.language}" />
<script>
	var locale = "${loc}";
</script>

<c:choose>
	<c:when test="${loc eq 'en'}">
		<li><a href="#" onclick="common.changeLocale('ru')">ru</a></li>
	</c:when>
	<c:otherwise>
		<li><a href="#" onclick="common.changeLocale('en')">en</a></li>
	</c:otherwise>
</c:choose>
