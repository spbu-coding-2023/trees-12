package tree_trippers

import tree_trippers.nodes.SearchTreeNode

public interface SearchTree<K : Comparable<K>, V, N : SearchTreeNode<K, V, N>> {
    public fun search(key: K): V?
    public fun searchOrDefault(key: K, defaultValue: V): V
    public fun isContains(key: K): Boolean
    public fun insert(key: K, value: V)
    public fun remove(key: K): V?
    public fun removeWithDefault(key: K, defaultValue: V): V
    public fun keys(): List<K>
    public fun values(): List<V>
    public fun maxDescendant(key: K): V?
    public fun max(): V?
    public fun minDescendant(key: K): V?
    public fun min(): V?
    public fun size(): Int
}
