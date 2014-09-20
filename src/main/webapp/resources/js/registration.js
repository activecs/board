var RegistrationService = {
		
		REGISTRATION_URL : 'url',
		
		logoUploadActiveFlag : false,
		
		initUpload : function initialize() {
			if (!RegistrationService.logoUploadActiveFlag) {
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
			    RegistrationService.logoUploadActiveFlag = true;
				}
		},
		
		destroyUpload : function destroy() {
			if (RegistrationService.logoUploadActiveFlag) {
				$('#fileupload').fileupload('destroy');
				$('#fileupload').removeAttr( 'disabled' )
				$('#files').html('');
				RegistrationService.logoUploadActiveFlag = false;
			}
		}
}