package tree_trippers.binary_trees

import tree_trippers.nodes.binary_nodes.AVLTreeNode


/**
 * AVLTree class represents a self-balancing binary search tree that maintains
 * the property of AVL tree, ensuring that the heights
 * of the two child subtrees of any node differ by at most one.
 *
 * @param K the type of the keys in the tree
 * @param V the value type associated with the key
 */
public class AVLTree<K: Comparable<K>, V>: AbstractBSTree<K, V, AVLTreeNode<K, V>>() {

    override fun createNode(key: K, value: V): AVLTreeNode<K, V> {
        return AVLTreeNode(key, value)
    }

    override fun balanceTree(node: AVLTreeNode<K, V>): AVLTreeNode<K, V> {
        node.updateHeight()
        return balance(node)
    }

    /**
     * @param node the AVLTreeNode for which to calculate the balance factor
     * @return balance factor (height difference of his children) of the [node]
     */
    private fun balanceFactor(node: AVLTreeNode<K, V>?): Int {
        return (node?.rightChild?.height ?: 0) - (node?.leftChild?.height ?: 0)
    }

    /**
     * Defines and calls the method(s) for balancing the current [node].
     *
     * @param node the node to balance in the AVL tree
     * @return the root of the rebalanced subtree or a [node] if balance is not required
     */
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

    /**
     * Performs a left rotation (counterclockwise) of the tree with the [node] as the root.
     * Updates the heights of the affected nodes.
     *
     * @param node the node to perform a left rotation on
     * @returns the root of the rotated subtree
     */
    private fun rotateLeft(node: AVLTreeNode<K, V>): AVLTreeNode<K, V> {
        val nodeSwapped = node.rightChild ?: return node
        node.rightChild = nodeSwapped.leftChild
        nodeSwapped.leftChild = node

        node.updateHeight()
        nodeSwapped.updateHeight()
        return nodeSwapped
    }

    /**
     * Performs a right rotation (clockwise) of the tree with the [node] as the root.
     * Updates the heights of the affected nodes.
     *
     * @param node the node to perform a right rotation on
     * @returns the root of the rotated subtree
     */
    private fun rotateRight(node: AVLTreeNode<K, V>): AVLTreeNode<K, V> {
        val nodeSwapped = node.leftChild ?: return node
        node.leftChild = nodeSwapped.rightChild
        nodeSwapped.rightChild = node

        node.updateHeight()
        nodeSwapped.updateHeight()
        return nodeSwapped
    }

}
