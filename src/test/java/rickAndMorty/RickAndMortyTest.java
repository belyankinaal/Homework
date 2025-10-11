package rickAndMorty;

import ifellow.belyankina.assertions.RickAndMortyAssertions;
import ifellow.belyankina.service.RickAndMortyService;
import ifellow.belyankina.util.Specification;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.logging.Logger;

public class RickAndMortyTest {

    private static final Logger log = Logger.getLogger(RickAndMortyTest.class.getName());
    private static RickAndMortyService service;

    @BeforeAll
    public static void setUp() {
        RestAssured.requestSpecification = Specification.forRickAndMorty();
        service = new RickAndMortyService();
    }

    @Test
    @DisplayName("Сравнение персонажей")
    public void testComparison() {
        log.info("Начало теста");

        var morty = service.getFirstCharacterByName("Morty Smith");
        var episode = service.getLastEpisodeOfCharacter(morty);
        var character = service.getLastCharacterOfEpisode(episode);

        RickAndMortyAssertions.assertFullComparison(morty, character, episode, log);

        log.info("Тест завершен");
    }
}