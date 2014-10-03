package com.kharkiv.board.util;

public class Constants {

    private Constants() {
    }
    
    // cache name
    public static final String CACHE_NAME = "serviceGetAllCache";
    
    // cache keys
    public static final String GET_ALL_USER_CACHE_KEY = "#root.methodName";
    public static final String GET_ALL_SCHEDULE_CACHE_KEY = "#root.methodName";
    
    public static final String GET_ALL_USER_CACHE_CONDITION = "#root.methodName != null";
    public static final String GET_ALL_SCHEDULE_CACHE_CONDITION = "#root.methodName != null";
}
