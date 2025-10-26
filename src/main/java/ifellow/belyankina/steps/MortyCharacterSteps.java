package ifellow.belyankina.steps;

import ifellow.belyankina.dto.Character;
import ifellow.belyankina.dto.Episode;
import ifellow.belyankina.service.RickAndMortyService;
import ifellow.belyankina.util.Specification;
import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Когда;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.RestAssured;

import java.util.logging.Logger;

public class MortyCharacterSteps {

    private static final Logger log = Logger.getLogger(MortyCharacterSteps.class.getName());
    private RickAndMortyService service;
    private Character morty;
    private Episode episode;

    @Дано("^установлена спецификация Rick and Morty API$")
    public void setupSpecification() {
        RestAssured.requestSpecification = Specification.forRickAndMorty();
        service = new RickAndMortyService();
        log.info("Спецификация Rick and Morty установлена");
    }

    @Когда("^выполняется поиск персонажа \"([^\"]*)\"$")
    public void findCharacterByName(String name) {
        morty = service.getFirstCharacterByName(name);
        attachJson("Morty JSON", morty.toString());
    }

    @И("^находится последний эпизод с этим персонажем$")
    public void findLastEpisodeOfCharacter() {
        episode = service.getLastEpisodeOfCharacter(morty);
        attachJson("Episode JSON", episode.toString());
    }

    @Step("{name}")
    private void attachJson(String name, String json) {
        Allure.addAttachment(name, "application/json", json, ".json");
    }
}
