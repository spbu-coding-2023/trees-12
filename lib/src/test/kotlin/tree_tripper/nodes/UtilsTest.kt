package tree_tripper.nodes

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import tree_tripper.nodes.binary_nodes.BSTreeNode


public class UtilsTest {

    @ParameterizedTest(name = "{displayName}[{index}] {argumentsWithNames}")
    @MethodSource("testNodeUpdateCases")
    @DisplayName("util of node update")
    public fun testNodeUpdate(expected: Boolean, node: BSTreeNode<Int, Int>?) {
        var isActivateAction: Boolean = false
        notNullNodeUpdate(node) { isActivateAction = true }
        Assertions.assertEquals(expected, isActivateAction)
    }

    @ParameterizedTest(name = "{displayName}[{index}] {argumentsWithNames}")
    @MethodSource("testNodeActionCases")
    @DisplayName("util of action on node")
    public fun testNodeAction(expected: Boolean, node: BSTreeNode<Int, Int>?) {
        Assertions.assertEquals(expected, notNullNodeAction(node, false) { expected })
    }

    companion object {
        @JvmStatic
        fun testNodeUpdateCases(): List<Arguments> = listOf(
            Arguments.of(false, null),
            Arguments.of(true, BSTreeNode(0, 0)),
        )

        @JvmStatic
        fun testNodeActionCases(): List<Arguments> = listOf(
            Arguments.of(false, null),
            Arguments.of(true, BSTreeNode(0, 0)),
        )
    }

}
