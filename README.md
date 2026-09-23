# Arboria Tree Algorithms

[![tests](https://github.com/ahmed-r-z-adwan/arboria-tree-algorithms/actions/workflows/tests.yml/badge.svg)](https://github.com/ahmed-r-z-adwan/arboria-tree-algorithms/actions/workflows/tests.yml)

Five binary tree and BST algorithms in Java, each one separated into a pure function and a thin printer so the algorithm can be tested directly. 38 JUnit 5 tests cover them, and most of those tests are about the edge cases rather than the happy path.

## The algorithms

| # | Problem | Approach | Time | Space |
|---|---|---|---|---|
| 1 | Level-order traversal, grouped by level | BFS, one queue drain per level | O(n) | O(w) |
| 2 | Is this binary tree a BST? | Recursion carrying an open `(min, max)` range | O(n) | O(h) |
| 3 | Sum of values in `[L, R]` | Prune any subtree that cannot overlap the range | O(h + k) | O(h) |
| 4 | Lowest common ancestor in a BST | Iterative descent to the first splitting node | O(h) | O(1) |
| 5 | Rebuild from preorder + inorder, print postorder | Recursive split with an inorder index map | O(n) | O(n) |

`n` nodes, `h` height, `w` widest level, `k` values inside the range.

## What the tests are actually for

The interesting cases are the ones where a plausible implementation is wrong:

- **A BST violation against a distant ancestor.** In a tree where `5` has right child `7` and `7` has left child `4`, every parent-child pair is individually ordered, yet it is not a BST — `4` sits in `5`'s right subtree. A check that only compares a node with its own children passes this tree. Threading the bounds down the recursion is what catches it.

- **`Integer.MIN_VALUE` as a real value.** The bounds are `long`, not `int`. With `int` bounds the initial range would start at `Integer.MIN_VALUE`, and the strict comparison `node.value <= min` would then reject a node legitimately holding that value — a tree called invalid for containing the smallest int.

- **A range sum wider than an `int`.** Two values that each fit in an `int` can sum past it, so the traversal accumulates into a `long`. With an `int` accumulator the answer comes back negative.

- **Gaps in the level-order array.** `-1` marks a missing node, but it still occupies its index, because children are placed by the heap formula `2i+1` / `2i+2`. A value written below a gap is therefore unreachable. That is a property of the input format rather than a defect, and a test pins it so it stays deliberate.

- **The LCA precondition.** The search assumes both values exist. Given one that does not, it still returns the first splitting node rather than failing — so it is a lookup for known values, not a membership test. Pinned, so the assumption is visible instead of implied.

## Running it

```bash
mvn -B test          # 38 tests
mvn -B package
java -cp target/classes com.mycompany.arboria.Arboria
```

`Arboria` is an interactive menu over the five missions. `TheGreatNetwork` also has a `main` that reads a mission number followed by that mission's input, which is the shape used for judge-style input.

Targets **Java 21**; CI runs the suite on 21 and 25.

## Academic context

Written as Data Structures practice at the Islamic University of Gaza; the course mark was 95/100. The test suite and CI were added afterwards.

## Licence

[MIT](LICENSE)
