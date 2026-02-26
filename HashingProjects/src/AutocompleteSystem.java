import java.util.*;

public class AutocompleteSystem {

    private final Map<String, Integer> frequency = new HashMap<>();

    public void addQuery(String query) {
        frequency.merge(query, 1, Integer::sum);
    }

    public List<String> search(String prefix) {
        PriorityQueue<Map.Entry<String, Integer>> pq =
                new PriorityQueue<>((a, b) -> b.getValue() - a.getValue());

        for (var entry : frequency.entrySet()) {
            if (entry.getKey().startsWith(prefix))
                pq.add(entry);
        }

        List<String> result = new ArrayList<>();
        for (int i = 0; i < 10 && !pq.isEmpty(); i++)
            result.add(pq.poll().getKey());

        return result;
    }
}