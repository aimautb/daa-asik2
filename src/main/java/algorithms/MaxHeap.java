package algorithms;

/**
 * A max-heap data structure implementation using an array.
 */
public class MaxHeap {
    private int[] heap;
    private int size;
    private int capacity;

    /**
     * Constructs a new max-heap with the given fixed capacity.
     *
     * @param capacity the maximum number of elements the heap can hold
     * @throws IllegalArgumentException if capacity is not positive
     */
    public MaxHeap(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive");
        }
        this.capacity = capacity;
        this.heap = new int[capacity];
        PerformanceTracker.incrementMemoryAllocation();
        this.size = 0;
    }

    /**
     * Returns the current number of elements in the heap.
     *
     * @return the size of the heap
     */
    public int size() {
        return size;
    }

    /**
     * Checks if the heap is empty.
     *
     * @return true if the heap has no elements, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the maximum element in the heap without removing it.
     *
     * @return the maximum element
     * @throws IllegalStateException if the heap is empty
     */
    public int getMax() {
        if (isEmpty()) {
            throw new IllegalStateException("Heap is empty");
        }
        PerformanceTracker.incrementArrayAccess();
        return heap[0];
    }

    /**
     * Inserts a new value into the heap.
     *
     * @param value the value to insert
     * @throws IllegalStateException if the heap is full
     */
    public void insert(int value) {
        if (size == capacity) {
            throw new IllegalStateException("Heap is full");
        }
        PerformanceTracker.incrementArrayAccess();
        heap[size] = value;
        heapifyUp(size);
        size++;
    }

    /**
     * Removes and returns the maximum element from the heap.
     *
     * @return the maximum element
     * @throws IllegalStateException if the heap is empty
     */
    public int extractMax() {
        if (isEmpty()) {
            throw new IllegalStateException("Heap is empty");
        }
        PerformanceTracker.incrementArrayAccess();
        int max = heap[0];
        size--;
        if (size > 0) {
            PerformanceTracker.incrementArrayAccess();
            int last = heap[size];
            PerformanceTracker.incrementArrayAccess();
            heap[0] = last;
            heapifyDown(0);
        }
        return max;
    }

    /**
     * Increases the key at the given index to a new value and restores the heap property.
     *
     * @param index the index of the element to update
     * @param newValue the new value for the element
     * @throws IndexOutOfBoundsException if the index is invalid
     * @throws IllegalArgumentException if the new value is smaller than the current value
     */
    public void increaseKey(int index, int newValue) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
        PerformanceTracker.incrementArrayAccess();
        int current = heap[index];
        PerformanceTracker.incrementComparison();
        if (newValue < current) {
            throw new IllegalArgumentException("New value is smaller");
        }
        PerformanceTracker.incrementArrayAccess();
        heap[index] = newValue;
        heapifyUp(index);
    }

    /**
     * Builds the max-heap from the given array in O(n) time by copying the array into the heap.
     *
     * @param array the array to build the heap from
     * @throws IllegalArgumentException if the array is null or larger than the heap's capacity
     */
    public void buildHeap(int[] array) {
        if (array == null) {
            throw new IllegalArgumentException("Array is null");
        }
        if (array.length > capacity) {
            throw new IllegalArgumentException("Array larger than capacity");
        }
        for (int i = 0; i < array.length; i++) {
            PerformanceTracker.incrementArrayAccess();
            heap[i] = array[i];
        }
        size = array.length;
        for (int i = size / 2 - 1; i >= 0; i--) {
            heapifyDown(i);
        }
    }

    private void heapifyDown(int index) {
        while (true) {
            int left = 2 * index + 1;
            int right = 2 * index + 2;
            int largest = index;

            if (left < size) {
                PerformanceTracker.incrementArrayAccess();
                PerformanceTracker.incrementArrayAccess();
                PerformanceTracker.incrementComparison();
                if (heap[left] > heap[largest]) {
                    largest = left;
                }
            }
            if (right < size) {
                PerformanceTracker.incrementArrayAccess();
                PerformanceTracker.incrementArrayAccess();
                PerformanceTracker.incrementComparison();
                if (heap[right] > heap[largest]) {
                    largest = right;
                }
            }
            if (largest == index) {
                break;
            }
            swap(index, largest);
            index = largest;
        }
    }

    private void heapifyUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;
            PerformanceTracker.incrementArrayAccess();
            PerformanceTracker.incrementArrayAccess();
            PerformanceTracker.incrementComparison();
            if (heap[index] <= heap[parent]) {
                break;
            }
            swap(index, parent);
            index = parent;
        }
    }

    private void swap(int i, int j) {
        PerformanceTracker.incrementArrayAccess();
        int temp = heap[i];
        PerformanceTracker.incrementArrayAccess();
        PerformanceTracker.incrementArrayAccess();
        heap[i] = heap[j];
        PerformanceTracker.incrementArrayAccess();
        heap[j] = temp;
        PerformanceTracker.incrementSwap();
    }
}