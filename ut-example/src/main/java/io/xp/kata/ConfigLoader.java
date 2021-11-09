package io.xp.kata;

import java.io.IOException;
import java.util.Properties;

public class ConfigLoader {
    public Properties loadConfig(String path) {
        Properties properties = new Properties();
        try {
            properties.load(Main.class.getResourceAsStream(path));
        } catch (IOException ignored) {
        }
        return properties;
    }
}