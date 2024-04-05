package tree_tripper.binary_trees

import tree_tripper.SearchTree
import tree_tripper.iterators.BinarySearchTreeIterator
import tree_tripper.iterators.IterationOrders
import tree_tripper.nodes.binary_nodes.AbstractBSTreeNode
import tree_tripper.nodes.notNullNodeAction


/**
 * The class represents an abstract binary search tree,
 * from which binary search trees are inherited.
 *
 * @param K the key type in a tree, supporting the [Comparable] interface
 * @param V the value type in a tree
 * @param N the node type in a tree
 */
public abstract class AbstractBSTree<K: Comparable<K>, V, N: AbstractBSTreeNode<K, V, N>>: SearchTree<K, V> {
    protected var root: N? = null
        private set
    private var size: Int = 0

    override fun insert(key: K, value: V) {
        insert(key, value, permissionUpdate = true)
    }

    override fun insertIfAbsent(key: K, value: V): Boolean {
        return insert(key, value, permissionUpdate = false)
    }

    override fun set(key: K, value: V) {
        insert(key, value)
    }

    override fun remove(key: K): V? {
        val resultRemove = removeNode(root, key)
        updateRoot(resultRemove.first)
        if (resultRemove.second != null) size--
        return resultRemove.second
    }

    override fun removeOrDefault(key: K, defaultValue: V): V {
        return remove(key) ?: defaultValue
    }

    override fun search(key: K): V? {
        return searchNode(key)?.value
    }

    override fun searchOrDefault(key: K, defaultValue: V): V {
        return search(key) ?: defaultValue
    }

    override fun contains(key: K): Boolean {
        return search(key) != null
    }

    override fun get(key: K): V? {
        return search(key)
    }

    override fun getMaxInSubtree(key: K): Pair<K, V>? {
        val resultSearch = getMaxNodeInSubtree(searchNode(key)) ?: return null
        return Pair(resultSearch.key, resultSearch.value)
    }

    override fun getMax(): Pair<K, V>? {
        return notNullNodeAction(root, null) { node -> getMaxInSubtree(node.key) }
    }

    override fun getMinInSubtree(key: K): Pair<K, V>? {
        val resultSearch = getMinNodeInSubtree(searchNode(key)) ?: return null
        return Pair(resultSearch.key, resultSearch.value)
    }

    override fun getMin(): Pair<K, V>? {
        return notNullNodeAction(root, null) { node -> getMinInSubtree(node.key) }
    }

    override fun getSize(): Int {
        return size
    }

    override fun iterator(): BinarySearchTreeIterator<K, V, N> {
        return iterator(IterationOrders.WIDTH_ORDER)
    }

    override fun iterator(order: IterationOrders): BinarySearchTreeIterator<K, V, N> {
        return BinarySearchTreeIterator(root, order)
    }

    override fun forEach(order: IterationOrders, action: (Pair<K, V>) -> Unit) {
        val treeIterator: BinarySearchTreeIterator<K, V, N> = iterator(order)
        while (treeIterator.hasNext())
            action(treeIterator.next())
    }

    override fun toString(): String {
        return toString(IterationOrders.WIDTH_ORDER)
    }

    override fun toString(order: IterationOrders): String {
        val builder = StringBuilder()
        this.forEach(order) { pair: Pair<K, V> -> builder.append("${pair.first}: ${pair.second}, ") }
        if (builder.isNotEmpty())
            repeat(2) { builder.deleteCharAt(builder.length - 1) } // remove ", " in the last pair
        return "${this.javaClass.simpleName}($builder)"
    }

    override fun toStringWithTreeView(): String {
        val builder = StringBuilder()
        notNullNodeAction(root, Unit) { node -> node.toStringWithSubtreeView(0, builder) }
        return "${this.javaClass.simpleName}(\n$builder)"
    }

    /**
     * Returns a new [N] node with the specified [value] with the specified [key].
     */
    protected abstract fun createNode(key: K, value: V): N

    /**
     * Changes the root to a given [node].
     */
    protected open fun updateRoot(node: N?) {
        root = node
    }

    /**
     * Balances subtree with a given [node] at the top.
     */
    protected open fun balanceTree(node: N): N {
        return node
    }

    /**
     * Inserts the specified [value] with the specified [key] in a tree or
     * updates [value] if [permissionUpdate] is true
     * @return true if the specified [value] with the specified [key] was inserted in a tree,
     * false if a tree was not modified.
     */
    private fun insert(key: K, value: V, permissionUpdate: Boolean): Boolean {
        val insertResult: Pair<N, Boolean> = insertNode(root, key, value, permissionUpdate)
        updateRoot(insertResult.first)
        if (insertResult.second) size++
        return insertResult.second
    }

    /**
     * Add recursively the node with the specified [value] with the specified [key] in a tree or
     * updates [value] if [permissionUpdate] is true and balances tree on every call.
     * @return a pair of a new or balanced [N] node, and true if the specified [value] with
     * the specified [key] was inserted in a tree, false if not.
     */
    private fun insertNode(node: N?, key: K, value: V, permissionUpdate: Boolean): Pair<N, Boolean> {
        if (node == null) return Pair(createNode(key, value), true)

        val resultInsert: Pair<N, Boolean>
        val resultCompare: Int = key.compareTo(node.key)
        if (resultCompare < 0) {
            resultInsert = insertNode(node.leftChild, key, value, permissionUpdate)
            node.leftChild = resultInsert.first
        } else if (resultCompare > 0) {
            resultInsert = insertNode(node.rightChild, key, value, permissionUpdate)
            node.rightChild = resultInsert.first
        } else {
            if (permissionUpdate) node.value = value
            return Pair(node, false)
        }

        return Pair(balanceTree(node), resultInsert.second)
    }

    /**
     * Removes recursively the node with a given [key] and balances tree on every call.
     * @return a pair of a balanced [N] node or null, and [V] value corresponding the given [key]
     * if a node was removed, null if not.
     */
    protected open fun removeNode(node: N?, key: K): Pair<N?, V?> {
        if (node == null) return Pair(null, null)

        val resultRemove: Pair<N?, V?>
        val resultCompare: Int = key.compareTo(node.key)
        if (resultCompare < 0) {
            resultRemove = removeNode(node.leftChild, key)
            node.leftChild = resultRemove.first
        } else if (resultCompare > 0) {
            resultRemove = removeNode(node.rightChild, key)
            node.rightChild = resultRemove.first
        } else {
            val nodeSubstitutive: N?
            if (node.leftChild == null || node.rightChild == null) {
                nodeSubstitutive = node.leftChild ?: node.rightChild
                return Pair(nodeSubstitutive, node.value)
            }
            nodeSubstitutive = getMaxNodeInSubtree(node.leftChild) as N
            node.leftChild = removeNode(node.leftChild, nodeSubstitutive.key).first
            nodeSubstitutive.rightChild = node.rightChild
            nodeSubstitutive.leftChild = node.leftChild
            return Pair(balanceTree(nodeSubstitutive), node.value)
        }

        return Pair(balanceTree(node), resultRemove.second)
    }

    /**
     * Searches the node with a given key.
     * @return the found node or null if the node is not contained in a tree.
     */
    private fun searchNode(key: K): N? {
        var nodeCurrent: N? = root ?: return null

        while (nodeCurrent != null) {
            val resultCompare = key.compareTo(nodeCurrent.key)
            if (resultCompare < 0)
                nodeCurrent = nodeCurrent.leftChild
            else if (resultCompare > 0)
                nodeCurrent = nodeCurrent.rightChild
            else
                return nodeCurrent
        }
        return null
    }

    /**
     * Returns the [N] node with the max key in subtree with a given [node] in root, or null
     * if such a [node] is not contained in a tree.
     */
    protected fun getMaxNodeInSubtree(node: N?): N? {
        if (node == null) return null

        var nodeCurrent: N = node
        while (true)
            nodeCurrent = nodeCurrent.rightChild ?: break
        return nodeCurrent
    }

    /**
     * Returns the [N] node with the min key in subtree with a given [node] in root, or null
     * if such a [node] is not contained in a tree.
     */
    protected fun getMinNodeInSubtree(node: N?): N? {
        if (node == null) return null

        var nodeCurrent: N = node
        while (true)
            nodeCurrent = nodeCurrent.leftChild ?: break
        return nodeCurrent
    }

}
