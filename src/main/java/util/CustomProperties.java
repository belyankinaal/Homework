package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class CustomProperties {
    private static final Properties props = new Properties();

    public static void loadProperties() {
        try {
            props.load(new FileInputStream(new File("src/test/resources/config.properties")));
        } catch (IOException e) {
            throw new RuntimeException("Не удалось загрузить файл конфигурации", e);
        }
    }

    public static String getProperty(String key) {
        return props.getProperty(key);
    }

    public static String getProperty(String key, String defaultValue) {
        return props.getProperty(key, defaultValue);
    }
}
