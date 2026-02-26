import java.util.*;

public class ParkingLotManagement {

    private static final int SIZE = 500;
    private final String[] spots = new String[SIZE];

    private int hash(String plate) {
        return Math.abs(plate.hashCode()) % SIZE;
    }

    public int parkVehicle(String plate) {
        int index = hash(plate);
        int start = index;

        while (spots[index] != null) {
            index = (index + 1) % SIZE;
            if (index == start) return -1;
        }
        spots[index] = plate;
        return index;
    }

    public void exitVehicle(String plate) {
        int index = hash(plate);
        while (spots[index] != null) {
            if (spots[index].equals(plate)) {
                spots[index] = null;
                return;
            }
            index = (index + 1) % SIZE;
        }
    }
}