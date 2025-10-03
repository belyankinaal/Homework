package util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class CustomProperties {
    private static final Properties props = new Properties();

    public static void loadProperties() {
        try (InputStream stream = CustomProperties.class
                .getClassLoader()
                .getResourceAsStream("config.properties")) {
            if (stream == null) {
                throw new RuntimeException("Файл config.properties не найден в resources");
            }
            props.load(stream);
        } catch (IOException e) {
            throw new RuntimeException("Не удалось загрузить файл конфигурации", e);
        }
    }

    public static String getProperty(String key) {
        return props.getProperty(key);
    }
}
