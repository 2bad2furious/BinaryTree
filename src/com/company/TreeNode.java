package com.company;

import java.util.Optional;

import static com.company.StringUtils.repeat;

public class TreeNode<T extends Comparable> {
    private final T data;
    private Optional<TreeNode<T>> leftNode = Optional.empty();
    private Optional<TreeNode<T>> rightNode = Optional.empty();

    TreeNode(T data) {
        this.data = data;
    }

    TreeNode(T data, Optional<TreeNode<T>> leftNode, Optional<TreeNode<T>> rightNode) {
        this.data = data;
        this.leftNode = leftNode;
        this.rightNode = rightNode;
    }

    int getBalanceFactor() {
        return this.getRightDepth() - this.getLeftDepth();
    }

    boolean isBalanced() {
        return Math.abs(getBalanceFactor()) < 2;
    }

    boolean exists(T data) {
        if (data.compareTo(this.data) == 0) return true;
        if (data.compareTo(this.data) > 0) {
            return leftNode.map(treeNode -> treeNode.exists(data)).orElse(false);
        } else {
            return rightNode.map(treeNode -> treeNode.exists(data)).orElse(false);
        }
    }

    int getDepth() {
        return 1 + Math.max(getLeftDepth(), getRightDepth());
    }

    int getCount() {
        return 1 + leftNode.map(TreeNode::getCount).orElse(0) + rightNode.map(TreeNode::getCount).orElse(0);
    }

    private int getLeftDepth() {
        return leftNode.map(TreeNode::getDepth).orElse(0);
    }

    private int getRightDepth() {
        return rightNode.map(TreeNode::getDepth).orElse(0);
    }

    public T getData() {
        return data;
    }

    public Optional<TreeNode<T>> getLeftNode() {
        return leftNode;
    }

    public void setLeftNode(Optional<TreeNode<T>> leftNode) {
        this.leftNode = leftNode;
    }

    public Optional<TreeNode<T>> getRightNode() {
        return rightNode;
    }

    public void setRightNode(Optional<TreeNode<T>> rightNode) {
        this.rightNode = rightNode;
    }

    @Override
    public String toString() {
        return toString(0);
    }

    private String toString(int indents) {
        String indent = "\n" + repeat("\t", indents + 1);
        return "TreeNode{" +
                indent + "data=" + data +
                indent + "left=" + (leftNode.map(treeNode -> treeNode.toString(indents + 2)).orElse(null)) +
                indent + "right=" + (rightNode.map(treeNode -> treeNode.toString(indents + 2)).orElse(null)) +
                "\n" + repeat("\t", indents) + "}";
    }
}
