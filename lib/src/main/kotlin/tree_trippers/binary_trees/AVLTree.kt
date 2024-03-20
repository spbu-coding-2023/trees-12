package tree_trippers.binary_trees

import tree_trippers.nodes.binary_nodes.AVLTreeNode

public class AVLTree<K: Comparable<K>, V>: AbstractBSTree<K, V, AVLTreeNode<K, V>>() {

    override fun insert (key: K, value: V) {
        this.size++
        if (this.root == null) root = AVLTreeNode(key, value)
        else this.root?.let { add(it, AVLTreeNode(key, value)) }
    }

    private fun add(root: AVLTreeNode<K, V>, node: AVLTreeNode<K, V>) {
        if (node.key < root.key) {
            if (root.leftChild == null) root.leftChild = node
            else add(root.leftChild!!, node)
        } else if (node.key == root.key) {
            root.value = node.value
        } else {
            if (root.rightChild == null) root.rightChild = node
            else add(root.rightChild!!, node)
        }
        root.updateHeight()
        balance(root)
    }

    override fun remove(key: K): V? {
        val dataToDelete = search(key)
        if (dataToDelete != null) {
            this.size--
            this.root = delete(this.root, key)
            return dataToDelete
        }
        else return null
    }

    private fun delete(root: AVLTreeNode<K, V>?, key: K): AVLTreeNode<K, V>? {
        var tempRoot = root     //This is necessary to be able to change the root
        if (tempRoot == null) return null
        else if (key < tempRoot.key) tempRoot.leftChild = tempRoot.leftChild?.let { delete(it, key) }
        else if (key > tempRoot.key) tempRoot.rightChild = tempRoot.rightChild?.let { delete(it, key) }
        else {
            if (tempRoot.rightChild == null || tempRoot.leftChild == null ) {
                tempRoot = if (tempRoot.leftChild == null) tempRoot.rightChild else tempRoot.leftChild
            } else {
                val maxLeftNode: AVLTreeNode<K, V> = maxDescendantForAVL(tempRoot.leftChild!!)
                tempRoot.key = maxLeftNode.key
                tempRoot.value = maxLeftNode.value
                tempRoot.leftChild =  delete(tempRoot.leftChild, maxLeftNode.key)
            }
        }
        if (tempRoot != null) {
            tempRoot.updateHeight()
            balance(tempRoot)
        }
        return tempRoot
    }

    private fun getBalance(node: AVLTreeNode<K, V> ): Int {
        return (node.rightChild?.height ?: 0) - (node.leftChild?.height ?: 0)
    }

    private fun swapNodes(node1: AVLTreeNode<K, V>, node2: AVLTreeNode<K, V>) {
        val node1Key = node1.key
        node1.key = node2.key
        node2.key = node1Key
        val node1Value = node1.value
        node1.value = node2.value
        node2.value = node1Value
    }

    private fun AVLRightRotation(node: AVLTreeNode<K, V> ): AVLTreeNode<K, V> {
        node.leftChild?.let { swapNodes(node, it) }
        val temp = node.rightChild
        node.rightChild = node.leftChild
        node.leftChild = node.rightChild?.leftChild
        node.rightChild?.leftChild = node.rightChild?.rightChild
        node.rightChild?.rightChild = temp
        node.rightChild?.updateHeight()
        node.updateHeight()
        return node.leftChild ?: node
    }

    private fun AVLLeftRotation(node: AVLTreeNode<K, V> ): AVLTreeNode<K, V> {
        node.rightChild?.let { swapNodes(node, it) }
        val temp = node.leftChild
        node.leftChild = node.rightChild
        node.rightChild = node.leftChild?.rightChild
        node.leftChild?.rightChild = node.leftChild?.leftChild
        node.leftChild?.leftChild = temp
        node.leftChild?.updateHeight()
        node.updateHeight()
        return node.rightChild ?: node
    }

    private fun balance(node: AVLTreeNode<K, V> ): AVLTreeNode<K, V> {
        when (getBalance(node)) {
            -2 -> {
                if (node.leftChild?.let { getBalance(it) } == 1) {
                    AVLLeftRotation(node.leftChild!!)
                }
                AVLRightRotation(node)
            }
            2 -> {
                if (node.rightChild?.let { getBalance(it) } == -1) {
                    AVLRightRotation(node.rightChild!!)
                }
                AVLLeftRotation(node)
            }
        }
        return node
    }

    private fun maxDescendantForAVL(node: AVLTreeNode<K, V>): AVLTreeNode<K, V> { // todo(MAXIM)
        if (node.rightChild == null) return node
        return maxDescendantForAVL(node.rightChild!!)
    }

}