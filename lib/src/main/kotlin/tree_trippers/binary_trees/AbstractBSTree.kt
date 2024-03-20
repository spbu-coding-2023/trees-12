package tree_trippers.binary_trees

import tree_trippers.SearchTree
import tree_trippers.iterators.BinarySearchTreeIterator
import tree_trippers.iterators.IterationOrders
import tree_trippers.nodes.binary_nodes.AbstractBSTreeNode
import tree_trippers.nodes.binary_nodes.BSTreeNode
import tree_trippers.nodes.notNullNodeAction

public abstract class AbstractBSTree<K : Comparable<K>, V, N : AbstractBSTreeNode<K, V, N>> :
    SearchTree<K, V, N> {
    protected var root: N? = null
    protected var size: Int = 0

    override fun search(key: K): V? {
        return searchNode(key)?.value
    }

    override fun searchOrDefault(key: K, defaultValue: V): V {
        return search(key) ?: defaultValue
    }

    override fun isContains(key: K): Boolean {
        return (search(key) != null)
    }

    override fun removeWithDefault(key: K, defaultValue: V): V {
        return remove(key) ?: defaultValue
    }

    private fun searchNode(key: K): N? {
        var nodeCurrent = this.root

        while (nodeCurrent != null) {
            if (key == nodeCurrent.key) {
                return nodeCurrent
            } else if (key < nodeCurrent.key) {
                if (nodeCurrent.leftChild == null)
                    break

                nodeCurrent = nodeCurrent.leftChild
            } else {
                if (nodeCurrent.rightChild == null)
                    break

                nodeCurrent = nodeCurrent.rightChild
            }
        }

        return null
    }

    override fun maxDescendant(key: K): V? {
        var resultSearch = searchNode(key)

        while (resultSearch != null) {
            if (resultSearch.rightChild == null)
                return resultSearch.value

            resultSearch = resultSearch.rightChild
        }

        return null
    }

    override fun max(): V? {
        if (this.root != null)
            return maxDescendant(this.root!!.key) // todo(!!)
        return null
    }

    override fun minDescendant(key: K): V? {
        var resultSearch = searchNode(key)

        while (resultSearch != null) {
            if (resultSearch.leftChild == null)
                return resultSearch.value

            resultSearch = resultSearch.leftChild
        }

        return null
    }

    override fun min(): V? {
        if (this.root != null)
            return minDescendant(this.root!!.key) // todo(!!)
        return null
    }

    override fun size(): Int {
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
        while (treeIterator.hasNext()) {
            action(treeIterator.next())
        }
    }

    override fun toString(): String {
        return toString(IterationOrders.WIDTH_ORDER)
    }

    override fun toString(order: IterationOrders): String {
        val sb = StringBuilder()
        this.forEach(order) { pair: Pair<K, V> ->
            sb.append("${pair.first}: ${pair.second}, ")
        }
        return "${this.javaClass.simpleName}($sb)"
    }

    override fun toTreeViewString(): String {
        val sb = StringBuilder()
        notNullNodeAction(root, Unit) {node -> node.toTreeViewString(0, sb)}
        return "${this.javaClass.simpleName}(\n$sb)"
    }

}