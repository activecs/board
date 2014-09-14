<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ attribute name="title" required="true" %>

<!DOCTYPE>
<html>
<head>
	<title>${title}</title>
	<%@ include file="/WEB-INF/jspf/head.jspf"%>
</head>
	<body>
		<header>
			<%@ include file="/WEB-INF/jspf/header.jspf"%>
		</header>
	    <div class="container">
	        <div class="row">
				<jsp:doBody />
				<%@ include file="/WEB-INF/jspf/right.jspf"%>
			</div>
	    	<hr>
			<footer>
				<%@ include file="/WEB-INF/jspf/footer.jspf"%>
			</footer>
		</div>
	</body>
</html>