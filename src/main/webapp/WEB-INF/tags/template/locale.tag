<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="loc" scope="request" value="${requestContext.locale.language}" />

<script>
	var locale = "${loc}";
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
