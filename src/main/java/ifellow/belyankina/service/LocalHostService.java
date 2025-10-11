package ifellow.belyankina.service;

import ifellow.belyankina.steps.ApiMethodSteps;
import ifellow.belyankina.util.ConfigReader;
import io.restassured.response.Response;

public class LocalHostService {
    private final ApiMethodSteps stepsApi = new ApiMethodSteps();

    private final String baseUrl = ConfigReader.getProperty("api.url");
    private final String registerUrl = baseUrl + ConfigReader.getProperty("api.register.path");
    private final String loginUrl = baseUrl + ConfigReader.getProperty("api.login.path");
    private final String logoutUrl = baseUrl + ConfigReader.getProperty("api.logout.path");

    public Response register(String jsonBody) {
        return stepsApi.sendRequest("POST", registerUrl, jsonBody, null);
    }

    public Response login(String jsonBody) {
        return stepsApi.sendRequest("POST", loginUrl, jsonBody, null);
    }

    public Response logout(String token) {
        return stepsApi.sendRequest("GET", logoutUrl, null, token);
    }
}