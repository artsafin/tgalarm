package com.artsafin.tgalarm;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

public class Configuration {

    private HashMap<String, String> values;

    private Configuration(HashMap<String, String> values) {
        this.values = values;
    }

    public static Configuration fromFile(String file) throws IOException {
        try (FileInputStream inputStream = new FileInputStream(file)) {

            Properties properties = new Properties();

            properties.load(inputStream);

            if (properties.getProperty("token") == null || properties.getProperty("username") == null) {
                throw new RuntimeException("Configuration must contain `token` and `username` fields");
            }

            return new Configuration(new HashMap<String, String>() {{
                put("token", properties.getProperty("token"));
                put("username", properties.getProperty("username"));
            }});
        }
    }

    String getToken() {
        return values.get("token");
    }

    String getUsername() {
        return values.get("username");
    }
}
