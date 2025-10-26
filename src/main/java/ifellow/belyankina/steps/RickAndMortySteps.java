package ifellow.belyankina.steps;

import ifellow.belyankina.assertions.RickAndMortyAssertions;
import ifellow.belyankina.dto.Character;
import ifellow.belyankina.dto.Episode;
import ifellow.belyankina.service.RickAndMortyService;
import ifellow.belyankina.util.Specification;
import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import io.qameta.allure.Step;
import io.restassured.RestAssured;

import java.util.logging.Logger;

public class RickAndMortySteps {

    private static final Logger log = Logger.getLogger(RickAndMortySteps.class.getName());
    private RickAndMortyService service;
    private Character morty;
    private Episode episode;
    private Character character;

    @Дано("^установлена спецификация Rick and Morty API$")
    public void setupSpecification() {
        RestAssured.requestSpecification = Specification.forRickAndMorty();
        service = new RickAndMortyService();
        log.info("Спецификация Rick and Morty установлена");
    }

    @Когда("^выполняется поиск персонажа \"([^\"]*)\"$")
    public void findCharacterByName(String name) {
        searchCharacter(name);
    }

    @И("^находится последний эпизод с этим персонажем$")
    public void findLastEpisodeOfCharacter() {
        findLastEpisode();
    }

    @И("^находится последний персонаж из этого эпизода$")
    public void findLastCharacterOfEpisode() {
        findLastCharacter();
    }

    @Тогда("^выполняется сравнение найденных данных персонажей и эпизода$")
    public void compareData() {
        performComparison();
    }

    // --------- Методы с аннотацией @Step для Allure ---------

    @Step("Поиск персонажа по имени: {name}")
    private void searchCharacter(String name) {
        morty = service.getFirstCharacterByName(name);
        attachJson("Morty JSON", morty.toString());
    }

    @Step("Поиск последнего эпизода с персонажем")
    private void findLastEpisode() {
        episode = service.getLastEpisodeOfCharacter(morty);
        attachJson("Episode JSON", episode.toString());
    }

    @Step("Поиск последнего персонажа из эпизода")
    private void findLastCharacter() {
        character = service.getLastCharacterOfEpisode(episode);
        attachJson("Last Character JSON", character.toString());
    }

    @Step("Сравнение найденных данных персонажей и эпизода")
    private void performComparison() {
        RickAndMortyAssertions.assertFullComparison(morty, character, episode, log);
        log.info("Сравнение завершено успешно");
    }

    @Step("{name}")
    private void attachJson(String name, String json) {
        io.qameta.allure.Allure.addAttachment(name, "application/json", json, ".json");
    }
}
