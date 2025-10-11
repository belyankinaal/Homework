package RickAndMortyTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class Assertions {

    public static void assertCharacterData(Character character) {
        assertAll("Проверка данных персонажа",
                () -> assertNotNull(character.getName(), "Имя не указано"),
                () -> assertNotNull(character.getSpecies(), "Раса не указана"),
                () -> assertNotNull(character.getLocationName(), "Местоположение не указано")
        );
    }

    public static void assertEpisodeHasCharacters(Episode episode) {
        List<String> characters = episode.getCharacterUrls();
        assertFalse(characters.isEmpty(), "В эпизоде нет персонажей");
    }
}