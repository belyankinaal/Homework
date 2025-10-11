package ifellow.belyankina.dto;

import io.restassured.response.Response;
import lombok.Getter;

import java.util.List;

@Getter
public class Character {
    private final String name;
    private final String species;
    private final String locationName;
    private final List<String> episodeUrls;

    public Character(Response response) {
        this.name = response.jsonPath().getString("name");
        this.species = response.jsonPath().getString("species");
        this.locationName = response.jsonPath().getString("location.name");
        this.episodeUrls = response.jsonPath().getList("episode");
    }
}