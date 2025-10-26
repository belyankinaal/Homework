package ifellow.belyankina.steps;

import ifellow.belyankina.dto.Character;
import ifellow.belyankina.dto.Episode;
import ifellow.belyankina.service.RickAndMortyService;
import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;

import java.util.logging.Logger;

public class LastCharacterSteps {

    private static final Logger log = Logger.getLogger(LastCharacterSteps.class.getName());
    private static final String ATTACHMENT_NAME = "LastCharacter";
    private final RickAndMortyService service = new RickAndMortyService();
    private Episode episode;
    private Character lastCharacter;

    @Дано("^установлен эпизод для анализа$")
    public void setEpisodeForAnalysis() {
        Character morty = service.getFirstCharacterByName("Morty");
        this.episode = service.getLastEpisodeOfCharacter(morty);
        log.info("Эпизод для анализа установлен: " + episode.getName());
    }

    @Когда("^находится последний персонаж из этого эпизода$")
    public void findLastCharacter() {
        this.lastCharacter = service.getLastCharacterOfEpisode(episode);
        log.info("Последний персонаж получен: " + lastCharacter.getName());
    }

    @Тогда("^выводим данные персонажа$")
    public void printCharacterData() {
        log.info("Данные персонажа:");
        log.info("Имя: " + lastCharacter.getName());
        log.info("Раса: " + lastCharacter.getSpecies());
        log.info("Местоположение: " + lastCharacter.getLocationName());

        attachJson(lastCharacter.toString());
    }

    @Step("Добавление JSON")
    private void attachJson(String json) {
        Allure.addAttachment(ATTACHMENT_NAME, "application/json", json, ".json");
    }
}
