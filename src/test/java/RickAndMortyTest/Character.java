package RickAndMortyTest;

import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class Character {
    private final String name;
    private final String species;
    private final String locationName;

    private final Map<String, Object> rawData;   // Сохраняем все данные

    public Character(Map<String, Object> jsonMap) {
        this.rawData = jsonMap;
        this.name = (String) jsonMap.get("name");
        this.species = (String) jsonMap.get("species");
        Map<String, String> location = (Map<String, String>) jsonMap.get("location");
        this.locationName = location.get("name");
    }

    public Character(io.restassured.response.Response response) {
        this.rawData = response.jsonPath().getMap("");
        this.name = response.jsonPath().getString("name");
        this.species = response.jsonPath().getString("species");
        this.locationName = response.jsonPath().getString("location.name");
    }

    public List<String> getEpisodeUrls() {
        return (List<String>) rawData.get("episode");
    }
}