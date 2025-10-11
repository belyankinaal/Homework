package ifellow.belyankina.service;

import ifellow.belyankina.dto.Character;
import ifellow.belyankina.dto.Episode;
import ifellow.belyankina.steps.RickAndMortyApiSteps;
import ifellow.belyankina.util.ListUtils;
import io.restassured.response.Response;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class RickAndMortyService {
    private final RickAndMortyApiSteps api = new RickAndMortyApiSteps();

    public Character getFirstCharacterByName(String name) {
        Response r = api.getCharacterByName(name);
        assertFalse(r.jsonPath().getList("results").isEmpty(), "Персонаж не найден: " + name);
        return character(r.jsonPath().getString("results[0].url"));
    }

    public Episode getLastEpisodeOfCharacter(Character c) {
        return episode(ListUtils.getLastElement(c.getEpisodeUrls()));
    }

    public Character getLastCharacterOfEpisode(Episode e) {
        return character(ListUtils.getLastElement(e.getCharacterUrls()));
    }

    private Character character(String url) {
        Response r = api.getCharacterByUrl(url);
        return new Character(r);
    }

    private Episode episode(String url) {
        return new Episode(api.getEpisodeByUrl(url));
    }
}