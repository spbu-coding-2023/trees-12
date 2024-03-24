package tree_trippers.nodes.binary_nodes

import kotlin.math.max


public class AVLTreeNode<K: Comparable<K>, V>(
    key: K,
    value: V
): AbstractBSTreeNode<K, V, AVLTreeNode<K, V>>(key, value) {
    public var height: Int = 1

    public fun updateHeight() {
        val leftHeight = this.leftChild?.height ?: 0
        val rightHeight = this.rightChild?.height ?: 0
        height = (max(leftHeight, rightHeight) + 1)
    }

}
