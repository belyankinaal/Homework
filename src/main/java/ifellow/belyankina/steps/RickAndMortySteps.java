package ifellow.belyankina.steps;

import ifellow.belyankina.assertions.RickAndMortyAssertions;
import ifellow.belyankina.dto.Character;
import ifellow.belyankina.dto.Episode;
import ifellow.belyankina.service.RickAndMortyService;
import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Тогда;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;

import java.util.logging.Logger;

public class RickAndMortySteps {

    private static final Logger log = Logger.getLogger(RickAndMortySteps.class.getName());
    private RickAndMortyService service = new RickAndMortyService();
    private Character character;
    private Episode episode;
    private Character morty;

    @И("^находится последний персонаж из этого эпизода$")
    public void findLastCharacterOfEpisode() {
        character = service.getLastCharacterOfEpisode(episode);
        attachJson("Last Character JSON", character.toString());
    }

    @Тогда("^выполняется сравнение найденных данных персонажей и эпизода$")
    public void compareData() {
        RickAndMortyAssertions.assertFullComparison(morty, character, episode, log);
        log.info("Сравнение завершено успешно");
    }

    @Step("{name}")
    private void attachJson(String name, String json) {
        Allure.addAttachment(name, "application/json", json, ".json");
    }

    public void setMorty(Character morty) {
        this.morty = morty;
    }

    public void setEpisode(Episode episode) {
        this.episode = episode;
    }
}
