package tree_trippers

import tree_trippers.iterators.IterationOrders
import tree_trippers.iterators.SearchTreeIterator
import tree_trippers.nodes.SearchTreeNode

public interface SearchTree<K : Comparable<K>, V, N : SearchTreeNode<K, V, N>>: Iterable<Pair<K, V>> {
    public fun search(key: K): V?
    public fun searchOrDefault(key: K, defaultValue: V): V
    public fun isContains(key: K): Boolean
    public fun insert(key: K, value: V)
    public fun remove(key: K): V?
    public fun removeWithDefault(key: K, defaultValue: V): V
    public fun maxDescendant(key: K): V?
    public fun max(): V?
    public fun minDescendant(key: K): V?
    public fun min(): V?
    public fun size(): Int
    public fun iterator(order: IterationOrders): SearchTreeIterator<K, V, N>
    public fun forEach(order: IterationOrders, action: (Pair<K, V>) -> Unit)
    public fun toString(order: IterationOrders): String
    public fun toTreeViewString(): String
}
