// src/main/java/util/ConfigLoader.java
package util;

import java.io.IOException;
import java.util.Properties;

public class ConfigLoader {
    private static final Properties props = new Properties();

    static {
        try (var in = ConfigLoader.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (in == null) {
                System.err.println("config.properties bulunamadı!");
            } else {
                props.load(in);
            }
        } catch (IOException e) {
            System.err.println("Config yüklenemedi: " + e.getMessage());
        }
    }

    public static String get(String key) {
        return props.getProperty(key, "default-notes.dat"); // Varsayılan!
    }
}