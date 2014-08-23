package com.kharkiv.board.util;

public class QueryNamesConstants {

    private QueryNamesConstants() {
    }

    public static class UserQueries {
        private UserQueries() {
        }

        public static final String GET_ALL = "Users.getAll";
        public static final String GET_BY_ID = "Users.getById";
        public static final String GET_BY_LOGIN = "Users.getByLogin";

        public static final String DELETE_BY_ID = "User.deleteById";
        public static final String DELETE_BY_LOGIN = "User.deleteByLogin";
    }

    public static class CommentQueries {
        private CommentQueries() {
        }

        public static final String GET_4_USER_BY_USER_ID = "Comments.get4USerByUserId";
        public static final String GET_4_USER_BY_USER_LOGIN = "Comments.get4USerByUserLogin";
        public static final String GET_4_SCHEDULE_BY_SCHEDULE_ID = "Comments.get4ScheduleByScheduleId";
    }

    public static class ScheduleQueries {
        private ScheduleQueries() {
        }

        public static final String GET_ALL = "Schedules.getAll";
        public static final String GET_BY_ID = "Schedules.getById";
        public static final String GET_4_USER_BY_USER_ID = "Schedules.get4UserByUserId";
        public static final String GET_4_USER_BY_USER_LOGIN = "Schedules.get4UserByUserLogin";

        public static final String DELETE_BY_ID = "Schedules.deleteById";
    }

    public static class TrainingVisitsQueris {
        private TrainingVisitsQueris() {
        }

        public static final String GET_4_USER_BY_USER_ID = "TrainngVisits.get4UserByUserId";
        public static final String GET_4_SCHEDULE_BY_SCHEDULE_ID = "TrainingVisits.get4ScheduleByScheduleId";
        
        public static final String DELETE_BY_ID = "TrainingVisits.deleteById";

    }
}
