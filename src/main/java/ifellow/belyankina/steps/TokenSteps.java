package ifellow.belyankina.steps;

import ifellow.belyankina.service.LogoutService;
import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Тогда;
import io.restassured.response.Response;

public class TokenSteps {

    private final LogoutService userLogout = new LogoutService();
    private Response response;
    private String savedToken;

    public void setSavedToken(String token) {
        this.savedToken = token;
    }


    @И("кладем невалидный токен и делаем запрос")
    public void putInvalidTokenAndSendRequest() {
        String[] parts = savedToken.split("\\.");

        if (parts.length != 3) {
            throw new IllegalStateException("Token has invalid format");
        }

        String signature = parts[2];
        int len = signature.length();
        String corruptedSignature;
        if (len >= 2) {
            corruptedSignature = signature.substring(0, len - 2) + "ab";
        } else {
            corruptedSignature = signature + "ab";
        }

        String invalidToken = parts[0] + "." + parts[1] + "." + corruptedSignature;

        response = userLogout.logoutWithTokenForStep("Bearer " + invalidToken, false);
    }

    @Тогда("пользователь получает ответ 'not found' и статус 401 для невалидного токена")
    public void checkInvalidTokenResponse() {
        if (response.getStatusCode() != 401) {
            throw new AssertionError("Expected status 401 but got: " + response.getStatusCode());
        }
        if (!response.getBody().asString().contains("not found")) {
            throw new AssertionError("Expected response 'not found' but got: " + response.getBody().asString());
        }
    }
}
