package tree_tripper.iterators

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import tree_tripper.nodes.binary_nodes.BSTreeNode


class BinarySearchTreeIteratorTest {
    lateinit var iterator: BinarySearchTreeIterator<Int, Int, BSTreeNode<Int, Int>>

    @ParameterizedTest(name = "{displayName}[{index}] {argumentsWithNames}")
    @MethodSource("testIteratorCases")
    @DisplayName("test iterator at width order")
    public fun testWidthOrderIterator(expected: List<Int>, root: BSTreeNode<Int, Int>) {
        iterator = BinarySearchTreeIterator(root)
        var index: Int = 0

        while (iterator.hasNext()) {
            Assertions.assertEquals(iterator.next(), Pair(expected[index], expected[index]))
            index++
        }
    }

    @ParameterizedTest(name = "{displayName}[{index}] {argumentsWithNames}")
    @MethodSource("testGetALotOfElementsCases")
    @DisplayName("try get more elements than iterator has")
    public fun testGetALotOfElements(order: IterationOrders) {
        iterator = BinarySearchTreeIterator(null, order)
        Assertions.assertFalse(iterator.hasNext())
        Assertions.assertThrows(NoSuchElementException::class.java) { iterator.next() }
    }

    @ParameterizedTest(name = "{displayName}[{index}] {argumentsWithNames}")
    @MethodSource("testIteratorCases")
    @DisplayName("test iterator at increase order")
    public fun testIncreasingOrderIterator(expected: List<Int>, root: BSTreeNode<Int, Int>) {
        iterator = BinarySearchTreeIterator(root, IterationOrders.INCREASING_ORDER)
        val sortedElements = expected.sorted()
        var index: Int = 0

        while (iterator.hasNext()) {
            Assertions.assertEquals(iterator.next(), Pair(sortedElements[index], sortedElements[index]))
            index++
        }
    }

    @ParameterizedTest(name = "{displayName}[{index}] {argumentsWithNames}")
    @MethodSource("testIteratorCases")
    @DisplayName("test iterator at decrease order")
    public fun testDecreasingOrderIterator(expected: List<Int>, root: BSTreeNode<Int, Int>) {
        iterator = BinarySearchTreeIterator(root, IterationOrders.DECREASING_ORDER)
        val sortedElements = expected.sorted().reversed()
        var index: Int = 0

        while (iterator.hasNext()) {
            Assertions.assertEquals(iterator.next(), Pair(sortedElements[index], sortedElements[index]))
            index++
        }
    }

    companion object {

        @JvmStatic
        fun testGetALotOfElementsCases(): List<Arguments> = listOf(
            Arguments.of(IterationOrders.WIDTH_ORDER),
            Arguments.of(IterationOrders.INCREASING_ORDER),
            Arguments.of(IterationOrders.DECREASING_ORDER),
        )

        @JvmStatic
        fun testIteratorCases(): List<Arguments> = listOf(
            Arguments.of(listOf(0), BSTreeNode(0, 0)),
            Arguments.of(
                listOf(0, -5, 5),
                BSTreeNode(
                    0, 0,
                    BSTreeNode(-5, -5),
                    BSTreeNode(5, 5),
                )
            ),
            Arguments.of(
                listOf(0, -5, 5, -10),
                BSTreeNode(
                    0, 0,
                    BSTreeNode(
                        -5, -5,
                        BSTreeNode(-10, -10),
                        null,
                    ),
                    BSTreeNode(5, 5),
                )
            ),
            Arguments.of(
                listOf(0, -5, 5, -10, 10),
                BSTreeNode(
                    0, 0,
                    BSTreeNode(
                        -5, -5,
                        BSTreeNode(-10, -10),
                        null,
                    ),
                    BSTreeNode(
                        5, 5,
                        null,
                        BSTreeNode(10, 10),
                    )
                )
            ),
            Arguments.of(
                listOf(0, -5, 5, -10, -3, 3, 10, 4),
                BSTreeNode(
                    0, 0,
                    BSTreeNode(
                        -5, -5,
                        BSTreeNode(-10, -10),
                        BSTreeNode(-3, -3),
                    ),
                    BSTreeNode(
                        5, 5,
                        BSTreeNode(
                            3, 3,
                            null,
                            BSTreeNode(4, 4)
                        ),
                        BSTreeNode(10, 10),
                    )
                )
            )
        )

    }

}
