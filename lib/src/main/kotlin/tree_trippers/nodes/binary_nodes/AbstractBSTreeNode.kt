package tree_trippers.nodes.binary_nodes

import tree_trippers.nodes.SearchTreeNode
import tree_trippers.nodes.notNullNodeAction


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
        return "($key, $value)"
    }

    override fun toStringWithSubtreeView(indent: Int, builder: StringBuilder) {
        notNullNodeAction(this.rightChild, Unit) {node -> node.toStringWithSubtreeView(indent + 1, builder)}
        builder.append("\t".repeat(indent)).append(this.toStringSimpleView()).append("\n")
        notNullNodeAction(this.leftChild, Unit) {node -> node.toStringWithSubtreeView(indent + 1, builder)}
    }

}
