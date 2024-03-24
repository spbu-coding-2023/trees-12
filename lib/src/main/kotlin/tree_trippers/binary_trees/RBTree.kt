package tree_trippers.binary_trees

import tree_trippers.nodes.binary_nodes.RBTreeNode
import tree_trippers.nodes.notNullNodeAction
import tree_trippers.nodes.notNullNodeUpdate


public class RBTree<K: Comparable<K>, V>: AbstractBSTree<K, V, RBTreeNode<K, V>>() {

    override fun createNode(key: K, value: V): RBTreeNode<K, V> {
        return RBTreeNode(key, value)
    }

    override fun updateRoot(node: RBTreeNode<K, V>?) {
        notNullNodeUpdate(node) { it.isRed = false }
        super.updateRoot(node)
    }

    override fun balanceTree(node: RBTreeNode<K, V>): RBTreeNode<K, V> {
        var nodeCurrent: RBTreeNode<K, V> = node
        if (isRedColor(nodeCurrent.rightChild)) {
            nodeCurrent = rotateLeft(nodeCurrent)
        }
        if (isRedColor(nodeCurrent.leftChild) && isRedLeftChild(nodeCurrent.leftChild)) {
            nodeCurrent = rotateRight(nodeCurrent)
        }
        if (isRedColor(nodeCurrent.leftChild) && isRedColor(nodeCurrent.rightChild)) {
            flipColors(nodeCurrent)
        }
        return nodeCurrent
    }

    override fun removeNode(node: RBTreeNode<K, V>?, key: K): Pair<RBTreeNode<K, V>?, V?> {
        if (node == null) return Pair(null, null)

        val removeResult: Pair<RBTreeNode<K, V>?, V?>
        val resultCompare: Int = key.compareTo(node.key)
        var nodeCurrent: RBTreeNode<K, V> = node
        if (resultCompare < 0) {
            if (!isRedColor(nodeCurrent.leftChild) && !isRedLeftChild(nodeCurrent.leftChild))
                nodeCurrent = moveRedLeft(nodeCurrent)

            removeResult = removeNode(nodeCurrent.leftChild, key)
            nodeCurrent.leftChild = removeResult.first
        } else {
            if (isRedColor(nodeCurrent.leftChild))
                nodeCurrent = rotateRight(nodeCurrent)
            if (resultCompare == 0 && nodeCurrent.rightChild == null)
                return Pair(null, nodeCurrent.value)
            if (!isRedColor(nodeCurrent.rightChild) && !isRedLeftChild(nodeCurrent.rightChild))
                nodeCurrent = moveRedRight(nodeCurrent)
            if (resultCompare == 0) {
                val nodeWithMinimalKey = getMinDescendantNode(nodeCurrent.rightChild) as RBTreeNode
                val nodeSubstitutive: RBTreeNode<K, V> = createNode(nodeWithMinimalKey.key, nodeWithMinimalKey.value)
                nodeSubstitutive.isRed = nodeCurrent.isRed
                nodeSubstitutive.leftChild = nodeCurrent.leftChild
                nodeSubstitutive.rightChild = removeMinNode(nodeCurrent.rightChild)
                return Pair(balanceTree(nodeSubstitutive), nodeCurrent.value)
            } else {
                removeResult = removeNode(nodeCurrent.rightChild, key)
                nodeCurrent.rightChild = removeResult.first
            }
        }

        return Pair(balanceTree(nodeCurrent), removeResult.second)
    }

    private fun isRedColor(node: RBTreeNode<K, V>?): Boolean {
        if (node == null) return false
        return node.isRed
    }

    private fun isRedLeftChild(node: RBTreeNode<K, V>?): Boolean {
        if (node == null) return false
        return isRedColor(node.leftChild)
    }

    private fun rotateLeft(node: RBTreeNode<K, V>): RBTreeNode<K, V> {
        val rightChild: RBTreeNode<K, V> = node.rightChild ?: return node
        node.rightChild = rightChild.leftChild
        rightChild.leftChild = node

        rightChild.isRed = node.isRed
        node.isRed = true
        return rightChild
    }

    private fun rotateRight(node: RBTreeNode<K, V>): RBTreeNode<K, V> {
        val leftChild: RBTreeNode<K, V> = node.leftChild ?: return node
        node.leftChild = leftChild.rightChild
        leftChild.rightChild = node

        leftChild.isRed = node.isRed
        node.isRed = true
        return leftChild
    }

    private fun flipColors(node: RBTreeNode<K, V>): Unit {
        node.isRed = !node.isRed
        notNullNodeUpdate(node.leftChild) { child -> child.isRed = !child.isRed }
        notNullNodeUpdate(node.rightChild) { child -> child.isRed = !child.isRed }
    }

    private fun moveRedRight(node: RBTreeNode<K, V>): RBTreeNode<K, V> {
        var nodeCurrent: RBTreeNode<K, V> = node

        flipColors(nodeCurrent)
        if (isRedLeftChild(nodeCurrent.leftChild)) {
            nodeCurrent = rotateRight(nodeCurrent)
            flipColors(nodeCurrent)
        }
        return nodeCurrent
    }

    private fun moveRedLeft(node: RBTreeNode<K, V>): RBTreeNode<K, V> {
        var nodeCurrent: RBTreeNode<K, V> = node

        flipColors(nodeCurrent)
        if (isRedLeftChild(nodeCurrent.rightChild)) {
            nodeCurrent.rightChild = notNullNodeAction(
                node.rightChild, null
            ) {rightChild -> rotateRight(rightChild)}
            nodeCurrent = rotateLeft(nodeCurrent)
            flipColors(nodeCurrent)
        }
        return nodeCurrent
    }

    private fun removeMinNode(node: RBTreeNode<K, V>?): RBTreeNode<K, V>? {
        if (node == null) return null
        if (node.leftChild == null) return node.rightChild

        var nodeCurrent: RBTreeNode<K, V> = node
        if (!isRedColor(nodeCurrent.leftChild) && !isRedLeftChild(nodeCurrent.leftChild))
            nodeCurrent = moveRedLeft(nodeCurrent)

        nodeCurrent.leftChild = notNullNodeAction(
            nodeCurrent.leftChild, null
        ) {child -> removeMinNode(child)}

        return balanceTree(nodeCurrent)
    }

}
