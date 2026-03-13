# Striver A2Z — Step 3: Arrays (Easy)
**Cheat Sheet | Java | 11 Problems · Brute → Better → Optimal**

---

## Table of Contents
1. [Largest Element](#1-largest-element)
2. [Second Largest & Second Smallest](#2-second-largest--second-smallest)
3. [Check if Array is Sorted (Rotated)](#3-check-if-array-is-sorted-rotated)
4. [Remove Duplicates from Sorted Array](#4-remove-duplicates-from-sorted-array)
5. [Left Rotate Array by K](#5-left-rotate-array-by-k)
6. [Move Zeros to End](#6-move-zeros-to-end)
7. [Union of Two Sorted Arrays](#7-union-of-two-sorted-arrays)
8. [Missing Number in [1..N]](#8-missing-number-in-1n)
9. [Max Consecutive Ones](#9-max-consecutive-ones)
10. [Single Number (Element Appearing Once)](#10-single-number-element-appearing-once)
11. [Longest Subarray with Sum K](#11-longest-subarray-with-sum-k)
12. [Patterns & Techniques Across All Problems](#12-patterns--techniques-across-all-problems)
13. [Revision Checklist](#13-revision-checklist)

---

## 1. Largest Element

**Approach 1 — Sort:** `Arrays.sort(nums)` → return `nums[n-1]`. O(n log n).

**Approach 2 — Single Pass (optimal):**
```java
int maxi = nums[0];
for (int i = 1; i < nums.length; i++)
    maxi = Math.max(maxi, nums[i]);
```
O(n), O(1). Always initialize `maxi` from `nums[0]`, never from `0` or `Integer.MIN_VALUE` unless you know input can have negatives and you want to handle edge cases — here `nums[0]` is safe and direct.

**Edge case:** array of length 1 → return `nums[0]` directly.

---

## 2. Second Largest & Second Smallest

**Approach 1 — Sort:** `nums[1]` = second smallest, `nums[n-2]` = second largest. O(n log n). Fails if all elements are equal.

**Approach 2 — Two passes:** First pass finds `maxi` and `mini`. Second pass finds `maxi2` (largest value ≠ maxi) and `mini2` (smallest value ≠ mini). O(2n).

**Approach 3 — Single pass (optimal):**
```java
if (num > maxi)  { maxi2 = maxi; maxi = num; }
else if (num > maxi2 && num != maxi) { maxi2 = num; }

if (num < mini)  { mini2 = mini; mini = num; }
else if (num < mini2 && num != mini) { mini2 = num; }
```
O(n), O(1). The `num != maxi` guard handles **duplicates** — without it, `[1,1,2]` would set `maxi2 = maxi = 1` wrongly.

**Key Insight:** `Integer.MIN_VALUE` as initial `maxi2` and `Integer.MAX_VALUE` as initial `mini2` — these are safe sentinels for any valid integer input. Using `-1` would fail for negative arrays.

**Edge case:** if `maxi2` is still `Integer.MIN_VALUE` after the loop, no second largest exists (all elements equal).

---

## 3. Check if Array is Sorted (Rotated)

**Problem:** array may be a rotated version of a sorted array (e.g., `[4,5,1,2,3]`). Return true if sorted or sorted+rotated.

**Approach 1 — Brute:** try all n rotation starting points, check if that rotation is sorted. O(n²).

**Approach 2 — Find drop point:**
1. Find index of the single descent (where `nums[i] < nums[i-1]`)
2. If no descent → already sorted → return true
3. If `nums[n-1] > nums[0]` → two parts don't connect cleanly → return false
4. Verify both the prefix [0..flag-1] and suffix [flag..n-1] are individually sorted

**Approach 3 — Count drops (optimal, cleanest):**
```java
boolean drop = false;
for (int i = 0; i < n; i++) {
    if (nums[i] > nums[(i + 1) % n]) {
        if (!drop) drop = true;
        else return false;  // second drop → not valid
    }
}
return true;
```
O(n), O(1). The `% n` wraps around to also compare `nums[n-1]` with `nums[0]` (the rotation boundary).

**Key Insight:** a sorted-rotated array has **at most one descent**. If there are two or more, it cannot be a rotation of a sorted array. The modulo wrap is the trick that handles the circular comparison without extra code.

---

## 4. Remove Duplicates from Sorted Array

**Constraint:** sorted array, in-place, return count of unique elements. Elements beyond that count don't matter.

**Approach 1 — HashSet:** store seen elements, write unique ones front-to-back. O(n), O(n). Overkill since array is sorted.

**Approach 2 — Two pointer (optimal):**
```java
int idx = 0;
for (int i = 0; i < nums.length; i++) {
    if (nums[i] != nums[idx])
        nums[++idx] = nums[i];
}
return idx + 1;
```
O(n), O(1). `idx` points to the last written unique element. When `nums[i]` differs from `nums[idx]`, it's a new unique value — advance `idx` and write.

**Key Insight:** the sorted property guarantees all duplicates are adjacent, so a single pointer suffices. This wouldn't work on an unsorted array (you'd need a HashSet).

**Related:** LeetCode 80 (allow at most 2 duplicates) — same pattern, just change condition to `nums[i] != nums[idx - 1]`.

---

## 5. Left Rotate Array by K

**Approach 1 — Rotate one step at a time, k times:** O(n×k), not practical for large k.

**Approach 2 — Temp array (copy first k elements):** O(n), O(k).
```java
int[] temp = Arrays.copyOfRange(nums, 0, k);
for (int i = 0; i < n - k; i++) nums[i] = nums[i + k];
for (int i = 0; i < k; i++) nums[n - k + i] = temp[i];
```

**Approach 3 — Reverse trick (optimal):** O(n), O(1).
```java
reverse(nums, 0, k - 1);      // reverse first k elements
reverse(nums, k, n - 1);      // reverse remaining
reverse(nums, 0, n - 1);      // reverse entire array
```
Example: `[1,2,3,4,5]`, k=2 → reverse [1,2]→[2,1] → reverse [3,4,5]→[5,4,3] → reverse all→[3,4,5,1,2]. ✓

**Always normalize k first:** `k = k % n`. If k = n the array is unchanged. Without this, `k = n` would cause index out of bounds in the reverse approach.

**Right rotate by k = Left rotate by (n - k).** Same reverse trick, different split point.

---

## 6. Move Zeros to End

**Constraint:** maintain relative order of non-zero elements.

**Approach 1 — Temp array:** copy non-zeros to temp, copy back, fill rest with 0. O(n), O(n).

**Approach 2 — Two pointer in-place (optimal):**
```java
int idx = 0;
for (int i = 0; i < n; i++)
    if (nums[i] != 0) nums[idx++] = nums[i];
for (int i = idx; i < n; i++) nums[i] = 0;
```
O(n), O(1). First pass overwrites from front with all non-zeros. Second pass fills tail with zeros.

**Key Insight:** this is the same two-pointer pattern as Remove Duplicates — a write pointer `idx` trails a read pointer `i`. Works on any "filter and compact" problem.

**Variation:** if you can allow non-stable (reordering), you could swap non-zeros to front, but the problem requires relative order preserved.

---

## 7. Union of Two Sorted Arrays

**Union = all unique elements from both arrays, sorted.**

**Approach 1 — Brute:** add all to ArrayList, check `!res.contains()` before adding, sort. `contains` on ArrayList is O(n) → total O((n+m)²).

**Approach 2 — TreeSet:** insert all from both arrays into a `TreeSet` (no duplicates, sorted automatically). O((n+m) log(n+m)), O(n+m). Clean but uses extra space.

**Approach 3 — Two pointer merge (optimal):**
```java
while (f1 < n1 && f2 < n2) {
    // skip duplicates within each array
    if (f1 > 0 && a[f1] == a[f1-1]) { f1++; continue; }
    if (f2 > 0 && b[f2] == b[f2-1]) { f2++; continue; }

    if (a[f1] == b[f2])       { res.add(a[f1]); f1++; f2++; }
    else if (a[f1] < b[f2])   { res.add(a[f1++]); }
    else                       { res.add(b[f2++]); }
}
// drain remaining (with dedup)
```
O(n+m), O(1) extra (output list aside). This is the merge step of merge sort with deduplication layered on top.

**Key Insight:** sorted arrays allow dedup in O(1) by just comparing with the previous element — no HashSet needed. The `f1 > 0 && a[f1] == a[f1-1]` check skips within-array duplicates before the cross-array comparison.

**Intersection** (not union) uses the same two-pointer frame — just only add when `a[f1] == b[f2]`.

---

## 8. Missing Number in [1..N]

**Problem:** array contains N-1 distinct numbers from [1..N]. Find the missing one.

**Approach 1 — Brute:** for each `i` in [1..N], linear search if it exists. O(n²), O(1).

**Approach 2 — HashSet:** add all to set, check [1..N] for absence. O(n), O(n).

**Approach 3 — Sum formula (optimal):**
```java
long expected = (long) n * (n + 1) / 2;
long actual = 0;
for (int num : nums) actual += num;
return (int)(expected - actual);
```
O(n), O(1). Can overflow if n is large and you use `int` — use `long`.

**Approach 4 — Cyclic sort:**
```java
while (arr[i] != i + 1) {
    if (arr[i] == n) break;   // n is out of index range, skip
    swap(arr, i, arr[i] - 1); // place arr[i] at its correct index
}
// scan for arr[i] != i+1
```
O(n), O(1). Cyclic sort places each element at index `element - 1`. After the sort, the index with the wrong element reveals the missing number.

**Approach 5 — XOR:**
```java
int xor = 0;
for (int num : nums) xor ^= num;
// XOR with 1..n using pattern:
// n%4==0 → xor with n
// n%4==1 → xor with 1
// n%4==2 → xor with n+1
// n%4==3 → xor result is 0
```
O(n), O(1). Clever but the XOR-of-1-to-n formula is tricky to memorize. The sum formula is simpler and equally optimal.

**Key Insight — XOR of 1 to N pattern:**
```
n % 4 == 0  →  n
n % 4 == 1  →  1
n % 4 == 2  →  n + 1
n % 4 == 3  →  0
```
XOR of all elements cancels pairs (a^a = 0), leaving the missing number XORed with XOR(1..n).

**Which to use in interviews:** sum formula (v3) — simple, readable, O(n). Use XOR if interviewer asks for no arithmetic overflow concern.

---

## 9. Max Consecutive Ones

**Approach — Single pass:**
```java
int curr = 0, max = 0;
for (int num : nums) {
    if (num == 1) curr++;
    else { max = Math.max(max, curr); curr = 0; }
}
return Math.max(max, curr);  // handle case where array ends with 1s
```
O(n), O(1). Reset `curr` on every 0. The final `Math.max(max, curr)` is the critical line — if the array ends in a streak of 1s, the `else` branch never fires for it, so you must check `curr` after the loop.

**Common bug:** returning `max` inside the loop or forgetting the final comparison.

**Variant (LeetCode 1004):** max consecutive ones with at most K zeros flippable → sliding window.

---

## 10. Single Number (Element Appearing Once)

**Problem:** every element appears exactly twice except one. Find it.

**Approach 1 — Brute:** for each element, count occurrences. O(n²), O(1).

**Approach 2 — HashMap:** store frequencies, return key with freq ≠ 2. O(n), O(n).

**Approach 3 — Sort:** sort, then walk in pairs `nums[i] != nums[i+1]` → return `nums[i]`, else skip by `i++`. O(n log n), O(1).
```java
for (int i = 0; i < n - 1; i++) {
    if (nums[i] != nums[i + 1]) return nums[i];
    else i++;   // skip the pair
}
return nums[n - 1];  // last element is the single
```

**Approach 4 — XOR (optimal):**
```java
int xor = 0;
for (int num : nums) xor ^= num;
return xor;
```
O(n), O(1). `a ^ a = 0` and `0 ^ a = a`. Every duplicate cancels itself. Only the single element survives.

**Key XOR properties to memorize:**
```
a ^ a = 0       (self-cancellation)
a ^ 0 = a       (identity)
XOR is commutative and associative — order doesn't matter
```

**Variant:** what if every element appears 3 times except one? → XOR doesn't directly work. Use bit counting: for each of 32 bits, count how many numbers have that bit set. If `count % 3 != 0`, that bit belongs to the single number.

---

## 11. Longest Subarray with Sum K

**Two very different cases depending on input:**

### Case A: All non-negative integers → Sliding Window

```java
int l = 0, res = 0;
long curr = 0;
for (int r = 0; r < n; r++) {
    curr += arr[r];
    while (l <= r && curr > k) curr -= arr[l++];
    if (curr == k) res = Math.max(res, r - l + 1);
}
return res;
```
O(n), O(1). Works because with non-negatives, adding elements only increases sum and removing only decreases — the window is monotone, so two pointers are valid.

**Why sliding window fails with negatives:** adding a negative element can decrease sum, so shrinking the window from left doesn't necessarily bring sum down. The monotone property breaks.

### Case B: Array with negatives → Prefix Sum + HashMap

```java
HashMap<Long, Integer> vis = new HashMap<>();
vis.put(0L, -1);  // empty prefix has sum 0 at index -1
long curr = 0;
int max = 0;
for (int i = 0; i < n; i++) {
    curr += arr[i];
    if (vis.containsKey(curr - k))
        max = Math.max(max, i - vis.get(curr - k));
    vis.putIfAbsent(curr, i);  // store FIRST occurrence only
}
return max;
```
O(n), O(n).

**The core idea:** if `prefix[i] - prefix[j] = k`, then subarray `(j+1, i)` has sum k. So you look for `prefix[j] = prefix[i] - k` in the map.

**Why `putIfAbsent`?** You want the **earliest** index where a prefix sum occurred to maximize subarray length. If you overwrite with a later index, you'd get a shorter subarray.

**Why `vis.put(0, -1)`?** Handles the case where the subarray starts at index 0. `curr - k = 0` must map to index `-1` so length = `i - (-1) = i + 1`.

### Approach 1 (prefix sum + brute check): O(n²), O(n) — TLE for large inputs.

---

## 12. Patterns & Techniques Across All Problems

### Two-Pointer / Write-Pointer Pattern
Used in: Move Zeros, Remove Duplicates, Union (two arrays). 

One pointer `i` reads, one pointer `idx` writes. Write only when condition is met.
```java
int idx = 0;
for (int i = 0; i < n; i++) {
    if (condition(nums[i])) nums[idx++] = nums[i];
}
```

### Sliding Window
Used in: Longest Subarray Sum K (positive only), Max Consecutive Ones.

Expand right, shrink left when constraint violated.
Valid only when adding elements monotonically increases/decreases the tracked value.

### Prefix Sum
Used in: Longest Subarray Sum K (general case).

`prefix[i] = arr[0] + ... + arr[i]`  
Range sum `[l, r]` = `prefix[r] - prefix[l-1]`

Always seed the map with `(0, -1)` before iterating.

### XOR Tricks
Used in: Single Number, Missing Number (v5).

- All pairs cancel → only the unpaired element survives
- Entire XOR of [1..N] has a 4-cycle pattern

### Cyclic Sort
Used in: Missing Number (v4). Pattern: whenever values are in range [1..n] and you need O(n) O(1) — cyclic sort places `arr[i]` at index `arr[i]-1`.

### Reverse Trick for Rotation
Used in: Left Rotate by K.

`rotate(arr, k)` = reverse(0,k-1) + reverse(k,n-1) + reverse(0,n-1)

Works for both left and right rotate (just change the split index).

### HashSet for O(1) Lookup
Used in: Remove Duplicates (v1), Missing Number (v2), Union (v2).

When you need existence checks and don't care about order. Upgrade to TreeSet if sorted output is needed.

---

## 13. Revision Checklist

- [ ] Largest element — linear scan, initialize from `nums[0]`
- [ ] Second largest — single pass with `num != maxi` guard for duplicates
- [ ] Sorted rotated check — count drops, at most one allowed, use `% n` for wrap
- [ ] Remove duplicates — two pointer on sorted array, `nums[i] != nums[idx]`
- [ ] Left rotate by k — normalize `k % n`, reverse trick: first-k, rest, all
- [ ] Move zeros — write pointer, fill tail with zeros after loop
- [ ] Union of sorted arrays — two pointer merge with `f>0 && a[f]==a[f-1]` dedup
- [ ] Missing number — sum formula with `long`, know XOR formula too
- [ ] Max consecutive ones — final `Math.max(max, curr)` after loop
- [ ] Single number — XOR all elements, know the 3× variant (bit counting)
- [ ] Longest subarray sum K — know when to use sliding window vs prefix+hashmap
- [ ] `putIfAbsent` vs `put` distinction in prefix sum map
- [ ] Why `vis.put(0, -1)` is mandatory in prefix sum approach
- [ ] Sliding window fails on negative arrays — can explain why