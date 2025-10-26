package ifellow.belyankina.service;

import io.restassured.response.Response;

public class LogoutService {

    private final LocalHostService localHostService = new LocalHostService();

    public Response logoutWithTokenForStep(String token) {
        return localHostService.logout(token);
    }
}
