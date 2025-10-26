package ifellow.belyankina.steps;

import ifellow.belyankina.dto.Character;
import ifellow.belyankina.service.RickAndMortyService;
import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.Тогда;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;

import java.util.logging.Logger;

public class CharacterComparisonSteps {

    private static final Logger log = Logger.getLogger(CharacterComparisonSteps.class.getName());
    private final RickAndMortyService service = new RickAndMortyService();
    private Character morty;
    private Character lastCharacter;

    @Дано("^получен персонаж \"([^\"]*)\"$")
    public void getMortyCharacter(String name) {
        this.morty = service.getFirstCharacterByName(name);
        log.info("Получен персонаж: " + morty.getName());
    }

    @Дано("^получен последний персонаж эпизода$")
    public void getLastCharacter() {
        this.lastCharacter = service.getLastCharacterOfEpisode(service.getLastEpisodeOfCharacter(morty));
        log.info("Получен последний персонаж эпизода: " + lastCharacter.getName());
    }

    @Тогда("^сравниваем расу и местоположение персонажей$")
    public void compareRaceAndLocation() {
        boolean sameSpecies = morty.getSpecies().equals(lastCharacter.getSpecies());
        boolean sameLocation = morty.getLocationName().equals(lastCharacter.getLocationName());

        log.info("Сравнение с Морти:");
        log.info("Раса одинаковая: " + sameSpecies);
        log.info("Местоположение одинаковое: " + sameLocation);

        attachJson("Morty", morty.toString());
        attachJson("LastCharacter", lastCharacter.toString());
    }

    @Step("{name}")
    private void attachJson(String name, String json) {
        Allure.addAttachment(name, "application/json", json, ".json");
    }
}
