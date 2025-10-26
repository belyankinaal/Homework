package ifellow.belyankina.assertions;

import ifellow.belyankina.dto.Character;
import ifellow.belyankina.dto.Episode;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RickAndMortyAssertions {

    public static void assertCharacterData(Character character) {
        assertNotNull(character.getName(), "Имя не указано");
        assertNotNull(character.getSpecies(), "Раса не указана");
        assertNotNull(character.getLocationName(), "Местоположение не указано");
    }

    public static void assertEpisodeHasCharacters(Episode episode) {
        assertFalse(episode.getCharacterUrls().isEmpty(), "В эпизоде нет персонажей");
    }

    // Метод сравнения персонажа с эпизодом
    public static void assertFullComparison(Character character, Episode episode, Logger logger) {
        assertCharacterData(character);
        assertEpisodeHasCharacters(episode);

        logger.info("Сравнение персонажа с эпизодом:");
        logger.info("Персонаж: " + character.getName());
        boolean isInEpisode = episode.getCharacterUrls().stream()
                .anyMatch(url -> url.contains(character.getName()));
        logger.info("Персонаж присутствует в эпизоде: " + isInEpisode);

        if (!isInEpisode) {
            throw new AssertionError("Персонаж " + character.getName() + " отсутствует в эпизоде " + episode.getName());
        }
    }
}
