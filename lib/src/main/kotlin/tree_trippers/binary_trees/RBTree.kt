package tree_trippers.binary_trees

import tree_trippers.nodes.binary_nodes.RBTreeNode
import tree_trippers.nodes.notNullNodeUpdate

public class RBTree<K: Comparable<K>, V>: AbstractBSTree<K, V, RBTreeNode<K, V>>() {

    override fun remove(key: K): V? {
        TODO("Not yet implemented")
    }
    override fun insert(key: K, value: V): Unit {
        insert(key, value, true)
    }

    override fun insertIfAbsent(key: K, value: V): Boolean {
        return insert(key, value, false)
    }

    private fun insert(key: K, value: V, isUpdate: Boolean): Boolean {
        val insertResult: Pair<RBTreeNode<K, V>, Boolean> = insertNode(root, key, value, isUpdate)
        root = insertResult.first
        notNullNodeUpdate(root) {node -> node.isRed = false}
        return insertResult.second
    }

    private fun insertNode(node: RBTreeNode<K, V>?, key: K, value: V, isUpdate: Boolean): Pair<RBTreeNode<K, V>, Boolean> {
        if (node == null) return Pair(RBTreeNode(key, value), true)

        val insertResult: Pair<RBTreeNode<K, V>, Boolean>
        val cmpResult: Int = key.compareTo(node.key)
        if (cmpResult > 0) {
            insertResult = insertNode(node.rightChild, key, value, isUpdate)
            node.rightChild = insertResult.first
        } else if (cmpResult < 0) {
            insertResult = insertNode(node.leftChild, key, value, isUpdate)
            node.leftChild = insertResult.first
        } else {
            if (isUpdate) node.value = value
            return Pair(node, isUpdate)
        }

        return Pair(fixNode(node), insertResult.second)
    }

    private fun <K : Comparable<K>, V> isRedColor(node: RBTreeNode<K, V>?): Boolean {
        if (node == null) return false
        return node.isRed
    }

    private fun <K : Comparable<K>, V> rotateLeft(node: RBTreeNode<K, V>): RBTreeNode<K, V> {
        val rightChild: RBTreeNode<K, V> = node.rightChild ?: return node
        node.rightChild = rightChild.leftChild
        rightChild.leftChild = node
        rightChild.isRed = node.isRed
        node.isRed = true

        return rightChild
    }

    private fun <K : Comparable<K>, V> rotateRight(node: RBTreeNode<K, V>): RBTreeNode<K, V> {
        val leftChild: RBTreeNode<K, V> = node.leftChild ?: return node
        node.leftChild = leftChild.rightChild
        leftChild.rightChild = node
        leftChild.isRed = node.isRed
        node.isRed = true

        return leftChild
    }

    private fun <K : Comparable<K>, V> flipColors(node: RBTreeNode<K, V>): Unit {
        node.isRed = !node.isRed
        notNullNodeUpdate(node.leftChild) { child -> child.isRed = !child.isRed }
        notNullNodeUpdate(node.rightChild) { child -> child.isRed = !child.isRed }
    }

    private fun <K : Comparable<K>, V> fixNode(node: RBTreeNode<K, V>): RBTreeNode<K, V> {
        var currentNode: RBTreeNode<K, V> = node
        if (isRedColor(currentNode.rightChild)) {
            currentNode = rotateLeft(currentNode)
        }
        if (isRedColor(currentNode.leftChild) && isRedLeftChild(currentNode.leftChild)) {
            currentNode = rotateRight(currentNode)
        }
        if (isRedColor(node.leftChild) && isRedColor(node.rightChild)) {
            flipColors(node)
        }
        return currentNode
    }

    private fun <K : Comparable<K>, V> isRedLeftChild(node: RBTreeNode<K, V>?): Boolean {
        if (node == null) return false
        return isRedColor(node.leftChild)
    }
}