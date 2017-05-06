package com.github.ocelotwarsplayer;

import static io.netty.handler.codec.http.HttpResponseStatus.INTERNAL_SERVER_ERROR;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;

import com.github.ocelotwarsplayer.playground.api.Playground;
import com.github.ocelotwarsplayer.strategy.basic.BasicStrategy;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientResponse;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class Player extends AbstractVerticle {

	private static final String PLAYER_NAME = "myplayer";
	private static final String ACCEPT = "accept";
	private static final int GAME_HOST_PORT = 8080;
	private static final String GAME_HOST = "localhost";

	private static final int MY_PORT = 8081;

	private BasicStrategy strategy = new BasicStrategy(new PlayerName(PLAYER_NAME));

	@Override
	public void start() {
		Router router = Router.router(vertx);

		HttpClient client = vertx.createHttpClient();
		client.post(GAME_HOST_PORT, GAME_HOST, "/register/" + PLAYER_NAME + "/" + MY_PORT, this::registerResponse).end();

		router.post("/requestMove").handler(this::requestMove);
		router.post("/invite").handler(this::invite);
		router.route().failureHandler(this::fail);

		HttpServer server = vertx.createHttpServer();
		server.requestHandler(router::accept).listen(MY_PORT);
	}

	public void registerResponse(HttpClientResponse response) {
		System.out.println("Received Registration Response: " + response.statusCode() + " " + response.statusMessage());
		if (response.statusCode() != OK.code()) {
			System.out.println("Registration Failed with Status Code " + response.statusCode());
		}
		if (!ACCEPT.equals(response.statusMessage())) {
			System.out.println("Registration Failed with Status Message " + response.statusMessage());
			System.out.println("Expected Status Message: " + ACCEPT);
		}
	}

	public void invite(RoutingContext context) {
		context.response().setStatusMessage(ACCEPT).setStatusCode(OK.code()).end();
	}

	public void requestMove(RoutingContext context) {
		System.out.println(context.getBodyAsString());
		Playground playground = Json.decodeValue(context.getBodyAsString(),
			Playground.class);

		strategy.calculateMoves(playground);//TODO this method should return a list of moves to be sent to the host
		context.response().setStatusCode(OK.code()).end();
	}

	public void fail(RoutingContext context) {

		context.response().setStatusCode(INTERNAL_SERVER_ERROR.code()).end();
	}

}
