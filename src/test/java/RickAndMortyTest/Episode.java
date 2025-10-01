package RickAndMortyTest;

import io.restassured.response.Response;
import lombok.Getter;

import java.util.List;

@Getter
public class Episode {
    private final String name;
    private final String airDate;
    private final String episodeCode;
    private final List<String> characterUrls;

    public Episode(Response response) {
        this.name = response.jsonPath().getString("name");
        this.airDate = response.jsonPath().getString("air_date");
        this.episodeCode = response.jsonPath().getString("episode");
        this.characterUrls = response.jsonPath().getList("characters");
    }
}