package tree_tripper.binary_trees

import tree_tripper.nodes.binary_nodes.RBTreeNode
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

    override fun removeCurrentNode(nodeCurrent: RBTreeNode<K, V>): Pair<RBTreeNode<K, V>?, V?> {
        val leftChild = nodeCurrent.leftChild
        val rightChild = nodeCurrent.rightChild
        if (leftChild == null && rightChild == null) {
            if (isRedColor(nodeCurrent)) return Pair(null, nodeCurrent.value)
            if (isRedColor(nodeCurrent.parent)) {
                flipColors(nodeCurrent.parent)
            } else {
                val uncle = nodeCurrent.getUncle()
                if (isRedColor(uncle)) {
                    flipColors(uncle)
                    nodeCurrent.parent?.flipColor()
                }
                uncle?.flipColor()
            }
            return Pair(null, nodeCurrent.value)
        } else if (leftChild == null) {
            throw IllegalArgumentException(
                "Invalid RedBlackTree state, node with one child as right is not valid rb-tree"
            )
        } else if (rightChild == null) {
            if (isRedColor(leftChild)) {
                flipColors(nodeCurrent)
                return Pair(leftChild, nodeCurrent.value)
            }
            throw IllegalArgumentException(
                "Invalid RedBlackTree state, node with one child as left with black color is not valid rb-tree"
            )
        } else {
            val newNode: RBTreeNode<K, V>
            val nodeCached: RBTreeNode<K, V>
            if (isRedColor(nodeCurrent)) {
                nodeCached = getMinNodeInSubtree(rightChild) as RBTreeNode<K, V>
                newNode = RBTreeNode(nodeCached.key, nodeCached.value, nodeCached.isRed)
                newNode.leftChild = leftChild
                newNode.rightChild = removeNode(rightChild, nodeCached.key).first
                if (!isRedColor(nodeCached)) leftChild.flipColor()
            } else {
                if (isRedColor(leftChild)) {
                    nodeCached = getMaxNodeInSubtree(leftChild) as RBTreeNode<K, V>
                    newNode = RBTreeNode(nodeCached.key, nodeCached.value, nodeCurrent.isRed)
                    newNode.rightChild = rightChild
                    newNode.leftChild = removeNode(leftChild, nodeCached.key).first
                    return Pair(balanceTree(newNode), nodeCurrent.value)
                }
                nodeCached = getMinNodeInSubtree(rightChild) as RBTreeNode<K, V>
                newNode = RBTreeNode(nodeCached.key, nodeCached.value, nodeCurrent.isRed)
                newNode.leftChild = leftChild
                newNode.rightChild = removeNode(rightChild, nodeCached.key).first
                if (!isRedColor(nodeCached)) {
                    leftChild.isRed = true
                    newNode.isRed = true
                }
            }
            return Pair(balanceTree(newNode), nodeCurrent.value)
        }
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
    protected fun flipColors(node: RBTreeNode<K, V>?): Unit {
        if (node == null) return
        node.flipColor()
        notNullNodeUpdate(node.leftChild) { child -> child.flipColor() }
        notNullNodeUpdate(node.rightChild) { child -> child.flipColor() }
    }

}
