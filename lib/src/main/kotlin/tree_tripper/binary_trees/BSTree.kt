package tree_tripper.binary_trees

import tree_tripper.nodes.binary_nodes.BSTreeNode


/**
 * The class represents the binary search tree.
 *
 * @param K the key type in the tree, supporting the [Comparable] interface
 * @param V the value type in the tree
 */
public open class BSTree<K: Comparable<K>, V>: AbstractBSTree<K, V, BSTreeNode<K, V>>() {

    override fun createNode(key: K, value: V): BSTreeNode<K, V> {
        return BSTreeNode(key, value)
    }

}
