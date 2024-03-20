package tree_trippers.iterators

import tree_trippers.nodes.binary_nodes.AbstractBSTreeNode
import java.util.LinkedList
import java.util.Queue

class BinarySearchTreeIterator<K : Comparable<K>, V, N : AbstractBSTreeNode<K, V, N>> :
    SearchTreeIterator<K, V, N> {
    private val iterationState: IterationState<K, V, N>

    constructor(root: N?): this(root, IterationOrders.WIDTH_ORDER)

    constructor(root: N?, order: IterationOrders) {
        iterationState = when (order) {
            IterationOrders.WIDTH_ORDER -> WidthIterationState(root)
            IterationOrders.INCREASING_ORDER -> IncreasingIterationState(root)
            IterationOrders.DECREASING_ORDER -> DecreasingIterationState(root)
            else -> throw Exception("Unsupported iteration order")
        }
    }

    override fun hasNext(): Boolean {
        return iterationState.hasNext()
    }

    override fun next(): Pair<K, V> {
        if (!hasNext()) throw Exception("Try get next element from empty iterator")
        return iterationState.next()
    }

    private interface IterationState<K: Comparable<K>, V, N: AbstractBSTreeNode<K, V, N>> {
        abstract fun hasNext(): Boolean
        abstract fun next(): Pair<K, V>
    }

    private class WidthIterationState<K: Comparable<K>, V, N: AbstractBSTreeNode<K, V, N>>(root: N?): IterationState<K, V, N> {
        private val queue: Queue<N> = LinkedList<N>()

        init {
            if (root != null) queue.add(root)
        }

        override fun hasNext(): Boolean {
            return queue.size > 0
        }

        override fun next(): Pair<K, V> {
            if (!hasNext()) throw Exception("Try get next element from the end of iterator state")
            val currentNode: N = queue.poll()
            currentNode.getChildren().forEach() {
                child -> queue.add(child)
            }
            return Pair(currentNode.key, currentNode.value)
        }
    }

    private class IncreasingIterationState<K: Comparable<K>, V, N: AbstractBSTreeNode<K, V, N>>(root: N?): IterationState<K, V, N> {
        private val unprocessedNodesStack: LinkedList<N> = LinkedList<N>()
        private val semiProcessedNodesStack: LinkedList<N> = LinkedList<N>()

        init {
            if (root != null) unprocessedNodesStack.add(root)
        }

        override fun hasNext(): Boolean {
            return isHasUnprocessedNodes() || isHasSemiProcessedNodes()
        }

        private fun isHasSemiProcessedNodes(): Boolean {
            return semiProcessedNodesStack.isNotEmpty()
        }

        private fun isHasUnprocessedNodes(): Boolean {
            return unprocessedNodesStack.isNotEmpty()
        }

        override fun next(): Pair<K, V> {
            if (!hasNext()) throw Exception("Try get next element from the end of iterator state")
            var currentNode: N

            while (isHasUnprocessedNodes()) {
                currentNode = unprocessedNodesStack.pollFirst()
                semiProcessedNodesStack.addFirst(currentNode)
                if (currentNode.leftChild != null) {
                    unprocessedNodesStack.addFirst(currentNode.leftChild)
                } else {
                    semiProcessedNodesStack.pollFirst()
                    if (currentNode.rightChild != null) unprocessedNodesStack.addFirst(currentNode.rightChild)
                    return Pair(currentNode.key, currentNode.value)
                }
            }

            currentNode = semiProcessedNodesStack.pollFirst()
            if (currentNode.rightChild != null) unprocessedNodesStack.addFirst(currentNode.rightChild)
            return Pair(currentNode.key, currentNode.value)
        }

    }

    private class DecreasingIterationState<K: Comparable<K>, V, N: AbstractBSTreeNode<K, V, N>>(root: N?): IterationState<K, V, N> {
        private val unprocessedNodesStack: LinkedList<N> = LinkedList<N>()
        private val semiProcessedNodesStack: LinkedList<N> = LinkedList<N>()

        init {
            if (root != null) unprocessedNodesStack.add(root)
        }

        override fun hasNext(): Boolean {
            return isHasUnprocessedNodes() || isHasSemiProcessedNodes()
        }

        private fun isHasSemiProcessedNodes(): Boolean {
            return semiProcessedNodesStack.isNotEmpty()
        }

        private fun isHasUnprocessedNodes(): Boolean {
            return unprocessedNodesStack.isNotEmpty()
        }

        override fun next(): Pair<K, V> {
            if (!hasNext()) throw Exception("Try get next element from the end of iterator state")
            var currentNode: N

            while (isHasUnprocessedNodes()) {
                currentNode = unprocessedNodesStack.pollFirst()
                semiProcessedNodesStack.addFirst(currentNode)
                if (currentNode.rightChild != null) {
                    unprocessedNodesStack.addFirst(currentNode.rightChild)
                } else {
                    semiProcessedNodesStack.pollFirst()
                    if (currentNode.leftChild != null) unprocessedNodesStack.addFirst(currentNode.leftChild)
                    return Pair(currentNode.key, currentNode.value)
                }
            }

            currentNode = semiProcessedNodesStack.pollFirst()
            if (currentNode.leftChild != null) unprocessedNodesStack.addFirst(currentNode.leftChild)
            return Pair(currentNode.key, currentNode.value)
        }

    }
}
