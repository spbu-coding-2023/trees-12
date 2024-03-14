package tree_trippers.nodes.binary_nodes

import tree_trippers.nodes.SearchTreeNode

public abstract class AbstractBSTreeNode<K: Comparable<K>, V, N: AbstractBSTreeNode<K, V, N>>(val key: K, var value: V): SearchTreeNode<K, V, N> {
    public var leftChild: N? = null
        set(value) {
            field = value
            updateNodeData()
        }

    public var rightChild: N? = null
        set(value) {
            field = value
            updateNodeData()
        }

    constructor(key: K, value: V, leftChild: N?, rightChild: N?) : this(key, value) {
        this.leftChild = leftChild
        this.rightChild = rightChild
    }

    override fun getKeys(): List<K> {
        return listOf(key)
    }

    override fun getValues(): List<V> {
        return listOf(value)
    }

    override fun getChildren(): List<N> {
        return listOfNotNull(leftChild, rightChild)
    }

    protected abstract fun updateNodeData(): Unit
}