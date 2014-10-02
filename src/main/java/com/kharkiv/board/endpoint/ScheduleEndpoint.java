package com.kharkiv.board.endpoint;

import java.io.IOException;
import java.util.Set;

import javax.inject.Inject;
import javax.websocket.EncodeException;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.RemoteEndpoint.Basic;
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

	@OnMessage
	public void message(@PathParam("action") String action,Session session, Schedule schedule) throws IOException, EncodeException {
		System.out.println("receiver schedule id ->" + schedule.getTitle());
		share(schedule, session);
	}

	private void share(Schedule schedule, Session currentSession) throws IOException, EncodeException {
		Set<Session> allSessions = currentSession.getOpenSessions();
		for(Session session : allSessions){
			Basic client = session.getBasicRemote();
			client.sendObject(schedule);
		}
		
	}

	@OnError
	public void onError(Session session, Throwable error) throws Throwable {
		System.out.println(error);
	}
}