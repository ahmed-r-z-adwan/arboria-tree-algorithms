package com.mycompany.arboria;

import static com.mycompany.arboria.TheGreatNetwork.buildBSTFromInsertOrder;
import static com.mycompany.arboria.TheGreatNetwork.lcaBST;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.mycompany.arboria.TheGreatNetwork.Node;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Mission 4 — lowest common ancestor in a BST")
class LowestCommonAncestorTest {

    //            6
    //         /     \
    //        2       8
    //       / \     / \
    //      0   4   7   9
    //         / \
    //        3   5
    private static Node tree() {
        return buildBSTFromInsertOrder(new int[] {6, 2, 8, 0, 4, 7, 9, 3, 5});
    }

    @Test
    @DisplayName("two values in different subtrees meet at the root")
    void splitsAtRoot() {
        assertEquals(6, lcaBST(tree(), 2, 8));
        assertEquals(6, lcaBST(tree(), 0, 9));
    }

    @Test
    @DisplayName("when one value is an ancestor of the other, it is the answer")
    void ancestorOfTheOther() {
        assertEquals(2, lcaBST(tree(), 2, 4));
        assertEquals(8, lcaBST(tree(), 8, 9));
    }

    @Test
    @DisplayName("finds an ancestor deeper than the root")
    void deeperSplit() {
        assertEquals(4, lcaBST(tree(), 3, 5));
    }

    @Test
    @DisplayName("argument order does not matter")
    void orderIndependent() {
        // The search normalises the pair to low and high before descending, so the caller
        // does not have to sort them.
        assertEquals(lcaBST(tree(), 3, 5), lcaBST(tree(), 5, 3));
        assertEquals(lcaBST(tree(), 2, 8), lcaBST(tree(), 8, 2));
    }

    @Test
    @DisplayName("a value is its own ancestor")
    void sameValueTwice() {
        assertEquals(7, lcaBST(tree(), 7, 7));
        assertEquals(6, lcaBST(tree(), 6, 6));
    }

    @Test
    @DisplayName("returns -1 when the search walks off the tree")
    void missingValues() {
        assertEquals(-1, lcaBST(tree(), 100, 200));
        assertEquals(-1, lcaBST(tree(), -200, -100));
        assertEquals(-1, lcaBST(null, 1, 2));
    }

    @Test
    @DisplayName("assumes both values are present — a missing one yields a nearby node")
    void assumesValuesExist() {
        // 1 is not in the tree. The walk still stops at the first node that splits the
        // range, so the answer is 2 rather than an error. Pinning it here so the
        // precondition is visible: this is a lookup for values known to exist, not a
        // membership test.
        assertEquals(2, lcaBST(tree(), 1, 5));
    }
}
