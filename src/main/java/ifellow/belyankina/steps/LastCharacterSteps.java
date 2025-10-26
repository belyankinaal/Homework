package ifellow.belyankina.steps;

import ifellow.belyankina.dto.Character;
import ifellow.belyankina.dto.Episode;
import ifellow.belyankina.service.RickAndMortyService;
import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.И;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;

import java.util.logging.Logger;

public class LastCharacterSteps {

    private static final Logger log = Logger.getLogger(LastCharacterSteps.class.getName());
    private RickAndMortyService service = new RickAndMortyService();
    private Character lastCharacter;
    private Episode episode;

    @Дано("^установлен эпизод для анализа$")
    public void setEpisodeForAnalysis() {
        log.info("Эпизод для анализа установлен: " + (episode != null ? episode.getName() : "не задан"));
    }

    @И("^находится последний персонаж из этого эпизода$")
    public void findLastCharacterOfEpisode() {
        if (episode == null) {
            throw new IllegalStateException("Эпизод для анализа не установлен");
        }
        lastCharacter = service.getLastCharacterOfEpisode(episode);
        attachJson("Last Character JSON", lastCharacter.toString());
    }

    @Step("{name}")
    private void attachJson(String name, String json) {
        Allure.addAttachment(name, "application/json", json, ".json");
    }

    public Character getLastCharacter() {
        return lastCharacter;
    }

    public void setEpisode(Episode episode) {
        this.episode = episode;
    }
}
