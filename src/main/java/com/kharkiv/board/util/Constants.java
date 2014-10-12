package com.kharkiv.board.util;

public final class Constants {

	private Constants() {
	}

	// cache name
	public static final String CACHE_NAME = "serviceCache";

	// cache keys
	public static final String USER_CACHE_KEY = "'getAllUsersKey'";
	public static final String SCHEDULE_CACHE_KEY = "'getAllSchedulesKey'";

	public static final String USER_CACHE_CONDITION = USER_CACHE_KEY + " != null";
	public static final String SCHEDULE_CACHE_CONDITION = SCHEDULE_CACHE_KEY + " != null";
}
