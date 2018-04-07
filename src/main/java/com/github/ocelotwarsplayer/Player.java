package com.github.ocelotwarsplayer;

import static io.netty.handler.codec.http.HttpResponseStatus.INTERNAL_SERVER_ERROR;
import java.io.IOException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ocelotwarsplayer.strategy.basic.BasicStrategy;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.WebSocket;
import io.vertx.core.http.WebSocketFrame;
import io.vertx.ext.web.RoutingContext;

public class Player extends AbstractVerticle {

    private static final String PLAYER_NAME = "myplayer";
    private static final int GAME_HOST_PORT = 8080;
    private static final String GAME_HOST = "localhost";

    private ObjectMapper mapper;
    private BasicStrategy strategy;

    public Player() {
        mapper = new ObjectMapper();
        strategy = new BasicStrategy(new PlayerName(PLAYER_NAME));
    }

    @Override
    public void start() {
        HttpClient client = vertx.createHttpClient();
        client.websocket(GAME_HOST_PORT, GAME_HOST, "", this::websocket);
    }

    public void websocket(WebSocket socket) {
        try {
            socket.writeFinalTextFrame(json(new Register(PLAYER_NAME)));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        socket.frameHandler(frame -> message(frame, socket));
    }

    public void message(WebSocketFrame frame, WebSocket socket) {
        String text = frame.textData();
        try {
            Message msg = fromJson(text);
            System.out.println("message:" + text + "->" + msg.getClass().getName());
            if (msg instanceof Invite) {
                invite((Invite) msg, socket);
            } else if (msg instanceof Notify) {
                notify((Notify) msg, socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected String json(OutMessage value) throws JsonProcessingException {
        return mapper.writeValueAsString(value);
    }

    protected Message fromJson(String message) throws IOException {
        return mapper.readValue(message, Message.class);
    }

    public void invite(Invite invite, WebSocket websocket) {
        try {
            websocket.writeFinalTextFrame(json(new Accept()));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void notify(Notify notify, WebSocket websocket) {
        try {
            websocket.writeFinalTextFrame(json(new Commands()));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void fail(RoutingContext context) {

        context.response().setStatusCode(INTERNAL_SERVER_ERROR.code()).end();
    }

}
