package tree_trippers.binary_trees

import tree_trippers.SearchTree
import tree_trippers.nodes.binary_nodes.AbstractBSTreeNode

abstract class AbstractBSTree<K: Comparable<K>, V, N: AbstractBSTreeNode<K, V, N>>: SearchTree<K, V, N> {
    protected var root: N? = null
    private var size: Int = 0

    override fun size(): Int {
        return size
    }
}