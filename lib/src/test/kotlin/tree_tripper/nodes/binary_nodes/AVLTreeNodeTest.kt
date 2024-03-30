package tree_tripper.nodes.binary_nodes

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource


class AVLTreeNodeTest {

    @ParameterizedTest
    @MethodSource("testUpdateHeight")
    public fun testUpdateHeight(expected: Int, node: AVLTreeNode<Int, Int>) {
        node.updateHeight()
        Assertions.assertEquals(expected, node.height) {"The height does not match the expected."}
    }

    companion object {
        @JvmStatic
        fun testUpdateHeight(): List<Arguments> = listOf(
            Arguments.of(1,
                AVLTreeNode(0, 0, 0, null, null)
            ),

            Arguments.of(2,
                AVLTreeNode(
                    0, 0, 0,
                    AVLTreeNode(1, 1, 1, null, null),
                    null
                )
            ),

            Arguments.of(2,
                AVLTreeNode(
                    0, 0, 0,
                    null,
                    AVLTreeNode(1, 1, 1, null, null)
                )
            ),

            Arguments.of(2,
                AVLTreeNode(
                    0, 0, 0,
                    AVLTreeNode(1, 1, 1, null, null),
                    AVLTreeNode(2, 2, 1, null, null)
                )
            ),

            Arguments.of(3,
                AVLTreeNode(
                    0, 0, 0,
                    AVLTreeNode(1, 1, 2, null, null),
                    AVLTreeNode(2, 2, 1, null, null)
                )
            ),

            Arguments.of(3,
                AVLTreeNode(
                    0, 0, 0,
                    AVLTreeNode(1, 1, 1, null, null),
                    AVLTreeNode(2, 2, 2, null, null)
                )
            )

        )
    }
}
