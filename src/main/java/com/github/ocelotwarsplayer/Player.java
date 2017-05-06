package com.github.ocelotwarsplayer;

import static io.netty.handler.codec.http.HttpResponseStatus.INTERNAL_SERVER_ERROR;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientResponse;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class Player extends AbstractVerticle {
	
	private static final int GAME_HOST_PORT = 8080;
	private static final String GAME_HOST = "localhost";
	
	private static final String MY_HOST = "localhost";
	private static final int MY_PORT = 8081;

	public void start() {
        Router router = Router.router(vertx);
        
        HttpClient client = vertx.createHttpClient();
		client.getNow(GAME_HOST_PORT, GAME_HOST, "/register/myplayer/"+ MY_HOST  + "/"+ MY_PORT, this::registerResponse);
        
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
		if (!"accepted".equals(response.statusMessage())) {
			System.out.println("Registration Failed with Status Message " + response.statusMessage());
			System.out.println("Expected Status Message: accepted");
		}
	}
	
	public void invite(RoutingContext context) {
		context.response()
			.setStatusMessage("accepted")
	        .setStatusCode(OK.code())
	        .end();
	}

    public void requestMove(RoutingContext context) {
    	System.out.println(context.getBodyAsString());

        context.response()
            .setStatusCode(OK.code())
            .end();
    }
    
    public void fail(RoutingContext context) {

        context.response()
            .setStatusCode(INTERNAL_SERVER_ERROR.code())
            .end();
    }

}
