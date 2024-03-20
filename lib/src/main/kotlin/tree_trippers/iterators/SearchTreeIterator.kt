package tree_trippers.iterators

import tree_trippers.nodes.SearchTreeNode

interface SearchTreeIterator<K: Comparable<K>, V, N: SearchTreeNode<K, V, N>>: Iterator<Pair<K, V>> {

}