package searching;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Класс реализации поискового движка.
 */
public class BooleanSearchEngine implements SearchEngine {
    private final Map<String, List<PageEntry>> indexingMap = new HashMap<>();

    public BooleanSearchEngine(File pdfsDir) throws IOException {
        indexingPdfs(pdfsDir);
    }

    @Override
    public List<PageEntry> search(String word) {
        String lowerCaseWord = word.toLowerCase();
        if (indexingMap.containsKey(lowerCaseWord)) {
            return indexingMap.get(lowerCaseWord).stream()
                    .sorted(PageEntry::compareTo)
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

    /**
     * Метод индексации pdf файлов, в котором происходит чтение страниц каждого файла и выявление частоты встречи слова
     *
     * @param pdfsDir
     * @throws IOException
     */
    private void indexingPdfs(File pdfsDir) throws IOException {
        if (pdfsDir.isDirectory()) {
            for (File file : pdfsDir.listFiles()) {
                var doc = new PdfDocument(new PdfReader(file));
                String namePdf = file.getName();
                readingPagesPdfs(doc, namePdf);
            }
        } else {
            System.out.println("directory not found");
        }
    }

    /**
     * Метод чтения каждой страницы pdf файла
     * @param doc
     * @param namePdf
     */
    private void readingPagesPdfs(PdfDocument doc, String namePdf) {
        for (int pageNum = 1; pageNum <= doc.getNumberOfPages(); pageNum++) {
            PdfPage page = doc.getPage(pageNum);
            String text = PdfTextExtractor.getTextFromPage(page);
            String[] words = text.split("\\P{IsAlphabetic}+");
            frequencyWordCount(words, namePdf, pageNum);
        }
    }

    /**
     * Метод выявления частоты встречи слова
     * @param words
     * @param namePdf
     * @param pageNum
     */
    private void frequencyWordCount(String[] words, String namePdf, int pageNum) {
        Map<String, Integer> frequenceMap = new HashMap<>();
        for (String word : words) {
            word = word.toLowerCase();
            frequenceMap.put(word, frequenceMap.getOrDefault(word, 0) + 1);
            Integer frequenceCount = frequenceMap.get(word);
            List<PageEntry> wordPageEntries = indexingMap.getOrDefault(word, new ArrayList<>());
            indexingMap.put(word, wordPageEntries);

            Iterator<PageEntry> iterator = wordPageEntries.iterator();
            while (iterator.hasNext()) {
                PageEntry nextPageEntry = iterator.next();
                if (nextPageEntry.getPage() == pageNum && nextPageEntry.getPdfName().equals(namePdf)) {
                    iterator.remove();
                    break;
                }
            }
            PageEntry pageEntry = new PageEntry(namePdf, pageNum, frequenceCount);
            wordPageEntries.add(pageEntry);
        }
    }
}
