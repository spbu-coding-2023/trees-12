package tree_tripper.nodes.binary_nodes


public class BSTreeNode<K: Comparable<K>, V>(
    key: K,
    value: V
): AbstractBSTreeNode<K, V, BSTreeNode<K, V>>(key, value)
