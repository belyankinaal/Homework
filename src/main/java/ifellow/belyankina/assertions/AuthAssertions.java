package ifellow.belyankina.assertions;

import io.restassured.response.Response;

import static org.hamcrest.Matchers.equalTo;

public class AuthAssertions {

    public static void assertRegisterSuccess(Response r) {
        r.then()
                .statusCode(200)
                .body(equalTo("success register"));
    }
}
