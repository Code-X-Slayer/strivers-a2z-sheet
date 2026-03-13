# Striver A2Z — Step 2: Learn Important Sorting Techniques
**Cheat Sheet | Java | Bubble · Selection · Insertion · Merge · Quick + Recursive Variants**

---

## Table of Contents
1. [Sorting Mental Model](#1-sorting-mental-model)
2. [Selection Sort](#2-selection-sort)
3. [Bubble Sort](#3-bubble-sort)
4. [Insertion Sort](#4-insertion-sort)
5. [Merge Sort](#5-merge-sort)
6. [Quick Sort](#6-quick-sort)
7. [Recursive Variants](#7-recursive-variants)
8. [Stability — What It Means and Why It Matters](#8-stability--what-it-means-and-why-it-matters)
9. [Comparison Table](#9-comparison-table)
10. [What to Know Beyond the Code](#10-what-to-know-beyond-the-code)
11. [Revision Checklist](#11-revision-checklist)

---

## 1. Sorting Mental Model

Every comparison-based sort answers one question: **where does this element belong relative to others?**

The three O(n²) sorts differ only in *how they pick and place elements*:
- **Selection:** find the right element for each position
- **Bubble:** push the wrong element toward its position by adjacent swaps
- **Insertion:** take each element and insert it at the right spot in the already-sorted prefix

The two O(n log n) sorts use **divide and conquer**:
- **Merge:** split → sort halves → merge (top-down, extra space)
- **Quick:** partition around pivot → sort halves (in-place, but pivot choice matters)

---

## 2. Selection Sort

### Idea
In each pass, find the **maximum** of the unsorted suffix and **swap it to the end** of that suffix. Your code works right-to-left — placing the max at position `n-1-i` each round.

```java
// find max in [0 .. j]
int idx = maxIdx(nums, nums.length - 1 - i);
swap(nums, nums.length - 1 - i, idx);
```

Most implementations find the **minimum** and place it at the front — both are equivalent. Your variant is equally valid.

### Complexity
| | Value |
|---|---|
| Time (all cases) | O(n²) |
| Space | O(1) |
| Stable? | **No** — a distant swap can skip over equal elements |
| Adaptive? | No — always does n(n-1)/2 comparisons regardless of input |

### Key Insight
Selection sort does at most **n-1 swaps** — the fewest among the O(n²) sorts. Useful when write operations are expensive (e.g., flash memory).

---

## 3. Bubble Sort

### Idea
Each pass **bubbles the largest unsorted element to its correct position** via adjacent swaps. After pass `i`, the last `i+1` elements are sorted.

```java
for (int j = 0; j < nums.length - 1 - i; j++) {
    if (nums[j] > nums[j + 1]) {
        swap(nums, j, j + 1);
        flag = true;
    }
}
if (!flag) break;  // already sorted — early exit
```

### The `flag` Optimization
If no swap occurred in a full pass, the array is **already sorted**. This makes bubble sort **adaptive**:
- Best case (sorted input): O(n) — only one pass, no swaps, flag never set
- Worst case (reverse sorted): O(n²)

### Complexity
| | Value |
|---|---|
| Time (best) | O(n) |
| Time (avg/worst) | O(n²) |
| Space | O(1) |
| Stable? | **Yes** — only swaps when `nums[j] > nums[j+1]`, equal elements never swap |
| Adaptive? | Yes (with flag) |

---

## 4. Insertion Sort

### Idea
Maintain a **sorted prefix**. For each new element `key = nums[i]`, shift all larger elements in the prefix one position right, then drop `key` into the gap.

```java
int key = nums[i];
int j = i;
while (j > 0 && nums[j - 1] > key) {
    nums[j] = nums[j - 1];  // shift right, not swap
    j--;
}
nums[j] = key;
```

**Shift, not swap.** This matters — shifting is one write, swapping is three. Insertion sort does half the writes of bubble sort.

### Complexity
| | Value |
|---|---|
| Time (best) | O(n) — already sorted, inner while never runs |
| Time (avg/worst) | O(n²) |
| Space | O(1) |
| Stable? | **Yes** — strict `>` comparison, equal elements are not moved |
| Adaptive? | **Yes** — best among O(n²) sorts on nearly sorted data |

### Key Insight
Java's `Arrays.sort()` for primitives uses a **Dual-Pivot Quicksort** that falls back to **insertion sort** for small subarrays (n ≤ 47). Insertion sort's O(n) best case makes it perfect for nearly sorted small chunks — this is why it's embedded in real-world hybrid sorts.

---

## 5. Merge Sort

### Idea
**Divide** the array into two halves, **recursively sort** each, then **merge** the sorted halves back.

```
mergeSort(l, r):
    if l < r:
        m = l + (r - l) / 2   ← avoid overflow vs (l+r)/2
        mergeSort(l, m)
        mergeSort(m+1, r)
        merge(l, m, r)
```

### The Merge Step
Copy both halves into temp arrays. Use two pointers to pick the smaller element back into the original.

```java
while (f1 < n1 && f2 < n2) {
    if (left[f1] <= right[f2]) nums[f++] = left[f1++];
    else nums[f++] = right[f2++];
}
// drain remaining
while (f1 < n1) nums[f++] = left[f1++];
while (f2 < n2) nums[f++] = right[f2++];
```

`<=` keeps equal elements in original order → **stable**.

### Midpoint: `l + (r - l) / 2`
Never write `(l + r) / 2` — if both are large ints, `l + r` overflows. `l + (r - l) / 2` is overflow-safe. Same pattern used in binary search.

### Complexity
| | Value |
|---|---|
| Time (all cases) | O(n log n) — guaranteed |
| Space | O(n) — temp arrays for merge |
| Stable? | **Yes** |
| Adaptive? | No — always splits fully |
| Recursion depth | O(log n) |

### Why O(n log n)?
- There are **log n levels** of splitting
- Each level does **O(n) total work** during merge
- Total: n × log n

### Real-World Usage
- Java's `Arrays.sort()` for **Objects** uses **TimSort** — a hybrid of merge sort and insertion sort
- Preferred when stability is required or when sorting linked lists (no random access needed)

---

## 6. Quick Sort

### Idea
Pick a **pivot**, partition the array so all elements ≤ pivot are on the left and all > pivot are on the right, then recursively sort each side.

### Lomuto Partition Scheme (your implementation)
```java
int pivot = nums[high];  // pivot = last element
int i = low - 1;
for (int j = low; j < high; j++) {
    if (nums[j] <= pivot) {
        i++;
        swap(nums, i, j);
    }
}
swap(nums, i + 1, high);  // place pivot in correct position
return i + 1;
```

`i` tracks the boundary of elements ≤ pivot. Every time `nums[j] <= pivot`, extend that boundary and swap. At the end, pivot goes to `i+1`.

### Complexity
| | Value |
|---|---|
| Time (best/avg) | O(n log n) |
| Time (worst) | O(n²) — when pivot is always min or max |
| Space | O(log n) — recursion stack (avg) |
| Stable? | **No** |
| Adaptive? | No |

### Worst Case Trigger
Sorted or reverse-sorted input with last-element pivot → pivot is always extreme → n partitions of size (n-1, 0) → O(n²). 

**Fix:** random pivot or median-of-three pivot.
```java
// random pivot: swap a random element with nums[high] before partitioning
int randIdx = low + (int)(Math.random() * (high - low + 1));
swap(nums, randIdx, high);
```

### Hoare vs Lomuto
| | Lomuto | Hoare |
|---|---|---|
| Pivot position | last element | first element |
| Pointer style | one boundary pointer | two converging pointers |
| Swaps | more | fewer (~3×) |
| Usage | simpler to implement | more efficient |

Your code uses **Lomuto** — simpler and standard for learning.

### Why Quick Sort Is Preferred in Practice
- **In-place** — O(log n) stack space vs O(n) for merge sort
- Cache-friendly — works on contiguous memory
- Constants are smaller than merge sort in practice
- O(n²) worst case is avoidable with randomized pivot

---

## 7. Recursive Variants

You implemented sorting iteratively **and** recursively, which builds the recursion-to-iteration mental model both ways.

### Recursive Bubble Sort — Two Implementations

**Variant A: Two-parameter (i = pass, j = current comparison)**
```java
void recursiveBubbleSort(int[] nums, int i, int j) {
    if (i == nums.length - 1) return;
    if (j == nums.length - 1 - i) { recursiveBubbleSort(nums, i + 1, 0); return; }
    if (nums[j] > nums[j + 1]) swap(nums, j, j + 1);
    recursiveBubbleSort(nums, i, j + 1);
}
// call: recursiveBubbleSort(nums, 0, 0)
```
Simulates the nested loop with two recursive parameters. `j` increments within a pass, `i` increments between passes.

**Variant B: Single-parameter (reduce problem size)**
```java
void bubbleSortRecursive(int[] nums, int i) {
    if (i == 0) return;
    for (int j = 0; j < i; j++) {
        if (nums[j] > nums[j + 1]) swap(nums, j, j + 1);
    }
    bubbleSortRecursive(nums, i - 1);  // largest is placed, sort remaining
}
// call: bubbleSortRecursive(nums, nums.length - 1)
```
Cleaner — each call places the largest in `nums[0..i]` at position `i`, then recurse on `nums[0..i-1]`.

### Recursive Insertion Sort — Two Implementations

**Variant A: Two-parameter (i = current element, j = insertion pointer)**
```java
void recursiveInsertionSort(int[] nums, int i, int j) {
    if (i == nums.length) return;
    if (j >= 0 && nums[j] > nums[j + 1]) {
        swap(nums, j, j + 1);
        recursiveInsertionSort(nums, i, j - 1);  // keep inserting left
    }
    recursiveInsertionSort(nums, i + 1, i);  // move to next element
}
// call: recursiveInsertionSort(nums, 1, 0)
```
Uses swap instead of shift — slightly less efficient but recursion maps directly.

**Variant B: Single-parameter (cleaner)**
```java
void insertionSortRecursive(int[] nums, int i) {
    if (i == nums.length) return;
    int j = i, key = nums[i];
    while (j > 0 && nums[j - 1] > key) { nums[j] = nums[j - 1]; j--; }
    nums[j] = key;
    insertionSortRecursive(nums, i + 1);
}
// call: insertionSortRecursive(nums, 1)
```
Tail-recursive style — each call handles one element's insertion, then moves to next.

### What Recursive Variants Teach
- Any iterative sort can be expressed recursively by replacing the outer loop with a recursive call
- The **base case** = loop termination condition
- The **recursive call** = next iteration
- This is the bridge to understanding merge sort and quick sort which are naturally recursive

---

## 8. Stability — What It Means and Why It Matters

**Stable sort:** equal elements maintain their **original relative order** after sorting.

Example: sorting `[(3,A), (1,B), (3,C)]` by number:
- Stable result: `[(1,B), (3,A), (3,C)]` — A before C preserved
- Unstable result: `[(1,B), (3,C), (3,A)]` — original order lost

### Why It Matters
- Sorting by multiple criteria (sort by name, then by age — requires stable sort for second pass to preserve first)
- Counting sort, radix sort correctness depends on stability
- Java's `Arrays.sort()` for Objects (TimSort) is stable — for primitives (Dual-Pivot Quicksort) it is not

### Stability by Algorithm
| Algorithm | Stable? | Reason |
|---|---|---|
| Bubble | Yes | Only swaps adjacent when strictly greater |
| Insertion | Yes | Strict `>`, shifts don't reorder equals |
| Merge | Yes | `left[f1] <= right[f2]` picks left on tie |
| Selection | No | Distant swaps can cross equal elements |
| Quick | No | Partition swaps disrupt relative order |

---

## 9. Comparison Table

| Algorithm | Best | Avg | Worst | Space | Stable | Adaptive | Notes |
|---|---|---|---|---|---|---|---|
| Selection | O(n²) | O(n²) | O(n²) | O(1) | No | No | Min swaps |
| Bubble | O(n) | O(n²) | O(n²) | O(1) | Yes | Yes | Flag optimization |
| Insertion | O(n) | O(n²) | O(n²) | O(1) | Yes | Yes | Best for nearly sorted |
| Merge | O(n log n) | O(n log n) | O(n log n) | O(n) | Yes | No | Guaranteed performance |
| Quick | O(n log n) | O(n log n) | O(n²) | O(log n) | No | No | Fastest in practice |

---

## 10. What to Know Beyond the Code

### Counting Sort (Non-comparison based)
When values are bounded integers in range [0, k]:
```java
int[] count = new int[k + 1];
for (int x : arr) count[x]++;
int idx = 0;
for (int i = 0; i <= k; i++)
    while (count[i]-- > 0) arr[idx++] = i;
```
- Time: O(n + k)
- Space: O(k)
- Stable: yes (with output array variant)
- Not comparison-based → can break O(n log n) lower bound

### Radix Sort
Sort integers digit by digit using a stable sort (counting sort) at each digit position.
- Time: O(d × (n + b)) where d = digits, b = base (usually 10)
- For fixed-width integers: effectively O(n)

### TimSort (Java's actual sort for Objects)
Hybrid of merge sort and insertion sort:
- Detects naturally sorted **runs** in input
- Inserts small unsorted pieces using insertion sort
- Merges runs using merge sort
- Result: O(n) on already-sorted data, O(n log n) worst case, stable

### Lower Bound for Comparison Sorts
Any comparison-based sort requires **Ω(n log n)** comparisons in the worst case. This is proven via decision tree argument — there are n! possible orderings and each comparison eliminates at most half, so you need at least log₂(n!) ≈ n log n comparisons.

Counting sort / radix sort bypass this by not using comparisons.

### Quickselect (Related to Quick Sort)
Find the k-th smallest element in O(n) average using the same partition logic:
```java
int partition = helper(nums, low, high);
if (partition == k) return nums[k];
else if (partition < k) quickselect(nums, partition + 1, high, k);
else quickselect(nums, low, partition - 1, k);
```
Average O(n), worst O(n²). Used in problems like "K-th largest element in an array."

### Dutch National Flag (3-way Partition)
Sort array containing only 0s, 1s, and 2s in O(n):
```java
int lo = 0, mid = 0, hi = n - 1;
while (mid <= hi) {
    if (nums[mid] == 0) swap(lo++, mid++);
    else if (nums[mid] == 1) mid++;
    else swap(mid, hi--);
}
```
This is a 3-way Quick Sort partition — elements equal to pivot don't get recursed on. Faster on arrays with many duplicates.

### Merge Sort Applications Beyond Sorting
- **Count inversions:** number of pairs (i,j) where i < j but arr[i] > arr[j] — count during merge step
- **External sort:** when data doesn't fit in memory, merge sort works chunk by chunk
- **Sorting linked lists:** merge sort is preferred (no random access needed, O(1) extra space for LL merge)

---

## 11. Revision Checklist

- [ ] Can implement all 5 sorts from scratch without reference
- [ ] Know which sorts are stable and can explain why
- [ ] Explain why bubble sort's flag makes it O(n) best case
- [ ] Explain why insertion sort beats bubble sort on nearly sorted input
- [ ] Explain merge sort's O(n log n) recurrence: T(n) = 2T(n/2) + O(n)
- [ ] Know quick sort's worst case trigger and the random pivot fix
- [ ] Distinguish Lomuto vs Hoare partition
- [ ] Can write recursive bubble and insertion sort from iterative versions
- [ ] Know when to use counting sort vs comparison sorts
- [ ] Understand stability and give a concrete example of when it matters
- [ ] Know that `l + (r - l) / 2` is the overflow-safe midpoint — use it everywhere