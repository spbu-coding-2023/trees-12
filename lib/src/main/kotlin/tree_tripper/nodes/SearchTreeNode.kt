package tree_tripper.nodes


/**
 * The interface represents a node of a search tree.
 *
 * @param K the key type of node, supporting the [Comparable] interface
 * @param V the value type of node
 * @param N the node type
 */
public interface SearchTreeNode<K: Comparable<K>, V, N: SearchTreeNode<K, V, N>> {

    /**
     * Returns a [N] list of not null children of a node.
     */
    public fun getChildren(): List<N>

    /**
     * Returns a string with a transformed to the simple view node.
     */
    public fun toStringSimpleView(): String

    /**
     * Transforms a node to the [builder] of the subtree structure with a some [indent].
     */
    public fun toStringWithSubtreeView(indent: Int, builder: StringBuilder): Unit

}
