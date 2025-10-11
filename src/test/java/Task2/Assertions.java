package Task2;

import io.restassured.response.Response;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Assertions {

    public static void assertRegisterSuccess(Response response) {
        response.then().statusCode(200).body(equalTo("success register"));
    }

    public static void assertAllAuthScenarios(Response loginUserNotFound,
                                              Response loginWrongPass,
                                              Response loginSuccess,
                                              String token,
                                              Response logoutFail,
                                              Response logoutSuccess) {
        assertAll(
                "Проверка сценариев регистрации, авторизации и выхода",
                () -> loginUserNotFound.then().statusCode(401).body(equalTo("not found")),
                () -> loginWrongPass.then().statusCode(401).body(equalTo("not right pass")),
                () -> loginSuccess.then().statusCode(200).body(containsString("token :")),
                () -> assertTrue(token.length() > 10, "Токен содержит не менее 10 символов"),
                () -> logoutFail.then().statusCode(401).body(equalTo("not found")),
                () -> logoutSuccess.then().statusCode(200).body(equalTo("success logout"))
        );
    }
}
