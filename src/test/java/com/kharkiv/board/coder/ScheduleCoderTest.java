package com.kharkiv.board.coder;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

import javax.websocket.DecodeException;
import javax.websocket.EncodeException;
import javax.websocket.EndpointConfig;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.kharkiv.board.dto.schedule.Schedule;

public class ScheduleCoderTest {
	
	private static final String TITLE = "title";
	private static final String PLACE = "place";
	private final static String MALFORMED_JSON = "{unexisting:property";
	private final static String VALID_JSON = String.format("{\"place\":\"%s\",\"title\":\"%s\"}", PLACE, TITLE);
	
	@Mock
	private EndpointConfig mockEncodingConfig;
	private ScheduleCoder coder = new ScheduleCoder();
	
	@Before
	public void setUp() throws Exception {
		initMocks(this);
	}

	@Test
	public void shouldNotDecodeSchedule_whenJsonIsNull() {
		boolean willDecode = coder.willDecode(null);
		assertThat(willDecode).isFalse();
	}
	
	@Test
	public void shouldNotDecodeSchedule_whenJsonIsEmpty() {
		boolean willDecode = coder.willDecode(EMPTY);
		assertThat(willDecode).isFalse();
	}
	
	@Test
	public void shouldNotDecodeSchedule_whenJsonIsMalFormed() {
		coder.init(mockEncodingConfig);
		boolean willDecode = coder.willDecode(MALFORMED_JSON);
		assertThat(willDecode).isFalse();
	}
	
	@Test
	public void shouldDecodeGson_whenJsonIsValid() throws DecodeException{
		coder.init(mockEncodingConfig);
		Schedule schedule = coder.decode(VALID_JSON);
		assertThat(schedule.getTitle()).isEqualTo(TITLE);
		assertThat(schedule.getPlace()).isEqualTo(PLACE);
	}
	
	@Test
	public void shouldEncodeGson_whenJsonIsValid() throws DecodeException, EncodeException{
		coder.init(mockEncodingConfig);
		Schedule schedule = new Schedule();
		schedule.setPlace(PLACE);
		schedule.setTitle(TITLE);
		assertThat(coder.encode(schedule)).isEqualTo(VALID_JSON);
	}
}
