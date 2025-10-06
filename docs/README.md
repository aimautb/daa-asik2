# Assignment_2_DAA
## Overview
This repository implements a **Max-Heap** data structure in Java with integrated **performance tracking** and **benchmarking** tools for algorithmic analysis.

## Features
**Core Operations**
- `insert(value)` – Inserts a new element into the heap while maintaining the max-heap property.
- `extractMax()` – Removes and returns the largest element from the heap.
- `increaseKey(index, newValue)` – Increases the value of an element at a given index and re-heapifies if necessary.
- `merge(otherHeap)` – Efficiently merges two heaps in **O(n)** time using the bottom-up `buildHeap()` approach.

**Performance Metrics**
- Tracks **comparisons**, **swaps**, **array accesses**, and **memory allocations** through the `PerformanceTracker` utility.

**Benchmarking**
- Includes a **CLI benchmark runner** (`BenchmarkRunner`) for evaluating heap performance across various input sizes and distributions (random, sorted, reversed, nearly-sorted).
- Benchmark results are automatically exported to **CSV** files for empirical performance analysis and plotting.

**Testing**
- Comprehensive **JUnit test suite** covering edge cases (empty heap, single element, duplicate keys).
- Tests validate correctness and performance consistency against theoretical expectations.

## Project Structure
assignment2-maxheap/
├── src/main/java/
│ ├── algorithms/MaxHeap.java
│ ├── metrics/PerformanceTracker.java
│ └── cli/BenchmarkRunner.java
├── src/test/java/
│ └── algorithms/MaxHeapTest.java
├── docs/
│ ├── analysis-report.pdf
│ └── performance-plots/
├── README.md
└── pom.xml

markdown
Копировать код

## Git Workflow
- `feature/algorithm` – main Max-Heap implementation
- `feature/metrics` – performance tracking module
- `feature/testing` – JUnit test suite
- `feature/cli` – command-line benchmark runner
- `feature/optimization` – performance improvement branch

## Sample Commit History
init: create maven structure and setup junit5
feat(metrics): add performance tracker for comparisons/swaps
feat(algorithm): implement MaxHeap with in-place heapify
test(algorithm): add unit tests for edge cases
feat(cli): implement benchmark runner and CSV exporter
perf(benchmark): run benchmarks for n=100–10000
docs(readme): add overview and usage instructions
release: v1.0 complete implementation

bash
Копировать код

## Usage Example (CLI)
You can benchmark the MaxHeap implementation directly from the command line using the `BenchmarkRunner`:

```bash
# Compile the project
mvn clean package

# Run benchmark with custom parameters
java -cp target/classes cli.BenchmarkRunner --algo maxheap --size 1000 --input random

# Example output:
# Algorithm: MaxHeap
# Input: random, n=1000
# Time (ns): 1724100
# Comparisons: 16899
# Swaps: 8100
The results will be automatically saved into a CSV file located in the /docs/performance-plots/ or /target/ directory for further analysis.








