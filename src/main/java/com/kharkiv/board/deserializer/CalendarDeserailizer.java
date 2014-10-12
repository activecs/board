package com.kharkiv.board.deserializer;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;

public class CalendarDeserailizer implements JsonDeserializer<Calendar> {

	private static final Logger LOG = LoggerFactory
			.getLogger(CalendarDeserailizer.class);
	private static final String DATE_FORMAT = "dd.MM.yyyy - hh:mm";

	@Override
	public Calendar deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
			Date date = formatter.parse(json.getAsString());
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(date.getTime());
			return calendar;
		} catch (ParseException e) {
			LOG.error("Cannot deserialize json to calendar ->" + json, e);
			return null;
		}
	}

}
