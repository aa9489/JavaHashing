import java.util.concurrent.*;

public class DistributedRateLimiter {

    class TokenBucket {
        int tokens;
        long lastRefill;
        final int maxTokens = 1000;

        TokenBucket() {
            tokens = maxTokens;
            lastRefill = System.currentTimeMillis();
        }

        synchronized boolean allow() {
            long now = System.currentTimeMillis();
            if (now - lastRefill >= 3600000) {
                tokens = maxTokens;
                lastRefill = now;
            }
            if (tokens > 0) {
                tokens--;
                return true;
            }
            return false;
        }
    }

    private final ConcurrentHashMap<String, TokenBucket> clients = new ConcurrentHashMap<>();

    public boolean checkRateLimit(String clientId) {
        clients.putIfAbsent(clientId, new TokenBucket());
        return clients.get(clientId).allow();
    }
}