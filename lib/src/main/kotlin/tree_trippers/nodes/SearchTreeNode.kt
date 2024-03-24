package tree_trippers.nodes


public interface SearchTreeNode<K: Comparable<K>, V, N: SearchTreeNode<K, V, N>> {

    public fun getChildren(): List<N>

    public fun toStringSimpleView(): String

    public fun toStringWithSubtreeView(indent: Int, builder: StringBuilder): Unit

}
