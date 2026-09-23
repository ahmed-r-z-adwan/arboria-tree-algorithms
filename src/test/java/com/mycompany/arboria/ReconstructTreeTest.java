package com.mycompany.arboria;

import static com.mycompany.arboria.TheGreatNetwork.postorderFromPreIn;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Mission 5 — rebuild from preorder and inorder, print postorder")
class ReconstructTreeTest {

    @Test
    @DisplayName("rebuilds a tree with both subtrees")
    void rebuildsBranchingTree() {
        //        3
        //      /   \
        //     9     20
        //          /  \
        //        15    7
        assertEquals(
                "9 15 7 20 3",
                postorderFromPreIn(new int[] {3, 9, 20, 15, 7}, new int[] {9, 3, 15, 20, 7}));
    }

    @Test
    @DisplayName("a single node")
    void singleNode() {
        assertEquals("1", postorderFromPreIn(new int[] {1}, new int[] {1}));
    }

    @Test
    @DisplayName("empty input gives an empty string, not a stray space")
    void emptyInput() {
        assertEquals("", postorderFromPreIn(new int[] {}, new int[] {}));
    }

    @Test
    @DisplayName("a chain leaning left")
    void leftChain() {
        // 3 <- 2 <- 1, so postorder walks up from the deepest node.
        assertEquals("1 2 3", postorderFromPreIn(new int[] {3, 2, 1}, new int[] {1, 2, 3}));
    }

    @Test
    @DisplayName("a chain leaning right")
    void rightChain() {
        // Inorder is identical to the left-leaning case; only preorder distinguishes the
        // two shapes, which is the whole point of needing both traversals.
        assertEquals("3 2 1", postorderFromPreIn(new int[] {1, 2, 3}, new int[] {1, 2, 3}));
    }

    @Test
    @DisplayName("handles negative values, since -1 is only a sentinel in the array builder")
    void negativeValues() {
        //      -5
        //     /   \
        //   -9     0
        assertEquals("-9 0 -5", postorderFromPreIn(new int[] {-5, -9, 0}, new int[] {-9, -5, 0}));
    }
}
