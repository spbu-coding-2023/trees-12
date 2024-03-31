import org.junit.jupiter.api.Assertions
import tree_tripper.nodes.binary_nodes.AbstractBSTreeNode


public fun <K, V, N: AbstractBSTreeNode<K, V, N>> assertBinaryNodeDataEquals(nodeFirst: N?, nodeSecond: N?): Unit {
    assertBinaryNodeDataEquals(nodeFirst, nodeSecond) { _, _: N -> true }
}

public fun <K, V, N: AbstractBSTreeNode<K, V, N>> assertBinaryNodeDataEquals(
    nodeFirst: N?,
    nodeSecond: N?,
    assertAction: (N, N) -> (Boolean)
): Unit {
    if (nodeFirst == null) return Assertions.assertNull(nodeSecond) { "Second node is not null." }
    if (nodeSecond == null) return Assertions.assertNull(nodeFirst) { "First node is not null." }

    Assertions.assertEquals(nodeFirst.key, nodeSecond.key) { "Keys are not equal." }
    Assertions.assertEquals(nodeFirst.value, nodeSecond.value) { "Values are not equal." }
    Assertions.assertTrue(assertAction(nodeFirst, nodeSecond)) { "Action assertion is invalid equals" }
}

public fun <K, V, N: AbstractBSTreeNode<K, V, N>> assertBinaryNodeDeepEquals(nodeFirst: N?, nodeSecond: N?): Unit {
    assertBinaryNodeDeepEquals(nodeFirst, nodeSecond) { _, _: N? -> true }
}

public fun <K, V, N: AbstractBSTreeNode<K, V, N>> assertBinaryNodeDeepEquals(
    nodeFirst: N?,
    nodeSecond: N?,
    assertAction: (N, N) -> (Boolean)
): Unit {
    if (nodeFirst == null) return Assertions.assertNull(nodeSecond) { "Second node is not null." }
    if (nodeSecond == null) return Assertions.assertNull(nodeFirst) { "First node is not null." }

    assertBinaryNodeDataEquals(nodeFirst, nodeSecond, assertAction)
    assertBinaryNodeDeepEquals(nodeFirst.leftChild, nodeSecond.leftChild, assertAction)
    assertBinaryNodeDeepEquals(nodeFirst.rightChild, nodeSecond.rightChild, assertAction)
}
