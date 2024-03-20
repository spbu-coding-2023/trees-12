@file:Suppress("UNUSED")

package tree_trippers.binary_trees

import tree_trippers.nodes.binary_nodes.RBTreeNode

public class RBTree<K: Comparable<K>, V>: AbstractBSTree<K, V, RBTreeNode<K, V>>() {
    override fun insert(key: K, value: V) {
        TODO("Not yet implemented")
    }

    override fun remove(key: K): V? {
        TODO("Not yet implemented")
    }
}