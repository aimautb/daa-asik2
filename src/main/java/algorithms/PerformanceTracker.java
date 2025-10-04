package algorithms;

public class PerformanceTracker {
    private static long comparisons = 0;
    private static long swaps = 0;
    private static long memoryAllocations = 0;
    private static long arrayAccesses = 0;


    public static void incrementComparison() { comparisons++; }
    public static void incrementComparisons() { comparisons++; }
    public static long getComparisons() { return comparisons; }


    public static void incrementSwap() { swaps++; }

    public static long getSwaps() { return swaps; }


    public static void incrementMemoryAllocation() { memoryAllocations++; }

    public static long getMemoryAllocations() { return memoryAllocations; }


    public static void incrementArrayAccess() { arrayAccesses++; }

    public static long getArrayAccesses() { return arrayAccesses; }


    public static void reset() {
        comparisons = 0;
        swaps = 0;
        memoryAllocations = 0;
        arrayAccesses = 0;
    }
}
