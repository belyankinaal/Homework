package ifellow.belyankina.service;

import ifellow.belyankina.assertions.AuthAssertions;
import ifellow.belyankina.util.UserJsonProvider;
import io.restassured.response.Response;

public class RegistrationService {
    private final LocalHostService localHostService = new LocalHostService();

    public void successRegistration() {
        Response response = localHostService.register(UserJsonProvider.USER_JSON);
        AuthAssertions.assertRegisterSuccess(response);
    }
}