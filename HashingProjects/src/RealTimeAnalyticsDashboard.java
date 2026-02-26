import java.util.*;
import java.util.concurrent.*;

public class RealTimeAnalyticsDashboard {

    private final ConcurrentHashMap<String, Integer> pageViews = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Set<String>> uniqueVisitors = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Integer> trafficSources = new ConcurrentHashMap<>();

    public void processEvent(String url, String userId, String source) {
        pageViews.merge(url, 1, Integer::sum);
        uniqueVisitors.computeIfAbsent(url, k -> ConcurrentHashMap.newKeySet()).add(userId);
        trafficSources.merge(source, 1, Integer::sum);
    }

    public List<String> getTopPages() {
        return pageViews.entrySet()
                .stream()
                .sorted((a, b) -> b.getValue() - a.getValue())
                .limit(10)
                .map(Map.Entry::getKey)
                .toList();
    }
}