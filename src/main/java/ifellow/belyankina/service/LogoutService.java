package ifellow.belyankina.service;

import ifellow.belyankina.assertions.AuthAssertions;
import io.restassured.response.Response;

import java.util.UUID;

public class LogoutService {
    private final LocalHostService localHostService = new LocalHostService();

    public void logoutUnsuccessTest() {
        logoutWithToken(UUID.randomUUID().toString(), false);
    }

    public void logoutSuccessTest(String token) {
        logoutWithToken(token, true);
    }

    private void logoutWithToken(String token, boolean shouldSucceed) {
        Response response = localHostService.logout(token);
        if (shouldSucceed) {
            AuthAssertions.assertLogoutSuccess(response);
        } else {
            AuthAssertions.assertLogoutFail(response);
        }
    }

    public Response logoutWithTokenForStep(String token, boolean shouldSucceed) {
        Response response = localHostService.logout(token);
        if (shouldSucceed) {
            AuthAssertions.assertLogoutSuccess(response);
        } else {
            AuthAssertions.assertLogoutFail(response);
        }
        return response;
    }

}
