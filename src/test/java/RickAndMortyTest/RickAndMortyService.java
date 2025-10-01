package RickAndMortyTest;

import io.restassured.response.Response;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RickAndMortyService {
    private final RickAndMortyClient api = new RickAndMortyClient();

    private <T> T last(List<T> list) {
        assertFalse(list.isEmpty(), "Список пуст");
        return list.get(list.size() - 1);
    }

    public Character getFirstCharacterByName(String name) {
        Response response = api.getCharacterByName(name);
        List<?> results = response.jsonPath().getList("results");
        assertFalse(results.isEmpty(), "Персонаж не найден: " + name);
        return new Character(response.jsonPath().getMap("results[0]"));
    }

    public Episode getLastEpisodeOfCharacter(Character character) {
        List<String> episodeUrls = character.getEpisodeUrls();
        String lastEpisodeUrl = last(episodeUrls);
        return new Episode(api.getEpisodeByUrl(lastEpisodeUrl));
    }

    public Character getLastCharacterOfEpisode(Episode episode) {
        String lastCharacterUrl = last(episode.getCharacterUrls());
        Response response = api.getCharacterByUrl(lastCharacterUrl);
        assertNotNull(response.jsonPath().getString("name"), "Имя не найдено");
        return new Character(response);
    }
}