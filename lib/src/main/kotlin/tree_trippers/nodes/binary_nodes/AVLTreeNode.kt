package tree_trippers.nodes.binary_nodes

import kotlin.math.max

public class AVLTreeNode<K: Comparable<K>, V>(key: K, value: V): AbstractBSTreeNode<K, V, AVLTreeNode<K, V>>(key, value) {
    public var height: Int = 1

    private fun updateHeight(): Unit{
        // TODO(Create tests for it method)
        var maxChildHeight: Int = 0
        val children: List<AVLTreeNode<K, V>> = getChildren()
        children.forEach() {
            child -> maxChildHeight = max(maxChildHeight, child.height)
        }
        height = 1 + maxChildHeight
    }

    protected override fun updateNodeData(): Unit {
        return updateHeight()
    }

}