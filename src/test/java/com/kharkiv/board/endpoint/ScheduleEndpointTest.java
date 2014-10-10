package com.kharkiv.board.endpoint;

import static com.google.common.collect.Sets.newHashSet;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.io.IOException;

import javax.websocket.EncodeException;
import javax.websocket.RemoteEndpoint.Basic;
import javax.websocket.Session;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.kharkiv.board.dto.schedule.Schedule;
import com.kharkiv.board.service.ScheduleService;

public class ScheduleEndpointTest {
	
	@InjectMocks
	private ScheduleEndpoint endpoint = new ScheduleEndpoint();
	@Mock
	private ScheduleService mockService;
	@Mock
	private Session mockSession;
	@Mock
	private Session mockOpenSession;
	@Mock
	private Basic mockClient;
	@Mock
	private Schedule mockSchedule;
	
	@Before
	public void setUp() throws Exception {
		initMocks(this);
		initMockBehaviour();
	}
	
	private void initMockBehaviour() {
		when(mockSession.getOpenSessions()).thenReturn(newHashSet(mockOpenSession));
		when(mockOpenSession.getBasicRemote()).thenReturn(mockClient);
	}

	@Test
	public void shouldSaveSchedule_whenPublishNewSchedule() throws IOException, EncodeException{
		endpoint.publish(mockSession, mockSchedule);
		verify(mockService).addSchedule(mockSchedule);
	}
	
	@Test
	public void shouldGetsAllOpenSessions_whenPublishNewSchedule() throws IOException, EncodeException{
		endpoint.publish(mockSession, mockSchedule);
		verify(mockSession).getOpenSessions();
	}
	
	@Test
	public void shouldGetsBasicRemoteFromEachOpenSession_whenPublishNewSchedule() throws IOException, EncodeException{
		endpoint.publish(mockSession, mockSchedule);
		verify(mockOpenSession).getBasicRemote();
	}
	
	@Test
	public void shouldSendNewScheduleToEachOpenSession_whenPublishNewSchedule() throws IOException, EncodeException{
		endpoint.publish(mockSession, mockSchedule);
		verify(mockClient).sendObject(mockSchedule);
	}
}
