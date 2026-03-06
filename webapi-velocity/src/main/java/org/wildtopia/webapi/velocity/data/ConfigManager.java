package org.wildtopia.webapi.velocity.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

import org.slf4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.Strictness;

public class ConfigManager {

    private final File file;
    private final Logger logger;
    private final Gson gson;

    private Config config;

    public ConfigManager(Path dataDirectory, Logger logger) {
        this.file = dataDirectory.resolve("config.json").toFile();
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .setStrictness(Strictness.LENIENT)
                .create();
        this.logger = logger;
    }

    public void load() {
        // --- Create default configuration file if it doesn't exist
        if (!this.file.exists()) {
            config = new Config();
            this.save();
            return;
        }

        try (var reader = new FileReader(this.file)) {
            config = gson.fromJson(reader, Config.class);
        } catch (JsonSyntaxException e) {
            this.logger.error("Malformed configuration file: ", e);
        } catch (JsonIOException e) {
            this.logger.error("Cannot read configuration file: ", e);
        } catch (FileNotFoundException e) {
            this.logger.info("Failed to load configuration file: ", e);
        } catch (IOException e) {
            this.logger.error("Failed to load configuration file: ", e);
        }
    }

    public void save() {
        final var folder = this.file.getParentFile();

        // --- Create plugin directory if it doesn't exist
        if (!folder.exists())
            folder.mkdirs();

        // ---
        try (var writer = new FileWriter(this.file)) {
            gson.toJson(this.config, writer);
        } catch (JsonIOException e) {
            logger.error("Malformed configuration file: ", e);
            e.printStackTrace();
        } catch (IOException e) {
            logger.error("Failed to save configuration file: ", e);
            e.printStackTrace();
        }

    }

    public Config getConfig() {
        return config;
    }
}
