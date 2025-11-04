package util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {

    private static final Properties props = new Properties();
    private static final String CONFIG_FILE = "config.properties";

    static  {
        load();
    }

    private static void load() {
        try {
            InputStream is = ConfigLoader.class.getClassLoader().getResourceAsStream(CONFIG_FILE);
            if (is == null) {
                System.err.println("Config dosyası bulunamadı");
                return;
            }
            props.load(is);
            System.out.println("Config yüklendi: " + CONFIG_FILE);

        } catch (IOException e) {
            System.err.println("Config okuma hatası: " + e.getMessage());
        }
    }

    public static String get(String key){
        return props.getProperty(key);
    }

    public static String get(String key, String defaultValue) {
        return props.getProperty(key, defaultValue);
    }

    public static int getInt(String key, int defaultValue) {
        String val = props.getProperty(key);
        try {
            return val != null ? Integer.parseInt(val) : defaultValue;
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        String val = props.getProperty(key);
        return val != null ? Boolean.parseBoolean(val) : defaultValue;
    }

}
