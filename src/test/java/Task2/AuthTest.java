package Task2;

import Config.Specification;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.UUID;

public class AuthTest {

    private static final Logger log = LoggerFactory.getLogger(AuthTest.class);
    private static AuthService service;
    private static final ObjectMapper mapper = new ObjectMapper();
    private static String token;

    @BeforeAll
    public static void setUp() {
        RestAssured.requestSpecification = Specification.forLocalApi();
        service = new AuthService();
    }

    @Test
    @DisplayName("Работа с localhost")
    public void testFullAuthFlowWithLogout() {
        logStep("1. Регистрация пользователя");
        Response registerResponse = service.register(TestData.USER_JSON);
        Assertions.assertRegisterSuccess(registerResponse);

        logStep("2. Авторизация с невалидным именем");
        Response loginUserNotFound = loginWithModifiedField("username", "Belyankina1");

        logStep("3. Авторизация с невалидным паролем");
        Response loginWrongPass = loginWithModifiedField("password", "NoQwerty");

        logStep("4. Успешная авторизация");
        Response loginSuccess = service.login(TestData.USER_JSON);
        token = extractToken(loginSuccess);

        logStep("5. Попытка выхода с неверным токеном");
        Response logoutFail = service.logout(UUID.randomUUID().toString());

        logStep("6. Успешный logout");
        Response logoutSuccess = service.logout(token);

        Assertions.assertAllAuthScenarios(
                loginUserNotFound,
                loginWrongPass,
                loginSuccess,
                token,
                logoutFail,
                logoutSuccess
        );
    }

    private void logStep(String message) {
        log.info("[step] {}", message);
    }

    private Map<String, Object> generateModifiedUser(String key, String value) {
        try {
            Map<String, Object> map = mapper.readValue(TestData.USER_JSON, Map.class);
            map.put(key, value);
            return map;
        } catch (Exception e) {
            throw new RuntimeException("Ошибка", e);
        }
    }

    private Response loginWithModifiedField(String field, String value) {
        try {
            Map<String, Object> modifiedUser = generateModifiedUser(field, value);
            return service.login(mapper.writeValueAsString(modifiedUser));
        } catch (Exception e) {
            throw new RuntimeException("Ошибка", e);
        }
    }

    private String extractToken(Response response) {
        return response.getBody().asString().replace("token : ", "").trim();
    }
}
