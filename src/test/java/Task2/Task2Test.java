package Task2;

import Config.Specification;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;

public class Task2Test {

    private static UserService service;

    @BeforeAll
    public static void setUp() {
        RestAssured.requestSpecification = Specification.forReqres();
        service = new UserService();
    }

    @Test
    @DisplayName("Создание пользователя с JSON из строки")
    public void testCreateUserDirectJson() {
        Response response = service.createUser(TestData.CREATE_USER_JSON);

        response.then()
                .statusCode(201)
                .body("name", equalTo("Tomato"))
                .body("job", equalTo("Eat maket"))
                .body("id", notNullValue());

        assertAll("Проверка полей ответа",
                () -> assertEquals("Tomato", response.jsonPath().getString("name")),
                () -> assertEquals("Eat maket", response.jsonPath().getString("job")),
                () -> assertNotNull(response.jsonPath().getString("id"))
        );
    }

    @Test
    @DisplayName("Получение пользователя по ID")
    public void testGetUser() {
        Response response = service.getUser(2);

        response.then()
                .statusCode(200)
                .body("data.id", equalTo(2))
                .body("data.email", notNullValue())
                .body("data.first_name", notNullValue())
                .body("data.last_name", notNullValue());


    }
}
