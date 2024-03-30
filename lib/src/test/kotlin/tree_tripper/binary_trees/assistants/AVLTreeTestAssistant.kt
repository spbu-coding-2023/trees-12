package tree_tripper.binary_trees.assistants

import assertBinaryNodeDataEquals
import assertBinaryNodeDeepEquals
import tree_tripper.binary_trees.AVLTree
import tree_tripper.nodes.binary_nodes.AVLTreeNode


public class AVLTreeTestAssistant<K : Comparable<K>, V> : AVLTree<K, V>() {

    fun assertRoot(node: AVLTreeNode<K, V>?, lazyMassage: () -> String) {
        try {
            assertBinaryNodeDataEquals(root, node) {root, expected -> root.height == expected.height}
        } catch (e: AssertionError) {
            throw AssertionError(lazyMassage(), e)
        }
    }

    fun assertNodeCreation(key: K, value: V) {
        assertBinaryNodeDeepEquals(createNode(key, value), AVLTreeNode(key, value)) {node1, node2 -> node1.height == node2.height}
    }

    fun assertBalanceTree(expected: AVLTreeNode<K, V>, node: AVLTreeNode<K, V>) {
        assertBinaryNodeDeepEquals(expected, balanceTree(node)) {node1, node2 -> node1.height == node2.height}
    }

    fun assertBalanceFactor(expected: Int, node: AVLTreeNode<K, V>?) {
        val factor = balanceFactor(node)
        assert(factor == expected) {
            "Invalid height balance of $node, balance factor: $factor, expected: $expected."
        }
    }

    fun assertBalance(expected: AVLTreeNode<K, V>, node: AVLTreeNode<K, V>) {
        assertBinaryNodeDeepEquals(expected, balance(node)) {node1, node2 -> node1.height == node2.height}
    }

    fun assertNodeRightRotation(expected: AVLTreeNode<K, V>, node: AVLTreeNode<K, V>) {
        assertBinaryNodeDeepEquals(expected, rotateRight(node)) {node1, node2 -> node1.height == node2.height}
    }

    fun assertNodeLeftRotation(expected: AVLTreeNode<K, V>, node: AVLTreeNode<K, V>) {
        assertBinaryNodeDeepEquals(expected, rotateLeft(node)) {node1, node2 -> node1.height == node2.height}
    }

}
