import java.util.*;
import java.util.concurrent.*;

public class UsernameAvailabilityChecker {

    private final ConcurrentHashMap<String, Integer> users = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Integer> attempts = new ConcurrentHashMap<>();

    public boolean checkAvailability(String username) {
        attempts.merge(username, 1, Integer::sum);
        return !users.containsKey(username);
    }

    public void registerUser(String username, int userId) {
        users.put(username, userId);
    }

    public List<String> suggestAlternatives(String username) {
        List<String> suggestions = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            String suggestion = username + i;
            if (!users.containsKey(suggestion))
                suggestions.add(suggestion);
        }
        String modified = username.replace("_", ".");
        if (!users.containsKey(modified))
            suggestions.add(modified);
        return suggestions;
    }

    public String getMostAttempted() {
        return attempts.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }
}