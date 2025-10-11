package localhost;

import ifellow.belyankina.util.Specification;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public class BaseTest {

    @BeforeAll
    public static void setUp() {
        RestAssured.requestSpecification = Specification.forLocalApi();
    }
}