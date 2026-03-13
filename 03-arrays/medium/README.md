# Striver A2Z — Step 3: Arrays (Medium)
**Cheat Sheet | Java | 13 Problems · Brute → Better → Optimal**

---

## Table of Contents
1. [Two Sum](#1-two-sum)
2. [Sort Colors (Dutch National Flag)](#2-sort-colors-dutch-national-flag)
3. [Majority Element (Moore's Voting)](#3-majority-element-moores-voting)
4. [Maximum Subarray (Kadane's)](#4-maximum-subarray-kadanes)
5. [Stock Buy and Sell](#5-stock-buy-and-sell)
6. [Rearrange Array by Sign](#6-rearrange-array-by-sign)
7. [Next Permutation](#7-next-permutation)
8. [Leaders in Array](#8-leaders-in-array)
9. [Longest Consecutive Sequence](#9-longest-consecutive-sequence)
10. [Set Matrix Zeroes](#10-set-matrix-zeroes)
11. [Rotate Matrix 90°](#11-rotate-matrix-90)
12. [Spiral Matrix Traversal](#12-spiral-matrix-traversal)
13. [Subarray Sum Equals K](#13-subarray-sum-equals-k)
14. [Patterns & Techniques Across All Problems](#14-patterns--techniques-across-all-problems)
15. [Revision Checklist](#15-revision-checklist)

---

## 1. Two Sum

**Problem:** find indices of two numbers that add to target.

**Approach 1 — Brute:** nested loop, check every pair. O(n²), O(1).

**Approach 2 — HashMap (optimal for indices):**
```java
HashMap<Integer, Integer> vis = new HashMap<>();
for (int i = 0; i < n; i++) {
    if (vis.containsKey(target - nums[i]))
        return new int[]{ vis.get(target - nums[i]), i };
    vis.put(nums[i], i);
}
```
O(n), O(n). Store value → index. For each element, check if its complement already exists.

**Why put after check?** If you put before checking, `target = 6`, `nums[i] = 3` → you'd find itself as the pair. Checking first guarantees two distinct elements.

**Approach 3 — Sort + Two Pointer:**
```java
// preserve original indices by storing [value, originalIndex] pairs
Arrays.sort(arr, (a, b) -> Integer.compare(a[0], b[0]));
int left = 0, right = n - 1;
while (left < right) {
    int sum = arr[left][0] + arr[right][0];
    if (sum == target) return new int[]{ arr[left][1], arr[right][1] };
    else if (sum < target) left++;
    else right--;
}
```
O(n log n), O(n) for the paired array. Sort destroys original indices so you must carry them.

**When to use which:**
- Return indices → HashMap (v2), indices not preserved after sort
- Return values only (or "does pair exist") → two pointer after sort, O(1) space
- Multiple pairs → two pointer on sorted array is cleaner

---

## 2. Sort Colors (Dutch National Flag)

**Problem:** sort array of 0s, 1s, 2s in-place without using sort.

**Approach 1 — Library sort:** `Arrays.sort()`. O(n log n). Defeats the purpose.

**Approach 2 — Count frequencies:** count 0s, 1s, 2s, overwrite array. O(n), O(1). Two passes.

**Approach 3 — Dutch National Flag (optimal):**
```java
int lo = 0, mid = 0, hi = n - 1;
while (mid <= hi) {
    if (nums[mid] == 0)      { swap(lo++, mid++); }
    else if (nums[mid] == 1) { mid++; }
    else                     { swap(mid, hi--); }  // mid NOT incremented
}
```
O(n), O(1). Single pass.

**Three-region invariant:**
- `[0, lo)` → all 0s (confirmed)
- `[lo, mid)` → all 1s (confirmed)
- `[mid, hi]` → unknown (being processed)
- `(hi, n-1]` → all 2s (confirmed)

**Why `mid` doesn't increment when swapping with `hi`?** The element swapped from `hi` is unknown — it hasn't been examined yet. You must re-examine `nums[mid]` after the swap. When swapping with `lo`, `nums[lo]` is known to be 1 (since `lo` is behind `mid`), so you can safely advance `mid`.

**Key insight:** this is a 3-way partition — same logic used in 3-way quicksort for arrays with many duplicates.

---

## 3. Majority Element (Moore's Voting)

**Problem:** element appearing more than n/2 times. Guaranteed to exist.

**Approach 1 — Brute:** count each element. O(n²), O(1).

**Approach 2 — HashMap:** frequency map, return key with freq > n/2. O(n), O(n).

**Approach 3 — Moore's Voting Algorithm (optimal):**
```java
int res = 0, votes = 0;
for (int num : nums) {
    if (votes == 0) res = num;       // pick new candidate
    votes += (res == num) ? 1 : -1;
}
return res;
```
O(n), O(1).

**How it works:** treat the majority element as having a "army" larger than all others combined. Every non-majority element cancels one majority element. Since majority > n/2, the majority element's army always survives to the end. When `votes` reaches 0, all previous elements cancelled out — start fresh.

**Critical constraint:** only works when a majority element is **guaranteed to exist**. If no guarantee, you must verify the candidate with a second pass: count `res` in the array, check if count > n/2.

**Missed — Majority Element II (appears > n/3 times):** at most 2 such elements can exist. Use Boyer-Moore with two candidates and two counters. Same cancellation logic, just two simultaneous candidates.

---

## 4. Maximum Subarray (Kadane's)

**Problem:** find contiguous subarray with maximum sum.

**Approach 1 — Brute O(n³):** three nested loops, sum every subarray.

**Approach 2 — Better O(n²):** fix start `i`, extend end `j`, track running sum.

**Approach 3 — Kadane's Algorithm (optimal):**
```java
int curr = nums[0], max = nums[0];
for (int i = 1; i < n; i++) {
    curr = Math.max(curr + nums[i], nums[i]);
    max = Math.max(max, curr);
}
```
O(n), O(1).

**Decision at each step:** `max(curr + nums[i], nums[i])`. This asks: "is it better to extend the current subarray, or start fresh from here?" Starting fresh (`nums[i]` alone) is better when `curr` is negative — a negative prefix always drags down any future sum.

**Equivalent form:**
```java
if (curr < 0) curr = 0;  // reset when negative
curr += nums[i];
max = Math.max(max, curr);
```
Both are identical. The `Math.max` form is cleaner for understanding.

**Initialize from `nums[0]`, not 0 or `Integer.MIN_VALUE`** — the subarray must be non-empty. If you init `max = 0`, an all-negative array returns 0 (wrong — should return the least negative element).

**Extended version — track indices:**
```java
if (curr + nums[k] > nums[k]) curr += nums[k];
else { curr = nums[k]; l = k; }    // new start
if (curr > max) { max = curr; i = l; j = k; }  // update best window
```
Tracks `l` (current window start) and updates `[i, j]` whenever a new max is found.

**Missed — Maximum Product Subarray:** similar idea but track both `maxProd` and `minProd` at each step (negatives flip sign so min can become max). `currMax = max(num, currMax*num, currMin*num)`.

---

## 5. Stock Buy and Sell

**Problem:** buy on one day, sell on a later day. Maximize profit. One transaction only.

**Approach 1 — Brute:** try every (buy, sell) pair. O(n²), O(1).

**Approach 2 — Track running minimum (optimal):**
```java
int maxProfit = 0, minPrice = prices[0];
for (int i = 1; i < n; i++) {
    minPrice = Math.min(minPrice, prices[i]);
    maxProfit = Math.max(maxProfit, prices[i] - minPrice);
}
```
O(n), O(1). At each day, compute profit if you had bought at the cheapest price seen so far.

**Key insight:** you don't need to track which day was the min — just the value. The constraint "buy before sell" is automatically satisfied because `minPrice` only uses prices from `[0, i-1]`.

**Missed — Multiple transactions allowed (Buy & Sell II):** buy and sell as many times as you want. Optimal: sum all upward slopes — `if (prices[i] > prices[i-1]) profit += prices[i] - prices[i-1]`. This is equivalent to buying at every valley and selling at every peak.

**Missed — At most K transactions:** DP problem. `dp[k][i]` = max profit using at most k transactions up to day i.

---

## 6. Rearrange Array by Sign

**Problem:** equal positives and negatives, rearrange so positive at even indices, negative at odd. Maintain relative order.

**Approach — Two write pointers:**
```java
int posIdx = 0, negIdx = 1;
int[] res = new int[n];
for (int num : nums) {
    if (num > 0) { res[posIdx] = num; posIdx += 2; }
    else         { res[negIdx] = num; negIdx += 2; }
}
```
O(n), O(n). Positives fill indices 0, 2, 4... Negatives fill indices 1, 3, 5...

**Key insight:** even/odd index separation via `+= 2` jump. Clean because equal count is guaranteed.

**Missed — Unequal counts of positives and negatives:** the two-pointer approach breaks when counts differ. Collect positives and negatives separately, merge alternately up to the shorter count, then append the leftover elements. O(n), O(n).

---

## 7. Next Permutation

**Problem:** rearrange array to the lexicographically next greater permutation. If none exists (fully descending), wrap to smallest (ascending).

**Algorithm (3 steps):**

**Step 1 — Find the "dip":** scan right to left, find the first index `k` where `nums[k] < nums[k+1]`. Everything to the right of `k` is descending.
```java
int k = n - 2;
while (k >= 0 && nums[k] >= nums[k+1]) k--;
```

**Step 2 — Find the swap partner:** scan right to left from end, find the first element `nums[l]` strictly greater than `nums[k]`.
```java
for (int i = n - 1; i > k; i--)
    if (nums[i] > nums[k]) { l = i; break; }
```

**Step 3 — Swap and reverse suffix:**
```java
swap(nums, k, l);
reverse(nums, k + 1, n - 1);
```

**If no dip found (`k == -1`):** the entire array is the last permutation → reverse whole array to get first.

**Why does reversing the suffix work?** After the swap, the suffix `[k+1, n-1]` is still in descending order (we swapped with the smallest element larger than `nums[k]`, which preserves the descending property). Reversing a descending sequence gives the smallest ascending sequence — exactly what you need for "next" permutation.

**Example walkthrough:** `[1, 4, 5, 3, 2, 1]`
- Dip at k=1 (`nums[1]=4 < nums[2]=5`)  
- Swap partner: rightmost element > 4 in suffix → `nums[4]=5`... wait, scanning right: 1,2,3,5 → first > 4 from right is 5 at index 2... actually `nums[5]=1, nums[4]=2, nums[3]=3, nums[2]=5` → `l=2`  
- Swap: `[1,5,4,3,2,1]` → reverse suffix from index 2: `[1,5,1,2,3,4]`  
- Result: `[1,5,1,2,3,4]` ✓

---

## 8. Leaders in Array

**Problem:** element is a leader if it is greater than all elements to its right. Rightmost is always a leader.

**Approach 1 — Brute:** for each element, scan all elements to its right. O(n²), O(1).

**Approach 2 — Right to left scan (optimal):**
```java
int curr = nums[n - 1];
res.add(curr);                           // rightmost always a leader
for (int i = n - 2; i >= 0; i--) {
    if (nums[i] > curr) {
        res.add(nums[i]);
        curr = nums[i];
    }
}
Collections.reverse(res);               // restore left-to-right order
```
O(n), O(1) extra. Track the running maximum from the right. An element is a leader iff it's greater than this running max.

**Key insight:** scan right-to-left because a leader needs to beat everything to its right — the right-side maximum is exactly what you need to compare against. `Collections.reverse` at the end restores order for the output.

---

## 9. Longest Consecutive Sequence

**Problem:** find the length of the longest sequence of consecutive integers (e.g., [1,2,3,4]) in an unsorted array. O(n) required.

**Approach 1 — Brute O(n²) or O(n³):** for each element, linearly search for `num+1`, `num+2`... Multiply by HashSet → O(n²).

**Approach 2 — Sort:** O(n log n). Sort, walk forward tracking consecutive runs. Handle duplicates by skipping `nums[i] == last`.
```java
if (nums[i] - 1 == last) { curr++; last = nums[i]; }
else if (nums[i] != last) { curr = 1; last = nums[i]; }  // reset, skip dups
```

**Approach 3 — HashSet (optimal):**
```java
HashSet<Integer> vis = new HashSet<>(Arrays.asList(nums));
int res = 0;
for (int num : nums) {
    if (vis.contains(num - 1)) continue;  // not sequence start, skip
    int curr = 1, next = num + 1;
    while (vis.contains(next)) { next++; curr++; }
    res = Math.max(res, curr);
}
```
O(n), O(n). The `vis.contains(num - 1)` check ensures you only start counting from the **beginning** of a sequence. Without this, you'd recount every element in a sequence from every starting point → O(n²) in the worst case.

**Key insight — the skip condition is the optimization:** each element is visited at most twice (once in outer loop, once in inner while). Total work = O(n).

---

## 10. Set Matrix Zeroes

**Problem:** if a cell is 0, set its entire row and column to 0. In-place.

**Approach 1 — Brute O(n³):** for each 0, mark its row and column using a sentinel value, then replace sentinels with 0. O(n×m) extra if using copy.

**Approach 2 — Boolean arrays O(n²), O(n+m):** first pass records which rows/cols have a zero. Second pass zeroes them out.

**Approach 3 — Use first row and column as markers (optimal O(n²), O(1)):**
```java
// 1. Check if first row/col themselves have a zero (save in booleans)
boolean firstRow = ..., firstCol = ...;

// 2. Use matrix[0][j] and matrix[i][0] as markers for col j and row i
for (int i = 1; i < m; i++)
    for (int j = 1; j < n; j++)
        if (matrix[i][j] == 0) { matrix[0][j] = 0; matrix[i][0] = 0; }

// 3. Zero out interior cells based on markers
for (int i = 1; i < m; i++)
    for (int j = 1; j < n; j++)
        if (matrix[i][0] == 0 || matrix[0][j] == 0) matrix[i][j] = 0;

// 4. Handle first row and col using saved booleans
if (firstRow) zero out row 0;
if (firstCol) zero out col 0;
```

**Why save first row/col state separately?** The first row is used as a marker for columns, and the first column is used as a marker for rows. If either originally contained a zero, you'd corrupt the markers before using them. The two boolean flags handle this edge case.

**Why process interior first, first row/col last?** If you zero out row 0 first, you'd corrupt markers for columns before using them in step 3.

---

## 11. Rotate Matrix 90°

**Problem:** rotate N×N matrix 90° clockwise in-place.

**Approach 1 — Extra matrix:** map `(i,j) → (j, n-1-i)` into a new matrix, copy back. O(n²), O(n²).

**Approach 2 — Transpose + Reverse rows (optimal O(n²), O(1)):**
```java
// Step 1: Transpose — swap (i,j) with (j,i) for i < j only
for (int i = 0; i < n; i++)
    for (int j = 0; j < n; j++)
        if (i <= j) continue;   // only lower triangle
        else swap(matrix[i][j], matrix[j][i]);

// Step 2: Reverse each row
for (int i = 0; i < n; i++) reverse(matrix[i]);
```

**All rotation mappings (memorize):**
| Rotation | Direct mapping | In-place steps |
|---|---|---|
| 90° CW | `(i,j) → (j, n-1-i)` | Transpose → reverse each row |
| 180° | `(i,j) → (n-1-i, n-1-j)` | Reverse each row → reverse each col (or reverse all rows then transpose) |
| 270° CW (90° CCW) | `(i,j) → (n-1-j, i)` | Transpose → reverse each col |

**Why transpose + reverse works for 90°:** transpose maps `(i,j) → (j,i)`. Then reversing each row maps `(j,i) → (j, n-1-i)`. Composed: `(i,j) → (j, n-1-i)` which is exactly 90° CW.

**Transpose only swaps the lower triangle** (`i < j` condition, or equivalently `i > j`). Swapping both `(i,j)↔(j,i)` and `(j,i)↔(i,j)` would swap twice and undo the operation.

---

## 12. Spiral Matrix Traversal

**Problem:** return all elements of a matrix in spiral (clockwise) order.

**Approach 1 — Direction array + visited matrix:**
```java
int[] dr = {0, 1, 0, -1};   // Right, Down, Left, Up
int[] dc = {1, 0, -1, 0};
// move in current direction; if blocked (boundary or visited), turn right: di = (di+1)%4
```
O(n×m), O(n×m) for visited array.

**Approach 2 — Four boundary pointers (optimal):**
```java
int left=0, right=n-1, top=0, bottom=m-1;
while (left <= right && top <= bottom) {
    // → traverse top row left to right, then top++
    // ↓ traverse right col top to bottom, then right--
    // ← traverse bottom row right to left, then bottom--
    // ↑ traverse left col bottom to top, then left++
    // Break early if boundaries cross mid-traversal
}
```
O(n×m), O(1) extra. Each element visited exactly once.

**The boundary-cross early break is critical.** After traversing the top row and incrementing `top`, check `top > bottom` before traversing the right column — for matrices with a single row or column, you'd double-count otherwise.

```java
top++;
if (top > bottom) break;   // single row case
right--;
if (left > right) break;   // single column case
// ... etc
```

**Traversal direction order:** Right → Down → Left → Up → repeat. In the direction array approach, index 0=Right, 1=Down, 2=Left, 3=Up, and `(di+1)%4` cycles through them.

---

## 13. Subarray Sum Equals K

**Problem:** count the number of subarrays whose sum equals k. Array may contain negatives.

**Approach 1 — Brute O(n²):** fix start, extend end, count when sum == k.

**Approach 2 — Prefix Sum + HashMap (optimal):**
```java
HashMap<Integer, Integer> freq = new HashMap<>();
freq.put(0, 1);    // empty prefix sum = 0, seen once
int curr = 0, res = 0;
for (int num : nums) {
    curr += num;
    if (freq.containsKey(curr - k)) res += freq.get(curr - k);
    freq.put(curr, freq.getOrDefault(curr, 0) + 1);
}
```
O(n), O(n).

**Core idea:** if `prefix[j] - prefix[i] = k`, then subarray `(i+1, j)` has sum k. For each `j`, count how many previous prefix sums equal `prefix[j] - k`.

**Difference from Longest Subarray Sum K:**
| | Longest Subarray | Count Subarrays |
|---|---|---|
| Goal | max length | count |
| Map stores | first index of prefix sum | frequency of prefix sum |
| Init | `(0, -1)` | `(0, 1)` |
| Update | `putIfAbsent` | `getOrDefault + 1` always |

**Why `freq.put(0, 1)` and not `(0, -1)`?** Here you count occurrences, not track index. A prefix sum of 0 existing once means there is one way a subarray from index 0 sums to k.

**Why put after checking?** Prevents using the same element as both start and end (counting a zero-length subarray). It also ensures you count subarrays ending at current index using only previous prefix sums.

**This does NOT work with sliding window** — negatives make the window non-monotone. Prefix sum + hash is the only O(n) solution for the general case.

---

## 14. Patterns & Techniques Across All Problems

### Prefix Sum + HashMap (the universal "subarray" pattern)
Covers: Longest Subarray Sum K (easy), Subarray Sum Equals K (medium), and many more.

The template is always:
```java
map.put(0, seed);   // seed = -1 for max length, 1 for count
long curr = 0;
for (int i = 0; i < n; i++) {
    curr += arr[i];
    // use map.get(curr - k)
    map.put(curr, ...);   // putIfAbsent for length, getOrDefault for count
}
```

### Boyer-Moore Voting
Covers: Majority Element (> n/2). Generalizes to > n/3 with two candidates.
Invariant: majority's votes can never fully cancel.

### Kadane's Algorithm
Covers: Maximum Subarray. The `max(curr + x, x)` decision = "extend or restart."
Always init from `nums[0]` for non-empty subarray constraint.

### Dutch National Flag (3-way partition)
Covers: Sort Colors. Generalizes to 3-way quicksort.
Invariant: three regions, `mid` pointer processes unknowns, `hi` swap does not advance `mid`.

### HashSet for O(1) Existence Check
Covers: Longest Consecutive Sequence. The key optimization is the **sequence-start filter** — only expand from elements with no left neighbor.

### In-place Matrix Tricks
- **Set Matrix Zeroes:** first row/col as marker array → separate boolean flags for row 0 / col 0
- **Rotate Matrix:** transpose + reverse. Know all 4 rotation mappings.
- **Spiral:** four shrinking boundaries with early-break guards.

### Observation-Based Algorithms
- **Next Permutation:** find rightmost dip → swap with smallest element to its right that is larger → reverse suffix
- **Leaders:** right-to-left running maximum
- **Stock:** running minimum from left, compute profit at each step

### Two Pointer (on sorted or structured arrays)
Covers: Two Sum (sort + two pointer variant), Sort Colors (DNF), Rearrange by Sign (two write pointers).

---

## 15. Revision Checklist

- [ ] Two Sum — HashMap: check before put, why order matters; two-pointer: carry original indices through sort
- [ ] Sort Colors — DNF three pointers, why `mid` doesn't advance on swap with `hi`
- [ ] Majority Element — Moore's voting, candidate resets when `votes == 0`, must verify if existence not guaranteed
- [ ] Kadane's — init from `nums[0]`, the reset-when-negative equivalence, extended version with indices
- [ ] Stock Buy & Sell — running min, why "buy before sell" is implicit; multiple-transaction variant
- [ ] Rearrange by Sign — `+= 2` jump trick, what breaks when counts are unequal
- [ ] Next Permutation — 3 steps: find dip, find swap partner from right, swap + reverse suffix
- [ ] Leaders — right-to-left running max + `Collections.reverse` at end
- [ ] Longest Consecutive — HashSet, the `contains(num-1)` skip is the O(n) optimization
- [ ] Set Matrix Zeroes — use first row/col as markers, why first row/col handled last
- [ ] Rotate Matrix — transpose only swaps lower triangle (`i > j`); know all 4 rotation mappings
- [ ] Spiral Matrix — four boundary pointers, early break after each boundary move
- [ ] Subarray Sum K — `freq.put(0, 1)` init, put after check, difference from longest subarray version
- [ ] Know when prefix sum + map applies vs sliding window (negatives → always prefix sum)
- [ ] Know why Kadane's fails for max product (need both min and max tracked)