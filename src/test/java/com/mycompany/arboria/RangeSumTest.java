package com.mycompany.arboria;

import static com.mycompany.arboria.TheGreatNetwork.buildBSTFromInsertOrder;
import static com.mycompany.arboria.TheGreatNetwork.rangeSumBST;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.mycompany.arboria.TheGreatNetwork.Node;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Mission 3 — range sum in a BST")
class RangeSumTest {

    /** 10, 5, 15, 3, 7, 18 — the shape used by most of these cases. */
    private static Node tree() {
        return buildBSTFromInsertOrder(new int[] {10, 5, 15, 3, 7, 18});
    }

    @Test
    @DisplayName("sums the values inside the range")
    void sumsInsideRange() {
        // 7 + 10 + 15 = 32
        assertEquals(32L, rangeSumBST(tree(), 7, 15));
    }

    @Test
    @DisplayName("the range is inclusive at both ends")
    void boundsAreInclusive() {
        // 3 and 18 are the smallest and largest values, so the whole tree is in range.
        assertEquals(58L, rangeSumBST(tree(), 3, 18));
        assertEquals(58L, rangeSumBST(tree(), Integer.MIN_VALUE, Integer.MAX_VALUE));
    }

    @Test
    @DisplayName("a single-value range returns just that value")
    void singleValueRange() {
        assertEquals(7L, rangeSumBST(tree(), 7, 7));
        // A value that is not in the tree contributes nothing.
        assertEquals(0L, rangeSumBST(tree(), 8, 8));
    }

    @Test
    @DisplayName("a range beyond the tree sums to zero")
    void rangeOutsideTree() {
        assertEquals(0L, rangeSumBST(tree(), 100, 200));
        assertEquals(0L, rangeSumBST(tree(), -200, -100));
    }

    @Test
    @DisplayName("an empty tree sums to zero")
    void emptyTree() {
        assertEquals(0L, rangeSumBST(null, 0, 100));
    }

    @Test
    @DisplayName("an inverted range sums to zero rather than throwing")
    void invertedRange() {
        // L greater than R describes no values at all. Every node then fails both the
        // `< L` and `> R` tests on one side or the other and the recursion runs out.
        assertEquals(0L, rangeSumBST(tree(), 15, 7));
    }

    @Test
    @DisplayName("a sum wider than an int does not overflow")
    void doesNotOverflowInt() {
        // Two values that each fit in an int but whose sum does not. The function returns
        // long for exactly this reason; with an int accumulator this would come back
        // negative.
        Node big = buildBSTFromInsertOrder(new int[] {2_000_000_000, 2_000_000_001});
        assertEquals(4_000_000_001L, rangeSumBST(big, 0, Integer.MAX_VALUE));
    }
}
