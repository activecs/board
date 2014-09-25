package com.kharkiv.board.endpoint;

import javax.inject.Inject;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.springframework.web.socket.server.standard.SpringConfigurator;

import com.kharkiv.board.coder.ScheduleCoder;
import com.kharkiv.board.dto.schedule.Schedule;
import com.kharkiv.board.service.ScheduleService;

@ServerEndpoint(value = "/schedule/{action}", 
	configurator = SpringConfigurator.class, 
	encoders = ScheduleCoder.class, 
	decoders = ScheduleCoder.class)
public class ScheduleEndpoint {

	@Inject
	private ScheduleService scheduleService;

	@OnOpen
	public void open(Session session) {
	}

	@OnClose
	public void close(Session session) {
	}

	@OnMessage
	public void message(@PathParam("action") String action,Session session, Schedule schedule) {
		System.out.println(schedule);
	}

	@OnError
	public void onError(Session session, Throwable error) throws Throwable {
		System.out.println(error);
	}

}
