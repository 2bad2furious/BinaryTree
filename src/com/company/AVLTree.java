package com.company;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Optional;

public class AVLTree<T extends Comparable> {
    private Optional<TreeNode<T>> root = Optional.empty();

    public boolean add(T data) {
        if (!root.isPresent()) {
            root = Optional.of(new TreeNode<T>(data));
            return true;
        }
        if (!add(data, root.get())) return false;
        balance();
        return true;
    }

    private boolean add(T data, TreeNode<T> node) {
        if (node.getData().equals(data)) return false;
        if (node.getData().compareTo(data) > 0) {
            if (node.getLeftNode().isPresent()) return add(data, node.getLeftNode().get());

            node.setLeftNode(Optional.of(new TreeNode<T>(data)));
            return true;
        } else {
            if (node.getRightNode().isPresent()) return add(data, node.getRightNode().get());

            node.setRightNode(Optional.of(new TreeNode<T>(data)));
            return true;
        }
    }

    public boolean exists(T data) {
        return root.map(treeNode -> treeNode.exists(data)).orElse(false);
    }

    public boolean delete(T data) {
        //if(!exists(data)) return false; would actually be slower
        if (!root.isPresent()) return false;
        if (!delete(data, root.get())) return false;
        balance();
        return true;
    }

    private boolean delete(T data, TreeNode node) {
        throw new NotImplementedException();
    }

    private void balance() {
        try {
            root = balance(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Optional<TreeNode<T>> balance(Optional<TreeNode<T>> node) {
        if (!node.isPresent()) return node;
        node.get().setLeftNode(balance(node.get().getLeftNode()));
        node.get().setRightNode(balance(node.get().getRightNode()));

        if (!node.get().isBalanced()) {
            TreeNode<T> newroot = null;

            if (needsLeftLeft(node)) newroot = leftLeftRotate(node.get());
            else if (needsRightRight(node)) newroot = rightRightRotate(node.get());
            else if (needsLeftRight(node)) newroot = leftRightRotate(node.get());
            else if (needsRightLeft(node)) newroot = rightLeftRotate(node.get());

            if(newroot == null) throw new NullPointerException();
            node = Optional.of(newroot);
        }

        return node;
    }

    private boolean needsLeftLeft(Optional<TreeNode<T>> needer) {
        return needer.isPresent() && needer.get().getRightNode().isPresent() && needer.get().getBalanceFactor() == 2 && needer.get().getRightNode().get().getBalanceFactor() == 1;
    }

    private boolean needsRightRight(Optional<TreeNode<T>> needer) {
        return needer.isPresent() && needer.get().getLeftNode().isPresent() && needer.get().getBalanceFactor() == -2 && needer.get().getLeftNode().get().getBalanceFactor() == -1;
    }

    private boolean needsLeftRight(Optional<TreeNode<T>> needer) {
        return needer.isPresent() && needer.get().getLeftNode().isPresent() && needer.get().getBalanceFactor() == -2 && needer.get().getLeftNode().get().getBalanceFactor() == 1;
    }

    private boolean needsRightLeft(Optional<TreeNode<T>> needer) {
        return needer.isPresent() && needer.get().getRightNode().isPresent() && needer.get().getBalanceFactor() == 2 && needer.get().getRightNode().get().getBalanceFactor() == -1;
    }

    public TreeNode<T> leftLeftRotate(TreeNode toBalance) {
        TreeNode<T> balanced = (TreeNode<T>) toBalance.getRightNode().get();
        toBalance.setRightNode(balanced.getLeftNode());
        balanced.setLeftNode(Optional.of(toBalance));
        return balanced;
    }

    private TreeNode<T> rightRightRotate(TreeNode toBalance) {
        TreeNode<T> balanced = (TreeNode<T>) toBalance.getLeftNode().get();
        toBalance.setLeftNode(balanced.getRightNode());
        balanced.setRightNode(Optional.of(toBalance));
        return balanced;
    }

    private TreeNode<T> leftRightRotate(TreeNode toBalance) {
        toBalance.setLeftNode(Optional.of(leftLeftRotate((TreeNode) toBalance.getLeftNode().get())));
        return rightRightRotate(toBalance);
    }

    private TreeNode<T> rightLeftRotate(TreeNode toBalance) {
        toBalance.setRightNode(Optional.of(rightRightRotate((TreeNode) toBalance.getRightNode().get())));
        return leftLeftRotate(toBalance);
    }

    public int getDepth() {
        return root.map(TreeNode::getDepth).orElse(0);
    }

    public int getCount() {
        return root.map(TreeNode::getCount).orElse(0);
    }

    private boolean isBalanced() {
        return root.map(TreeNode::isBalanced).orElse(true);
    }

    @Override
    public String toString() {
        return root.map(TreeNode::toString).orElse(null);
    }
}
