$(function(){
	$('.form_datetime').datetimepicker({
		language:  locale,
		format: "dd MM yyyy - hh:ii",
		autoclose: true,
		todayHighlight: true,
		startDate: "2014-01-01 10:00",
		minuteStep: 10
    });
	
	$('.glyphicon-remove').bind('click', function(){
		$(this).closest('div').find('input').val('');
	});
	
	$('button.add-event').bind('click', function(){
		scheduleEvent.showForm();
	});
	
	scheduleEvent.$form.find('button.cancel').bind('click', function(){
		scheduleEvent.hideForm();
	});
});

var common = {
	changeLocale : function(loc) {
		$.get(window.location.pathname, { "lang": loc }, function() {
			window.location.reload(true);
		});
	}
}

var registrationService = {
		
		REGISTRATION_URL : 'url',
		logoUploadActiveFlag : false,
		
		initUpload : function initialize() {
			if (!registrationService.logoUploadActiveFlag) {
			    'use strict';
			    $('#fileupload').fileupload({
			        autoUpload: false,
			        maxFileSize: 5000000, // 5 MB
			        previewMaxWidth: 150,
			        previewMaxHeight: 150,
			        previewCrop: true
			    }).on('fileuploadadd', function (e, data) {
			    	$('#files').html('');
			        data.context = $('<div/>').attr('id','image').attr('align','center').appendTo('#files');
			        $.each(data.files, function (index, file) {
			            var node = $('<p/>')
			                    .append($('<span/>').text(file.name));
			            if (!index) {
			  
			            }
			            node.appendTo(data.context);
			        });
			    }).on('fileuploadprocessalways', function (e, data) {
			        var index = data.index,
			            file = data.files[index],
			            node = $(data.context.children()[index]);
			        if (file.preview) {
			        	$('#image').append(file.preview);
			            $('#fileupload').attr('disabled','disabled');
			        }
			        if (file.error) {
			            node
			                .append('<br>')
			                .append($('<span class="text-danger"/>').text(file.error));
			        }
			    }).prop('disabled', !$.support.fileInput)
			        .parent().addClass($.support.fileInput ? undefined : 'disabled');
			    registrationService.logoUploadActiveFlag = true;
				}
		},
		
		destroyUpload : function destroy() {
			if (registrationService.logoUploadActiveFlag) {
				$('#fileupload').fileupload('destroy');
				$('#fileupload').removeAttr( 'disabled' )
				$('#files').html('');
				registrationService.logoUploadActiveFlag = false;
			}
		}
}

var scheduleEvent = {
	
	$form : $('form.add-event'),
		
	showForm : function(){
		this.$form.animate({
			opacity: 1,
			height: "toggle"
		}, 500);
	},
		
	hideForm : function() {
		this.$form.animate({
			opacity: 0,
			height: "toggle"
		}, 500);
	}
}