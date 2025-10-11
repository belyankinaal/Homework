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

    public static void assertCharactersComparison(Character char1, Character char2, Logger logger) {
        logger.info("Сравнение персонажей:");
        logger.info(char1.getName() + " vs " + char2.getName());
        logger.info("Одинаковая раса: " + char1.getSpecies().equals(char2.getSpecies()));
        logger.info("Одинаковая локация: " + char1.getLocationName().equals(char2.getLocationName()));
    }

    public static void assertFullComparison(Character char1, Character char2, Episode episode, Logger logger) {
        assertCharacterData(char1);
        assertCharacterData(char2);
        assertEpisodeHasCharacters(episode);
        assertCharactersComparison(char1, char2, logger);
    }
}
