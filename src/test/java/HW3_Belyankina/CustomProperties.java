package HW3_Belyankina;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class CustomProperties {
    private static Properties props = new Properties();

    public static void loadProperties() {
        try {
            props.load(new FileInputStream(new File("src/main/resources/config.properties")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getWebUrl() {
        return props.getProperty("web.url");
    }

    public static String getUserName() {
        return props.getProperty("user.name");
    }

    public static String getUserPassword() {
        return props.getProperty("user.password");
    }
}

