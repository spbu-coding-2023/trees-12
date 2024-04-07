package tree_tripper.binary_trees.assistants

import assertBinaryNodeDeepEquals
import org.junit.jupiter.api.Assertions
import tree_tripper.binary_trees.BSTree
import tree_tripper.nodes.binary_nodes.BSTreeNode
import java.util.*


public class BSTreeTestAssistant<K: Comparable<K>, V>: BSTree<K, V>() {

    public fun assertNullRoot() {
        Assertions.assertEquals(root, null, "Incorrect a root initialization.")
    }

    public fun assertWasCreatedNode(key: K, value: V) {
        assertBinaryNodeDeepEquals(createNode(key, value), BSTreeNode(key, value))
    }

    public fun assertWasUpdatedRoot(key: K, value: V) {
        val node = createNode(key, value)
        updateRoot(node)
        Assertions.assertEquals(root, node, "Incorrect a root update.")
    }

    public fun assertWasBalancedTree(key: K, value: V) {
        val node = createNode(key, value)
        Assertions.assertEquals(balanceTree(node), node, "Incorrect a tree balance.")
    }

    public fun assertIsBSTree() {
        if (root == null) throw NullPointerException("Root is null")
        val queue: Queue<BSTreeNode<K, V>> = LinkedList(listOfNotNull(root))

        while (queue.isNotEmpty()) {
            val node = queue.remove()
            val nodeLeft = node.leftChild
            if (nodeLeft != null)
                assert(nodeLeft.key < node.key)
                { "Incorrect the binary search tree structure: a left child key is no less than the parent key." }
            val nodeRight = node.rightChild
            if (nodeRight != null)
                assert(nodeRight.key > node.key)
                { "Incorrect the binary search tree structure: a left child key is no more than the parent key." }

            queue.addAll(node.getChildren())
        }
    }

    public fun getRoot(): Pair<K, V> {
        val root = this.root ?: throw NullPointerException("Root is null")
        return Pair(root.key, root.value)
    }

}
