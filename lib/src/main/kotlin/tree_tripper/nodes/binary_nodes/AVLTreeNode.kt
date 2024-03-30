package tree_tripper.nodes.binary_nodes

import kotlin.math.max

/**
 * AVLTreeNode class represents a node in an AVL tree, containing a key-value pair and additional
 * information for maintaining the AVL property such as the height of the node.
 *
 * @param K the key type that implements the Comparable interface
 * @param V the value type associated with the key
 * @property height the height of the [AVLTreeNode] in the AVL tree
 */
public class AVLTreeNode<K: Comparable<K>, V>(
    key: K,
    value: V
): AbstractBSTreeNode<K, V, AVLTreeNode<K, V>>(key, value) {

    public constructor(
        key: K, value: V, height: Int,
        leftChild: AVLTreeNode<K, V>?,
        rightChild: AVLTreeNode<K, V>?
    ) : this(key, value) {
        this.height = height
        this.leftChild = leftChild
        this.rightChild = rightChild
    }

    /** The height of the node in the AVL tree, initialized to 1. */
    public var height: Int = 1

    /** Updates height of the node in AVL tree based on the heights of its left and right child subtrees. */
    public fun updateHeight() {
        val leftHeight = this.leftChild?.height ?: 0
        val rightHeight = this.rightChild?.height ?: 0
        height = (max(leftHeight, rightHeight) + 1)
    }

}
