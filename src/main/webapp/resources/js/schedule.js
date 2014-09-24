var scheduleWS = {

	SCHEDULE_WEB_SOCKET_URL : window.location.host + 'wsschedule',

	socket : null,

	connect : function connectWebSocket(host) {
		if ('WebSocket' in window)
			socket = new WebSocket(host);
		else if ('MozWebSocket' in window)
			socket = new MozWebSocket(host);
		else
			return;

		socket.onopen = function() {
			alert('WebSocket connection is open!');
		};

		socket.onclose = function() {
			// leave error message that web socket is closed
		};

		socket.onmessage = function(message) {
			// message.data - get the info from the message
		};
		
		socket.onerror = function(cause) {
			// show message when error
		}
	},

	initialize : function setUpConnection() {
		var host = window.location.protocol == 'http:' ? 'ws://' : 'wss://';
		host += scheduleWS.SCHEDULE_WEB_SOCKET_URL; 
		scheduleWS.connect(host);
	},

	sendMessage : function send() {
	}
};