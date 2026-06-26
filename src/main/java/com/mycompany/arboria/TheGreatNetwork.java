package com.mycompany.arboria;

import java.util.*;

/**
 * TheGreatNetwork - All 5 Missions in ONE class.
 *
 * How to run:
 * 1) First input line: mission number (1..5)
 * 2) Then provide the rest of the input exactly as required by that mission.
 *
 * Example (Mission 2):
 * 2
 * 7
 * 5 1 7 -1 -1 6 8
 */
public class TheGreatNetwork {

    // =========================
    //   Node (for all missions)
    // =========================
    static class Node {
        int value;
        Node left, right;

        Node(int value) {
            this.value = value;
        }
    }

    // =========================================================
    // Mission 1 & 2: Build General Binary Tree from Level-Order Array
    // (-1 means missing node)
    // =========================================================
    static Node buildTreeFromLevelOrderArray(int[] arr) {
        if (arr.length == 0 || arr[0] == -1) return null;

        Node[] nodes = new Node[arr.length];

        // Create nodes
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != -1) {
                nodes[i] = new Node(arr[i]);
            }
        }

        // Link children using index formula
        for (int i = 0; i < arr.length; i++) {
            if (nodes[i] == null) continue;

            int leftIndex = 2 * i + 1;
            int rightIndex = 2 * i + 2;

            if (leftIndex < arr.length) nodes[i].left = nodes[leftIndex];
            if (rightIndex < arr.length) nodes[i].right = nodes[rightIndex];
        }

        return nodes[0]; // root
    }

    // =========================
    // Mission 1: Level Order (print by levels)
    // =========================
    static void mission1_levelOrderPrintByLevels(Node root) {
        if (root == null) return;

        Queue<Node> q = new LinkedList<>();
        q.add(root);

        int level = 0;
        while (!q.isEmpty()) {
            int size = q.size();
            System.out.print("Level " + level + ": ");

            for (int i = 0; i < size; i++) {
                Node cur = q.poll();
                System.out.print(cur.value + " ");

                if (cur.left != null) q.add(cur.left);
                if (cur.right != null) q.add(cur.right);
            }

            System.out.println();
            level++;
        }
    }

    // =========================
    // Mission 2: Validate BST (no duplicates)
    // =========================
    static boolean isValidBST(Node node, long min, long max) {
        if (node == null) return true;

        // Strict inequality (duplicates not allowed)
        if (node.value <= min || node.value >= max) return false;

        return isValidBST(node.left, min, node.value)
                && isValidBST(node.right, node.value, max);
    }

    static void mission2_validateBST(Node root) {
        System.out.println(isValidBST(root, Long.MIN_VALUE, Long.MAX_VALUE) ? "YES" : "NO");
    }

    // =========================================================
    // Mission 3 & 4: Build BST by insertion order
    // =========================================================
    static Node bstInsert(Node root, int val) {
        if (root == null) return new Node(val);

        Node cur = root;
        while (true) {
            if (val < cur.value) {
                if (cur.left == null) {
                    cur.left = new Node(val);
                    break;
                }
                cur = cur.left;
            } else { // val > cur.value (distinct values assumed)
                if (cur.right == null) {
                    cur.right = new Node(val);
                    break;
                }
                cur = cur.right;
            }
        }
        return root;
    }

    static Node buildBSTFromInsertOrder(int[] values) {
        Node root = null;
        for (int v : values) root = bstInsert(root, v);
        return root;
    }

    // =========================
    // Mission 3: Range Sum in BST [L, R]
    // =========================
    static long rangeSumBST(Node root, int L, int R) {
        if (root == null) return 0;

        if (root.value < L) return rangeSumBST(root.right, L, R);
        if (root.value > R) return rangeSumBST(root.left, L, R);

        return root.value + rangeSumBST(root.left, L, R) + rangeSumBST(root.right, L, R);
    }

    static void mission3_rangeSum(Node bstRoot, int L, int R) {
        System.out.println(rangeSumBST(bstRoot, L, R));
    }

    // =========================
    // Mission 4: LCA in BST
    // =========================
    static int lcaBST(Node root, int p, int q) {
        int low = Math.min(p, q);
        int high = Math.max(p, q);

        Node cur = root;
        while (cur != null) {
            if (high < cur.value) {
                cur = cur.left;
            } else if (low > cur.value) {
                cur = cur.right;
            } else {
                return cur.value; // split point
            }
        }
        // If inputs are guaranteed to exist, we should never reach here.
        return -1;
    }

    static void mission4_lca(Node bstRoot, int p, int q) {
        System.out.println(lcaBST(bstRoot, p, q));
    }

    // =========================================================
    // Mission 5: Build Tree from Preorder + Inorder, then print Postorder
    // =========================================================
    static Node buildFromPreIn(
            int[] preorder, int preL, int preR,
            int[] inorder, int inL, int inR,
            Map<Integer, Integer> inIndex
    ) {
        if (preL > preR || inL > inR) return null;

        int rootVal = preorder[preL];
        Node root = new Node(rootVal);

        int mid = inIndex.get(rootVal);
        int leftSize = mid - inL;

        root.left = buildFromPreIn(
                preorder, preL + 1, preL + leftSize,
                inorder, inL, mid - 1,
                inIndex
        );

        root.right = buildFromPreIn(
                preorder, preL + leftSize + 1, preR,
                inorder, mid + 1, inR,
                inIndex
        );

        return root;
    }

    static void postorderCollect(Node root, StringBuilder sb) {
        if (root == null) return;
        postorderCollect(root.left, sb);
        postorderCollect(root.right, sb);
        sb.append(root.value).append(' ');
    }

    static void mission5_postorderFromPreIn(int[] preorder, int[] inorder) {
        Map<Integer, Integer> inIndex = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            inIndex.put(inorder[i], i);
        }

        Node root = buildFromPreIn(
                preorder, 0, preorder.length - 1,
                inorder, 0, inorder.length - 1,
                inIndex
        );

        StringBuilder sb = new StringBuilder();
        postorderCollect(root, sb);

        // Remove last space (optional neat output)
        if (sb.length() > 0) sb.setLength(sb.length() - 1);

        System.out.println(sb.toString());
    }

    // =========================================================
    // Main
    // =========================================================
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int mission = sc.nextInt(); // 1..5

        if (mission == 1 || mission == 2) {
            int n = sc.nextInt();
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) arr[i] = sc.nextInt();

            Node root = buildTreeFromLevelOrderArray(arr);

            if (mission == 1) {
                mission1_levelOrderPrintByLevels(root);
            } else {
                mission2_validateBST(root);
            }

        } else if (mission == 3) {
            int n = sc.nextInt();
            int[] values = new int[n];
            for (int i = 0; i < n; i++) values[i] = sc.nextInt();

            int L = sc.nextInt();
            int R = sc.nextInt();

            Node bstRoot = buildBSTFromInsertOrder(values);
            mission3_rangeSum(bstRoot, L, R);

        } else if (mission == 4) {
            int n = sc.nextInt();
            int[] values = new int[n];
            for (int i = 0; i < n; i++) values[i] = sc.nextInt();

            int p = sc.nextInt();
            int q = sc.nextInt();

            Node bstRoot = buildBSTFromInsertOrder(values);
            mission4_lca(bstRoot, p, q);

        } else if (mission == 5) {
            int n = sc.nextInt();
            int[] preorder = new int[n];
            int[] inorder = new int[n];

            for (int i = 0; i < n; i++) preorder[i] = sc.nextInt();
            for (int i = 0; i < n; i++) inorder[i] = sc.nextInt();

            mission5_postorderFromPreIn(preorder, inorder);

        } else {
            System.out.println("Invalid mission number. Use 1..5");
        }
    }
}
