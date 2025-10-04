package algorithms;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class MaxHeapTest {

    @Test
    void shouldThrowExceptionWhenGetMaxOnEmptyHeap() {
        MaxHeap heap = new MaxHeap(10);
        assertThrows(IllegalStateException.class, heap::getMax);
    }

    @Test
    void shouldThrowExceptionWhenExtractMaxOnEmptyHeap() {
        MaxHeap heap = new MaxHeap(10);
        assertThrows(IllegalStateException.class, heap::extractMax);
    }

    @Test
    void shouldInsertIntoEmptyHeap() {
        MaxHeap heap = new MaxHeap(10);
        heap.insert(5);
        assertEquals(1, heap.size());
        assertFalse(heap.isEmpty());
        assertEquals(5, heap.getMax());
    }

    @Test
    void shouldInsertAndExtractSingleElement() {
        MaxHeap heap = new MaxHeap(10);
        heap.insert(10);
        assertEquals(10, heap.extractMax());
        assertTrue(heap.isEmpty());
    }

    @Test
    void shouldHandleDuplicateValues() {
        MaxHeap heap = new MaxHeap(10);
        heap.insert(5);
        heap.insert(5);
        heap.insert(3);
        heap.insert(5);
        assertEquals(5, heap.getMax());
        assertEquals(5, heap.extractMax());
        assertEquals(5, heap.getMax());
        assertEquals(5, heap.extractMax());
        assertEquals(5, heap.getMax());
        assertEquals(5, heap.extractMax());
        assertEquals(3, heap.getMax());
    }

    @Test
    void shouldThrowExceptionWhenInsertIntoFullHeap() {
        MaxHeap heap = new MaxHeap(2);
        heap.insert(1);
        heap.insert(2);
        assertThrows(IllegalStateException.class, () -> heap.insert(3));
    }

    @Test
    void shouldReturnCurrentMaxWithGetMax() {
        MaxHeap heap = new MaxHeap(10);
        heap.insert(1);
        assertEquals(1, heap.getMax());
        heap.insert(3);
        assertEquals(3, heap.getMax());
        heap.insert(2);
        assertEquals(3, heap.getMax());
        heap.extractMax();
        assertEquals(2, heap.getMax());
    }

    @Test
    void shouldThrowExceptionWhenIncreaseKeyDecreasesValue() {
        MaxHeap heap = new MaxHeap(10);
        heap.insert(10);
        assertThrows(IllegalArgumentException.class, () -> heap.increaseKey(0, 5));
    }

    @Test
    void shouldThrowExceptionWhenIncreaseKeyOnInvalidIndex() {
        MaxHeap heap = new MaxHeap(10);
        assertThrows(IndexOutOfBoundsException.class, () -> heap.increaseKey(-1, 10));
        assertThrows(IndexOutOfBoundsException.class, () -> heap.increaseKey(0, 10));
        heap.insert(1);
        assertThrows(IndexOutOfBoundsException.class, () -> heap.increaseKey(1, 10));
    }

    @Test
    void shouldIncreaseKeyAndMaintainHeapProperty() {
        MaxHeap heap = new MaxHeap(10);
        heap.insert(1);
        heap.insert(3);
        heap.insert(2);
        assertEquals(3, heap.getMax());
        heap.increaseKey(1, 4); // Increase 1 to 4, should bubble up
        assertEquals(4, heap.getMax());
        assertEquals(4, heap.extractMax());
        assertEquals(3, heap.getMax());
    }

    @Test
    void shouldBuildHeapFromRandomArray() {
        int[] array = {4, 1, 3, 2, 16, 9, 10, 14, 8, 7};
        MaxHeap heap = new MaxHeap(20);
        heap.buildHeap(array);
        assertEquals(10, heap.size());
        assertEquals(16, heap.getMax());
        List<Integer> extracted = extractAll(heap);
        List<Integer> expected = Arrays.stream(array).boxed().sorted(Comparator.reverseOrder()).toList();
        assertEquals(expected, extracted);
    }

    @Test
    void shouldBuildHeapFromAscendingSortedArray() {
        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        MaxHeap heap = new MaxHeap(20);
        heap.buildHeap(array);
        assertEquals(10, heap.size());
        assertEquals(10, heap.getMax());
        List<Integer> extracted = extractAll(heap);
        List<Integer> expected = Arrays.stream(array).boxed().sorted(Comparator.reverseOrder()).toList();
        assertEquals(expected, extracted);
    }

    @Test
    void shouldBuildHeapFromDescendingSortedArray() {
        int[] array = {10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
        MaxHeap heap = new MaxHeap(20);
        heap.buildHeap(array);
        assertEquals(10, heap.size());
        assertEquals(10, heap.getMax());
        List<Integer> extracted = extractAll(heap);
        List<Integer> expected = Arrays.stream(array).boxed().sorted(Comparator.reverseOrder()).toList();
        assertEquals(expected, extracted);
    }

    @Test
    void shouldBuildHeapFromArrayWithDuplicates() {
        int[] array = {5, 5, 5, 5, 5};
        MaxHeap heap = new MaxHeap(10);
        heap.buildHeap(array);
        assertEquals(5, heap.size());
        assertEquals(5, heap.getMax());
        List<Integer> extracted = extractAll(heap);
        List<Integer> expected = Arrays.stream(array).boxed().sorted(Comparator.reverseOrder()).toList();
        assertEquals(expected, extracted);
    }

    @Test
    void shouldThrowExceptionWhenBuildHeapWithNullArray() {
        MaxHeap heap = new MaxHeap(10);
        assertThrows(IllegalArgumentException.class, () -> heap.buildHeap(null));
    }

    @Test
    void shouldThrowExceptionWhenBuildHeapWithArrayLargerThanCapacity() {
        MaxHeap heap = new MaxHeap(5);
        int[] array = new int[6];
        assertThrows(IllegalArgumentException.class, () -> heap.buildHeap(array));
    }

    @Test
    void shouldMaintainOrderWithRandomArraysUsingInsert() {
        Random random = new Random(42); // Fixed seed for reproducibility
        for (int test = 0; test < 10; test++) {
            int n = random.nextInt(100) + 1;
            int[] array = new int[n];
            for (int i = 0; i < n; i++) {
                array[i] = random.nextInt(1000);
            }
            MaxHeap heap = new MaxHeap(n * 2);
            for (int value : array) {
                heap.insert(value);
            }
            List<Integer> extracted = extractAll(heap);
            List<Integer> expected = Arrays.stream(array).boxed().sorted(Comparator.reverseOrder()).toList();
            assertEquals(expected, extracted);
        }
    }

    @Test
    void shouldMaintainOrderWithRandomArraysUsingBuildHeap() {
        Random random = new Random(42); // Fixed seed for reproducibility
        for (int test = 0; test < 10; test++) {
            int n = random.nextInt(100) + 1;
            int[] array = new int[n];
            for (int i = 0; i < n; i++) {
                array[i] = random.nextInt(1000);
            }
            MaxHeap heap = new MaxHeap(n * 2);
            heap.buildHeap(array);
            List<Integer> extracted = extractAll(heap);
            List<Integer> expected = Arrays.stream(array).boxed().sorted(Comparator.reverseOrder()).toList();
            assertEquals(expected, extracted);
        }
    }

    @Test
    void shouldPerformWellWith100Elements() {
        assertTimeout(Duration.ofSeconds(1), () -> {
            MaxHeap heap = new MaxHeap(200);
            for (int i = 0; i < 100; i++) {
                heap.insert(i);
            }
            for (int i = 0; i < 100; i++) {
                heap.extractMax();
            }
        });
    }

    @Test
    void shouldPerformWellWith1000Elements() {
        assertTimeout(Duration.ofSeconds(1), () -> {
            MaxHeap heap = new MaxHeap(2000);
            for (int i = 0; i < 1000; i++) {
                heap.insert(i);
            }
            for (int i = 0; i < 1000; i++) {
                heap.extractMax();
            }
        });
    }

    @Test
    void shouldPerformWellWith10000Elements() {
        assertTimeout(Duration.ofSeconds(1), () -> {
            MaxHeap heap = new MaxHeap(20000);
            for (int i = 0; i < 10000; i++) {
                heap.insert(i);
            }
            for (int i = 0; i < 10000; i++) {
                heap.extractMax();
            }
        });
    }

    @Test
    void shouldUpdatePerformanceCountersDuringOperations() {
        PerformanceTracker.reset();
        MaxHeap heap = new MaxHeap(10);
        assertEquals(1, PerformanceTracker.getMemoryAllocations());
        heap.insert(1);
        heap.insert(3);
        heap.insert(2);
        heap.extractMax();
        heap.increaseKey(0, 5);
        assertTrue(PerformanceTracker.getComparisons() > 0);
        assertTrue(PerformanceTracker.getSwaps() > 0);
        assertTrue(PerformanceTracker.getArrayAccesses() > 0);
    }

    @Test
    void shouldUpdatePerformanceCountersDuringBuildHeap() {
        PerformanceTracker.reset();
        MaxHeap heap = new MaxHeap(20);
        int[] array = {4, 1, 3, 2, 16, 9, 10, 14, 8, 7};
        heap.buildHeap(array);
        assertTrue(PerformanceTracker.getComparisons() > 0);
        assertTrue(PerformanceTracker.getSwaps() > 0);
        assertTrue(PerformanceTracker.getArrayAccesses() > 0);
    }

    private List<Integer> extractAll(MaxHeap heap) {
        List<Integer> extracted = new ArrayList<>();
        while (!heap.isEmpty()) {
            extracted.add(heap.extractMax());
        }
        return extracted;
    }
}