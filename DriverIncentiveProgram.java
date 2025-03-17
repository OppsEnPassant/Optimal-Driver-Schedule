// MOTHIBA
// KOKETSO
// 4370898
// CSC212 2024 Practical 2 Term 4




import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DriverIncentiveProgram {

    // Inner class to represent each trip with its ID, start time, end time, and passenger count
    static class CarTrip {
        String id;
        int startTime;
        int endTime;
        int passengers;

        CarTrip(String id, int startTime, int endTime, int passengers) {
            this.id = id;
            this.startTime = startTime;
            this.endTime = endTime;
            this.passengers = passengers;
        }
    }

    public static void main(String[] args) {
        // Defines trips
        List<CarTrip> trips = initializeTrips();

        // Select optimal non-overlapping trips
        List<CarTrip> selectedTrips = getOptimalTrips(trips);

        // Calculate passengers and commission
        int totalPassengers = calculateTotalPassengers(selectedTrips);
        int totalCommission = calculateCommission(totalPassengers);
        List<String> carIds = extractCarIds(selectedTrips);

        // Writes results to file
        writeToFile(totalPassengers, totalCommission, carIds);
    }

    // Initializes the list of trips
    private static List<CarTrip> initializeTrips() {
        return List.of(
            new CarTrip("A", 3, 16, 21),
            new CarTrip("B", 1, 8, 10),
            new CarTrip("C", 9, 21, 20),
            new CarTrip("D", 22, 24, 2),
            new CarTrip("E", 2, 4, 5),
            new CarTrip("F", 7, 12, 7),
            new CarTrip("G", 15, 19, 10),
            new CarTrip("H", 17, 24, 10),
            new CarTrip("I", 5, 10, 7),
            new CarTrip("J", 11, 14, 6),
            new CarTrip("K", 0, 6, 8),
            new CarTrip("L", 20, 23, 3),
            new CarTrip("M", 13, 18, 12)
        );
    }

    // Gets the optimal set of non-overlapping trips
    private static List<CarTrip> getOptimalTrips(List<CarTrip> trips) {
        return trips.stream()
                .sorted((a, b) -> Integer.compare(a.endTime, b.endTime))  // Sort by end time
                .reduce(new ArrayList<>(), (selected, current) -> {
                    if (selected.isEmpty() || current.startTime >= selected.get(selected.size() - 1).endTime) {
                        selected.add(current);
                    }
                    return selected;
                }, (l1, l2) -> l1);  // Accumulate non-overlapping trips
    }

    // Calculates the total number of passengers from the selected trips
    private static int calculateTotalPassengers(List<CarTrip> trips) {
        return trips.stream().mapToInt(trip -> trip.passengers).sum();
    }

    // Calculates total commission based on the number of passengers
    private static int calculateCommission(int totalPassengers) {
        return totalPassengers * 10;  // Each passenger earns R10 commission
    }

    // Extracts car IDs from the selected trips
    private static List<String> extractCarIds(List<CarTrip> trips) {
        return trips.stream().map(trip -> trip.id).collect(Collectors.toList());
    }

    // Writes results to a file
    private static void writeToFile(int totalPassengers, int totalCommission, List<String> carIds) {
        try (FileWriter writer = new FileWriter("output.txt")) {
            writer.write("The maximum number of passengers is " + totalPassengers + "\n");
            writer.write("The maximum commission possible is R" + totalCommission + "\n");
            writer.write("The cars driven to earn the maximum commission are " + String.join(", ", carIds) + ".\n");
            System.out.println("Output written to output.txt successfully.");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
}
