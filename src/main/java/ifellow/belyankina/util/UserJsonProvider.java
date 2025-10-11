package ifellow.belyankina.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class UserJsonProvider {

    public static final String USER_JSON;

    static {
        try {

            String path = ConfigReader.getProperty("json.user");
            USER_JSON = Files.readString(Paths.get(path));
        } catch (IOException e) {
            throw new RuntimeException("Не удалось прочитать json.user", e);
        }
    }
}
