package com.kharkiv.board.deserializer;

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.MINUTE;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.lang.reflect.Type;
import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;

public class CalendarDeserailizerTest {
	
	private static final String NOT_VALID_DATE = "12.12.2014 - 10:zz";
	private static final String VALID_DATE = "12.12.2014 - 10:15";
	
	private CalendarDeserailizer deserailizer = new CalendarDeserailizer();
	
	@Mock
	private JsonElement mockJson;
	@Mock
	private JsonDeserializationContext mockContext;
	@Mock
	private Type mockType;
	
	@Before
	public void setUp() throws Exception {
		initMocks(this);
		initMockBehaviour();
	}

	private void initMockBehaviour() {
		when(mockJson.getAsString()).thenReturn(VALID_DATE);	
	}

	@Test
	public void shouldReturnCalendarWithExpectedDateAndTime_whenJsonIsValid() {
		Calendar deserailizedDate = deserailizer.deserialize(mockJson, mockType, mockContext);
		assertThat(deserailizedDate.get(YEAR)).isEqualTo(2014);
		assertThat(deserailizedDate.get(MONTH)).isEqualTo(11);
		assertThat(deserailizedDate.get(DAY_OF_MONTH)).isEqualTo(12);
		assertThat(deserailizedDate.get(HOUR_OF_DAY)).isEqualTo(10);
		assertThat(deserailizedDate.get(MINUTE)).isEqualTo(15);
	}

	@Test
	public void shouldReturnNull_whenJsonIsNotValid() {
		when(mockJson.getAsString()).thenReturn(NOT_VALID_DATE);
		Calendar deserailizedDate = deserailizer.deserialize(mockJson, mockType, mockContext);
		assertThat(deserailizedDate).isNull();
	}
}