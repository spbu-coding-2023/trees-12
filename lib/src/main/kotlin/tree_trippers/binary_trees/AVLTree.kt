package tree_trippers.binary_trees

import tree_trippers.nodes.binary_nodes.AVLTreeNode


public class AVLTree<K: Comparable<K>, V>: AbstractBSTree<K, V, AVLTreeNode<K, V>>() {

    override fun createNode(key: K, value: V): AVLTreeNode<K, V> {
        return AVLTreeNode(key, value)
    }

    override fun balanceTree(node: AVLTreeNode<K, V>): AVLTreeNode<K, V> {
        node.updateHeight()
        return balance(node)
    }

    private fun balanceFactor(node: AVLTreeNode<K, V>?): Int {
        return (node?.rightChild?.height ?: 0) - (node?.leftChild?.height ?: 0)
    }

    private fun balance(node: AVLTreeNode<K, V>): AVLTreeNode<K, V> {
        when (balanceFactor(node)) {
            -2 -> {
                if (balanceFactor(node.leftChild) == 1)
                    node.leftChild = rotateLeft(node.leftChild as AVLTreeNode)
                return rotateRight(node)
            }
            2 -> {
                if (balanceFactor(node.rightChild) == -1)
                    node.rightChild = rotateRight(node.rightChild as AVLTreeNode)
                return rotateLeft(node)
            }
            else -> return node
        }
    }

    private fun rotateLeft(node: AVLTreeNode<K, V>): AVLTreeNode<K, V> {
        val nodeSwapped = node.rightChild ?: return node
        node.rightChild = nodeSwapped.leftChild
        nodeSwapped.leftChild = node

        node.updateHeight()
        nodeSwapped.updateHeight()
        return nodeSwapped
    }

    private fun rotateRight(node: AVLTreeNode<K, V>): AVLTreeNode<K, V> {
        val nodeSwapped = node.leftChild ?: return node
        node.leftChild = nodeSwapped.rightChild
        nodeSwapped.rightChild = node

        node.updateHeight()
        nodeSwapped.updateHeight()
        return nodeSwapped
    }

}
