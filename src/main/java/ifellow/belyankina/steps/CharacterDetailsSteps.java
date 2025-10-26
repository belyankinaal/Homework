package ifellow.belyankina.steps;

import ifellow.belyankina.dto.Character;
import ifellow.belyankina.service.RickAndMortyService;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;

import java.util.logging.Logger;

public class CharacterDetailsSteps {

    private static final Logger log = Logger.getLogger(CharacterDetailsSteps.class.getName());
    private final RickAndMortyService service = new RickAndMortyService();
    private Character character;

    @Когда("^получаем данные персонажа по имени \"([^\"]*)\"$")
    public void fetchCharacterByName(String name) {
        character = service.getFirstCharacterByName(name);
        log.info("Данные персонажа получены: " + character);
        attachJson("Character_" + name, character.toString());
    }

    @Тогда("^проверяем местонахождение персонажа$")
    public void checkLocation() {
        String location = character.getLocationName();
        log.info("Местонахождение персонажа: " + location);
        attachJson("Location", location);
    }

    @Тогда("^проверяем расу$")
    public void checkSpecies() {
        String species = character.getSpecies();
        log.info("Раса персонажа: " + species);
        attachJson("Species", species);
    }

    @Step("{name}")
    private void attachJson(String name, String json) {
        Allure.addAttachment(name, "application/json", json, ".json");
    }
}
