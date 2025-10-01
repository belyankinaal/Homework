package RickAndMortyTest;

import Config.ConfigReader;
import Config.Specification;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RickAndMortyTest {

    @BeforeAll
    public static void setUp() {
        RestAssured.requestSpecification = Specification.forRickAndMorty();
    }

    @Test
    @DisplayName("Информация по Морти Смит")
    public void getMortySmith() {

        // 1. Получаем персонажа Morty Smith через поиск по имени
        Response characterResponse = RestAssured
                .given()
                .queryParam("name", "Morty Smith")
                .when()
                .get("/character")
                .then()
                .statusCode(200)
                .extract()
                .response();

        // Проверяем, что персонаж найден
        List<String> characterNames = characterResponse.jsonPath().getList("results.name");
        assertFalse(characterNames.isEmpty(), "Не найден персонаж Morty Smith");

        // --- Получаем species и location Морти Смит ---
        String mortySpecies = characterResponse.jsonPath().getString("results[0].species");
        String mortyLocation = characterResponse.jsonPath().getString("results[0].location.name");

        System.out.println("Морти Смит:");
        System.out.println("Местонахождение: " + mortyLocation);
        System.out.println("Раса: " + mortySpecies);

        // Получаем список эпизодов первого найденного персонажа (Morty Smith)
        List<String> episodeUrls = characterResponse.jsonPath().getList("results[0].episode");
        assertFalse(episodeUrls.isEmpty(), "У Морти нет эпизодов");

        // 2. Берём последний эпизод из списка (URL)
        String lastEpisodeUrl = episodeUrls.get(episodeUrls.size() - 1);
        String episodePath = lastEpisodeUrl.replace(ConfigReader.getProperty("web.url"), "");

        // 3. Получаем данные по последнему эпизоду
        Response episodeResponse = RestAssured
                .given()
                .when()
                .get(episodePath)
                .then()
                .statusCode(200)
                .extract()
                .response();

        // Инфо по эпизоду
        String episodeName = episodeResponse.jsonPath().getString("name");
        String airDate = episodeResponse.jsonPath().getString("air_date");
        String episodeCode = episodeResponse.jsonPath().getString("episode");
        System.out.println("Последний эпизод с Морти Смит:");
        System.out.println("Название: " + episodeName);
        System.out.println("Дата выхода: " + airDate);
        System.out.println("Код эпизода: " + episodeCode);

        // 4. Получаем список персонажей из этого эпизода
        List<String> characterUrls = episodeResponse.jsonPath().getList("characters");
        assertFalse(characterUrls.isEmpty(), "В эпизоде нет персонажей");

        // 5. Берём последнего персонажа из списка
        String lastCharacterUrl = characterUrls.get(characterUrls.size() - 1);
        String lastCharacterPath = lastCharacterUrl.replace(ConfigReader.getProperty("web.url"), "");

        // 6. Получаем данные по последнему персонажу
        Response lastCharacterResponse = RestAssured
                .given()
                .when()
                .get(lastCharacterPath)
                .then()
                .statusCode(200)
                .extract()
                .response();

        String lastCharacterName = lastCharacterResponse.jsonPath().getString("name");
        assertNotNull(lastCharacterName, "Имя последнего персонажа не найдено");

        System.out.println("Последний персонаж последнего эпизода с Морти: " + lastCharacterName);

        // --- Задание 3: Получаем данные по местонахождению и расе персонажа ---
        String species = lastCharacterResponse.jsonPath().getString("species");
        String locationName = lastCharacterResponse.jsonPath().getString("location.name");

        System.out.println("Данные персонажа:");
        System.out.println("Местонахождение: " + locationName);
        System.out.println("Раса: " + species);

        // --- Задание 4: Сравнение с Морти ---
        boolean sameSpecies = species.equals(mortySpecies);
        boolean sameLocation = locationName.equals(mortyLocation);

        System.out.println("\nСравнение с Морти Смит:");
        System.out.println("Та же раса? " + (sameSpecies ? "Да" : "Нет"));
        System.out.println("То же местонахождение? " + (sameLocation ? "Да" : "Нет"));
    }
}