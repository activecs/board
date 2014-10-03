package com.kharkiv.board.coder;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

import java.util.Calendar;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.kharkiv.board.deserializer.CalendarDeserailizer;
import com.kharkiv.board.dto.schedule.Schedule;

public class ScheduleCoder implements Encoder.Text<Schedule>,
		Decoder.Text<Schedule> {

	private Gson gson;

	@Override
	public void init(EndpointConfig config) {
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(Calendar.class, new CalendarDeserailizer());
		gson = builder.create();
	}

	@Override
	public void destroy() {
		gson = null;
	}

	@Override
	public String encode(Schedule object) throws EncodeException {
		return gson.toJson(object);
	}

	@Override
	public Schedule decode(String json) throws DecodeException {
		return gson.fromJson(json, Schedule.class);
	}

	@Override
	public boolean willDecode(String json) {
		boolean willDecode = false;
		if (isNotEmpty(json)) {
			try {
				gson.fromJson(json, Schedule.class);
				willDecode = true;
			} catch (JsonSyntaxException e) {
			}
		}
		return willDecode;
	}
}
