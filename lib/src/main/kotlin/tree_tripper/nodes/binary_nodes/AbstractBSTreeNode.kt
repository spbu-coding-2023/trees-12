package tree_tripper.nodes.binary_nodes

import tree_tripper.nodes.SearchTreeNode
import tree_tripper.nodes.notNullNodeAction


/**
 * The class represents a node of the abstract binary search tree,
 * from which nodes of binary search trees are inherited.
 *
 * @param K the [key] type of node, supporting the [Comparable] interface
 * @param V the [value] type of node
 * @param N the node type
 */
public abstract class AbstractBSTreeNode<K: Comparable<K>, V, N: AbstractBSTreeNode<K, V, N>>(
    public val key: K,
    public var value: V
): SearchTreeNode<K, V, N> {
    public var leftChild: N? = null
    public var rightChild: N? = null

    override fun getChildren(): List<N> {
        return listOfNotNull(leftChild, rightChild)
    }

    public open fun dateEqual(other: Any?): Boolean {
        if (other == null) return false
        if (this == other) return true
        if (this.javaClass != other.javaClass) return false

        other as AbstractBSTreeNode<*, *, *>
        return this.key == other.key && this.value == other.value
    }

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (this === other) return true
        if (javaClass != other.javaClass) return false

        other as AbstractBSTreeNode<*, *, *>

        if (key != other.key) return false
        if (value != other.value) return false
        if (leftChild != other.leftChild) return false
        if (rightChild != other.rightChild) return false

        return true
    }

    override fun hashCode(): Int {
        var result = key.hashCode()
        result = 31 * result + (value?.hashCode() ?: 0)
        result = 31 * result + (leftChild?.hashCode() ?: 0)
        result = 31 * result + (rightChild?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "${this.javaClass.simpleName}(key=$key, value=$value)"
    }

    override fun toStringSimpleView(): String {
        return "($key, $value)"
    }

    override fun toStringWithSubtreeView(indent: Int, builder: StringBuilder) {
        notNullNodeAction(this.rightChild, Unit) {node -> node.toStringWithSubtreeView(indent + 1, builder)}
        builder.append("\t".repeat(indent)).append(this.toStringSimpleView()).append("\n")
        notNullNodeAction(this.leftChild, Unit) {node -> node.toStringWithSubtreeView(indent + 1, builder)}
    }

}
