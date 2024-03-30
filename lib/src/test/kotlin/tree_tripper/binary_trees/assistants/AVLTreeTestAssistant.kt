package tree_tripper.binary_trees.assistants

import assertBinaryNodeDataEquals
import assertBinaryNodeDeepEquals
import tree_tripper.binary_trees.AVLTree
import tree_tripper.nodes.binary_nodes.AVLTreeNode
import kotlin.math.abs


public class AVLTreeTestAssistant<K : Comparable<K>, V> : AVLTree<K, V>() {

    fun assertRoot(node: AVLTreeNode<K, V>?, lazyMassage: () -> String) {
        try {
            assertBinaryNodeDataEquals(root, node) {rootNode, expectedNode -> rootNode.height == expectedNode.height}
        } catch (e: AssertionError) {
            throw AssertionError(lazyMassage(), e)
        }
    }

    fun assertBalanceFactor(node: AVLTreeNode<K, V>?) {
        assert(abs(balanceFactor(node)) <= 1) {
            "Invalid height balance of $node, balance factor: ${balanceFactor(node)}."
        }
    }

    fun assertNodeRightRotation(expected: AVLTreeNode<K, V>, node: AVLTreeNode<K, V>) {
        assertBinaryNodeDeepEquals(expected, rotateRight(node)) {node1, node2 -> node1.height == node2.height}
    }

    fun assertNodeLeftRotation(expected: AVLTreeNode<K, V>, node: AVLTreeNode<K, V>) {
        assertBinaryNodeDeepEquals(expected, rotateLeft(node)) {node1, node2 -> node1.height == node2.height}
    }

}
