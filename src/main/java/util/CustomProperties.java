package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class CustomProperties {
    private static final Properties props = new Properties();

    // Загрузка конфигурации
    public static void loadProperties() {
        try {
            props.load(new FileInputStream(new File("src/test/resources/config.properties")));
        } catch (IOException e) {
            throw new RuntimeException("Не удалось загрузить файл конфигурации", e);
        }
    }

    // Универсальный метод получения значения по ключу
    public static String getProperty(String key) {
        return props.getProperty(key);
    }

    // Перегрузка с дефолтным значением
    public static String getProperty(String key, String defaultValue) {
        return props.getProperty(key, defaultValue);
    }
}
