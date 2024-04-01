package tree_tripper.iterators

import tree_tripper.nodes.binary_nodes.AbstractBSTreeNode
import java.util.LinkedList
import java.util.Queue


/**
 * A generic binary search tree iterator that can iterate over a binary search tree in different orders.
 *
 * @param K the type of the keys in the binary search tree
 * @param V the type of the values in the binary search tree
 * @param N the type of the nodes in the binary search tree
 */
class BinarySearchTreeIterator<K : Comparable<K>, V, N : AbstractBSTreeNode<K, V, N>>(
    root: N?,
    order: IterationOrders = IterationOrders.WIDTH_ORDER
) : Iterator<Pair<K, V>> {
    private val iterationState: IterationState<K, V, N>

    init {
        iterationState = when (order) {
            IterationOrders.WIDTH_ORDER -> WidthIterationState(root)
            IterationOrders.INCREASING_ORDER -> IncreasingIterationState(root)
            IterationOrders.DECREASING_ORDER -> DecreasingIterationState(root)
        }
    }

    override fun hasNext(): Boolean {
        return iterationState.hasNext()
    }

    override fun next(): Pair<K, V> {
        return iterationState.next()
    }

    /**
     * An interface for the different iteration states of a binary search tree iterator.
     */
    private interface IterationState<K : Comparable<K>, V, N : AbstractBSTreeNode<K, V, N>> {

        /**
         * Returns `true` if the iteration has more elements.
         */
        abstract fun hasNext(): Boolean

        /**
         * Returns `true` if the iteration has more elements.
         * @throws: NoSuchElementException - if the iteration state has no next element.
         */
        abstract fun next(): Pair<K, V>

    }

    /**
     * A concrete iteration state for a binary search tree iterator with the width iteration order.
     */
    private class WidthIterationState<K : Comparable<K>, V, N : AbstractBSTreeNode<K, V, N>>(
        root: N?
    ) : IterationState<K, V, N> {
        private val queue: Queue<N> = LinkedList<N>()

        init {
            if (root != null) queue.add(root)
        }

        override fun hasNext(): Boolean {
            return (queue.size > 0)
        }

        override fun next(): Pair<K, V> {
            if (!hasNext()) throw NoSuchElementException("Try get next element from the end of iterator state")
            val nodeCurrent: N = queue.poll()
            nodeCurrent.getChildren().forEach() { child ->
                queue.add(child)
            }
            return Pair(nodeCurrent.key, nodeCurrent.value)
        }

    }

    /**
     * A concrete iteration state for a binary search tree iterator with the increasing iteration order.
     */
    private class IncreasingIterationState<K : Comparable<K>, V, N : AbstractBSTreeNode<K, V, N>>(
        root: N?
    ) : IterationState<K, V, N> {
        private val unprocessedNodesStack = LinkedList<N>()
        private val semiProcessedNodesStack = LinkedList<N>()

        init {
            if (root != null) unprocessedNodesStack.add(root)
        }

        override fun hasNext(): Boolean {
            return (hasUnprocessedNodes() || hasSemiProcessedNodes())
        }

        private fun hasUnprocessedNodes(): Boolean {
            return unprocessedNodesStack.isNotEmpty()
        }

        private fun hasSemiProcessedNodes(): Boolean {
            return semiProcessedNodesStack.isNotEmpty()
        }

        override fun next(): Pair<K, V> {
            if (!hasNext()) throw NoSuchElementException("Try get next element from the end of iterator state")
            var nodeCurrent: N

            while (hasUnprocessedNodes()) {
                nodeCurrent = unprocessedNodesStack.pollFirst()
                semiProcessedNodesStack.addFirst(nodeCurrent)
                if (nodeCurrent.leftChild != null)
                    unprocessedNodesStack.addFirst(nodeCurrent.leftChild)
                else {
                    semiProcessedNodesStack.pollFirst()
                    if (nodeCurrent.rightChild != null)
                        unprocessedNodesStack.addFirst(nodeCurrent.rightChild)
                    return Pair(nodeCurrent.key, nodeCurrent.value)
                }
            }

            nodeCurrent = semiProcessedNodesStack.pollFirst()
            if (nodeCurrent.rightChild != null)
                unprocessedNodesStack.addFirst(nodeCurrent.rightChild)
            return Pair(nodeCurrent.key, nodeCurrent.value)
        }

    }

    /**
     * A concrete iteration state for a binary search tree iterator with the decreasing iteration order.
     */
    private class DecreasingIterationState<K : Comparable<K>, V, N : AbstractBSTreeNode<K, V, N>>(
        root: N?
    ) : IterationState<K, V, N> {
        private val unprocessedNodesStack = LinkedList<N>()
        private val semiProcessedNodesStack = LinkedList<N>()

        init {
            if (root != null) unprocessedNodesStack.add(root)
        }

        override fun hasNext(): Boolean {
            return (hasUnprocessedNodes() || hasSemiProcessedNodes())
        }

        private fun hasSemiProcessedNodes(): Boolean {
            return semiProcessedNodesStack.isNotEmpty()
        }

        private fun hasUnprocessedNodes(): Boolean {
            return unprocessedNodesStack.isNotEmpty()
        }

        override fun next(): Pair<K, V> {
            if (!hasNext()) throw NoSuchElementException("Try get next element from the end of iterator state")
            var nodeCurrent: N

            while (hasUnprocessedNodes()) {
                nodeCurrent = unprocessedNodesStack.pollFirst()
                semiProcessedNodesStack.addFirst(nodeCurrent)
                if (nodeCurrent.rightChild != null)
                    unprocessedNodesStack.addFirst(nodeCurrent.rightChild)
                else {
                    semiProcessedNodesStack.pollFirst()
                    if (nodeCurrent.leftChild != null)
                        unprocessedNodesStack.addFirst(nodeCurrent.leftChild)
                    return Pair(nodeCurrent.key, nodeCurrent.value)
                }
            }

            nodeCurrent = semiProcessedNodesStack.pollFirst()
            if (nodeCurrent.leftChild != null)
                unprocessedNodesStack.addFirst(nodeCurrent.leftChild)
            return Pair(nodeCurrent.key, nodeCurrent.value)
        }

    }

}
