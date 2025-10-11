package RickAndMortyTest;

import Config.Specification;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RickAndMortyTest {

    private static final Logger log = LoggerFactory.getLogger(RickAndMortyTest.class);
    private static RickAndMortyService service;

    @BeforeAll
    public static void setUp() {
        RestAssured.requestSpecification = Specification.forRickAndMorty();
        service = new RickAndMortyService();
    }

    @Test
    @DisplayName("Сравнение Морти Смита с последним персонажем из его последнего эпизода")
    public void testMortyLastEpisodeCharacterComparison() {
        var morty = service.getFirstCharacterByName("Morty Smith");
        var lastEpisode = service.getLastEpisodeOfCharacter(morty);
        var lastCharacter = service.getLastCharacterOfEpisode(lastEpisode);

        log.info("Морти Смит: имя='{}', раса='{}', локация='{}'",
                morty.getName(), morty.getSpecies(), morty.getLocationName());
        log.info("Последний эпизод: название='{}', код='{}', дата выхода='{}'",
                lastEpisode.getName(), lastEpisode.getEpisodeCode(), lastEpisode.getAirDate());
        log.info("Последний персонаж эпизода: имя='{}', раса='{}', локация='{}'",
                lastCharacter.getName(), lastCharacter.getSpecies(), lastCharacter.getLocationName());

        Assertions.assertCharacterData(morty);
        Assertions.assertCharacterData(lastCharacter);
        Assertions.assertEpisodeHasCharacters(lastEpisode);

        boolean sameSpecies = morty.getSpecies().equals(lastCharacter.getSpecies());
        boolean sameLocation = morty.getLocationName().equals(lastCharacter.getLocationName());

        log.info("Сравнение персонажей: Такая же раса? {}, Такое же местоположение? {}",
                sameSpecies ? "Да" : "Нет",
                sameLocation ? "Да" : "Нет");
    }
}
