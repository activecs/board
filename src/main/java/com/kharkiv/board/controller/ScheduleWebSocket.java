package com.kharkiv.board.controller;

import javax.inject.Inject;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.web.socket.server.standard.SpringConfigurator;

import com.kharkiv.board.service.ScheduleService;

@ServerEndpoint(value = "/pages/wsschedule", configurator = SpringConfigurator.class)
public class ScheduleWebSocket {

    @Inject
    private ScheduleService scheduleService;

    @OnOpen
    public void open(Session session) {
    }

    @OnClose
    public void close(Session session) {
    }

    @OnMessage
    public void message(Session session, String msg) {
        // add encoder and decoder
        // session.getOpenSessions();
    }

    @OnError
    public void onError(Session session, Throwable error) throws Throwable {
    }

}
