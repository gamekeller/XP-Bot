package de.gamekeller.Parser;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.gamekeller.Configs.ServerConfig;

import java.io.File;
import java.io.IOException;

public class ServerConfigParser {

    public static ServerConfig parse() {
        try {
            File jsonFile = new File("ServerConfig.json");
            ObjectMapper objectMapper = new ObjectMapper();
            ServerConfig serverConfig = objectMapper.readValue(jsonFile, ServerConfig.class);
            return serverConfig;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
