<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- jQuery library -->
<script type="text/javascript" src="<c:url value='/resources/js/jquery.js'/>"></script>
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

<!-- customization js -->
<script type="text/javascript" src="<c:url value='/resources/js/changes.js'/>" ></script>
<script type="text/javascript" src="<c:url value='/resources/js/schedule.js'/>"></script>