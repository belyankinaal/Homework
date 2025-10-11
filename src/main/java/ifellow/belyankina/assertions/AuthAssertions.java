package ifellow.belyankina.assertions;

import ifellow.belyankina.util.ConfigReader;
import io.restassured.response.Response;
import org.hamcrest.Matcher;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class AuthAssertions {


    private static final int MIN_TOKEN_LENGTH = Integer.parseInt(ConfigReader.getProperty("test.token.min.length"));

    public static void assertRegisterSuccess(Response r) {
        check(r, 200, equalTo("success register"));
    }

    public static void assertLoginUserNotFound(Response r) {
        check(r, 401, equalTo("not found"));
    }

    public static void assertLoginWrongPassword(Response r) {
        check(r, 401, equalTo("not right pass"));
    }

    public static void assertLoginSuccess(Response r) {
        check(r, 200, containsString("token :"));
    }

    public static void assertLogoutFail(Response r) {
        check(r, 401, equalTo("not found"));
    }

    public static void assertLogoutSuccess(Response r) {
        check(r, 200, equalTo("success logout"));
    }

    public static void assertTokenValid(String t) {
        assertThat(t, not(emptyString()));
        assertThat(t.length(), greaterThanOrEqualTo(MIN_TOKEN_LENGTH));
    }

    private static void check(Response r, int code, Matcher<String> matcher) {
        r.then().statusCode(code).body(matcher);
    }
}