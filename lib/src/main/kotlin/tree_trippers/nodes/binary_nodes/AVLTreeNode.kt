package tree_trippers.nodes.binary_nodes

import kotlin.math.max

public class AVLTreeNode<K: Comparable<K>, V>(key: K, value: V): AbstractBSTreeNode<K, V, AVLTreeNode<K, V>>(key, value) {
    public var height: Int = 1

    constructor(key: K, value: V, leftChild: AVLTreeNode<K, V>?, rightChild: AVLTreeNode<K, V>?) : this(key, value) {
        this.leftChild = leftChild
        this.rightChild = rightChild
        updateHeight()
    }

    private fun updateHeight() {
        // TODO(Create tests for it method)
        var maxChildrenHeight: Int = 0
        val children: List<AVLTreeNode<K, V>> = getChildren()
        children.forEach() {
            child -> maxChildrenHeight = max(maxChildrenHeight, child.height)
        }
        height = 1 + maxChildrenHeight
    }

    // TODO(Connect method `updateHeight` to setters of properties: `leftChild` and `rightChild`)
}