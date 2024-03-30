package tree_tripper.binary_trees

import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import tree_tripper.binary_trees.assistants.AVLTreeTestAssistant
import tree_tripper.nodes.binary_nodes.AVLTreeNode


class AVLTreeTest {
    private lateinit var tree: AVLTreeTestAssistant<Int, Int>

    @BeforeEach
    fun setup() {
        tree = AVLTreeTestAssistant<Int, Int>()
    }

    @Test
    public fun testTreeInitializing() {
        tree.assertRoot(null) { "Root of AVLTree is not null by standard initialize." }
        Assertions.assertEquals(0, tree.getSize())
    }

    @ParameterizedTest
    @MethodSource("checkBalanceFactor")
    public fun checkBalanceFactor(node: AVLTreeNode<Int, Int>?) {
        tree.assertBalanceFactor(node)
    }

    @ParameterizedTest
    @MethodSource("testNodeRotateRightCases")
    public fun testNodeRotateRightCases(expected: AVLTreeNode<Int, Int>, node: AVLTreeNode<Int, Int>) {
        tree.assertNodeRightRotation(expected, node)
    }

    @ParameterizedTest
    @MethodSource("testNodeRotateLeftCases")
    public fun testNodeRotateLeftCases(expected: AVLTreeNode<Int, Int>, node: AVLTreeNode<Int, Int>) {
        tree.assertNodeLeftRotation(expected, node)
    }

    companion object {

        @JvmStatic
        fun checkBalanceFactor(): List<Arguments> = listOf(

            Arguments.of(
                AVLTreeNode(
                    0, 0, 1,
                    null,
                    null
                )
            ),

            Arguments.of(
                AVLTreeNode(
                    0, 0, 2,
                    null,
                    AVLTreeNode(0, 0, 1, null, null)
                )
            ),

            Arguments.of(
                AVLTreeNode(
                    0, 0, 2,
                    AVLTreeNode(0, 0, 1,null, null),
                    null
                )
            ),

            Arguments.of(
                AVLTreeNode(0, 0, 2,
                    AVLTreeNode(0, 0, 1, null, null),
                    AVLTreeNode(0, 0, 1, null, null)
                )
            ),
        )

        @JvmStatic
        fun testNodeRotateLeftCases(): List<Arguments> = listOf(

            //Null check
            Arguments.of(
                AVLTreeNode(
                    0, 0, 1,
                    null, null
                ),
                AVLTreeNode(
                    0, 0, 1,
                    null, null
                )
            ),

            //Simple left rotation
            Arguments.of(
                AVLTreeNode(
                    1, 1, 2,
                    AVLTreeNode(
                        0, 0, 1, null, null),
                    AVLTreeNode(
                        2, 2, 1, null, null),
                ),
                AVLTreeNode(
                    0, 0, 3,
                    null,
                    AVLTreeNode(
                        1, 1, 2,
                        null,
                        AVLTreeNode(
                            2, 2, 1,
                            null, null
                        ),
                    ),
                ),
            ),

            //Left rotation with children
            Arguments.of(
                AVLTreeNode(
                    3, 3, 3,
                    AVLTreeNode(
                        1, 1, 2,
                        AVLTreeNode(
                            0, 0, 1,
                            null, null
                        ),
                        AVLTreeNode(
                            2, 2, 1,
                            null, null
                        )
                    ),
                    AVLTreeNode(
                        4, 4, 2,
                        null,
                        AVLTreeNode(
                            5, 5, 1,
                            null, null
                        )
                    )
                ),
                AVLTreeNode(
                    1, 1, 4,
                    AVLTreeNode(
                        0, 0, 1,
                        null, null
                    ),
                    AVLTreeNode(
                        3, 3, 3,
                        AVLTreeNode(
                            2, 2, 1,
                            null, null
                        ),
                        AVLTreeNode(
                            4, 4, 2,
                            null,
                            AVLTreeNode(
                                5, 5, 1,
                                null, null
                            )
                        )
                    )
                )
            )
        )

        @JvmStatic
        fun testNodeRotateRightCases(): List<Arguments> = listOf(

            //Null check
            Arguments.of(
                AVLTreeNode(
                    0, 0, 1,
                    null, null
                ),
                AVLTreeNode(
                    0, 0, 1,
                    null, null
                )
            ),

            //Right rotation with children
            Arguments.of(
                AVLTreeNode(
                    1, 1, 2,
                    AVLTreeNode(
                        0, 0, 1, null, null),
                    AVLTreeNode(
                        2, 2, 1, null, null),
                ),
                AVLTreeNode(
                    2, 2, 3,
                    AVLTreeNode(
                        1, 1, 2,
                        AVLTreeNode(
                            0, 0, 1,
                            null, null
                        ),
                        null,
                    ),
                    null
                ),
            ),

            //Right rotation with children
            Arguments.of(
                AVLTreeNode(
                    2, 2, 3,
                    AVLTreeNode(
                        1, 1, 2,
                        AVLTreeNode(
                            0, 0, 1,
                            null, null
                        ),
                        null
                    ),
                    AVLTreeNode(
                        4, 4, 2,
                        AVLTreeNode(
                            3, 3, 1,
                            null, null
                        ),
                        AVLTreeNode(
                            5, 5, 1,
                            null, null
                        ),
                    ),
                ),
                AVLTreeNode(
                    4, 4, 4,
                    AVLTreeNode(
                        2, 2, 3,
                        AVLTreeNode(
                            1, 1, 2,
                            AVLTreeNode(
                                0, 0, 1,
                                null, null
                            ),
                            null
                        ),
                        AVLTreeNode(
                            3, 3, 1,
                            null, null
                        )
                    ),
                    AVLTreeNode(
                        5, 5, 1,
                        null, null),
                )
            )
        )

    }
}
