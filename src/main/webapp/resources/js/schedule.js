var sockets = {
			
	connect : function(host, onopen, onclose, onmessage, onerror) {
		var socket = new WebSocket(host);
		socket.onopen = function() {
			if(typeof onopen == 'function')
				onopen();
		};
		socket.onclose = function() {
			if(typeof onclose == 'function')
				onclose();
		};
		socket.onmessage = function(message) {
			if(typeof onmessage == 'function')
				onmessage(message);
		};
		socket.onerror = function(cause) {
			if(typeof onerror == 'function')
				onerror(cause);
		}
		
		return socket;
	},
	
	getHost : function(url) {
		var host = window.location.protocol == 'http:' ? 'ws://' : 'wss://';
		host += window.location.host;
		host += url;
		return host;
	}
}

var scheduleWS = {

	scheduleUrl : '/schedule/add',
	
	socket : null,

	initialize : function() {
		var host = sockets.getHost(this.scheduleUrl);
		this.socket = sockets.connect(host);
	},

	send : function(event) {
		this.socket.send(event)
	}
};