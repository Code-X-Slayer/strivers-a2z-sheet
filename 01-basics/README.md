# Striver A2Z — Step 1: Learn the Basics
**Cheat Sheet | Java | Sections: Patterns · Maths · Recursion · Hashing**

---

## Table of Contents
1. [Java Fundamentals You Must Know](#1-java-fundamentals-you-must-know)
2. [Patterns — Logical Thinking](#2-patterns--logical-thinking)
3. [Basic Maths](#3-basic-maths)
4. [Recursion](#4-recursion)
5. [Hashing](#5-hashing)
6. [Java Collections Quick Reference](#6-java-collections-quick-reference)
7. [Complexity & Interview Rules of Thumb](#7-complexity--interview-rules-of-thumb)

---

## 1. Java Fundamentals You Must Know

### Data Types & Ranges
| Type | Size | Range |
|------|------|-------|
| `int` | 32-bit | −2³¹ to 2³¹−1 (~2.1 × 10⁹) |
| `long` | 64-bit | −2⁶³ to 2⁶³−1 (~9.2 × 10¹⁸) |
| `char` | 16-bit | Unicode 0–65535 |
| `double` | 64-bit | ~15 decimal digits precision |

> **Key rule:** If intermediate products can exceed ~2×10⁹, cast to `long` before multiplying.
> `(long)(r - l + 1) * nums[r]` — this is exactly what MostFreqEle does to avoid overflow.

### Java vs C++ Memory Model
- In **C++**, arrays use **stack memory** → segfault at ~10⁶–10⁷ elements for local arrays.
- In **Java**, arrays use **heap memory** → JVM default heap is 256MB–2GB, so ~10⁷–10⁸ elements are feasible.
- This is why precomputed hash arrays up to size 10⁶ inside methods are fine in Java, while in C++ you declare them globally.

### Character Encoding
- C++ `char` → 1 byte, ASCII (0–255). You can do `int arr[256]` for character hashing.
- Java `char` → 2 bytes, **Unicode (0–65535)**. Avoid `int arr[65536]` for character hashing.
  - For lowercase only: `freq[26]`, index = `ch - 'a'`
  - For full character set: **use a HashMap**

### Useful Java Tricks
```java
// Digit count via log
int digits = (int)(Math.log10(n)) + 1;

// Reverse a number as string
Integer.parseInt(new StringBuilder(String.valueOf(n)).reverse().toString());

// Power
Math.pow(base, exp);  // returns double — cast to int/long if needed

// Default value in map
map.getOrDefault(key, 0);

// Overflow-safe reverse check
int maxi = Integer.MAX_VALUE / 10;  // check before res = res*10 + digit
```

---

## 2. Patterns — Logical Thinking

### What Patterns Actually Teach
Patterns are not about memorizing shapes. They build the habit of:
- Thinking in terms of **rows** (outer loop `i`) and **columns** (inner loop `j`)
- Recognizing what changes per row: spaces, stars, characters, numbers
- Combining **multiple inner loops** in a single row (spaces + stars + spaces)

### General Framework for Any Pattern
```
for i in rows:
    [left padding / spaces]
    [main content]
    [right padding / mirror]
    newline
```

### Key Observations from Your Code

**Space-Star patterns (print7–print9):**
- Stars in row `i` = `2*i + 1` (odd sequence: 1, 3, 5, …)
- Spaces on each side = `n - i - 1`
- Diamond = upper triangle (print7) + lower triangle (print8) together → print9

**Number patterns:**
- `(i + j + 1) & 1` → alternating 0/1 binary triangle (print11). Bitwise AND with 1 checks odd/even parity.
- Sequential global counter `k++` across rows → print13 (number triangle not reset per row)

**Character patterns (print14–print18):**
- `char c = 'A'; c++` increments through alphabet
- `(char)('A' + n - 1 - i)` → starts character from end of alphabet going down
- Palindrome row (print17): go A→D then D→A using `c -= 2` after peak

**Concentric rectangle / spiral-like (print22):**
```java
int k = n - Math.min(Math.min(i, j), Math.min(2*n-2-i, 2*n-2-j));
```
This formula computes the **distance from the nearest border**, which is the key insight for inward-numbered rectangles.

**Hollow rectangle (print21):**
- First and last rows: full stars
- Middle rows: only first and last column are stars, rest are spaces
- Width of spaces = `2*n - 3`

### Patterns You Must Know (Summary Table)
| Pattern | Key Formula / Trick |
|---|---|
| Full rectangle | `n × n` stars |
| Right triangle | `j <= i` |
| Inverted triangle | `j <= n - i` |
| Number/char increment per row | `j` as value |
| Same number per row | `i` as value |
| Star pyramid | spaces = `n-i-1`, stars = `2i+1` |
| Diamond | pyramid + inverted pyramid |
| Rhombus / palindrome row | forward + backward char loop |
| Binary alternating | `(i+j) % 2` or `(i+j+1) & 1` |
| Concentric border numbers | `n - min(top, bottom, left, right)` |

---

## 3. Basic Maths

### Count Digits
```java
(int)(Math.log10(n)) + 1   // O(1)
// or loop: while(n > 0) { c++; n /= 10; }  O(digits)
```
> `Math.log10(0)` is `-Infinity` — always guard `n > 0`.

### Reverse a Number
**Loop approach (preferred in interviews — handles overflow):**
```java
while (n != 0) {
    res = res * 10 + n % 10;
    n /= 10;
}
```
**Overflow check (LeetCode style):**
```java
if (res > Integer.MAX_VALUE / 10 || (res == Integer.MAX_VALUE / 10 && digit > 7)) return 0;
if (res < Integer.MIN_VALUE / 10 || (res == Integer.MIN_VALUE / 10 && digit < -8)) return 0;
```
> Magic numbers `7` and `-8` come from `Integer.MAX_VALUE = 2147483647` (ends in 7) and `Integer.MIN_VALUE = -2147483648` (ends in 8).

### Palindrome Number
- Fastest: reverse the number and compare.
- Optimal (half-reverse): only reverse half the digits.
  - Stop when `revHalf >= x`. Then check `x == revHalf` (even digits) or `x == revHalf / 10` (odd digits).
- **Edge case:** negative numbers are never palindromes. Numbers ending in `0` (except `0` itself) are never palindromes.

### GCD — Euclidean Algorithm
```java
int gcd(int a, int b) {
    return b == 0 ? a : gcd(b, a % b);
}
```
- Time complexity: O(log(min(a, b)))
- **LCM:** `(a / gcd(a, b)) * b` — divide first to avoid overflow

### Armstrong Number
A number is Armstrong if sum of each digit raised to power (number of digits) equals the number.
```java
int digits = (int)(Math.log10(n)) + 1;
int res = 0;
int temp = n;
while (temp > 0) {
    res += Math.pow(temp % 10, digits);
    temp /= 10;
}
return res == n;
```
> Examples: 153 = 1³+5³+3³, 371 = 3³+7³+1³

### Print All Divisors
**Brute:** O(n) — loop 1 to n.

**Optimal:** O(√n) — loop 1 to √n, for each `i` that divides `n`, add both `i` and `n/i`.
```java
for (int i = 1; i * i <= n; i++) {
    if (n % i == 0) {
        res.add(i);
        if (n / i != i) res.add(n / i);  // avoid duplicate when i == √n
    }
}
Collections.sort(res);
```

### Check Prime
**Brute:** O(n) — loop 2 to n-1.

**Optimal:** O(√n) — loop 2 to √n.
```java
for (int i = 2; i * i <= n; i++) {
    if (n % i == 0) return false;
}
return n >= 2;
```
> Why √n? If `n = a × b` and `a ≤ b`, then `a ≤ √n`. Every composite number has a factor ≤ its square root.

### Missed: Sieve of Eratosthenes
Used when you need **all primes up to N** — much faster than checking each individually.
```java
boolean[] sieve = new boolean[N + 1];
Arrays.fill(sieve, true);
sieve[0] = sieve[1] = false;
for (int i = 2; i * i <= N; i++) {
    if (sieve[i]) {
        for (int j = i * i; j <= N; j += i)
            sieve[j] = false;
    }
}
```
- Time: O(N log log N)
- Start inner loop at `i * i` (smaller multiples already marked by earlier primes)

### Missed: Prime Factorization
```java
// O(√n)
for (int i = 2; i * i <= n; i++) {
    while (n % i == 0) {
        factors.add(i);
        n /= i;
    }
}
if (n > 1) factors.add(n);  // remaining prime factor
```

---

## 4. Recursion

### Mental Model: Trust the Stack
Recursion = **break into smaller subproblem + trust that the smaller call returns correctly**.
Never mentally simulate the full stack — just define the base case and the recurrence.

### Types of Recursion in Your Code

**Parameterized (accumulator style):**
```java
void sumOfN(int i, int sum) {
    if (i == 0) { print(sum); return; }
    sumOfN(i - 1, sum + i);
}
```
Result is carried as a parameter — no return value needed.

**Functional (return-value style):**
```java
int sumOfN(int i) {
    if (i == 0) return 0;
    return i + sumOfN(i - 1);
}
```
Result bubbles up through return values.

**Backtracking (post-call action):**
```java
void printNToOne(int i, int n) {
    if (i > n) return;
    printNToOne(i + 1, n);  // go deep first
    print(i);               // action happens on the way BACK
}
```
The key insight: actions placed **after** the recursive call execute in **reverse order** as the stack unwinds.

### Recursion Patterns Summary
| Goal | Direction | Action placement |
|------|-----------|-----------------|
| Print 1 to N | forward (`i+1`) | before recursive call |
| Print N to 1 | forward (`i+1`) | after recursive call (backtracking) |
| Print N to 1 | backward (`i-1`) | before recursive call |
| Print 1 to N | backward (`i-1`) | after recursive call (backtracking) |

### Reverse Array with Recursion
```java
void reverse(int[] arr, int i) {
    if (i >= arr.length / 2) return;
    swap(arr, i, arr.length - 1 - i);
    reverse(arr, i + 1);
}
```
Two-pointer logic implemented recursively. Stops at midpoint.

### Palindrome Check with Recursion
```java
boolean isPalindrome(String s, int i) {
    if (i >= s.length() / 2) return true;
    if (s.charAt(i) != s.charAt(s.length() - 1 - i)) return false;
    return isPalindrome(s, i + 1);
}
```

### Fibonacci
```java
int fib(int n) {
    if (n < 2) return n;
    return fib(n - 1) + fib(n - 2);
}
```
- Time: O(2ⁿ) — exponential, terrible for large n
- **Fix later:** memoization → O(n), or bottom-up DP → O(n), or matrix exponentiation → O(log n)

### Stack Overflow
Each recursive call occupies a stack frame. Deep recursion (n > ~10⁴–10⁵) can cause `StackOverflowError` in Java. Convert to iteration for such cases.

### Missed: Multiple Recursive Calls (Subsets, Permutations)
These come in Striver's Recursion PatternWise section later but the foundation starts here:
- Pick / Not-pick pattern for subsets
- Swap-based permutations
- Start recognizing the **recursion tree** shape for any problem

---

## 5. Hashing

### Core Problem
Given an array and Q queries each asking frequency of a number → naïve is O(Q × N).

### Approach 1: Precomputed Array (Frequency Array)
```java
int[] hash = new int[MAX];
for (int x : arr) hash[x]++;
// query: hash[num] in O(1)
```
**Limits:**
- Works only for non-negative integers within a bounded range
- Java: safe up to ~10⁶ in method (heap), ~10⁷ with increased JVM heap
- C++: safe only ~10⁶ in method (stack), ~10⁷ if declared globally

### Approach 2: HashMap
```java
HashMap<Integer, Integer> freq = new HashMap<>();
for (int x : arr) freq.put(x, freq.getOrDefault(x, 0) + 1);
// query: freq.getOrDefault(num, 0)
```
- Works for **any range** (negative, large, sparse)
- Time: O(1) average per operation
- Worst case: O(n) due to hash collisions (extremely rare in practice — ignore for interviews unless asked)

### Approach 3: TreeMap
```java
TreeMap<Integer, Integer> freq = new TreeMap<>();
```
- Internally a **Red-Black Tree** (sorted order)
- All operations: **O(log n)** — guaranteed, no worst-case spikes
- Use when: you need sorted iteration over keys, or want to avoid hash collision edge cases

### HashMap vs TreeMap
| | HashMap | TreeMap |
|---|---|---|
| Internal structure | Hash table | Red-Black Tree |
| Get / Put | O(1) avg, O(n) worst | O(log n) guaranteed |
| Key order | No order | Sorted ascending |
| Use case | Pure frequency / lookup | Sorted output needed |

### Character Hashing
```java
// Only a-z
int[] freq = new int[26];
freq[ch - 'a']++;

// Full Unicode — use HashMap
HashMap<Character, Integer> freq = new HashMap<>();
```

### Missed: Collision in HashMaps
Java's HashMap uses **separate chaining**. Worst case degrades to O(n) if all keys hash to same bucket (rare but possible with adversarial inputs). Java 8+ switches bucket from linked list to **balanced tree** when chain length > 8, giving O(log n) worst case in practice.

### Missed: Common HashMap Patterns
```java
// Two-sum pattern
map.put(target - num, index);

// Sliding window with frequency
map.merge(key, 1, Integer::sum);      // put or increment
map.merge(key, -1, Integer::sum);     // decrement
if (map.get(key) == 0) map.remove(key);

// Group by (anagrams, etc.)
map.computeIfAbsent(key, k -> new ArrayList<>()).add(value);
```

---

## 6. Java Collections Quick Reference

### Key Interfaces & Implementations
| Interface | Common Implementation | Notes |
|---|---|---|
| `List<E>` | `ArrayList`, `LinkedList` | `ArrayList` for random access |
| `Set<E>` | `HashSet`, `TreeSet`, `LinkedHashSet` | No duplicates |
| `Map<K,V>` | `HashMap`, `TreeMap`, `LinkedHashMap` | Key-value pairs |
| `Queue<E>` | `LinkedList`, `ArrayDeque` | FIFO |
| `Deque<E>` | `ArrayDeque` | Use as stack too (prefer over `Stack`) |
| `PriorityQueue<E>` | `PriorityQueue` | Min-heap by default |

### Must-Know Methods
```java
Collections.sort(list);                     // O(n log n)
Collections.sort(list, Comparator.reverseOrder());
Arrays.sort(arr);                           // O(n log n), primitives
Arrays.toString(arr);                       // print array
Collections.frequency(list, element);       // count occurrences
Collections.min(list) / Collections.max(list);
```

### ArrayList vs Array
- `ArrayList`: dynamic size, only Object types (autoboxing for primitives)
- `int[]`: fixed size, primitives, faster due to no boxing overhead
- Use `int[]` when size is known and performance matters

---

## 7. Complexity & Interview Rules of Thumb

### Time Complexity Constraints (Java / competitive)
| Operations per second (approx) | Max n |
|---|---|
| O(n²) | ~10⁴ |
| O(n log n) | ~10⁶ |
| O(n) | ~10⁷–10⁸ |
| O(log n) | ~10¹⁸ (with `long`) |
| O(1) | any |

> If n ≤ 10⁵ and time limit is 1s: O(n log n) is safe. O(n²) will TLE.

### Overflow Checklist
- `int * int` can overflow if either > ~46340
- Use `(long) a * b` or cast first
- `a + b` where both are `int` near `MAX_VALUE` → use `long`
- `i * i <= n` in prime check: safe for `int` since max i ≈ 46340 when n ≤ 2×10⁹

### Space Complexity
- Recursion stack: O(depth). For linear recursion → O(n). Watch for deep recursion.
- HashMap: O(n) space for n unique keys
- Precomputed array: O(max_value) — can be huge for large ranges

### Most Frequent Element — Three Approaches (MostFreqEle.java)

**Problem:** After at most K operations (increment any element), find the maximum frequency of any element.

**Key insight:** Sort first. The cheapest way to make elements equal is to make them all equal to the current maximum in your window, since you can only increment.

**Approach 1 — Sliding Window (standard):**
```java
Arrays.sort(nums);
// window [l, r]: cost = nums[r] * windowSize - windowSum
while (cost > k) { curr -= nums[l++]; }
res = Math.max(res, r - l + 1);
```
Time: O(n log n). Space: O(1).

**Approach 2 — Magic Sliding Window:**
Same logic but never shrink the window size — only slide it. The window size only grows when we find a better answer. This guarantees we track the **maximum window ever seen** without going backward.
```java
if (cost > k) { curr -= nums[l++]; }  // shift instead of shrink
return nums.length - l;
```

**Approach 3 — Binary Search per index:**
For each right pointer `i`, binary search for the leftmost valid `l`. Uses prefix sums.
Time: O(n log n). More complex but demonstrates combining sorting + prefix sum + binary search.

**Sliding window condition:**
`(r - l + 1) * nums[r] - sum_of_window <= k`
This is the cost to make all elements in window equal to `nums[r]` (the max after sort).

---

## Missing Topics from This Section (Complete the Coverage)

### Sorting Algorithms (Step 2 of A2Z)
You haven't coded these yet — they build on recursion:
- **Bubble Sort:** O(n²) — swap adjacent if out of order
- **Selection Sort:** O(n²) — find min, place at front
- **Insertion Sort:** O(n²) worst, O(n) best (nearly sorted) — key interview insight
- **Merge Sort:** O(n log n), O(n) space — divide and conquer, stable
- **Quick Sort:** O(n log n) avg, O(n²) worst — in-place, not stable

### Bit Manipulation Basics
```java
n & 1        // check if odd
n >> 1       // divide by 2
n << 1       // multiply by 2
n & (n-1)    // clear lowest set bit
n & (-n)     // isolate lowest set bit
Integer.bitCount(n)  // count set bits
```

### Two Pointer Pattern
Often paired with sorting (like in MostFreqEle):
```java
int l = 0, r = arr.length - 1;
while (l < r) {
    // process, then move l++ or r--
}
```

### Prefix Sum
```java
long[] pre = new long[n];
pre[0] = nums[0];
for (int i = 1; i < n; i++) pre[i] = pre[i-1] + nums[i];
// range sum [l, r] = pre[r] - (l > 0 ? pre[l-1] : 0)
```
Your MostFreqEle binary search approach uses this exactly.

---

## Revision Checklist

- [ ] Can derive digit count and Armstrong check without looking
- [ ] Know GCD by heart, derive LCM from it
- [ ] Can write prime check O(√n) and explain why √n is enough
- [ ] Know Sieve of Eratosthenes logic and time complexity
- [ ] Understand parameterized vs functional recursion — can convert between them
- [ ] Understand backtracking = action after recursive call
- [ ] Know when to use HashMap vs TreeMap vs array for hashing
- [ ] Can explain Java heap vs C++ stack memory for arrays
- [ ] Can write sliding window for max frequency problem and explain the cost formula
- [ ] Know overflow safe casting patterns with `long`