package tree_tripper.binary_trees.assistants

import assertBinaryNodeDataEquals
import assertBinaryNodeDeepEquals
import org.junit.jupiter.api.Assertions
import tree_tripper.binary_trees.RBTree
import tree_tripper.nodes.binary_nodes.RBTreeNode
import tree_tripper.nodes.notNullNodeAction
import java.util.Queue
import java.util.LinkedList


public class RBTreeTestAssistant<K: Comparable<K>, V>: RBTree<K, V>() {

    public fun assertIsRBTree() {
        assert(!isRedColor(root)) {"Root of RBTree is red. Must be black."}
        val queue: Queue<RBTreeNode<K, V>> = LinkedList<RBTreeNode<K, V>>(listOfNotNull(root))

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
        assertBlackHeight(root)
    }

    private fun assertBlackHeight(node: RBTreeNode<K, V>?): Int {
        if (node == null) return 1
        val left = assertBlackHeight(node.leftChild)
        val right = assertBlackHeight(node.rightChild)
        Assertions.assertEquals(left, right)
        return (if (node.isRed) 0 else 1) + left
    }

    fun assertRoot(node: RBTreeNode<K, V>?, lazyMassage: () -> String) {
        try {
            assertBinaryNodeDataEquals(root, node) {rootNode, expectedNode -> rootNode.isRed == expectedNode.isRed}
        } catch (e: AssertionError) {
            throw AssertionError(lazyMassage(), e)
        }
    }

    fun assertNodeColor(expected: Boolean, node: RBTreeNode<K, V>?) {
        Assertions.assertEquals(expected, isRedColor(node))
    }

    fun assertNodeLeftChildColor(expected: Boolean, node: RBTreeNode<K, V>?) {
        Assertions.assertEquals(expected, isRedLeftChild(node))
    }

    fun assertNodeLeftRotation(expected: RBTreeNode<K, V>, node: RBTreeNode<K, V>) {
        assertBinaryNodeDeepEquals(expected, rotateLeft(node)) {n1, n2 -> n1.isRed == n2.isRed}
    }

    fun assertNodeRightRotation(expected: RBTreeNode<K, V>, node: RBTreeNode<K, V>) {
        assertBinaryNodeDeepEquals(expected, rotateRight(node)) {n1, n2 -> n1.isRed == n2.isRed}
    }

    fun assertNodeColorFlip(expected: RBTreeNode<K, V>, node: RBTreeNode<K, V>) {
        flipColors(node)
        assertBinaryNodeDeepEquals(expected, node) {n1, n2 -> n1.isRed == n2.isRed}
    }

    fun assertNodeCreation(key: K, value: V) {
        assertBinaryNodeDeepEquals(createNode(key, value), RBTreeNode(key, value)) { n1, n2 -> n1.isRed == n2.isRed}
    }

    fun assertUpdateRoot(node: RBTreeNode<K, V>?) {
        updateRoot(node)
        assertBinaryNodeDataEquals(
            root,
            if (node != null) RBTreeNode(node.key, node.value, false) else null
        )
    }

    fun getRoot(): Pair<K, V> {
        val root = this.root ?: throw NullPointerException("Tree is empty can't get root pair")
        return Pair(root.key, root.value)
    }

    fun assertRemoveMinNode(tree_view: RBTreeNode<K, V>?, expected: RBTreeNode<K, V>?) {
        val result = removeMinNode(tree_view)
        assertBinaryNodeDeepEquals(expected, result) {n1, n2 -> n1.isRed == n2.isRed}
    }

}