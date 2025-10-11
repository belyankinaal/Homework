package Config;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class Specification {

    public static RequestSpecification forRickAndMorty() {
        return new RequestSpecBuilder()
                .setBaseUri(ConfigReader.getProperty("web.url"))
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();
    }

    public static RequestSpecification forLocalApi() {
        return new RequestSpecBuilder()
                .setBaseUri(ConfigReader.getProperty("api.url"))
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();
    }
}