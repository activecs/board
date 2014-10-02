<%@ include file="/WEB-INF/jspf/taglibs.jspf"%>
<template:page title="Schedule">

	<!-- Blog Entries Column -->
	<div class="col-md-8">
	
	    <h1 class="page-header">
	        Page Heading
	        <small>Secondary Text</small>
        	<button  type="button" class="btn btn-primary pull-right add-event"><fmt:message key="event.add.new"/></button>
	        
	    </h1>
		
		<form class="add-event form-horizontal" role="form">
			<legend><fmt:message key="event.add.new"/></legend>
			
			<div class="form-group">
				<p class="col-sm-2"></p>
			    <label for="eventTitle" class="col-sm-2 control-label"><fmt:message key="event.title"/></label>
			    <div class="input-group col-sm-6">
			      <input type="text" class="form-control validate[required,minSize[5],maxSize[18]]" name="title" placeholder='<fmt:message key="event.title"/>'>
			    </div>
			</div>
			
			<div class="form-group">
				<p class="col-sm-2"></p>
			    <label for="eventPlace" class="col-sm-2 control-label"><fmt:message key="event.place"/></label>
			    <div class="input-group col-sm-6">
			      <input type="text" class="form-control validate[required,minSize[5],maxSize[18]] datepicker" name="place" placeholder='<fmt:message key="event.place"/>'>
			    </div>
			</div>
			
			<div class="form-group">
				<p class="col-sm-2"></p>
                <label for="eventDate" class="col-sm-2 control-label"><fmt:message key="event.date"/></label>
                <div class="input-group date form_datetime col-sm-6" data-link-field="eventDate">
                    <input class="form-control validate[required]" name="dateTime" type="text" readonly>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove icon-remove"></span></span>
					<span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
                </div>
            </div>
			
			<button type="submit" class="btn btn-success">
				<span class="glyphicon glyphicon-plus"></span><fmt:message key="event.create"/>
			</button>
			<div class="col-sm-6" ></div>
			<button type="button" class="btn btn-danger cancel">
				<span class="glyphicon glyphicon-remove"></span><fmt:message key="event.cancel"/>
			</button>
			    
		</form>
	    
	    <!-- Posts -->
		<div id="posts">
		    <c:forEach items="${schedules}" var="schedule" >
			    <h2>
			        <a href="#">${schedule.title}</a>
			    </h2>
			    <p class="lead">
			        by <a href="index.php">${schedule.user.login}</a>
			    </p>
			    <p><span class="glyphicon glyphicon-time"></span> Posted on ${schedule.created}</p>
			    <hr>
			    <img class="img-responsive" src="http://placehold.it/900x300" alt="${schedule.title}">
			    <hr>
			    <p>${schedule.place}<br>${schedule.dateTime}</p>
			    <a class="btn btn-primary" href="#">Read More <span class="glyphicon glyphicon-chevron-right"></span></a>
			    <hr>
		    </c:forEach>
		</div>
	
	    <!-- Pager -->
	    <ul class="pager">
	        <li class="previous">
	            <a href="#">&larr; Older</a>
	        </li>
	        <li class="next">
	            <a href="#">Newer &rarr;</a>
	        </li>
	    </ul>
	
	</div>
	
</template:page>