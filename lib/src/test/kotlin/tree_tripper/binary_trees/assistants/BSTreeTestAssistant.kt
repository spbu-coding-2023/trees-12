package tree_tripper.binary_trees.assistants

import org.junit.jupiter.api.Assertions.assertEquals
import tree_tripper.binary_trees.BSTree
import tree_tripper.nodes.binary_nodes.BSTreeNode
import java.util.*


class BSTreeTestAssistant<K: Comparable<K>, V>: BSTree<K, V>() {

    public fun assertRootInitialization() {
        assertEquals(root, null, "Incorrect a root initialization")
    }

    public fun assertWasCreatedNode(key: K, value: V) {
        val node = createNode(key, value)
        assertEquals(node.key, key, "Incorrect a key assignment")
        assertEquals(node.value, value, "Incorrect a value assignment")
        assertEquals(node.leftChild, null, "Incorrect a left child assignment")
        assertEquals(node.rightChild, null, "Incorrect a right child assignment")
    }

    public fun assertWasUpdatedRoot(key: K, value: V) {
        val node = createNode(key, value)
        updateRoot(node)
        assertEquals(root, node, "Incorrect a root update")
    }

    public fun assertWasBalancedTree(key: K, value: V) {
        val node = createNode(key, value)
        assertEquals(balanceTree(node), node, "Incorrect a tree balance")
    }

    public fun assertIsBSTree() {
        val queue: Queue<BSTreeNode<K, V>> = LinkedList(listOfNotNull(root))

        while (queue.isNotEmpty()) {
            val node = queue.remove()
            val nodeLeft = node.leftChild
            if (nodeLeft != null)
                assert(nodeLeft.key < node.key)
                { "Incorrect the binary search tree structure: a left child key is no less than the parent key" }
            val nodeRight = node.rightChild
            if (nodeRight != null)
                assert(nodeRight.key > node.key)
                { "Incorrect the binary search tree structure: a left child key is no more than the parent key" }

            queue.addAll(node.getChildren())
        }
    }

}
