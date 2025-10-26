package ifellow.belyankina.service;

import io.restassured.response.Response;

public class LogoutService {

    private final LocalHostService localHostService = new LocalHostService();

    /**
     * Для шагов Cucumber — возвращаем Response без assert
     */
    public Response logoutWithTokenForStep(String token) {
        return localHostService.logout(token);
    }

    public void logoutSuccessTest(String token) {
        Response response = localHostService.logout(token);
        if (response.statusCode() != 200 || !"success logout".equals(response.getBody().asString())) {
            throw new AssertionError("Logout success assertion failed");
        }
    }

    public void logoutUnsuccessTest() {
        Response response = localHostService.logout("invalid-token");
        if (response.statusCode() != 401 || !"not found".equals(response.getBody().asString())) {
            throw new AssertionError("Logout fail assertion failed");
        }
    }
}
