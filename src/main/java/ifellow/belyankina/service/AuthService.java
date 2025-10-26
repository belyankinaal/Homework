package ifellow.belyankina.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import ifellow.belyankina.util.ConfigReader;
import ifellow.belyankina.util.UserJsonProvider;
import io.restassured.response.Response;

import java.util.Map;

public class AuthService {
    private static final String BAD_USER = ConfigReader.getProperty("test.invalid.username");
    private static final String BAD_PASS = ConfigReader.getProperty("test.invalid.password");
    private final LocalHostService api = new LocalHostService();
    private final ObjectMapper mapper = new ObjectMapper();
    private int lastStatus;

    public Response wrongLoginAuth(String invalidUsername) {
        Response r = modifiedLogin("username", invalidUsername);
        lastStatus = r.getStatusCode();
        return r;
    }

    public Response wrongPasswordAuth(String invalidPassword) {
        Response r = modifiedLogin("password", invalidPassword);
        lastStatus = r.getStatusCode();
        return r;
    }

    public String successCredentialsAuth() {
        Response r = api.login(UserJsonProvider.USER_JSON);
        String token = r.getBody().asString().replace("token : ", "");
        lastStatus = r.getStatusCode();
        return token;
    }

    public int getLastStatus() {
        return lastStatus;
    }

    private Response modifiedLogin(String field, String value) {
        try {
            Map<String, Object> user = generateUser(field, value);
            return api.login(mapper.writeValueAsString(user));
        } catch (Exception e) {
            throw new RuntimeException("Ошибка модификации пользователя", e);
        }
    }

    private Map<String, Object> generateUser(String key, String value) {
        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> map = mapper.readValue(UserJsonProvider.USER_JSON, Map.class);
            map.put(key, value);
            return map;
        } catch (Exception e) {
            throw new RuntimeException("Ошибка создания пользователя", e);
        }
    }
}
