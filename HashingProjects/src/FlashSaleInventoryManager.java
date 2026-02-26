import java.util.*;
import java.util.concurrent.*;

public class FlashSaleInventoryManager {

    private final ConcurrentHashMap<String, Integer> stock = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Queue<Integer>> waitingList = new ConcurrentHashMap<>();

    public void addProduct(String productId, int quantity) {
        stock.put(productId, quantity);
        waitingList.put(productId, new ConcurrentLinkedQueue<>());
    }

    public int checkStock(String productId) {
        return stock.getOrDefault(productId, 0);
    }

    public synchronized String purchaseItem(String productId, int userId) {
        int current = stock.getOrDefault(productId, 0);
        if (current > 0) {
            stock.put(productId, current - 1);
            return "Success, remaining: " + (current - 1);
        } else {
            waitingList.get(productId).add(userId);
            return "Added to waiting list, position: " + waitingList.get(productId).size();
        }
    }
}