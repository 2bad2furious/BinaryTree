package com.company;

public class Main {

    public static void main(String[] args) {

        AVLTree<Integer> tree = new AVLTree<>();
        simulateRL(tree);
    }

    private static void simulateRL(AVLTree<Integer> tree) {
        tree.add(12);
        tree.add(5);
        tree.add(14);
        tree.add(2);
        tree.add(9);
        tree.add(13);
        tree.add(7);
        tree.add(10);
        System.out.println(tree);
        tree.add(8);
        System.out.println(tree);
    }

    private static void simulateLR(AVLTree<Integer> tree) {
        tree.add(12);
        tree.add(5);
        tree.add(14);
        tree.add(2);
        tree.add(9);
        tree.add(13);
        tree.add(1);
        tree.add(7);
        tree.add(10);
        System.out.println(tree);
        tree.add(8);
        System.out.println(tree);
    }

    private static void simulateRR(AVLTree<Integer> tree) {
        tree.add(8);
        tree.add(2);
        tree.add(11);
        tree.add(4);
        tree.add(9);
        tree.add(15);
        tree.add(14);
        tree.add(18);
        System.out.println(tree);
        tree.add(17);
        System.out.println(tree);
    }

    private static void simulateLL(AVLTree<Integer> tree) {
        tree.add(11);
        tree.add(6);
        tree.add(16);
        tree.add(3);
        tree.add(8);
        tree.add(21);
        tree.add(2);
        tree.add(4);
        tree.add(7);
        System.out.println(tree);
        tree.add(1);
        System.out.println(tree);
    }
}
