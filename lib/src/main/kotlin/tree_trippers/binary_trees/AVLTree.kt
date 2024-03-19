package tree_trippers.binary_trees

import tree_trippers.nodes.binary_nodes.AVLTreeNode

public class AVLTree<K: Comparable<K>, V>: AbstractBSTree<K, V, AVLTreeNode<K, V>>() {

    private fun getBalance(node: AVLTreeNode<K, V> ): Int {
        return (node.rightChild?.height ?: 0) - (node.leftChild?.height ?: 0)
    }

    private fun swapNodes(node1: AVLTreeNode<K, V>, node2: AVLTreeNode<K, V>) {
        val node1Key = node1.key
        node1.key = node2.key
        node2.key = node1Key
        val node1Value = node1.value
        node1.value = node2.value
        node2.value = node1Value
    }

    private fun AVLRightRotation(node: AVLTreeNode<K, V> ): AVLTreeNode<K, V> {
        node.leftChild?.let { swapNodes(node, it) }
        val temp = node.rightChild
        node.rightChild = node.leftChild
        node.leftChild = node.rightChild?.leftChild
        node.rightChild?.leftChild = node.rightChild?.rightChild
        node.rightChild?.rightChild = temp
        node.rightChild?.updateHeight()
        node.updateHeight()
        return node.leftChild ?: node
    }

    private fun AVLLeftRotation(node: AVLTreeNode<K, V> ): AVLTreeNode<K, V> {
        node.rightChild?.let { swapNodes(node, it) }
        val temp = node.leftChild
        node.leftChild = node.rightChild
        node.rightChild = node.leftChild?.rightChild
        node.leftChild?.rightChild = node.leftChild?.leftChild
        node.leftChild?.leftChild = temp
        node.leftChild?.updateHeight()
        node.updateHeight()
        return node.rightChild ?: node
    }

    private fun balance(node: AVLTreeNode<K, V> ): AVLTreeNode<K, V> {
        when (getBalance(node)) {
            -2 -> {
                if (node.leftChild?.let { getBalance(it) } == 1) {
                    AVLLeftRotation(node.leftChild!!)
                }
                AVLRightRotation(node)
            }
            2 -> {
                if (node.rightChild?.let { getBalance(it) } == -1) {
                    AVLRightRotation(node.rightChild!!)
                }
                AVLLeftRotation(node)
            }
        }
        return node
    }
}