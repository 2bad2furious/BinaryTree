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

        AVLRotation rotation = getNeededRotation(node);
        if (rotation != AVLRotation.NONE) {
            TreeNode newroot = null;
            int factor = node.get().getBalanceFactor();
            switch (rotation) {
                case LL: {
                    newroot = leftLeftRotate(node.get());
                    break;
                }
                case RR: {
                    newroot = rightRightRotate(node.get());
                    break;
                }
                case LR: {
                    newroot = leftRightRotate(node.get());
                    break;
                }
                case RL: {
                    newroot = rightLeftRotate(node.get());
                    break;
                }
            }
            node = Optional.of(newroot);
        }
        return node;
    }

    private AVLRotation getNeededRotation(Optional<TreeNode<T>> needer) {
        if (!needer.isPresent()) return AVLRotation.NONE;
        if (needsLeftLeft(needer)) return AVLRotation.LL;
        if (needsRightRight(needer)) return AVLRotation.RR;
        if (needsLeftRight(needer)) return AVLRotation.LR;
        if (needsRightLeft(needer)) return AVLRotation.RL;
        return AVLRotation.NONE;
    }

    private boolean needsLeftLeft(Optional<TreeNode<T>> needer) {
        return needer.filter(tTreeNode -> tTreeNode.getBalanceFactor() == 2 && tTreeNode.getLeftNode().get().getBalanceFactor() == 1).isPresent();
    }

    private boolean needsRightRight(Optional<TreeNode<T>> needer) {
        return needer.filter(tTreeNode -> tTreeNode.getBalanceFactor() == -2 && tTreeNode.getRightNode().get().getBalanceFactor() == -1).isPresent();
    }

    private boolean needsLeftRight(Optional<TreeNode<T>> needer) {
        return needer.filter(tTreeNode -> tTreeNode.getBalanceFactor() == -2 && tTreeNode.getLeftNode().get().getBalanceFactor() == 1).isPresent();
    }

    private boolean needsRightLeft(Optional<TreeNode<T>> needer) {
        return needer.filter(tTreeNode -> tTreeNode.getBalanceFactor() == 2 && tTreeNode.getRightNode().get().getBalanceFactor() == -1).isPresent();
    }

    public TreeNode<T> leftLeftRotate(TreeNode toBalance) {
        TreeNode<T> balanced = (TreeNode<T>) toBalance.getLeftNode().get();
        toBalance.setLeftNode(balanced.getRightNode());
        balanced.setRightNode(Optional.of(toBalance));
        return balanced;
    }

    private TreeNode<T> rightRightRotate(TreeNode toBalance) {
        TreeNode<T> balanced = (TreeNode<T>) toBalance.getRightNode().get();
        toBalance.setRightNode(balanced.getLeftNode());
        balanced.setLeftNode(Optional.of(toBalance));
        return balanced;
    }

    private TreeNode<T> leftRightRotate(TreeNode toBalance) {
        toBalance.setLeftNode(Optional.of(rightRightRotate((TreeNode) toBalance.getLeftNode().get())));
        return leftLeftRotate(toBalance);
    }

    private TreeNode<T> rightLeftRotate(TreeNode toBalance) {
        toBalance.setRightNode(Optional.of(leftLeftRotate((TreeNode) toBalance.getRightNode().get())));
        return rightRightRotate(toBalance);
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
