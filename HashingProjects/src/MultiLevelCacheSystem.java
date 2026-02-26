import java.util.*;

public class MultiLevelCacheSystem {

    private final int L1_CAP = 10000;
    private final LinkedHashMap<String, String> L1 =
            new LinkedHashMap<>(L1_CAP, 0.75f, true) {
                protected boolean removeEldestEntry(Map.Entry<String, String> e) {
                    return size() > L1_CAP;
                }
            };

    private final Map<String, String> L2 = new HashMap<>();
    private final Map<String, String> L3 = new HashMap<>();

    public String getVideo(String id) {
        if (L1.containsKey(id))
            return L1.get(id);

        if (L2.containsKey(id)) {
            String data = L2.get(id);
            L1.put(id, data);
            return data;
        }

        String data = L3.getOrDefault(id, "FromDB");
        L2.put(id, data);
        return data;
    }

    public void addToDatabase(String id, String data) {
        L3.put(id, data);
    }
}