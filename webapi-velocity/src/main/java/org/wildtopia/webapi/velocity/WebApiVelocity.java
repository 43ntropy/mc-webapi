package org.wildtopia.webapi.velocity;

import java.io.IOException;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.wildtopia.webapi.WebApi;
import org.wildtopia.webapi.v1.ApiProvider;
import org.wildtopia.webapi.velocity.data.ConfigManager;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;

@Plugin(
    id = "webapi",
    name = "Velocity WebAPI",
    description = "A WebAPI implementation for Velocity",
    version = "0.1.1",
    authors = {
        "43ntropy"
    }
)
public class WebApiVelocity implements ApiProvider {

    private final ProxyServer proxy;
    private final Logger logger;
    private final ConfigManager configuration;

    private WebApi webApi;

    @Inject
    public WebApiVelocity(ProxyServer proxy, Logger logger, @DataDirectory Path dataDirectory) {
        this.proxy = proxy;
        this.logger = logger;
        this.configuration = new ConfigManager(dataDirectory, logger);
    }

    @Subscribe
    private void onProxyInitalize(ProxyInitializeEvent e) throws IOException {
        logger.info("Loading configuration...");
        configuration.load();
        logger.info("Preparing WebAPI...");
        this.webApi = new WebApi(this, configuration.getConfig().host, configuration.getConfig().port);
        this.webApi.start();
        logger.info("WebAPI listening on " + configuration.getConfig().host + ":" + configuration.getConfig().port);
    }

    @Subscribe
    private void onProxyShutdown(ProxyShutdownEvent e) {
        logger.info("Shutting down WebAPI...");
        this.webApi.stop();
        logger.info("WebAPI stopped, goodbye!");
    }

    @Override
    public int getCount() {
        return this.proxy.getPlayerCount();
    }

}
