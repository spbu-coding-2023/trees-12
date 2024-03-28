package tree_tripper.binary_trees

import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import tree_tripper.binary_trees.assistants.RBTreeTestAssistant
import tree_tripper.nodes.binary_nodes.RBTreeNode


class RBTreeTest {
    private lateinit var tree: RBTreeTestAssistant<Int, Int>

    @BeforeEach
    fun setup() {
        tree = RBTreeTestAssistant<Int, Int>()
    }

    @Test
    public fun testTreeInitializing() {
        tree = RBTreeTestAssistant<Int, Int>()
        tree.assertRoot(null) {"Root of RBTree is not null by standard initialize."}
        Assertions.assertEquals(0, tree.getSize())
    }

    @ParameterizedTest
    @MethodSource("testNodeColorCases")
    public fun testNodeColor(expected: Boolean, node: RBTreeNode<Int, Int>?) {
        tree.assertNodeColor(expected, node)
    }

    @ParameterizedTest
    @MethodSource("testNodeLeftChildColorCases")
    public fun testNodeLeftChildColor(expected: Boolean, node: RBTreeNode<Int, Int>?) {
        tree.assertNodeLeftChildColor(expected, node)
    }

    companion object {
        @JvmStatic
        fun testNodeColorCases(): List<Arguments> = listOf(
            Arguments.of(false, null),
            Arguments.of(true, RBTreeNode<Int, Int>(0, 0, true)),
            Arguments.of(false, RBTreeNode<Int, Int>(0, 0, false)),
        )

        @JvmStatic
        fun testNodeLeftChildColorCases(): List<Arguments> = listOf(
            Arguments.of(false, null),
            Arguments.of(false, RBTreeNode<Int, Int>(0, 0, true)),
            Arguments.of(false, RBTreeNode<Int, Int>(0, 0, false)),
            Arguments.of(
                false,
                RBTreeNode<Int, Int>(
                    1, 1, true,
                    null, RBTreeNode<Int, Int>(0, 0, true)
                )
            ),
            Arguments.of(
                false,
                RBTreeNode<Int, Int>(
                    1, 1, true,
                    null, RBTreeNode<Int, Int>(0, 0, false)
                )
            ),
            Arguments.of(
                true,
                RBTreeNode<Int, Int>(
                    1, 1, true,
                    RBTreeNode<Int, Int>(0, 0, true), null
                )
            ),
            Arguments.of(
                false,
                RBTreeNode<Int, Int>(
                    1, 1, true,
                    RBTreeNode<Int, Int>(0, 0, false), null
                )
            )
        )
    }

}