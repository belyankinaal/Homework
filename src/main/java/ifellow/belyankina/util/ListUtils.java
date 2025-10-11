package ifellow.belyankina.util;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class ListUtils {

    public static <T> T getLastElement(List<T> list) {
        assertFalse(list.isEmpty(), "Список пуст");
        return list.get(list.size() - 1);
    }
}