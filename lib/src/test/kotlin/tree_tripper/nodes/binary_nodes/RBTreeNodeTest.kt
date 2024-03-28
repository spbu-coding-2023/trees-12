package tree_tripper.nodes.binary_nodes

import assertBinaryNodeDeepEquals
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource


public class RBTreeNodeTest {

    @ParameterizedTest
    @MethodSource("testNodeSimpleInitializeCases")
    public fun testNodeSimpleInitialize(key: Int, value: Int?) {
        val node = RBTreeNode(key, value)
        Assertions.assertEquals(key, node.key) { "Key of node is not equal." }
        Assertions.assertEquals(value, node.value) { "Value of node is not equal." }
        Assertions.assertTrue(node.isRed) { "Color of node is not red." }
        Assertions.assertNull(node.leftChild) { "Left child of node is not null." }
        Assertions.assertNull(node.rightChild) { "Right child of node is not null." }
    }

    @ParameterizedTest
    @MethodSource("testNodeColorTypeInitializeCases")
    public fun testNodeColorTypeInitialize(isRed: Boolean) {
        val node = RBTreeNode(0, 0, isRed)
        Assertions.assertEquals(isRed, node.isRed) { "Color of node is not equal." }
        Assertions.assertNull(node.leftChild) { "Left child of node is not null." }
        Assertions.assertNull(node.rightChild) { "Right child of node is not null." }
    }

    @ParameterizedTest
    @MethodSource("testNodeFullInitializeCases")
    public fun testNodeFullInitialize(leftChild: RBTreeNode<Int, Int?>?, rightChild: RBTreeNode<Int, Int?>?) {
        val node = RBTreeNode(0, 0, false, leftChild, rightChild)
        assertBinaryNodeDeepEquals(leftChild, node.leftChild) { n1, n2 -> n1.isRed == n2.isRed }
        assertBinaryNodeDeepEquals(rightChild, node.rightChild) { n1, n2 -> n1.isRed == n2.isRed }
    }

//    @ParameterizedTest
//    @MethodSource("testColorNameCases")
//    public fun testColorName(expected: String, node: RBTreeNode<Int, Int?>) {
//        Assertions.assertEquals(expected, node())
//    }

    @ParameterizedTest
    @MethodSource("testToStringSimpleViewCases")
    public fun testToStringSimpleView(expected: String, node: RBTreeNode<Int, Int?>) {
        Assertions.assertEquals(expected, node.toStringSimpleView())
    }

    companion object {
        @JvmStatic
        fun testNodeSimpleInitializeCases(): List<Arguments> = listOf(
            Arguments.of(-1, -1),
            Arguments.of(0, 0),
            Arguments.of(2, 2),
            Arguments.of(3, null),
        )

        @JvmStatic
        fun testNodeColorTypeInitializeCases(): List<Arguments> = listOf(
            Arguments.of(false),
            Arguments.of(true),
        )

        @JvmStatic
        fun testNodeFullInitializeCases(): List<Arguments> = listOf(
            Arguments.of(null, null),
            Arguments.of(RBTreeNode(1, null), null),
            Arguments.of(null, RBTreeNode(-1, null)),
            Arguments.of(RBTreeNode(-1, null), RBTreeNode(-1, null)),
        )

//        @JvmStatic
//        fun testColorNameCases(): List<Arguments> = listOf(
//            Arguments.of("RED", RBTreeNode(-1, null)),
//            Arguments.of("BLACK", RBTreeNode(0, 0, false)),
//        )

        @JvmStatic
        fun testToStringSimpleViewCases(): List<Arguments> = listOf(
            Arguments.of("(-1, null) - RED", RBTreeNode(-1, null)),
            Arguments.of("(0, 0) - BLACK", RBTreeNode(0, 0, false)),
        )
    }

}
