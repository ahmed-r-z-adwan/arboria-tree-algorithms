package com.mycompany.arboria;

import static com.mycompany.arboria.TheGreatNetwork.buildTreeFromLevelOrderArray;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.mycompany.arboria.TheGreatNetwork.Node;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Mission 2 — validate BST")
class ValidateBstTest {

    /** Calls the checker with the full bounds, the way mission 2 does. */
    private static boolean isBst(Node root) {
        return TheGreatNetwork.isValidBST(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    @Test
    @DisplayName("accepts a valid BST")
    void acceptsValidBst() {
        //        5
        //      /   \
        //     1     7
        //          / \
        //         6   8
        assertTrue(isBst(buildTreeFromLevelOrderArray(new int[] {5, 1, 7, -1, -1, 6, 8})));
    }

    @Test
    @DisplayName("rejects a value that is only out of order against a distant ancestor")
    void rejectsViolationAgainstAncestor() {
        //        5
        //      /   \
        //     1     7
        //          / \
        //         4   8
        //
        // Every parent-child pair here is individually ordered: 4 is a valid left child of
        // 7. It is still not a BST, because 4 sits in 5's right subtree. This is the case a
        // check that only compares a node with its own children gets wrong, and the reason
        // the bounds are threaded down the recursion.
        assertFalse(isBst(buildTreeFromLevelOrderArray(new int[] {5, 1, 7, -1, -1, 4, 8})));
    }

    @Test
    @DisplayName("an empty tree is a BST")
    void emptyTreeIsValid() {
        assertTrue(isBst(null));
        assertTrue(isBst(buildTreeFromLevelOrderArray(new int[] {})));
        assertTrue(isBst(buildTreeFromLevelOrderArray(new int[] {-1})));
    }

    @Test
    @DisplayName("a single node is a BST")
    void singleNodeIsValid() {
        assertTrue(isBst(buildTreeFromLevelOrderArray(new int[] {42})));
    }

    @Test
    @DisplayName("a chain leaning one way is still a BST")
    void skewedChainsAreValid() {
        // Right chain 1 -> 2 -> 3, built by insertion so the shape is a straight line.
        assertTrue(isBst(TheGreatNetwork.buildBSTFromInsertOrder(new int[] {1, 2, 3, 4, 5})));
        // Left chain 5 -> 4 -> 3.
        assertTrue(isBst(TheGreatNetwork.buildBSTFromInsertOrder(new int[] {5, 4, 3, 2, 1})));
    }

    @Test
    @DisplayName("duplicates are rejected, because the bounds are strict")
    void rejectsDuplicates() {
        //      5
        //     /
        //    5
        assertFalse(isBst(buildTreeFromLevelOrderArray(new int[] {5, 5})));
    }

    @Test
    @DisplayName("Integer.MIN_VALUE and MAX_VALUE are ordinary values, not sentinels")
    void handlesExtremeIntegerValues() {
        //            0
        //          /   \
        //   MIN_VALUE   MAX_VALUE
        //
        // This is why the bounds are `long` rather than `int`. With int bounds the initial
        // range would have to start at Integer.MIN_VALUE, and the strict comparison
        // `node.value <= min` would then reject a legitimate node holding exactly that
        // value — a tree would be called invalid for containing the smallest int.
        Node root = buildTreeFromLevelOrderArray(
                new int[] {0, Integer.MIN_VALUE, Integer.MAX_VALUE});
        assertTrue(isBst(root));
    }

    @Test
    @DisplayName("a tree of only extreme values on the correct sides is valid")
    void extremesInAChain() {
        Node root = TheGreatNetwork.buildBSTFromInsertOrder(
                new int[] {Integer.MAX_VALUE, Integer.MIN_VALUE, 0});
        assertTrue(isBst(root));
    }
}
