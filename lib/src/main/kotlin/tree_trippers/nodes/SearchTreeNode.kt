package tree_trippers.nodes

// TODO(Adding docs for methods)
public interface SearchTreeNode<K: Comparable<K>, V, N: SearchTreeNode<K, V, N>> {
    public fun getKeys(): List<K>
    public fun getValues(): List<V>
    public fun getChildren(): List<N>
}