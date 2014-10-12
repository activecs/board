package com.kharkiv.board.endpoint;

import java.io.IOException;
import java.util.Set;

import javax.inject.Inject;
import javax.websocket.EncodeException;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.RemoteEndpoint.Basic;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.server.standard.SpringConfigurator;

import com.kharkiv.board.coder.ScheduleCoder;
import com.kharkiv.board.dto.schedule.Schedule;
import com.kharkiv.board.service.ScheduleService;

@ServerEndpoint(value = "/schedule/add", configurator = SpringConfigurator.class, encoders = ScheduleCoder.class, decoders = ScheduleCoder.class)
public class ScheduleEndpoint {

	private static final Logger LOG = LoggerFactory
			.getLogger(ScheduleEndpoint.class);

	@Inject
	private ScheduleService scheduleService;

	@OnMessage
	public void publish(Session session, Schedule schedule) throws IOException,	EncodeException {
		LOG.info("receiver schedule id ->" + schedule.getTitle());
		scheduleService.addSchedule(schedule);
		share(schedule, session);
	}

	@OnError
	public void onError(Session session, Throwable error) {
		LOG.error(error.getMessage());
	}

	private void share(Schedule schedule, Session currentSession) throws IOException, EncodeException {
		Set<Session> allSessions = currentSession.getOpenSessions();
		for (Session session : allSessions) {
			Basic client = session.getBasicRemote();
			client.sendObject(schedule);
		}
	}
}