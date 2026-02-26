import java.util.*;

public class PlagiarismDetector {

    private final Map<String, Set<String>> index = new HashMap<>();
    private final int N = 5;

    public void addDocument(String docId, String text) {
        String[] words = text.split("\\s+");
        for (int i = 0; i <= words.length - N; i++) {
            String gram = String.join(" ", Arrays.copyOfRange(words, i, i + N));
            index.computeIfAbsent(gram, k -> new HashSet<>()).add(docId);
        }
    }

    public double calculateSimilarity(String docId, String text) {
        String[] words = text.split("\\s+");
        int match = 0, total = 0;

        for (int i = 0; i <= words.length - N; i++) {
            total++;
            String gram = String.join(" ", Arrays.copyOfRange(words, i, i + N));
            if (index.containsKey(gram) && index.get(gram).contains(docId))
                match++;
        }
        return total == 0 ? 0 : (match * 100.0 / total);
    }
}