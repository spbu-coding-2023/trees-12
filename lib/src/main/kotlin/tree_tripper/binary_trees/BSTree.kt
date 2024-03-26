package tree_tripper.binary_trees

import tree_tripper.nodes.binary_nodes.BSTreeNode


public class BSTree<K: Comparable<K>, V>: AbstractBSTree<K, V, BSTreeNode<K, V>>() {

    override fun createNode(key: K, value: V): BSTreeNode<K, V> {
        return BSTreeNode(key, value)
    }

}
