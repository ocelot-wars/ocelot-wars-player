package com.github.ocelotwarsplayer;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.WebSocket;

public class Player extends AbstractVerticle {

	private static final String GAME_HOST = "localhost";
	private static final int GAME_HOST_PORT = 8080;

	@Override
	public void start() {
		HttpClient client = vertx.createHttpClient();
		client.websocket(GAME_HOST_PORT, GAME_HOST, "/register", this::websocket, this::failure);
	}

	public void websocket(WebSocket socket) {
		socket.frameHandler(frame -> {
			System.out.println(frame.textData());
			socket.writeFinalTextFrame("{\"@type\":\"accept\"}");
		});
		socket.writeFinalTextFrame("{\"@type\":\"register\",\"playerName\":\"XYZ\"}");
	}

	public void failure(Throwable e) {
		System.out.println("fail " + e.getMessage());
	}

}
