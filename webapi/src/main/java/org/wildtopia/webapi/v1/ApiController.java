package org.wildtopia.webapi.v1;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

public class ApiController {

    final ApiProvider provider;
    final Gson gson;

    public ApiController(ApiProvider provider) {
        this.provider = provider;
        this.gson = new Gson();
    }

    public void getCount(HttpExchange exchange) throws IOException {
        record Respose(int count) {
        }
        var json = this.gson.toJson(new Respose(provider.getCount()));
        byte[] responseBytes = json.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(200, json.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(responseBytes);
        }
    }

}