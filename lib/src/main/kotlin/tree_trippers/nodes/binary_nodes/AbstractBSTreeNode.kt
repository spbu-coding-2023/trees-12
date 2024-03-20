package tree_trippers.nodes.binary_nodes

import tree_trippers.nodes.SearchTreeNode
import tree_trippers.nodes.notNullNodeAction

public abstract class AbstractBSTreeNode<K: Comparable<K>, V, N: AbstractBSTreeNode<K, V, N>>(
    public var key: K, // todo(val)
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

    override fun toSimpleViewString(): String {
        return "($key, $value)"
    }

    override fun toTreeViewString(indent: Int, sb: StringBuilder) {
        notNullNodeAction(this.leftChild, Unit) {node -> node.toTreeViewString(indent + 1, sb)}
        sb.append("\t".repeat(indent)).append(this.toSimpleViewString()).append("\n")
        notNullNodeAction(this.rightChild, Unit) {node -> node.toTreeViewString(indent + 1, sb)}
    }
}