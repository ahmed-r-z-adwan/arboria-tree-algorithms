/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.arboria;

import com.mycompany.arboria.TheGreatNetwork.Node;
import static com.mycompany.arboria.TheGreatNetwork.buildBSTFromInsertOrder;
import static com.mycompany.arboria.TheGreatNetwork.buildTreeFromLevelOrderArray;
import static com.mycompany.arboria.TheGreatNetwork.mission1_levelOrderPrintByLevels;
import static com.mycompany.arboria.TheGreatNetwork.mission2_validateBST;
import static com.mycompany.arboria.TheGreatNetwork.mission3_rangeSum;
import static com.mycompany.arboria.TheGreatNetwork.mission4_lca;
import static com.mycompany.arboria.TheGreatNetwork.mission5_postorderFromPreIn;
import java.util.Scanner;

/**
 *
 * @author Ahmed-H
 */
public class Arboria {

    public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    while (true) {
        System.out.println("\nChoose a mission:");
        System.out.println("1) Level Order Traversal (print by levels)");
        System.out.println("2) Validate BST (YES/NO)");
        System.out.println("3) Range Sum in BST [L, R]");
        System.out.println("4) LCA in BST");
        System.out.println("5) Build from Preorder+Inorder and print Postorder");
        System.out.println("0) Exit");
        System.out.print("Your choice: ");

        int choice = sc.nextInt();
        if (choice == 0) {
            System.out.println("Bye!");
            break;
        }

        if (choice == 1 || choice == 2) {
            // Input: n then level-order array with -1
            System.out.print("Enter n: ");
            int n = sc.nextInt();

            int[] arr = new int[n];
            System.out.println("Enter the " + n + " values (use -1 for null):");
            for (int i = 0; i < n; i++) arr[i] = sc.nextInt();

            Node root = buildTreeFromLevelOrderArray(arr);

            if (choice == 1) {
                mission1_levelOrderPrintByLevels(root);
            } else {
                mission2_validateBST(root);
            }

        } else if (choice == 3) {
            // Input: n then insertion order values, then L R
            System.out.print("Enter n: ");
            int n = sc.nextInt();

            int[] values = new int[n];
            System.out.println("Enter the " + n + " BST insertion values:");
            for (int i = 0; i < n; i++) values[i] = sc.nextInt();

            System.out.print("Enter L and R: ");
            int L = sc.nextInt();
            int R = sc.nextInt();

            Node bstRoot = buildBSTFromInsertOrder(values);
            mission3_rangeSum(bstRoot, L, R);

        } else if (choice == 4) {
            // Input: n then insertion order values, then p q
            System.out.print("Enter n: ");
            int n = sc.nextInt();

            int[] values = new int[n];
            System.out.println("Enter the " + n + " BST insertion values:");
            for (int i = 0; i < n; i++) values[i] = sc.nextInt();

            System.out.print("Enter p and q: ");
            int p = sc.nextInt();
            int q = sc.nextInt();

            Node bstRoot = buildBSTFromInsertOrder(values);
            mission4_lca(bstRoot, p, q);

        } else if (choice == 5) {
            // Input: n then preorder then inorder
            System.out.print("Enter n: ");
            int n = sc.nextInt();

            int[] preorder = new int[n];
            int[] inorder = new int[n];

            System.out.println("Enter preorder (" + n + " values):");
            for (int i = 0; i < n; i++) preorder[i] = sc.nextInt();

            System.out.println("Enter inorder (" + n + " values):");
            for (int i = 0; i < n; i++) inorder[i] = sc.nextInt();

            mission5_postorderFromPreIn(preorder, inorder);

        } else {
            System.out.println("Invalid choice. Please enter 0..5");
        }
    }

    sc.close();
}

}
