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

    override fun toString(): String {
        return "${this.javaClass.simpleName}(key=$key, value=$value)"
    }

    override fun toStringSimpleView(): String {
        return "($key: $value)"
    }

    override fun toStringWithSubtreeView(indent: Int, builder: StringBuilder) {
        notNullNodeAction(this.rightChild, Unit) {node -> node.toStringWithSubtreeView(indent + 1, builder)}
        builder.append("\t".repeat(indent)).append(this.toStringSimpleView()).append("\n")
        notNullNodeAction(this.leftChild, Unit) {node -> node.toStringWithSubtreeView(indent + 1, builder)}
    }

}
