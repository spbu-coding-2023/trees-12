package tree_trippers.nodes.binary_nodes

public class RBTreeNode<K: Comparable<K>, V>(key: K, value: V): AbstractBSTreeNode<K, V, RBTreeNode<K, V>>(key, value) {
    var isRed: Boolean = false
}