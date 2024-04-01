package tree_tripper.binary_trees

import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import tree_tripper.binary_trees.assistants.RBTreeTestAssistant
import tree_tripper.nodes.binary_nodes.RBTreeNode


class RBTreeTest {
    private lateinit var tree: RBTreeTestAssistant<Int, Int>

    @BeforeEach
    fun setup() {
        tree = RBTreeTestAssistant()
    }

    @Test
    @DisplayName("tree initialization")
    public fun testTreeInitializing() {
        tree.assertRoot(null) { "Root of RBTree is not null by standard initialize." }
        tree.assertIsRBTree()
        Assertions.assertEquals(0, tree.getSize())
    }

    @ParameterizedTest
    @MethodSource("testNodeColorCases")
    @DisplayName("color of node")
    public fun testNodeColor(expected: Boolean, node: RBTreeNode<Int, Int>?) {
        tree.assertNodeColor(expected, node)
    }

    @ParameterizedTest
    @MethodSource("testNodeLeftChildColorCases")
    @DisplayName("color of left child of node")
    public fun testNodeLeftChildColor(expected: Boolean, node: RBTreeNode<Int, Int>?) {
        tree.assertNodeLeftChildColor(expected, node)
    }

    @ParameterizedTest
    @MethodSource("testNodeRotateLeftCases")
    @DisplayName("rotate node left")
    public fun testNodeRotateLeft(expected: RBTreeNode<Int, Int>, node: RBTreeNode<Int, Int>) {
        tree.assertNodeLeftRotation(expected, node)
    }

    @ParameterizedTest
    @MethodSource("testNodeRotateRightCases")
    @DisplayName("rotate node right")
    public fun testNodeRotateRight(expected: RBTreeNode<Int, Int>, node: RBTreeNode<Int, Int>) {
        tree.assertNodeRightRotation(expected, node)
    }

    @ParameterizedTest
    @MethodSource("testNodeColorFlipCases")
    @DisplayName("flip colors of node")
    public fun testNodeColorFlip(expected: RBTreeNode<Int, Int>?, node: RBTreeNode<Int, Int>?) {
        tree.assertNodeColorFlip(expected, node)
    }

    @ParameterizedTest
    @MethodSource("testNodeCreationCases")
    @DisplayName("create node")
    public fun testNodeCreation(key: Int, value: Int) {
        tree.assertNodeCreation(key, value)
    }

    @ParameterizedTest
    @MethodSource("testUpdateRootCases")
    @DisplayName("update root")
    public fun testUpdateRoot(node: RBTreeNode<Int, Int>?) {
        tree.assertUpdateRoot(node)
    }

    @ParameterizedTest
    @MethodSource("testBalanceTreeCases")
    @DisplayName("balance tree")
    public fun testBalanceTree(expectedNodeTreeView: RBTreeNode<Int, Int>, nodeTreeView: RBTreeNode<Int, Int>) {
        tree.assertBalanceTree(expectedNodeTreeView, nodeTreeView)
    }

    companion object {

        @JvmStatic
        fun testSortedInsertElementsCases(): List<Arguments> = listOf(
            Arguments.of(
                listOf(10, 20, -10),
                RBTreeNode(10,10, false,
                    RBTreeNode(-10, -10, false),
                    RBTreeNode(20, 20, false)
                )
            ),
            Arguments.of(
                listOf(-10, -20, 10),
                RBTreeNode(-10, -10, false,
                    RBTreeNode(-20, -20, false),
                    RBTreeNode(10, 10, false),
                )
            ),
            Arguments.of(
                listOf(15, 34, -23, 20, 10, -100),
                RBTreeNode(15, 15, false,
                    RBTreeNode(-23, -23, true,
                        RBTreeNode(-100, -100, false),
                        RBTreeNode(10, 10, false)
                    ),
                    RBTreeNode(34, 34, false,
                        RBTreeNode(20, 20, true), null
                    )
                )
            ),
        )

        @JvmStatic
        fun testReverseSortedInsertElementsCases(): List<Arguments> = listOf(
            Arguments.of(
                listOf(10, 20, -10),
                RBTreeNode(10,10, false,
                    RBTreeNode(-10, -10, false),
                    RBTreeNode(20, 20, false)
                )
            ),
            Arguments.of(
                listOf(-10, -20, 10),
                RBTreeNode(-10, -10, false,
                    RBTreeNode(-20, -20, false),
                    RBTreeNode(10, 10, false),
                )
            ),
            Arguments.of(
                listOf(15, 34, -23, 20, 10, -100),
                RBTreeNode(20, 20, false,
                    RBTreeNode(10, 10, true,
                        RBTreeNode(-23, -23, false,
                            RBTreeNode(-100, -100),
                            null
                        ),
                        RBTreeNode(15, 15, false)
                    ),
                    RBTreeNode(34, 34, false)
                )
            ),
        )

        @JvmStatic
        fun testUnsortedInsertElementsCases(): List<Arguments> = listOf(
            Arguments.of(
                listOf(10, 20, -10),
                RBTreeNode(10,10, false,
                    RBTreeNode(-10, -10, false),
                    RBTreeNode(20, 20, false)
                )
            ),
            Arguments.of(
                listOf(-10, -20, 10),
                RBTreeNode(-10, -10, false,
                    RBTreeNode(-20, -20, false),
                    RBTreeNode(10, 10, false),
                )
            ),
            Arguments.of(
                listOf(15, 34, -23, 20, 10, -100),
                RBTreeNode(15, 15, false,
                    RBTreeNode(-23, -23, true,
                        RBTreeNode(-100, -100, false),
                        RBTreeNode(10, 10, false)
                    ),
                    RBTreeNode(34, 34, false,
                        RBTreeNode(20, 20, true), null
                    )
                )
            ),
        )

        @JvmStatic
        fun testNodeColorCases(): List<Arguments> = listOf(
            Arguments.of(false, null),
            Arguments.of(true, RBTreeNode(0, 0, true)),
            Arguments.of(false, RBTreeNode(0, 0, false)),
        )

        @JvmStatic
        fun testNodeLeftChildColorCases(): List<Arguments> = listOf(
            Arguments.of(false, null),
            Arguments.of(false, RBTreeNode(0, 0, true)),
            Arguments.of(false, RBTreeNode(0, 0, false)),
            Arguments.of(
                false, RBTreeNode(
                    1, 1, true, null, RBTreeNode(0, 0, true)
                )
            ),
            Arguments.of(
                false, RBTreeNode(
                    1, 1, true, null, RBTreeNode(0, 0, false)
                )
            ),
            Arguments.of(
                true, RBTreeNode(
                    1, 1, true, RBTreeNode(0, 0, true), null
                )
            ),
            Arguments.of(
                false, RBTreeNode(
                    1, 1, true, RBTreeNode(0, 0, false), null
                )
            )
        )

        @JvmStatic
        fun testNodeRotateLeftCases(): List<Arguments> = listOf(
            Arguments.of(
                RBTreeNode(
                    0, 0, false, null, null
                ),
                RBTreeNode(
                    0, 0, false, null, null
                ),
            ), Arguments.of(
                RBTreeNode(
                    1, 1, false, RBTreeNode(0, 0, true), null
                ),
                RBTreeNode(
                    0, 0, false, null, RBTreeNode(1, 1, true)
                ),
            ), Arguments.of(
                RBTreeNode(
                    1, 1, true, RBTreeNode(0, 0, true), null
                ), RBTreeNode(
                    0, 0, true, null, RBTreeNode(1, 1, true)
                )
            ), Arguments.of(
                RBTreeNode(
                    1, 1, false, RBTreeNode(0, 0, true), null
                ), RBTreeNode(
                    0, 0, false, null, RBTreeNode(1, 1, false)
                )
            )
        )

        @JvmStatic
        fun testNodeRotateRightCases(): List<Arguments> = listOf(
            Arguments.of(
                RBTreeNode(
                    0, 0, false, null, null
                ),
                RBTreeNode(
                    0, 0, false, null, null
                ),
            ),
            Arguments.of(
                RBTreeNode(
                    1, 1, false, null, RBTreeNode(0, 0, true)
                ),
                RBTreeNode(
                    0, 0, false, RBTreeNode(1, 1, true), null
                ),
            ),
        )

        @JvmStatic
        fun testNodeColorFlipCases(): List<Arguments> = listOf(
            Arguments.of(null, null),
            Arguments.of(
                RBTreeNode(
                    0, 0, false, null, null
                ),
                RBTreeNode(
                    0, 0, true, null, null
                ),
            ),
            Arguments.of(
                RBTreeNode(
                    0, 0, false, null, null
                ),
                RBTreeNode(
                    0, 0, true, null, null
                ),
            ),
            Arguments.of(
                RBTreeNode(
                    0, 0, false, RBTreeNode(1, 1, false), null
                ),
                RBTreeNode(
                    0, 0, true, RBTreeNode(1, 1), null
                ),
            ),
            Arguments.of(
                RBTreeNode(
                    0, 0, false, null, RBTreeNode(1, 1, true)
                ),
                RBTreeNode(
                    0, 0, true, null, RBTreeNode(1, 1, false)
                ),
            ),
            Arguments.of(
                RBTreeNode(
                    0, 0, false, RBTreeNode(2, 2, false), RBTreeNode(1, 1, true)
                ),
                RBTreeNode(
                    0, 0, true, RBTreeNode(2, 2), RBTreeNode(1, 1, false)
                ),
            ),
        )

        @JvmStatic
        fun testNodeCreationCases(): List<Arguments> = listOf(
            Arguments.of(0, 0),
            Arguments.of(1, 1),
            Arguments.of(-1, -1),
        )

        @JvmStatic
        fun testUpdateRootCases(): List<Arguments> = listOf(
            Arguments.of(null),
            Arguments.of(RBTreeNode(0, 0, true)),
            Arguments.of(RBTreeNode(0, 0, false)),
        )

        @JvmStatic
        fun testBalanceTreeCases(): List<Arguments> = listOf(
            Arguments.of(
                RBTreeNode(0, 0, false), RBTreeNode(0, 0, false)
            ),
            Arguments.of(
                RBTreeNode(
                    0, 0, false,
                    RBTreeNode(-1, -1, true), null
                ),
                RBTreeNode(
                    0, 0, false,
                    RBTreeNode(-1, -1, true), null
                )
            ),
            Arguments.of(
                RBTreeNode(
                    1, 1, false,
                    RBTreeNode(0, 0, true), null
                ),
                RBTreeNode(
                    0, 0, false,
                    null, RBTreeNode(1, 1, true)
                )
            ),
            Arguments.of(
                RBTreeNode(
                    0, 0, true,
                    null, RBTreeNode(1, 1, false)
                ),
                RBTreeNode(
                    0, 0, true,
                    null, RBTreeNode(1, 1, false)
                )
            ),
            Arguments.of(
                RBTreeNode(
                    0, 0, false,
                    RBTreeNode(-1, -1, false), RBTreeNode(1, 1, false)
                ),
                RBTreeNode(
                    0, 0, false,
                    RBTreeNode(-1, -1, false), RBTreeNode(1, 1, false)
                )
            ),
            Arguments.of(
                RBTreeNode(
                    0, 0, false,
                    RBTreeNode(-1, -1, true), RBTreeNode(1, 1, false)
                ),
                RBTreeNode(
                    0, 0, false,
                    RBTreeNode(-1, -1, true), RBTreeNode(1, 1, false)
                )
            ),
            Arguments.of(
                RBTreeNode(
                    1, 1, false,
                    RBTreeNode(
                        0, 0, true,
                        RBTreeNode(-1, -1, false), null
                    ),
                    null
                ),
                RBTreeNode(
                    0, 0, false,
                    RBTreeNode(-1, -1, false), RBTreeNode(1, 1, true)
                )
            ),
            Arguments.of(
                RBTreeNode(
                    0, 0, true,
                    RBTreeNode(-1, -1, false), RBTreeNode(1, 1, false)
                ),
                RBTreeNode(
                    0, 0, false,
                    RBTreeNode(-1, -1, true), RBTreeNode(1, 1, true)
                )
            ),
            Arguments.of(
                RBTreeNode(
                    -1, -1, true,
                    RBTreeNode(-2, -2, false), RBTreeNode(0, 0, false)
                ),
                RBTreeNode(
                    0, 0, false,
                    RBTreeNode(
                        -1, -1, true, RBTreeNode(-2, -2), null
                    ), null
                )
            ),
            Arguments.of(
                RBTreeNode(
                    0, 0, true,
                    RBTreeNode(
                        -1, -1, false,
                        RBTreeNode(-2, -2), null
                    ), RBTreeNode(1, 1, false)
                ),
                RBTreeNode(
                    0, 0, false,
                    RBTreeNode(
                        -1, -1, true, RBTreeNode(-2, -2), null
                    ),
                    RBTreeNode(1, 1)
                )
            ),
        )
    }

    private fun insert(elements: List<Int>) {
        for (element in elements) {
            tree[element] = element
        }
    }

    private fun nodeToStringTreeView(node: RBTreeNode<Int, Int>): String {
        val builder = StringBuilder()
        node.toStringWithSubtreeView(0, builder)
        return builder.toString()
    }

}
