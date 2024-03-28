package tree_tripper.binary_trees.assistants

import org.junit.jupiter.api.Assertions
import tree_tripper.binary_trees.RBTree
import tree_tripper.nodes.binary_nodes.RBTreeNode
import tree_tripper.nodes.notNullNodeAction
import java.util.Queue
import java.util.LinkedList


public class RBTreeTestAssistant<K: Comparable<K>, V>: RBTree<K, V>() {

    public fun assertIsRBTree() {
        assert(!isRedColor(root)) {"Root of RBTree is red. Must be black."}
        val queue: Queue<RBTreeNode<K, V>> = LinkedList<RBTreeNode<K, V>>(listOf(root))

        while (queue.isNotEmpty()) {
            val node: RBTreeNode<K, V> = queue.remove()
            val leftCompareResult: Int = notNullNodeAction(
                node.leftChild, -1
            ) { leftChild -> leftChild.key.compareTo(node.key) }
            val rightCompareResult: Int = notNullNodeAction(
                node.rightChild, 1
            ) { rightChild -> rightChild.key.compareTo(node.key) }

            assert(leftCompareResult <= -1) {
                "Left child of $node is not a BST node: keys compare result: $leftCompareResult"
            }
            assert(rightCompareResult >= 1) {
                "Right child of $node is not a BST node: keys compare result: $rightCompareResult"
            }
            assert(!isRedColor(node.rightChild)) {"Right child of node at RBTree is red. Its must be black."}
            if (isRedColor(node)) {
                assert(!isRedLeftChild(node)) {"Left child of red node at RBTree is red. Its must be black."}
            }
            queue.addAll(node.getChildren())
        }
    }

    public fun checkTree(checkAction: (RBTreeNode<K, V>) -> Boolean): Boolean {
        val queue: Queue<RBTreeNode<K, V>> = LinkedList<RBTreeNode<K, V>>(listOf(root))

        while (queue.isNotEmpty()) {
            val node: RBTreeNode<K, V> = queue.remove()
            if (!checkAction(node)) return false
            queue.addAll(node.getChildren())
        }
        return true
    }

    fun assertRoot(node: RBTreeNode<K, V>?, lazyMassage: () -> String) {
        assert(root?.dateEqual(node) ?: (node == null)) { lazyMassage() }
    }

    fun assertNodeColor(expected: Boolean, node: RBTreeNode<K, V>?) {
        Assertions.assertEquals(expected, isRedColor(node))
    }

    fun assertNodeLeftChildColor(expected: Boolean, node: RBTreeNode<K, V>?) {
        Assertions.assertEquals(expected, isRedLeftChild(node))
    }

}