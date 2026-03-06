package org.wildtopia.webapi.v1;

import io.javalin.http.Context;

public class ApiController {

    final ApiProvider provider;

    public ApiController(ApiProvider provider) {
        this.provider = provider;
    }

    public void getCount(Context ctx) {
        record Respose(int count) {}
        ctx.json(new Respose(provider.getCount()));
    }

}