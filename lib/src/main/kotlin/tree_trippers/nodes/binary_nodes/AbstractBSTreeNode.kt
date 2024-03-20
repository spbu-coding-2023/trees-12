package tree_trippers.nodes.binary_nodes

import tree_trippers.nodes.SearchTreeNode

public abstract class AbstractBSTreeNode<K: Comparable<K>, V, N: AbstractBSTreeNode<K, V, N>>(
    public var key: K, // todo(val)
    public var value: V
): SearchTreeNode<K, V, N> {
    public var leftChild: N? = null
    public var rightChild: N? = null

    override fun getChildren(): List<N> {
        return listOfNotNull(leftChild, rightChild)
    }
}