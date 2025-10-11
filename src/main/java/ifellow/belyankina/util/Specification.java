package ifellow.belyankina.util;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class Specification {

    public static RequestSpecification forRickAndMorty() {
        return createSpecification(ConfigReader.getProperty("web.url"));
    }

    public static RequestSpecification forLocalApi() {
        return createSpecification(ConfigReader.getProperty("api.url"));
    }

    private static RequestSpecification createSpecification(String baseUrl) {
        return new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .setContentType(ContentType.JSON)
                .build();
    }
}