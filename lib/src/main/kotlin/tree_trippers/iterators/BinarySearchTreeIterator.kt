package tree_trippers.iterators

import tree_trippers.nodes.binary_nodes.AbstractBSTreeNode
import java.util.LinkedList
import java.util.Queue


class BinarySearchTreeIterator<K: Comparable<K>, V, N: AbstractBSTreeNode<K, V, N>>: Iterator<Pair<K, V>> {
    private val iterationState: IterationState<K, V, N>

    constructor(root: N?): this(root, IterationOrders.WIDTH_ORDER)

    constructor(root: N?, order: IterationOrders) {
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

    private interface IterationState<K: Comparable<K>, V, N: AbstractBSTreeNode<K, V, N>> {

        abstract fun hasNext(): Boolean

        abstract fun next(): Pair<K, V>

    }

    private class WidthIterationState<K: Comparable<K>, V, N: AbstractBSTreeNode<K, V, N>>(
        root: N?
    ): IterationState<K, V, N> {
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

    private class IncreasingIterationState<K: Comparable<K>, V, N: AbstractBSTreeNode<K, V, N>>(
        root: N?
    ): IterationState<K, V, N> {
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

    private class DecreasingIterationState<K: Comparable<K>, V, N: AbstractBSTreeNode<K, V, N>>(
        root: N?
    ): IterationState<K, V, N> {
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
