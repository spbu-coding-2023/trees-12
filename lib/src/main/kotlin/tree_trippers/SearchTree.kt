package tree_trippers

import tree_trippers.iterators.IterationOrders


public interface SearchTree<K: Comparable<K>, V>: Iterable<Pair<K, V>> {

    public fun insert(key: K, value: V)

    public fun insertIfAbsent(key: K, value: V): Boolean

    public fun remove(key: K): V?

    public fun removeWithDefault(key: K, defaultValue: V): V

    public fun search(key: K): V?

    public fun searchOrDefault(key: K, defaultValue: V): V

    public fun isContains(key: K): Boolean

    public fun getMaxDescendant(key: K): Pair<K, V>?

    public fun getMax(): Pair<K, V>?

    public fun getMinDescendant(key: K): Pair<K, V>?

    public fun getMin(): Pair<K, V>?

    public fun getSize(): Int

    public fun iterator(order: IterationOrders): Iterator<Pair<K, V>>

    public fun forEach(order: IterationOrders, action: (Pair<K, V>) -> Unit)

    public fun toString(order: IterationOrders): String

    public fun toStringWithTreeView(): String

}
