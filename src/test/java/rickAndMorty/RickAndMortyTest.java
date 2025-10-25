package rickAndMorty;

import ifellow.belyankina.assertions.RickAndMortyAssertions;
import ifellow.belyankina.service.RickAndMortyService;
import ifellow.belyankina.util.Specification;
import io.qameta.allure.*;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.logging.Logger;

@Epic("Rick and Morty АПИ Тест")
@Feature("Сравнение персонажей Тест Rick and Morty")
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
    @Tag("Test_1")
    @Story("Сравнение персонажей по расе и местонахождению")
    @Description("Поиск Морти Смита,последнего эпизода с Морти,нахождение местоположения и расы, сравнение")
    public void testComparison() {
        log.info("Начало теста");

        var morty = service.getFirstCharacterByName("Morty Smith");
        attachJson("Morty JSON", morty.toString());

        var episode = service.getLastEpisodeOfCharacter(morty);
        attachJson("Episode JSON", episode.toString());

        var character = service.getLastCharacterOfEpisode(episode);
        attachJson("Last Character JSON", character.toString());

        RickAndMortyAssertions.assertFullComparison(morty, character, episode, log);

        log.info("Тест завершен");
    }

    @Step("{name}")
    private void attachJson(String name, String json) {
        io.qameta.allure.Allure.addAttachment(name, "application/json", json, ".json");
    }
}
