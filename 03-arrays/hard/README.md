# Striver A2Z — Step 3: Arrays (Hard)
**Cheat Sheet | Java | 12 Problems · Brute → Better → Optimal**

---

## Table of Contents
1. [Pascal's Triangle](#1-pascals-triangle)
2. [Majority Element II (> n/3)](#2-majority-element-ii--n3)
3. [Three Sum](#3-three-sum)
4. [Four Sum](#4-four-sum)
5. [Largest Subarray with Sum 0](#5-largest-subarray-with-sum-0)
6. [Subarray XOR Equals K](#6-subarray-xor-equals-k)
7. [Merge Sorted Array (In-place)](#7-merge-sorted-array-in-place)
8. [Merge Intervals](#8-merge-intervals)
9. [Max Product Subarray](#9-max-product-subarray)
10. [Repeating and Missing Number](#10-repeating-and-missing-number)
11. [Count Inversions](#11-count-inversions)
12. [Count Reverse Pairs](#12-count-reverse-pairs)
13. [Patterns & Techniques Across All Problems](#13-patterns--techniques-across-all-problems)
14. [Revision Checklist](#14-revision-checklist)

---

## 1. Pascal's Triangle

Three sub-problems: generate entire triangle, get a specific row, get a specific element.

### Get element at row r, column c — nCr formula
```java
// C(n, k) = n! / (k! * (n-k)!)  computed iteratively
int findEle(int r, int c) {
    int res = 1, n = r - 1, k = c - 1;
    for (int i = 0; i < k; i++) {
        res *= (n - i);
        res /= (i + 1);   // divide at each step to prevent overflow
    }
    return res;
}
```
O(c), O(1). The `res /= (i+1)` at each step keeps intermediate values small. **Never compute full factorials** — they overflow for even moderate n.

### Get entire row n using C(n, k) rolling formula
```java
List<Integer> getNRow(int n) {
    int val = 1;
    row.add(val);               // C(n-1, 0) = 1 always
    for (int k = 1; k < n; k++) {
        val = val * (n - k) / k;  // C(n,k) = C(n,k-1) * (n-k+1) / k
        row.add(val);
    }
}
```
O(n), O(1). Each element derived from the previous using the recurrence `C(n,k) = C(n,k-1) * (n-k+1) / k`. Avoids recomputing nCr from scratch for each column.

**Why `val * (n-k) / k` works:** `C(r-1, k) = C(r-1, k-1) * (r-1-(k-1)) / k = C(r-1,k-1) * (r-k) / k`. In your code n is row number (1-indexed), k is column. The rolling multiplication avoids large intermediate values.

### Generate full triangle
```java
// Row i: initialize all to 1, then fill interior
for (int j = 1; j < i; j++)
    row.set(j, prev.get(j-1) + prev.get(j));
```
O(n²), O(n²) for output. Interior element = sum of two elements directly above it (the Pascal property).

**Three question types in interviews:**
1. Find element at (r, c) → nCr in O(r)
2. Get entire row n → rolling nCr in O(n)
3. Generate triangle up to row n → build row by row in O(n²)

---

## 2. Majority Element II (> n/3)

**Key observation:** at most **2** elements can appear more than n/3 times (since 3 × (n/3+1) > n).

**Approach 1 — Brute:** for each unique element count frequency. O(n²), O(n).

**Approach 2 — HashMap:** count all, filter > n/3. O(n), O(n).

**Approach 3 — Extended Boyer-Moore with 2 candidates:**
```java
int pos1 = -1, pos2 = -1, v1 = 0, v2 = 0;
// Phase 1: find candidates
for (int num : nums) {
    if (num == pos1)       v1++;
    else if (num == pos2)  v2++;
    else if (v1 == 0)      { pos1 = num; v1++; }
    else if (v2 == 0)      { pos2 = num; v2++; }
    else                   { v1--; v2--; }   // cancel one of each
}
// Phase 2: verify both candidates
v1 = 0; v2 = 0;
for (int num : nums) {
    if (num == pos1) v1++;
    else if (num == pos2) v2++;
}
if (v1 > n/3) res.add(pos1);
if (v2 > n/3) res.add(pos2);
```
O(n), O(1).

**Why two candidates?** Since > n/3 threshold allows up to 2 winners, you need two simultaneous "armies." The cancellation logic: whenever a non-candidate appears, one vote is cancelled from each candidate. The two majority elements' combined count > 2n/3, so they can't both be fully cancelled.

**Phase 2 is mandatory.** Phase 1 gives you candidates — not guaranteed winners. A candidate with 0 votes left still needs verification.

**The `else if` order matters:** check existing candidates before assigning new ones. If you assign `pos1 = num` before checking `num == pos1`, you'd always assign to `pos1` first.

---

## 3. Three Sum

**Problem:** find all unique triplets that sum to 0. No duplicate triplets in output.

**Approach 1 — Brute O(n³):** three loops, use Set to deduplicate by sorting each triplet before inserting.

**Approach 2 — HashMap O(n²):** fix `i`, for each `j` check if `-(nums[i]+nums[j])` is in a HashSet. Use outer Set for dedup.

**Approach 3 — Sort + Two Pointer (optimal):**
```java
Arrays.sort(nums);
for (int i = 0; i < n - 2; i++) {
    if (i > 0 && nums[i] == nums[i-1]) continue;  // skip duplicate i
    int l = i + 1, r = n - 1;
    while (l < r) {
        int sum = nums[i] + nums[l] + nums[r];
        if (sum == 0) {
            res.add(Arrays.asList(nums[i], nums[l], nums[r]));
            l++; r--;
            while (l < r && nums[l] == nums[l-1]) l++;  // skip dup l
            while (l < r && nums[r] == nums[r+1]) r--;  // skip dup r
        }
        else if (sum > 0) r--;
        else l++;
    }
}
```
O(n²), O(1) extra.

**Bug in your v3:** `while(i>0 && nums[i]==nums[i-1]) { continue; }` — this is an **infinite loop**. The `while` never advances `i`. Correct form is `if (i > 0 && nums[i] == nums[i-1]) continue;`. Watch this — it's a subtle and common mistake.

**Why skip duplicates after finding a triplet:** after `l++; r--`, if `nums[l] == nums[l-1]`, the triplet `(nums[i], nums[l], nums[r])` would be identical to the one just added. The inner while-loops skip these.

**Why `i > 0` in the outer skip?** `i == 0` is always valid — there's no previous `i` to compare to.

---

## 4. Four Sum

**Problem:** find all unique quadruplets summing to target.

**Approach 1 — Brute O(n⁴):** four nested loops + Set dedup.

**Approach 2 — HashMap O(n³):** fix i, j; for each k look up fourth in HashSet.

**Approach 3 — Sort + Two Pointer (optimal):**
```java
Arrays.sort(nums);
for (int i = 0; i < n - 3; i++) {
    if (i > 0 && nums[i] == nums[i-1]) continue;
    for (int j = i + 1; j < n - 2; j++) {
        if (j > i + 1 && nums[j] == nums[j-1]) continue;  // j > i+1, not j > 1
        int l = j + 1, r = n - 1;
        while (l < r) {
            long sum = (long)nums[i] + nums[j] + nums[l] + nums[r];
            if (sum == target) {
                res.add(...);
                l++; r--;
                while (l < r && nums[l] == nums[l-1]) l++;
                while (l < r && nums[r] == nums[r+1]) r--;
            }
            else if (sum > target) r--;
            else l++;
        }
    }
}
```
O(n³), O(1) extra.

**Critical: cast to `long` before summing.** Four ints can overflow int range easily. `(long)nums[i] + nums[j] + nums[l] + nums[r]` — cast the first operand, the rest auto-promote.

**Dedup condition for `j`: `j > i+1` not `j > 1`.** You only skip a duplicate `j` if it's not the first `j` for this particular `i`. `j > i+1` means "we've already processed at least one `j` for this `i`."

**Pattern:** ThreeSum is Two Sum + one outer loop. FourSum is ThreeSum + one outer loop. The dedup + two-pointer inner core scales to k-sum in O(nᵏ⁻¹) time.

---

## 5. Largest Subarray with Sum 0

**Approach 1 — Brute O(n²):** fix start, extend end, track when `curr == 0`.

**Approach 2 — Prefix Sum + HashMap (optimal):**
```java
HashMap<Long, Integer> seen = new HashMap<>();
seen.put(0L, -1);   // empty prefix has sum 0 at index -1
long curr = 0;
int res = 0;
for (int i = 0; i < n; i++) {
    curr += arr[i];
    if (seen.containsKey(curr))
        res = Math.max(res, i - seen.get(curr));
    else
        seen.put(curr, i);   // putIfAbsent effectively — store first occurrence
}
```
O(n), O(n).

**Core insight:** if `prefix[i] == prefix[j]` and `i < j`, then subarray `(i+1, j)` has sum 0 (the two prefix sums cancel). So finding two equal prefix sums gives a zero-sum subarray.

**Why `seen.put(0, -1)`?** If `prefix[i] == 0`, the entire subarray `[0, i]` has sum 0. Without the seed, you'd miss this — `seen.containsKey(0)` would return false and you'd `put(0, i)` instead of computing length `i - (-1) = i + 1`.

**Why `else` (putIfAbsent behavior)?** You want the earliest index where a prefix sum was seen to maximize subarray length. Overwriting with a later index would shrink the window.

**Relation to Subarray Sum K:** this is a special case where k=0. The same template works for any k by looking for `prefix[i] - k` instead of `prefix[i]`.

---

## 6. Subarray XOR Equals K

**Problem:** count subarrays whose XOR equals k.

**Approach 1 — Brute O(n²):** fix start, XOR as you extend, count when `XOR == k`.

**Approach 2 — Prefix XOR + HashMap (optimal):**
```java
HashMap<Long, Integer> freq = new HashMap<>();
long curr = 0;
int res = 0;
for (int num : nums) {
    curr ^= num;
    if (curr == k) res++;                           // subarray from index 0
    res += freq.getOrDefault(curr ^ k, 0);          // subarrays ending here
    freq.put(curr, freq.getOrDefault(curr, 0) + 1);
}
```
O(n), O(n).

**XOR analog of prefix sum:** if `prefix_xor[j] ^ prefix_xor[i] = k`, then subarray `(i+1, j)` has XOR = k. Rearranging: `prefix_xor[i] = prefix_xor[j] ^ k`. So look for `curr ^ k` in the map.

**Why `curr == k` check before map lookup?** This handles subarrays starting from index 0. Alternatively, seed the map with `freq.put(0, 1)` like the sum variant — then the `curr == k` check is subsumed. Both are correct; seeding is cleaner and consistent.

**XOR properties used:**
```
a ^ b = c  →  a ^ c = b  (rearrange XOR equations like addition)
a ^ a = 0               (self-cancel)
a ^ 0 = a               (identity)
```

**Why XOR can't use sliding window:** XOR is not monotone — XORing more elements doesn't consistently increase or decrease. Only prefix XOR + map works for the general case.

---

## 7. Merge Sorted Array (In-place)

**Problem:** merge `nums2` into `nums1` which has extra space at the end. `nums1` has m valid elements, `nums2` has n.

**Approach — Merge from the back (optimal):**
```java
int f1 = m - 1, f2 = n - 1, f = m + n - 1;
while (f1 >= 0 && f2 >= 0) {
    if (nums1[f1] >= nums2[f2]) nums1[f--] = nums1[f1--];
    else                        nums1[f--] = nums2[f2--];
}
while (f2 >= 0) nums1[f--] = nums2[f2--];
```
O(m+n), O(1).

**Why merge from back?** Merging forward would overwrite unprocessed elements in `nums1`. Merging backward uses the extra space at the tail — the largest elements go there first, and they've already been "used."

**Why no `while (f1 >= 0)` drain?** If `f2` is exhausted first, remaining `nums1` elements are already in place — no copy needed.

**Gap method (alternative O(1) approach):**
Used when the arrays are truly separate and you must merge into the first. Shell-sort inspired: compare elements gap apart, swap if out of order, reduce gap by ceil(gap/1.5) each round until gap = 1. This is the approach needed for the variant where `nums1` and `nums2` are separate arrays. Not implemented here but worth knowing.

---

## 8. Merge Intervals

**Problem:** given list of intervals, merge all overlapping ones.

**Approach 1 — Sort + expand window O(n²):** after sorting by start, for each interval expand until no overlap, then commit. Inner while loop can make this O(n²) in structure but O(n) in practice since each interval is visited once.

**Approach 2 — Sort + linear scan (optimal):**
```java
Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
LinkedList<int[]> merged = new LinkedList<>();
for (int[] interval : intervals) {
    if (merged.isEmpty() || merged.getLast()[1] < interval[0])
        merged.add(interval);                                       // no overlap
    else
        merged.getLast()[1] = Math.max(merged.getLast()[1], interval[1]);  // merge
}
```
O(n log n) for sort, O(n) scan. Total O(n log n).

**Overlap condition:** `merged.getLast()[1] >= interval[0]` — current interval's start ≤ last merged interval's end.

**Why `Math.max` when merging end?** An interval can be fully contained inside another (e.g., `[1,10]` and `[2,5]`). After sorting by start, `[2,5]` comes after `[1,10]`. Its end (5) < current end (10), so taking `max` correctly keeps 10.

**`LinkedList` for O(1) `getLast()` and `addLast()`:** `ArrayList` also works but `getLast()` on LinkedList is O(1) vs `get(size-1)` on ArrayList which is also O(1). Either is fine — the choice here is stylistic.

**Comparator:** `(a, b) -> Integer.compare(a[0], b[0])` sorts by interval start. Using `a[0] - b[0]` risks integer overflow if starts are large/negative — always use `Integer.compare`.

---

## 9. Max Product Subarray

**Problem:** find contiguous subarray with maximum product. Array may contain negatives and zeros.

**Approach 1 — Brute O(n²):** fix start, track running product.

**Approach 2 — Prefix/Suffix products (your v2):**
```java
int pre = 1, suff = 1, res = Integer.MIN_VALUE;
for (int i = 0; i < n; i++) {
    if (pre == 0) pre = 1;   // reset after zero
    if (suff == 0) suff = 1;
    pre  *= nums[i];
    suff *= nums[n - 1 - i];
    res = Math.max(res, Math.max(pre, suff));
}
```
O(n), O(1). The idea: the max product subarray either starts from the left or ends at the right (no zero in between). Prefix products scan left-to-right, suffix products scan right-to-left. Resetting on zero handles zero boundaries.

**Why this works for negatives:** an even count of negatives gives a positive product. If a prefix product is negative, the corresponding suffix product (from the other end) will have the matching negative cancelled. One of prefix or suffix will always cover the optimal subarray.

**Approach 3 — Track max and min simultaneously (Kadane's style):**
```java
int maxProd = nums[0], minProd = nums[0], res = nums[0];
for (int i = 1; i < n; i++) {
    if (nums[i] < 0) swap(maxProd, minProd);  // negative flips max/min
    maxProd = Math.max(maxProd * nums[i], nums[i]);
    minProd = Math.min(minProd * nums[i], nums[i]);
    res = Math.max(res, maxProd);
}
```
O(n), O(1).

**Why track `minProd`?** A negative number multiplied by a very negative number gives a large positive. `minProd` carries the most negative running product — when multiplied by a negative element, it becomes the new maximum.

**The swap trick:** when `nums[i] < 0`, the roles of max and min flip before multiplying. Swap first, then multiply — cleaner than computing both from old values and taking max/min of three.

**Zero handling:** `Math.max(maxProd * curr, curr)` — if `maxProd` was carried through a 0, `maxProd * curr = 0`, and `curr` alone is taken (starting fresh). The reset is implicit.

---

## 10. Repeating and Missing Number

**Problem:** array of [1..n], one number appears twice (repeating), one is absent (missing).

**Approach 1 — Brute O(n²):** for each i in [1..n], count occurrences.

**Approach 2 — Frequency array O(n), O(n):** `hash[num-1]++`, scan for 0 (missing) and 2 (repeating).

**Approach 3 — Math (sum + sum of squares):**
```
Let x = repeating, y = missing
S  = sum(array) - n(n+1)/2       → S  = x - y
S2 = sumSquares(array) - n(n+1)(2n+1)/6  → S2 = x² - y²
                                           → S2 = (x+y)(x-y)
x + y = S2 / S
→ x = (S + S2/S) / 2
→ y = x - S
```
O(n), O(1). Use `long` for all calculations — sum of squares overflows int for n > ~1000.

**Approach 4 — Cyclic sort O(n), O(1):**
Place each element at index `arr[i]-1`. After sorting, scan for `arr[i] != i+1` — that index+1 is missing, `arr[i]` is repeating.

**Approach 5 — XOR (most space-efficient, most conceptually involved):**
```java
// XOR all array elements and all of [1..n] → result = x ^ y
int xor = 0;
for (int i = 0; i < n; i++) { xor ^= nums[i]; xor ^= (i+1); }

// Find a set bit in xor (x and y differ here)
int setBit = xor & -xor;   // isolates lowest set bit

// Partition into two groups, XOR each group separately
int gx = 0, gy = 0;
for (...) {
    if ((nums[i] & setBit) == 0) gx ^= nums[i]; else gy ^= nums[i];
    if (((i+1) & setBit) == 0)  gx ^= (i+1);   else gy ^= (i+1);
}
// gx and gy are x and y in some order — verify which is repeating
```

**`xor & -xor` isolates the lowest set bit.** In two's complement, `-xor = ~xor + 1`. The lowest set bit is the only position where `xor` and its complement agree (both 1). This bit differs between x and y, so it separates them into two groups.

**Which to use in interviews:** v3 (math) is clean and O(n)/O(1). Know v5 (XOR) conceptually — interviewers often ask "can you do it without arithmetic?"

---

## 11. Count Inversions

**Problem:** count pairs (i, j) where `i < j` but `arr[i] > arr[j]`.

**Approach 1 — Brute O(n²):** check every pair.

**Approach 2 — Merge Sort (optimal O(n log n)):**

Count inversions during the merge step. When an element from the **right half** is placed before elements remaining in the **left half**, those remaining left elements all form inversions with it.

```java
// During merge, when right[f2] < left[f1]:
res += (n1 - f1);   // all remaining left elements form inversions with right[f2]
```

**Why this works:** both halves are sorted before merging. If `left[f1] > right[f2]`, then ALL elements `left[f1], left[f1+1], ..., left[n1-1]` are > `right[f2]` (since left is sorted). So `(n1 - f1)` inversions are added in one shot.

**Critical:** count BEFORE merge (or equivalently, count during merge but before placing the element). The count step and merge step in your code are separated — `count()` is called before `merge()` in `CountInversions`, but both halves are still sorted when `count` runs... actually in your code you call `res += count(nums, l, m, r)` and then `merge(nums, l, m, r)` separately. Alternatively (as in CountInversions.v2), counting is done inline during the merge itself via `res += (n1 - f1)` when `a[f1] > b[f2]`.

**The two implementations in your files:**
- `CountInversions` — counts inline during merge (`res += n1-f1`)
- `CountReversePairs` — counts in a separate `count()` pass before merging (because the condition `nums[i] > 2*nums[j]` is different from the merge condition)

---

## 12. Count Reverse Pairs

**Problem:** count pairs (i, j) where `i < j` and `nums[i] > 2 * nums[j]`.

**Approach 1 — Brute O(n²):** check every pair, use `2L*nums[j]` to avoid overflow.

**Approach 2 — Modified Merge Sort O(n log n):**

**Separation of counting and merging is mandatory here.**

The condition `nums[i] > 2 * nums[j]` is NOT the same as the merge condition `nums[i] > nums[j]`. If you merge first, you lose the original half boundaries needed for counting.

```java
private static int count(int[] nums, int l, int m, int r) {
    int c = 0, j = m + 1;
    for (int i = l; i <= m; i++) {
        while (j <= r && nums[i] > 2L * nums[j]) j++;
        c += (j - (m + 1));   // all j from m+1 to current j satisfy the condition
    }
    return c;
}
```

**Why `j` doesn't reset for each `i`?** Both halves are sorted. As `i` increases, `nums[i]` increases, so the valid `j` range only grows — `j` never needs to go back. This makes the count step O(n) total (two pointer pattern within sorted halves).

**Why `2L * nums[j]`?** `2 * nums[j]` where `nums[j]` is `int` overflows if `nums[j]` is near `Integer.MAX_VALUE / 2`. Cast to `long` first.

**Contrast with Count Inversions:**
| | Count Inversions | Count Reverse Pairs |
|---|---|---|
| Condition | `arr[i] > arr[j]` | `arr[i] > 2 * arr[j]` |
| Count during merge? | Yes — inline | No — separate step before merge |
| Why? | Same condition as merge order | Different condition — merge would corrupt halves |

---

## 13. Patterns & Techniques Across All Problems

### Merge Sort as a Counting Tool
Covers: Count Inversions, Count Reverse Pairs.

Anytime you need to count pairs across sorted halves, merge sort's divide step provides the structure. Key: both halves are sorted when you count → two pointer within halves gives O(n) count per level.

**Template:**
```
mergeSort(l, r):
    mergeSort(l, m)
    mergeSort(m+1, r)
    count(l, m, r)    ← counting step uses sorted halves
    merge(l, m, r)    ← merging happens after counting if conditions differ
```

### Prefix XOR + HashMap
Covers: Subarray XOR = K, analogous to Prefix Sum + HashMap.

The pattern: `prefix[j] OP prefix[i] = target` → `prefix[i] = prefix[j] OP target`. For sum OP is `-`, for XOR OP is `^` (self-inverse).

### Prefix Sum for Zero-Sum Subarrays
Covers: Largest Subarray Sum 0, Subarray Sum K (medium).

Equal prefix sums → zero-sum between them. Always seed `(0, -1)` or `(0, 1)` depending on whether you want max length or count.

### Sort + Two Pointer for K-Sum
Covers: Three Sum, Four Sum. Scales to K-Sum with K-2 outer loops + two pointer inner.

Dedup pattern:
- Outer loop: `if (i > 0 && nums[i] == nums[i-1]) continue`
- After finding a match: advance both pointers, then skip while equal to previous

### Boyer-Moore Extended
Covers: Majority Element II. Two candidates, two counters. Always verify in a second pass.

### Math Equations for Find-Missing/Repeating
Covers: Repeating and Missing. Set up system of equations using sum and sum-of-squares, solve for two unknowns.

### Backward Merge for In-place Problems
Covers: Merge Sorted Array. When the destination array has extra space at the tail, merge from back to front to avoid overwriting unprocessed data.

### nCr Rolling Computation
Covers: Pascal's Triangle. `C(n,k) = C(n,k-1) * (n-k+1) / k`. Always divide after each multiply to control intermediate size.

---

## 14. Revision Checklist

- [ ] Pascal's Triangle — nCr rolling formula, `val * (n-k) / k` pattern, three sub-problems
- [ ] Majority Element II — two candidates, why phase 2 verification is mandatory, order of `if/else if` matters
- [ ] Three Sum — sort + two pointer, skip dups at i (`i>0`) and after match, bug: `while` vs `if` for outer skip
- [ ] Four Sum — cast sum to `long`, `j > i+1` (not `j > 1`) for inner dedup, k-sum pattern
- [ ] Largest Subarray Sum 0 — prefix sum, `seen.put(0, -1)` seed, `else` to keep first occurrence
- [ ] Subarray XOR = K — `curr ^ k` lookup, XOR rearrangement rule, no sliding window possible
- [ ] Merge Sorted Array — merge from back, no need to drain `f1` leftover
- [ ] Merge Intervals — sort by start, `Math.max` for end on overlap, `Integer.compare` not subtraction
- [ ] Max Product Subarray — track both `maxProd` and `minProd`, swap on negative, reset implicit via `max(prod, curr)`
- [ ] Repeating and Missing — math approach with sum and sum-of-squares using `long`, XOR + lowest-set-bit partition
- [ ] Count Inversions — `res += (n1 - f1)` when right element wins, why it counts all remaining left elements
- [ ] Count Reverse Pairs — count BEFORE merge (not during), `j` pointer never resets across `i` iterations, `2L` cast
- [ ] Know when to count inline vs separately in merge sort problems
- [ ] Know the prefix XOR analogy to prefix sum — `curr ^ k` mirrors `curr - k`
- [ ] Know `n & -n` trick to isolate lowest set bit