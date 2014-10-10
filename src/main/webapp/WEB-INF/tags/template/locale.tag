<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- localization -->
<link rel="resource" type="application/l10n" href="<c:url value='/resources/js/locales/locales.ini'/>"/>
<script type="text/javascript" src="<c:url value='/resources/js/l10n.js'/>"></script>

<c:set var="loc" scope="request" value="${not empty sessionScope['javax.servlet.jsp.jstl.fmt.locale.session'] ? sessionScope['javax.servlet.jsp.jstl.fmt.locale.session'] : pageContext.request.locale.language}" />
<script>
	var locale = "${loc}";
	document.webL10n.setLanguage(locale);
	var _ = document.webL10n.get;
</script>

<c:choose>
	<c:when test="${loc eq 'en'}">
		<li><a href="#" onclick="common.changeLocale('ru')">ru</a></li>
	</c:when>
	<c:otherwise>
		<li><a href="#" onclick="common.changeLocale('en')">en</a></li>
	</c:otherwise>
</c:choose>
