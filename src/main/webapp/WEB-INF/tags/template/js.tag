<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- jQuery library -->
<script type="text/javascript" src="<c:url value='/resources/js/jquery.js'/>"></script>

<!-- localization -->
<link rel="resource" type="application/l10n" href="<c:url value='/resources/js/locales/locales.ini'/>"/>
<script type="text/javascript" src="<c:url value='/resources/js/l10n.js'/>"></script>

<script type="text/javascript" src="<c:url value='/resources/js/validationengine.js'/>"></script>
<c:choose>
	<c:when test="${loc eq 'en'}">
		<script type="text/javascript" src="<c:url value='/resources/js/locales/validationengine.en.js'/>"></script>
	</c:when>
	<c:otherwise>
		<script type="text/javascript" src="<c:url value='/resources/js/locales/validationengine.ru.js'/>"></script>
	</c:otherwise>
</c:choose>

<script type="text/javascript" src="<c:url value='/resources/js/bootstrap.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/js/analitics.js'/>"></script>

<!-- date time picker -->
<script type="text/javascript" src="<c:url value='/resources/js/datetimepicker.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/js/locales/datetimepicker.uk.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/js/locales/datetimepicker.ua.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/js/locales/datetimepicker.ru.js'/>"></script>

<!-- jQuery validate -->
<script type="text/javascript" src="<c:url value='/resources/js/jquery.validate.min.js'/>"></script>
<c:if test="${loc eq 'ru'}">
	<script type="text/javascript" src="<c:url value='/resources/js/locales/messages_ru.js'/>"></script>
</c:if>

<script type="text/javascript" src="<c:url value='/resources/js/jsrender.min.js'/>"></script>

<!-- customization js -->
<script type="text/javascript" src="<c:url value='/resources/js/sockets.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/js/changes.js'/>"></script>
