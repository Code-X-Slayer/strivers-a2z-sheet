# Striver A2Z — Step 4: Binary Search (1D Arrays)
**Cheat Sheet | Java | 13 Problems · Foundation → Bounds → Rotated → Special**

---

## Table of Contents
1. [Binary Search — The Foundation](#1-binary-search--the-foundation)
2. [Lower Bound](#2-lower-bound)
3. [Upper Bound](#3-upper-bound)
4. [Insert Position](#4-insert-position)
5. [Floor and Ceil](#5-floor-and-ceil)
6. [First and Last Occurrence](#6-first-and-last-occurrence)
7. [Count Occurrences](#7-count-occurrences)
8. [Search in Rotated Sorted Array I (distinct)](#8-search-in-rotated-sorted-array-i-distinct)
9. [Search in Rotated Sorted Array II (duplicates)](#9-search-in-rotated-sorted-array-ii-duplicates)
10. [Minimum in Rotated Sorted Array](#10-minimum-in-rotated-sorted-array)
11. [Number of Rotations](#11-number-of-rotations)
12. [Single Element in Sorted Array](#12-single-element-in-sorted-array)
13. [Peak Element](#13-peak-element)
14. [Binary Search Mental Models](#14-binary-search-mental-models)
15. [Revision Checklist](#15-revision-checklist)

---

## 1. Binary Search — The Foundation

**Prerequisite:** sorted (or partially sorted / monotone) array.

### Iterative
```java
int l = 0, r = nums.length - 1;
while (l <= r) {
    int mid = l + (r - l) / 2;     // overflow-safe midpoint
    if (nums[mid] == target)  return mid;
    else if (nums[mid] > target) r = mid - 1;
    else                         l = mid + 1;
}
return -1;
```

### Recursive
```java
int bs(int[] nums, int target, int l, int r) {
    if (l > r) return -1;
    int mid = l + (r - l) / 2;
    if (nums[mid] == target)  return mid;
    else if (nums[mid] > target) return bs(nums, target, l, mid - 1);
    else                         return bs(nums, target, mid + 1, r);
}
```

### Core Rules
- Loop condition: `l <= r` (inclusive on both ends)
- Midpoint: always `l + (r - l) / 2` — never `(l + r) / 2` (overflows when both are large)
- When target found: return immediately
- When `nums[mid] > target`: `r = mid - 1` (not `mid`)
- When `nums[mid] < target`: `l = mid + 1` (not `mid`)
- Termination: when `l > r`, target is absent

### Why `l <= r` not `l < r`?
`l < r` misses the case where the answer is a single-element range `[l, l]`. The loop exits before checking it. Use `l <= r` for standard binary search, `l < r` only in specific convergence patterns (peak element, single element — covered below).

### Complexity
O(log n) time, O(1) iterative / O(log n) recursive (stack frames).

---

## 2. Lower Bound

**Definition:** index of the **first element ≥ x**. Returns `n` if all elements < x.

```java
int l = 0, r = nums.length - 1, res = nums.length; // default: past end
while (l <= r) {
    int mid = l + (r - l) / 2;
    if (nums[mid] >= x) { res = mid; r = mid - 1; }  // potential answer, go left
    else                  l = mid + 1;
}
return res;
```

**Your implementation** initializes `res = -1` and returns `-1` if not found. The standard definition returns `n` (index past the end) to indicate "all elements < x." Know which variant the problem expects.

**Connection to Java:** `Collections.binarySearch()` returns negative values for missing elements. `Arrays.binarySearch()` returns `-(insertion point) - 1`. Lower bound = `-(result) - 1` when result is negative.

**When `nums[mid] >= x`:** this could be the answer but there might be a smaller valid index — save it in `res` and search left (`r = mid - 1`).

---

## 3. Upper Bound

**Definition (Striver's):** index of the **last element ≤ x**. Your code returns this.

**Standard C++ upper_bound definition:** first index where element **> x** (one past the last valid element). Know which definition is being used — they differ.

```java
// Last element <= x (your implementation)
int l = 0, r = nums.length - 1, res = -1;
while (l <= r) {
    int mid = l + (r - l) / 2;
    if (nums[mid] <= x) { res = mid; l = mid + 1; }  // potential answer, go right
    else                  r = mid - 1;
}
return res;
```

**Lower bound vs Upper bound summary:**
| | Condition to save `res` | Direction after saving |
|---|---|---|
| Lower bound (first ≥ x) | `nums[mid] >= x` | go left `r = mid - 1` |
| Upper bound (last ≤ x) | `nums[mid] <= x` | go right `l = mid + 1` |
| First occurrence of x | `nums[mid] == x` | go left `r = mid - 1` |
| Last occurrence of x | `nums[mid] == x` | go right `l = mid + 1` |

All four are the same BS template — only the save condition and search direction change.

---

## 4. Insert Position

**Problem:** find index where `target` should be inserted to keep sorted order. If exists, return its index.

```java
int l = 0, r = nums.length - 1, res = -1;
while (l <= r) {
    int mid = l + (r - l) / 2;
    if (nums[mid] >= target) { res = mid; r = mid - 1; }
    else l = mid + 1;
}
return res != -1 ? res : nums.length;
```

**This is exactly Lower Bound.** Insert position = first index where `nums[idx] >= target`. If no such index (all elements < target), insert at `nums.length`.

**Edge cases your code handles:**
- Target smaller than all elements → returns 0
- Target larger than all elements → `res` stays -1 → returns `nums.length`
- Target exists in array → returns its index (first occurrence if duplicates)

---

## 5. Floor and Ceil

**Floor:** largest element ≤ x. Returns its **index** in your code (return value, not value itself — check what the problem wants).

**Ceil:** smallest element ≥ x.

```java
// Floor: largest element <= x
int floor(int[] arr, int x) {
    int start = 0, end = arr.length - 1, res = -1;
    while (start <= end) {
        int mid = start + (end - start) / 2;
        if (arr[mid] <= x) { res = mid; start = mid + 1; }  // go right for larger valid
        else end = mid - 1;
    }
    return res;   // index, not value. return arr[res] for value
}

// Ceil: smallest element >= x
int ceil(int[] arr, int x) {
    int start = 0, end = arr.length - 1, res = -1;
    while (start <= end) {
        int mid = start + (end - start) / 2;
        if (arr[mid] >= x) { res = mid; end = mid - 1; }    // go left for smaller valid
        else start = mid + 1;
    }
    return res;
}
```

**Floor = Upper bound (last ≤ x). Ceil = Lower bound (first ≥ x).** These four names — floor, ceil, lower bound, upper bound — are variations of the same two underlying binary search patterns. Master the two patterns, the names follow.

**When x exists in array:** floor = ceil = index of x.
**When x doesn't exist:** floor is the predecessor, ceil is the successor. Both return -1 if no such element.

---

## 6. First and Last Occurrence

**First occurrence:** binary search, when `nums[mid] == target` save index and go **left** (`end = mid - 1`).

**Last occurrence:** binary search, when `nums[mid] == target` save index and go **right** (`start = mid + 1`).

```java
int firstOcc(int[] nums, int target) {
    int start = 0, end = n - 1, res = -1;
    while (start <= end) {
        int mid = start + (end - start) / 2;
        if (nums[mid] == target) { res = mid; end = mid - 1; }   // save, go left
        else if (nums[mid] < target) start = mid + 1;
        else end = mid - 1;
    }
    return res;
}
```

**The key behavioral difference from standard BS:** instead of returning immediately when found, you save the result and keep searching in the direction that could yield a better answer.

**Both are O(log n)** — two independent BS calls, not one extended search.

---

## 7. Count Occurrences

```java
int firstOcc = firstOcc(nums, target);
if (firstOcc == -1) return 0;
return lastOcc(nums, target) - firstOcc + 1;
```

O(log n). Reuses first/last occurrence logic.

**Why check `firstOcc == -1` before computing last?** If target doesn't exist, `lastOcc` also returns -1 and `-1 - (-1) + 1 = 1` which is wrong. Short-circuit on not-found.

**Alternative:** `lastOcc - firstOcc + 1` where both return the same -1 when absent — but this gives 1 instead of 0. Always guard.

---

## 8. Search in Rotated Sorted Array I (distinct elements)

**Problem:** array is sorted and rotated at some unknown pivot. All elements distinct. Find target.

**Your approach — Find pivot first, then binary search on correct half:**
```java
// pivot = index of the largest element (the "drop" point)
int flag = pivot(nums);   // returns -1 if not rotated
if (flag == -1) return bs(nums, 0, n - 1, target);
if (nums[flag] == target) return flag;
int pos = bs(nums, 0, flag - 1, target);     // search left half
return pos != -1 ? pos : bs(nums, flag + 1, n - 1, target);  // else right half
```

**Better approach — single-pass BS without finding pivot (know this for interviews):**
```java
int l = 0, r = n - 1;
while (l <= r) {
    int mid = l + (r - l) / 2;
    if (nums[mid] == target) return mid;
    // determine which half is sorted
    if (nums[l] <= nums[mid]) {    // left half is sorted
        if (nums[l] <= target && target < nums[mid]) r = mid - 1;
        else l = mid + 1;
    } else {                       // right half is sorted
        if (nums[mid] < target && target <= nums[r]) l = mid + 1;
        else r = mid - 1;
    }
}
return -1;
```
O(log n), O(1). One of the two halves around `mid` is always fully sorted — use that to determine if target is in it.

**Key insight:** in a rotated sorted array, at least one of the two halves around any midpoint is always sorted. You can identify which one by comparing `nums[l]` with `nums[mid]`. Once the sorted half is known, you can do a clean range check to decide which direction to search.

**Pivot function logic:**
- `nums[mid] > nums[mid+1]` → `mid` is the pivot (largest element)
- `nums[mid-1] > nums[mid]` → `mid-1` is the pivot
- `nums[mid] > nums[start]` → pivot is in right half → `start = mid + 1`
- else → pivot is in left half → `end = mid - 1`

---

## 9. Search in Rotated Sorted Array II (with duplicates)

**The duplicate problem:** when `nums[l] == nums[mid] == nums[r]`, you can't determine which half is sorted.

```java
// In the single-pass approach, add this before the sorted-half check:
if (nums[l] == nums[mid] && nums[mid] == nums[r]) {
    l++; r--;   // shrink both ends, sacrifice O(log n) for this step
    continue;
}
```

**Worst case becomes O(n)** — e.g., `[1,1,1,1,1,1,2]` — every step only shrinks by 1. This is unavoidable with duplicates; no purely O(log n) solution exists.

**Your implementation** handles this in the pivot function by detecting the ambiguous case (`nums[mid] == nums[start] && nums[mid] == nums[end]`) and incrementally shrinking. The extra edge-case checks at boundaries make it more complex — the `l++; r--` shrink in the single-pass approach is cleaner.

---

## 10. Minimum in Rotated Sorted Array

**Problem:** find minimum element in rotated sorted array.

**Approach — Find pivot, minimum is at pivot+1:**
```java
int pivotIdx = pivot(nums);   // pivot = index of largest element
return pivotIdx == -1 ? nums[0] : nums[pivotIdx + 1];
```

**Better — direct BS for minimum:**
```java
int l = 0, r = n - 1;
while (l < r) {
    int mid = l + (r - l) / 2;
    if (nums[mid] > nums[r]) l = mid + 1;  // min is in right half
    else r = mid;                           // mid could be the min
}
return nums[l];
```
O(log n), O(1). Loop condition `l < r` — converges to single element which is the minimum.

**Why compare `nums[mid]` with `nums[r]` not `nums[l]`?**
- If `nums[mid] > nums[r]`: the rotation point (and minimum) is to the right of `mid`
- If `nums[mid] <= nums[r]`: `mid` might be the minimum or it's in the left segment — keep `mid` in range with `r = mid`

**If array is not rotated (pivot returns -1):** minimum is `nums[0]`.

---

## 11. Number of Rotations

**Definition:** a sorted array rotated k times puts the minimum at index k. So number of rotations = index of minimum element.

**Approach 1 — Linear:** find index of minimum element. O(n).

**Approach 2 — Find first descent:** scan for `nums[i] > nums[i+1]`, return `i+1`. O(n). Returns 0 if not rotated.

**Approach 3 — Find pivot (O(log n)):** pivot index + 1 = number of rotations.
```java
int pivot = pivot(nums);   // index of largest element
return pivot == -1 ? 0 : pivot + 1;
```

**Why pivot + 1?** Pivot is the index of the largest element (the last element of the "first sorted segment"). The minimum comes right after it at `pivot + 1`. A rotation of k steps puts the original `nums[0]` at index k, so the original first element is now at index `pivot + 1 = k`.

**Not rotated case (pivot returns -1):** `nums[0]` is still the minimum → 0 rotations.

---

## 12. Single Element in Sorted Array

**Problem:** sorted array where every element appears twice except one. Find it in O(log n).

**Approach 1 — Linear scan O(n):** check `nums[0] != nums[1]`, `nums[n-2] != nums[n-1]`, then middle elements.

**Approach 2 — XOR O(n):** `XOR` all elements. Pairs cancel, single survives.

**Approach 3 — Binary Search O(log n):**

**Key observation:** before the single element, pairs start at **even indices** (`nums[0]==nums[1]`, `nums[2]==nums[3]`...). After the single element, pairs start at **odd indices**.

```java
int start = 0, end = nums.length - 1;
while (start < end) {
    int mid = start + (end - start) / 2;
    if ((mid & 1) == 1) mid--;        // force mid to even index
    if (nums[mid] == nums[mid + 1])   // pair is intact → single is to the right
        start = mid + 2;
    else                              // pair is broken → single is here or to the left
        end = mid;
}
return nums[start];
```
O(log n), O(1).

**Why force `mid` to even?** The "pair starts at even index" invariant only holds cleanly when `mid` is even. If `mid` is odd, decrement to make it even — this ensures you're always looking at a pair's starting index.

**Why `l < r` not `l <= r`?** This pattern converges `start` and `end` to the same index — the answer. `l <= r` would check `mid` one extra time when `start == end`, which is unnecessary.

**Why `start = mid + 2` not `mid + 1`?** Both `nums[mid]` and `nums[mid+1]` are confirmed pair members — skip both. Moving `mid + 1` would land on the second element of the pair.

---

## 13. Peak Element

**Problem:** element greater than both its neighbors. Boundaries count as -∞ (so `nums[0]` > left boundary always, `nums[n-1]` > right boundary always).

**Approach 1 — Linear O(n):**
```java
for (int i = 0; i < n; i++) {
    boolean left  = (i == 0)     || nums[i] > nums[i-1];
    boolean right = (i == n - 1) || nums[i] > nums[i+1];
    if (left && right) return i;
}
```

**Approach 2 — Binary Search O(log n):**
```java
int start = 0, end = nums.length - 1;
while (start < end) {
    int mid = start + (end - start) / 2;
    if (nums[mid] < nums[mid + 1]) start = mid + 1;  // ascending slope → peak is right
    else                           end = mid;          // descending or flat → peak is here or left
}
return start;
```
O(log n), O(1). `start < end` converges to one element.

**Intuition — follow the slope:**
- If `nums[mid] < nums[mid+1]`: you're on an ascending slope. Any peak must be to the right (or at `mid+1` itself) — `start = mid + 1`.
- If `nums[mid] >= nums[mid+1]`: you're on a descending slope or at a flat. A peak is at `mid` or to its left — `end = mid` (keep `mid` in range).

**Why `end = mid` not `end = mid - 1`?** `mid` itself could be the peak. Excluding it with `mid - 1` could discard the answer.

**This problem doesn't require a globally sorted array** — binary search still works because the existence of a peak is guaranteed (any local maximum qualifies), and the slope always points toward one.

**Contrast with standard BS:** in standard BS you narrow based on value comparison with target. Here you narrow based on slope direction — a fundamentally different use of binary search.

---

## 14. Binary Search Mental Models

### The Two Core Patterns

**Pattern A — Exact Search:**
Find specific target. Return immediately on match.
```java
while (l <= r) {
    if (nums[mid] == target) return mid;
    else if (nums[mid] > target) r = mid - 1;
    else l = mid + 1;
}
return -1;
```

**Pattern B — Boundary Search (find first/last satisfying condition):**
Find leftmost or rightmost element satisfying a predicate. Never return immediately — save candidate and keep searching.
```java
int res = -1;   // or nums.length for lower bound
while (l <= r) {
    if (condition(nums[mid])) { res = mid; r = mid - 1; }  // leftmost: go left
    else l = mid + 1;
    // OR for rightmost: { res = mid; l = mid + 1; } else r = mid - 1;
}
return res;
```

**Everything in this section is one of these two patterns:**
| Problem | Pattern | Condition | Direction |
|---|---|---|---|
| Binary Search | A | `== target` | — |
| Lower Bound | B | `>= x` | leftmost |
| Upper Bound | B | `<= x` | rightmost |
| Floor | B | `<= x` | rightmost |
| Ceil | B | `>= x` | leftmost |
| First Occurrence | B | `== target` | leftmost |
| Last Occurrence | B | `== target` | rightmost |
| Insert Position | B | `>= target` | leftmost |

### The Convergence Pattern (`l < r`)

Used in: Peak Element, Single Element, Minimum in Rotated Array.

```java
while (l < r) { ... }
return nums[l];   // l == r at end
```

Loop terminates when `l == r` — both point to the answer. No separate `res` variable needed. Used when you're converging two pointers toward an answer, not searching for a specific value.

**When to use `l <= r` vs `l < r`:**
- `l <= r` with `res` variable — finding a boundary in a sorted array
- `l < r` with `return nums[l]` — converging to an answer (peak, minimum, single element)

### Binary Search on Answer Space

Not in your current files but critical for next sub-topics (BS on answers):

When the question is "find minimum/maximum X such that condition(X) is true" — binary search on the **value space** not the array index.

```java
int lo = min_possible, hi = max_possible;
while (lo < hi) {
    int mid = lo + (hi - lo) / 2;
    if (feasible(mid)) hi = mid;     // minimize: try smaller
    else lo = mid + 1;
}
return lo;
```

Examples: minimum days to make bouquets, koko eating bananas, capacity to ship packages.

### Pivot — The Core of Rotated Array Problems

Your pivot function is shared across SearchRotatedI, SearchRotatedII, MinRotSorted, NumOfRot. All four problems reduce to "find the pivot."

```java
// pivot = index of the largest element
while (start <= end) {
    int mid = ...;
    if (mid < end && nums[mid] > nums[mid+1]) return mid;       // found descent
    if (mid > start && nums[mid-1] > nums[mid]) return mid - 1; // mid is on right side
    if (nums[mid] > nums[start]) start = mid + 1;               // left half sorted, pivot is right
    else end = mid - 1;                                          // pivot is left
}
return -1;   // not rotated
```

---

## 15. Revision Checklist

- [ ] Binary search — `l + (r-l)/2`, `l <= r`, return -1 when absent, both iterative and recursive
- [ ] Know the difference between `l <= r` (boundary search) and `l < r` (convergence)
- [ ] Lower bound: `>= x`, go left after saving. Returns `n` if all < x
- [ ] Upper bound: `<= x`, go right after saving. Know Striver's definition vs C++ `upper_bound`
- [ ] Floor = upper bound (last ≤ x). Ceil = lower bound (first ≥ x). Same two patterns
- [ ] Insert position = lower bound, return `n` when `res == -1`
- [ ] First occurrence: `== target`, go left. Last occurrence: `== target`, go right
- [ ] Count occurrences: `lastOcc - firstOcc + 1`, guard with `firstOcc == -1`
- [ ] Rotated sorted I: single-pass one-pass approach — one half always sorted, range-check against it
- [ ] Rotated sorted II: duplicates force shrink `l++; r--` → O(n) worst case, unavoidable
- [ ] Minimum rotated: compare `nums[mid]` with `nums[r]`, use `l < r`
- [ ] Number of rotations: index of minimum = pivot + 1
- [ ] Single element: force mid to even, `start = mid + 2` on intact pair, `l < r`
- [ ] Peak element: follow the slope, `end = mid` not `mid - 1`, `l < r`
- [ ] Know the two BS templates cold: exact search vs boundary search
- [ ] Know binary search on answer space concept (for next sub-section)