package tree_trippers.binary_trees

import tree_trippers.SearchTree
import tree_trippers.nodes.binary_nodes.AbstractBSTreeNode

abstract class AbstractBSTree<K: Comparable<K>, V, N: AbstractBSTreeNode<K, V, N>>: SearchTree<K, V, N> {
}