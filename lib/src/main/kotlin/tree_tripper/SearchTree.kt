package tree_tripper

import tree_tripper.iterators.IterationOrders


/**
 * The interface represents a search tree, that inherits from [Iterable] interface.
 *
 * @param K the key type in a tree, supporting the [Comparable] interface
 * @param V the value type in a tree
 */
public interface SearchTree<K: Comparable<K>, V>: Iterable<Pair<K, V>> {

    /**
     * Inserts or updates the specified [value] with the specified [key] in a tree.
     */
    public fun insert(key: K, value: V)

    /**
     * Inserts the specified [value] with the specified [key] in a tree.
     * @return true if the specified [value] with the specified [key] was inserted in a tree,
     * false if a tree was not modified.
     */
    public fun insertIfAbsent(key: K, value: V): Boolean

    /**
     * Associates the specified [value] with the specified [key] in a tree.
     */
    public operator fun set(key: K, value: V)

    /**
     * Removes a given [key] and its corresponding value from a tree.
     * @return the removed value associated with a given [key], or
     * null if such a [key] does not present in a tree.
     */
    public fun remove(key: K): V?

    /**
     * Removes a given [key] and its corresponding value from a tree.
     * @return the removed value associated with a given [key], or
     * the [defaultValue] if such a [key] does not present in a tree.
     */
    public fun removeOrDefault(key: K, defaultValue: V): V

    /**
     * Searches the value associated with a given [key].
     * @return the value associated with a given [key], or
     * null if such a [key] does not present in a tree.
     */
    public fun search(key: K): V?

    /**
     * Searches the value associated with a given [key].
     * @return the value associated with a given [key], or
     * the [defaultValue] if such a [key] does not present in a tree.
     */
    public fun searchOrDefault(key: K, defaultValue: V): V

    /**
     * Checks if a given [key] is contained in a tree.
     */
    public fun contains(key: K): Boolean

    /**
     * Returns the value associated with a given [key], or
     * null if such a [key] does not present in a tree.
     */
    public operator fun get(key: K): V?

    /**
     * Returns the key/value pair with the max key, or null if a tree is empty.
     */
    public fun getMax(): Pair<K, V>?

    /**
     * Returns the key/value pair with the max key in subtree with a given [key] in root, or
     * null if such a [key] does not present in a tree.
     */
    public fun getMaxInSubtree(key: K): Pair<K, V>?

    /**
     * Returns the key/value pair with the min key, or null if a tree is empty.
     */
    public fun getMin(): Pair<K, V>?

    /**
     * Returns the key/value pair with the min key in subtree with a given [key] in root, or
     * null if such a [key] does not present in a tree.
     */
    public fun getMinInSubtree(key: K): Pair<K, V>?

    // Iterator
    public fun iterator(order: IterationOrders): Iterator<Pair<K, V>>

    public fun forEach(order: IterationOrders, action: (Pair<K, V>) -> Unit)

    /**
     * Returns a string with a transformed tree according to the [order].
     */
    public fun toString(order: IterationOrders): String

    /**
     * Returns a string with a transformed tree according to the tree structure.
     */
    public fun toStringWithTreeView(): String

}
