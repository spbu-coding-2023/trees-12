package tree_tripper.binary_trees

import tree_tripper.nodes.binary_nodes.RBTreeNode
import tree_tripper.nodes.notNullNodeAction
import tree_tripper.nodes.notNullNodeUpdate


/**
 * A class that represents a red-black tree data structure.
 *
 * @param K the type of the keys in the tree
 * @param V the type of the values in the tree
 */
public open class RBTree<K: Comparable<K>, V>: AbstractBSTree<K, V, RBTreeNode<K, V>>() {

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

    /**
     * Returns whether the specified node is red or not.
     *
     * @param node the node to check
     * @return `true` if the node is red, `false` otherwise
     */
    protected fun isRedColor(node: RBTreeNode<K, V>?): Boolean {
        if (node == null) return false
        return node.isRed
    }

    /**
     * Returns whether the specified node is red or not.
     *
     * @param node the node to check color its left child
     * @return `true` if left child of `node` is red, `false` otherwise
     */
    protected fun isRedLeftChild(node: RBTreeNode<K, V>?): Boolean {
        if (node == null) return false
        return isRedColor(node.leftChild)
    }

    /**
     * Rotates the binary tree node with the given root to the left.
     *
     * @param node the root of the binary tree node to rotate left
     * @return if `node.rightChild` is null, returns `node`,
     *         otherwise `node` switches places with the right child
     */
    protected fun rotateLeft(node: RBTreeNode<K, V>): RBTreeNode<K, V> {
        val rightChild: RBTreeNode<K, V> = node.rightChild ?: return node
        node.rightChild = rightChild.leftChild
        rightChild.leftChild = node

        rightChild.isRed = node.isRed
        node.isRed = true
        return rightChild
    }

    /**
     * Rotates the binary tree node with the given root to the right.
     *
     * @param node the binary tree node to rotate right
     * @return if `node.leftChild` is null, returns `node`,
     *         otherwise `node` switches places with the left child
     */
    protected fun rotateRight(node: RBTreeNode<K, V>): RBTreeNode<K, V> {
        val leftChild: RBTreeNode<K, V> = node.leftChild ?: return node
        node.leftChild = leftChild.rightChild
        leftChild.rightChild = node

        leftChild.isRed = node.isRed
        node.isRed = true
        return leftChild
    }

    /**
     * Flips the colors of the specified node and its children.
     *
     * @param node needed to flip the colors
     */
    protected fun flipColors(node: RBTreeNode<K, V>): Unit {
        node.isRed = !node.isRed
        notNullNodeUpdate(node.leftChild) { child -> child.isRed = !child.isRed }
        notNullNodeUpdate(node.rightChild) { child -> child.isRed = !child.isRed }
    }

    /**
     * This function is used to move a red node to the right, if it has a red left child of its [node] left child.
     * It first flips the colors of the node and its children, then rotates the tree if the left child is also red.
     *
     * @param node the node to move
     * @return the new root of the tree, which is balanced node subtree
     */
    private fun moveRedRight(node: RBTreeNode<K, V>): RBTreeNode<K, V> {
        var nodeCurrent: RBTreeNode<K, V> = node

        flipColors(nodeCurrent)
        if (isRedLeftChild(nodeCurrent.leftChild)) {
            nodeCurrent = rotateRight(nodeCurrent)
            flipColors(nodeCurrent)
        }
        return nodeCurrent
    }

    /**
     * This function is used to move a red node to the left, if it has a red right child of its [node] left child.
     * It first flips the colors of the node and its children, then rotates the tree if the left child is also red.
     *
     * @param node the node to move
     * @return the new root of the tree, which is balanced node subtree
     */
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

    /**
     * Removes the node with the minimum key from the binary search tree.
     *
     * @param node the root of the binary search tree
     * @return the root of the binary search tree with the node removed, or `null` if the tree is empty
     */
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
