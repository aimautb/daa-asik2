package cli;

import algorithms.MaxHeap;
import algorithms.PerformanceTracker;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class BenchmarkRunner {

    public static void main(String[] args) {
        try {
            String algo = null;
            String input = null;
            int n = -1;

            for (int i = 0; i < args.length; i += 2) {
                if (i + 1 >= args.length) {
                    throw new IllegalArgumentException("Missing value for argument " + args[i]);
                }
                String key = args[i];
                if (!key.startsWith("--")) {
                    throw new IllegalArgumentException("Invalid argument format: " + key);
                }
                String value = args[i + 1];
                switch (key.substring(2)) {
                    case "algo":
                        algo = value;
                        break;
                    case "size":
                        try {
                            n = Integer.parseInt(value);
                        } catch (NumberFormatException e) {
                            throw new IllegalArgumentException("Invalid size: " + value);
                        }
                        break;
                    case "input":
                        input = value;
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown argument: " + key);
                }
            }

            if (algo == null || input == null || n == -1) {
                throw new IllegalArgumentException("Missing required arguments. Usage: --algo maxheap --size <int> --input <type>");
            }

            if (n < 1) {
                throw new IllegalArgumentException("Size must be at least 1");
            }

            switch (input) {
                case "random":
                case "sorted":
                case "reversed":
                case "nearly-sorted":
                    break;
                default:
                    throw new IllegalArgumentException("Invalid input type: " + input);
            }

            switch (algo) {
                case "maxheap":
                    runMaxHeapBenchmark(n, input);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown algorithm: " + algo);
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    private static void runMaxHeapBenchmark(int n, String inputType) throws IOException {
        Random random = new Random();
        int[] array = generateArray(n, inputType, random);

        PerformanceTracker.reset();
        long startTime = System.nanoTime();

        MaxHeap heap = new MaxHeap(n);
        heap.buildHeap(array);
        while (!heap.isEmpty()) {
            heap.extractMax();
        }

        long endTime = System.nanoTime();
        long timeNs = endTime - startTime;
        long comparisons = PerformanceTracker.getComparisons();
        long swaps = PerformanceTracker.getSwaps();

        // Console output
        System.out.println("Algorithm: MaxHeap");
        System.out.println("Input: " + inputType + ", n=" + n);
        System.out.println("Time (ns): " + timeNs);
        System.out.println("Comparisons: " + comparisons);
        System.out.println("Swaps: " + swaps);

        // Write to CSV
        String filePath = "docs/performance-plots/results.csv";
        File file = new File(filePath);
        file.getParentFile().mkdirs();
        boolean isNewFile = !file.exists() || file.length() == 0;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            if (isNewFile) {
                writer.write("algorithm,input_type,n,time_ns,comparisons,swaps\n");
            }
            writer.write("MaxHeap," + inputType + "," + n + "," + timeNs + "," + comparisons + "," + swaps + "\n");
        }
    }

    private static int[] generateArray(int size, String type, Random random) {
        int[] array = new int[size];
        switch (type) {
            case "random":
                for (int i = 0; i < size; i++) {
                    array[i] = random.nextInt();
                }
                break;
            case "sorted":
                for (int i = 0; i < size; i++) {
                    array[i] = i;
                }
                break;
            case "reversed":
                for (int i = 0; i < size; i++) {
                    array[i] = size - 1 - i;
                }
                break;
            case "nearly-sorted":
                for (int i = 0; i < size; i++) {
                    array[i] = i;
                }
                int numSwaps = (int) (size * 0.05);
                for (int j = 0; j < numSwaps; j++) {
                    int idx1 = random.nextInt(size);
                    int idx2 = random.nextInt(size);
                    int temp = array[idx1];
                    array[idx1] = array[idx2];
                    array[idx2] = temp;
                }
                break;
        }
        return array;
    }
}