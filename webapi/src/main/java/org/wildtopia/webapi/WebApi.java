package org.wildtopia.webapi;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.wildtopia.webapi.v1.ApiController;
import org.wildtopia.webapi.v1.ApiProvider;

import com.sun.net.httpserver.HttpServer;

public class WebApi {

    private final ApiController controller;
    private final HttpServer server;

    public WebApi(ApiProvider provider, String host, int port) throws IOException {
        this.controller = new ApiController(provider);
        this.server = HttpServer.create(new InetSocketAddress(host, port), 0);
        this.initApiV1();
    }

    private void initApiV1() throws IOException {
        this.server.createContext("/v1/count", this.controller::getCount);
    }

    public void start() {
        server.start();
    }

    public void stop() {
        server.stop(0);
    }

}