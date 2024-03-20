package tree_trippers.nodes

// TODO(Adding docs for methods)
public interface SearchTreeNode<K: Comparable<K>, V, N: SearchTreeNode<K, V, N>> {
    public fun getChildren(): List<N>
}