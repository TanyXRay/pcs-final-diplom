package searching;

import java.util.List;

/**
 * Интерфейс, описывающий поисковый движок.
 */
public interface SearchEngine {

    /**
     * Метод, который возвращает список элементов результата ответа на запрос из слова
     * @param word
     * @return
     */
    List<PageEntry> search(String word);
}
