package com.mycompany.arboria;

import static com.mycompany.arboria.TheGreatNetwork.buildBSTFromInsertOrder;
import static com.mycompany.arboria.TheGreatNetwork.buildTreeFromLevelOrderArray;
import static com.mycompany.arboria.TheGreatNetwork.levelOrderByLevels;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Mission 1 — level order, and the two tree builders")
class TraversalAndBuildersTest {

    @Nested
    @DisplayName("level order")
    class LevelOrder {

        @Test
        @DisplayName("groups values by depth")
        void groupsByDepth() {
            //        5
            //      /   \
            //     1     7
            //          / \
            //         6   8
            assertEquals(
                    List.of(List.of(5), List.of(1, 7), List.of(6, 8)),
                    levelOrderByLevels(
                            buildTreeFromLevelOrderArray(new int[] {5, 1, 7, -1, -1, 6, 8})));
        }

        @Test
        @DisplayName("an empty tree has no levels")
        void emptyTree() {
            assertTrue(levelOrderByLevels(null).isEmpty());
        }

        @Test
        @DisplayName("a single node is one level of one value")
        void singleNode() {
            assertEquals(List.of(List.of(42)), levelOrderByLevels(
                    buildTreeFromLevelOrderArray(new int[] {42})));
        }

        @Test
        @DisplayName("a chain produces one value per level")
        void chain() {
            assertEquals(
                    List.of(List.of(1), List.of(2), List.of(3)),
                    levelOrderByLevels(buildBSTFromInsertOrder(new int[] {1, 2, 3})));
        }
    }

    @Nested
    @DisplayName("level-order array builder")
    class ArrayBuilder {

        @Test
        @DisplayName("-1 marks a missing node")
        void minusOneIsAGap() {
            //      1
            //       \
            //        3
            var root = buildTreeFromLevelOrderArray(new int[] {1, -1, 3});
            assertEquals(1, root.value);
            assertNull(root.left);
            assertEquals(3, root.right.value);
        }

        @Test
        @DisplayName("an empty array, or -1 at the root, is an empty tree")
        void noRoot() {
            assertNull(buildTreeFromLevelOrderArray(new int[] {}));
            assertNull(buildTreeFromLevelOrderArray(new int[] {-1}));
        }

        @Test
        @DisplayName("a gap still occupies its index, so its would-be children are dropped")
        void gapsConsumeIndices() {
            // The builder places children by the heap formula 2i+1 and 2i+2, which means a
            // -1 keeps its slot. Index 3 here is the left child of the missing index 1, so
            // the 4 is unreachable and never appears in the tree.
            //
            // This is a property of the input format, not a bug: the array has to be the
            // complete-tree layout, gaps included.
            assertEquals(
                    List.of(List.of(1), List.of(3)),
                    levelOrderByLevels(buildTreeFromLevelOrderArray(new int[] {1, -1, 3, 4})));
        }
    }

    @Nested
    @DisplayName("BST insertion builder")
    class BstBuilder {

        @Test
        @DisplayName("insertion order decides the shape")
        void insertionOrderShapesTree() {
            // Balanced input.
            assertEquals(
                    List.of(List.of(5), List.of(3, 7)),
                    levelOrderByLevels(buildBSTFromInsertOrder(new int[] {5, 3, 7})));
            // Sorted input degenerates into a chain, which is the worst case for lookup.
            assertEquals(
                    List.of(List.of(3), List.of(5), List.of(7)),
                    levelOrderByLevels(buildBSTFromInsertOrder(new int[] {3, 5, 7})));
        }

        @Test
        @DisplayName("no values means no tree")
        void emptyInput() {
            assertNull(buildBSTFromInsertOrder(new int[] {}));
        }

        @Test
        @DisplayName("-1 is an ordinary value here, unlike in the array builder")
        void minusOneIsAValue() {
            //      0
            //     /
            //   -1
            var root = buildBSTFromInsertOrder(new int[] {0, -1});
            assertEquals(0, root.value);
            assertEquals(-1, root.left.value);
        }
    }
}
