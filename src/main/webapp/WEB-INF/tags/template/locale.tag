<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- localization -->
<link rel="resource" type="application/l10n" href="<c:url value='/resources/js/locales/locales.ini'/>"/>
<script type="text/javascript" src="<c:url value='/resources/js/l10n.js'/>"></script>

<c:set var="loc" scope="request" value="${requestContext.locale.language}" />
<script>
	var locale = "${loc}";
	document.webL10n.setLanguage(locale);
	var _ = document.webL10n.get;
</script>

<div class="btn-group lang-selector">
	<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
		<span class="lang-sm lang-lbl" lang="${loc}"></span> 
		<span class="caret"></span>
	</button>
	<ul class="dropdown-menu dropdown-menu-right" role="menu">
		<li><a><span class="lang-sm lang-lbl" onclick="common.changeLocale('en')" lang="en"></span></a></li>
		<li><a><span class="lang-sm lang-lbl" onclick="common.changeLocale('ru')" lang="ru"></span></a></li>
		<li><a><span class="lang-sm lang-lbl" onclick="common.changeLocale('uk')" lang="uk"></span></a></li>
	</ul>
</div>
