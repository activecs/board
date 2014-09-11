var ScheduleWS = {

	SCHEDULE_WEB_SOCKET_URL : window.location.host + '/pages/wsschedule',

	socket : null,

	Connect : function connectWebSocket(host) {
		if ('WebSocket' in window) {
			socket = new WebSocket(host);
		} else if ('MozWebSocket' in window) {
			socket = new MozWebSocket(host);
		} else {
			// add alert: WebSocket is not supported by this browser.
			return;
		}

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

	Initialize : function setUpConnection() {
		var host = (window.location.protocol == 'http:') ? 'ws://'
				+ ScheduleWS.SCHEDULE_WEB_SOCKET_URL : 'wss://'
				+ +ScheduleWS.SCHEDULE_WEB_SOCKET_URL;
		ScheduleWS.Connect(host);
	},

	SendMessage : function send() {
	}
};