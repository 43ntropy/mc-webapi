package org.wildtopia.webapi;

import org.wildtopia.webapi.v1.ApiController;
import org.wildtopia.webapi.v1.ApiProvider;
import io.javalin.Javalin;

public class WebApi {

    private final ApiController controller;
    private final Javalin server;

    public WebApi(ApiProvider provider, String host, int port) {
        this.controller = new ApiController(provider);
        this.server = Javalin.create(config -> {
            config.jetty.host = host;
            config.jetty.port = port;
            config.startup.showJavalinBanner = false;
            config.startup.showOldJavalinVersionWarning = true;
            config.routes.get("/v1/count", controller::getCount);
        });
    }

    public void start() {
        server.start();
    }

    public void stop() {
        server.stop();
    }

}