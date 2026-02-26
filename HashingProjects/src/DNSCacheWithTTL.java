import java.util.*;

public class DNSCacheWithTTL {

    class Entry {
        String ip;
        long expiry;

        Entry(String ip, long ttlSeconds) {
            this.ip = ip;
            this.expiry = System.currentTimeMillis() + ttlSeconds * 1000;
        }

        boolean isExpired() {
            return System.currentTimeMillis() > expiry;
        }
    }

    private final int capacity = 1000;
    private final LinkedHashMap<String, Entry> cache =
            new LinkedHashMap<>(capacity, 0.75f, true) {
                protected boolean removeEldestEntry(Map.Entry<String, Entry> eldest) {
                    return size() > capacity;
                }
            };

    private int hits = 0, misses = 0;

    public synchronized String resolve(String domain) {
        Entry e = cache.get(domain);
        if (e != null && !e.isExpired()) {
            hits++;
            return e.ip;
        }
        misses++;
        String newIp = queryUpstream(domain);
        cache.put(domain, new Entry(newIp, 300));
        return newIp;
    }

    private String queryUpstream(String domain) {
        return "192.168.1." + new Random().nextInt(255);
    }

    public String getStats() {
        int total = hits + misses;
        return "Hit Rate: " + (total == 0 ? 0 : (hits * 100.0 / total)) + "%";
    }
}