package RickAndMortyTest;

import Config.Specification;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RickAndMortyTest {

    private static RickAndMortyService service;

    @BeforeAll
    public static void setUp() {
        RestAssured.requestSpecification = Specification.forRickAndMorty();
        service = new RickAndMortyService();
    }

    @Test
    @DisplayName("Сравнение Морти Смита с последним персонажем из его последнего эпизода")
    public void testMortyLastEpisodeCharacterComparison() {
        var morty = service.getFirstCharacterByName("Morty Smith");
        System.out.println("Морти Смит:");
        System.out.println("  Имя: " + morty.getName());
        System.out.println("  Раса: " + morty.getSpecies());
        System.out.println("  Местонахождение: " + morty.getLocationName());

        var lastEpisode = service.getLastEpisodeOfCharacter(morty);
        System.out.println("\nПоследний эпизод:");
        System.out.println("  Название: " + lastEpisode.getName());
        System.out.println("  Код: " + lastEpisode.getEpisodeCode());
        System.out.println("  Дата выхода: " + lastEpisode.getAirDate());

        var lastCharacter = service.getLastCharacterOfEpisode(lastEpisode);
        System.out.println("\nПоследний персонаж эпизода:");
        System.out.println("  Имя: " + lastCharacter.getName());
        System.out.println("  Раса: " + lastCharacter.getSpecies());
        System.out.println("  Местонахождение: " + lastCharacter.getLocationName());

        assertAll("Проверка данных персонажей и эпизода",
                () -> assertNotNull(morty.getSpecies(), "Раса Морти не указана"),
                () -> assertNotNull(morty.getLocationName(), "Местонахождение Морти не указано"),
                () -> assertFalse(lastEpisode.getCharacterUrls().isEmpty(), "В эпизоде нет персонажей"),
                () -> assertNotNull(lastCharacter.getName(), "Имя последнего персонажа не указано"),
                () -> assertNotNull(lastCharacter.getSpecies(), "Раса последнего персонажа не указана"),
                () -> assertNotNull(lastCharacter.getLocationName(), "Местонахождение последнего персонажа не указано")
        );

        boolean sameSpecies = morty.getSpecies().equals(lastCharacter.getSpecies());
        boolean sameLocation = morty.getLocationName().equals(lastCharacter.getLocationName());

        System.out.println("\nСравнение персонажей:");
        System.out.println("  Такая же раса? " + (sameSpecies ? "Да" : "Нет"));
        System.out.println("  Такое же местоположение? " + (sameLocation ? "Да" : "Нет"));
    }
}