var schedule = {
		initialize : function() {
			scheduleAdd.initialize();
		}
}

var scheduleAdd = {

	scheduleUrl : '/schedule/add',
	
	socket : null,
	
	messages : null,

	initialize : function() {
		var host = sockets.getHost(this.scheduleUrl);
		this.socket = sockets.connect(host, null, null, this.showNewPost);
		document.localePromise.done(function() {
			scheduleAdd.setUpLocalization();				
		});
	},

	send : function(event) {
		this.socket.send(event)
	},
	
	setUpLocalization : function(){
		this.messages = {
			where : _("where"),
			when : _("when"),
			postedBy : _("postedBy"),
			postedOn : _("postedOn")
		};
	},
	
	showNewPost : function(message){
		var data = $.parseJSON(message.data);
		$.extend(data, scheduleAdd.messages);
		$.get('/resources/templates/scheduleTemplate.html',function(template) {
			$postTemplate = $.templates(template);
			$("#posts").prepend($postTemplate.render(data));
		});
	} 
};

var scheduleRemove = {

	scheduleUrl : '/schedule/remove',
	
	socket : null,

	initialize : function() {
		var host = sockets.getHost(this.scheduleUrl);
		this.socket = sockets.connect(host, null, null, this.showNewPost);
	},

	send : function(event) {
		this.socket.send(event)
	},
	
	removePost : function(message){
		var data = $.parseJSON(message.data);
		$.get('/resources/templates/scheduleTemplate.html',function(template) {
			$postTemplate = $.templates(template);
			$("#posts").prepend($postTemplate.render(data));
		});
	} 
};