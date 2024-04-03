package tree_tripper.nodes.binary_nodes

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import tree_tripper.nodes.notNullNodeAction
import kotlin.test.assertContains
import kotlin.test.assertEquals


public class BSTreeNodeTest {

    @Test
    @DisplayName("node initialization")
    public fun testNodeInitialization() {
        val node = BSTreeNode(1, -1)
        assertEquals(node.key, 1, "Incorrect a key assignment")
        assertEquals(node.value, -1, "Incorrect a value assignment")
        assertEquals(node.leftChild, null, "Incorrect a left child assignment")
        assertEquals(node.rightChild, null, "Incorrect a right child assignment")
    }

    @Test
    @DisplayName("get children")
    public fun testGetChildren() {
        val node = BSTreeNode(2, -2)
        assertEquals(node.getChildren(), listOf())
        val nodeLeft = BSTreeNode(1, -1)
        node.leftChild = nodeLeft
        assertEquals(node.getChildren(), listOf(nodeLeft))
        val nodeRight = BSTreeNode(3, -3)
        node.rightChild = nodeRight
        assertEquals(node.getChildren(), listOf(nodeLeft, nodeRight))
    }

    @Test
    @DisplayName("to string")
    public fun testToString() {
        val node = BSTreeNode(1, -1)
        assertEquals(node.toString(), "BSTreeNode(key=1, value=-1)")
    }

    @Test
    @DisplayName("to string simple view")
    public fun testToStringSimpleView() {
        val node = BSTreeNode(1, -1)
        assertEquals(node.toStringSimpleView(), "(1: -1)")
    }

    @Test
    @DisplayName("node to string with subtree view")
    public fun testNodeToStringWithSubtreeView() {
        val builder = StringBuilder()
        val node = BSTreeNode(1, -1)
        node.toStringWithSubtreeView(0, builder)
        assertEquals(builder.toString(), "${node.toStringSimpleView()}\n")
    }

    @Test
    @DisplayName("node with children to string with subtree view")
    public fun testNodeWithChildrenToStringWithSubtreeView() {
        val builder = StringBuilder()
        val node = BSTreeNode(2, -2)
        val nodeLeft = BSTreeNode(1, -1)
        node.leftChild = nodeLeft
        val nodeRight = BSTreeNode(3, -3)
        node.rightChild = nodeRight
        node.toStringWithSubtreeView(0, builder)
        assertEquals(builder.toString(),
            "\t${nodeRight.toStringSimpleView()}\n" +
                "${node.toStringSimpleView()}\n" +
                    "\t${nodeLeft.toStringSimpleView()}\n")
    }

}