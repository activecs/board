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
		
		REGISTRATION_URL : 'registration',
		
		validate : function validateForm(e) {
			var loginForm = $('#registrationForm');
			var confirmation = $('#conf_failed');
			
			confirmation.css({'display' : 'none'});
			
			loginForm.validate({
				  debug: true,
				  rules: {
			            login: {
			            	required: true,
			            	minlength: 3
			            },
			            password: {
			                required: true,
			                minlength: 6
			            },
			            confirm_password: {
			            	required: true,
			            	minlength: 6
			            },
			        }
				});
			
			loginForm.validate();
			if (loginForm.valid()) {
				var pass = $('#password').val();
				var passConf = $('#confirm_password').val();
				if (pass == passConf) {
					registrationService.register(e);
				} else {
					confirmation.css({'display' : 'block'});
				}
			}
		},
		
		register : function sendData(e) {
			var loginForm = $('#registrationForm');
			loginForm.submit(function(e){
			e.preventDefault();
			var input = $("#fileupload");
			input.prop('disabled', false);
			var formData = new FormData($(this)[0]);
				
			jQuery.ajax({
				url : registrationService.REGISTRATION_URL,
				type : 'POST',
				cache : false,
				contentType: false,
				processData: false,
				data : formData,
				beforeSend : function() {
					$('#loading').css({'display' : 'inline'});
				},
				complete : function() {
					$('#loading').css({'display' : 'none'});
				},
				success : function(data) {
					alert(data);
				},
				error : function() {
					alert('Server Error!');
				}
			});
					
			return false;
			});
			loginForm.submit();
			input.prop('disabled', true);
		},
		
		preview : function createPreview() {
			var input = $("#fileupload");
			input.on("change", function()
					    {
					        var files = !!this.files ? this.files : [];
					        if (!files.length || !window.FileReader) return;
					 
					        if (/^image/.test( files[0].type)){
					        	// lower than 5MB
					        	if (files[0].size <= 5242880) {
						            var reader = new FileReader(); 
						            reader.readAsDataURL(files[0]); 
						 
						            reader.onloadend = function(){ 
						                $("#files").css("background-image", "url("+this.result+")");
						            }
						            input.prop('disabled', true);
					        	} else {
					        		registrationService.clean();
					        		alert($('#image_error').text());
					        	}
					        }
					    });
		}, 
		
		clean : function cleanPreview() {
			$("#files").css("background-image", "url(/resources/images/profile-placeholder.jpg)");
			var input = $("#fileupload");
			input.prop('disabled', false);
			input.replaceWith(input.val('').clone(true));
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