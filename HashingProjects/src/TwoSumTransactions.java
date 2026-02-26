import java.util.*;

public class TwoSumTransactions {

    static class Transaction {
        int id, amount;
        String merchant;
        long time;

        Transaction(int id, int amount, String merchant, long time) {
            this.id = id;
            this.amount = amount;
            this.merchant = merchant;
            this.time = time;
        }
    }

    public List<int[]> findTwoSum(List<Transaction> list, int target) {
        Map<Integer, Transaction> map = new HashMap<>();
        List<int[]> result = new ArrayList<>();

        for (Transaction t : list) {
            int complement = target - t.amount;
            if (map.containsKey(complement)) {
                result.add(new int[]{map.get(complement).id, t.id});
            }
            map.put(t.amount, t);
        }
        return result;
    }
}