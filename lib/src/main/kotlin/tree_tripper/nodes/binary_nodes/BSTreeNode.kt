package tree_tripper.nodes.binary_nodes


/**
 * The class represents a node of the binary search tree.
 *
 * @param K the key type of the node, supporting the [Comparable] interface
 * @param V the value type of the node
 */
public class BSTreeNode<K : Comparable<K>, V>(
    key: K,
    value: V
) : AbstractBSTreeNode<K, V, BSTreeNode<K, V>>(key, value) {

    public constructor(key: K, value: V, leftChild: BSTreeNode<K, V>?, rightChild: BSTreeNode<K, V>?) : this(
        key,
        value
    ) {
        this.leftChild = leftChild
        this.rightChild = rightChild
    }
}
