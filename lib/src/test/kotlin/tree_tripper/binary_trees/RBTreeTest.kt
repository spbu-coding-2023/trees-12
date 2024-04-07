package tree_tripper.binary_trees

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import tree_tripper.binary_trees.assistants.RBTreeTestAssistant
import tree_tripper.nodes.binary_nodes.RBTreeNode
import kotlin.random.Random
import kotlin.random.nextInt


class RBTreeTest {
    private lateinit var tree: RBTreeTestAssistant<Int, Int>
    private val randomizer = Random(42)

    @BeforeEach
    public fun setup() {
        tree = RBTreeTestAssistant()
    }

    @Test
    @DisplayName("is root after initializing equals null")
    public fun testInitializing() {
        tree.assertRoot(null) { "Root is not null after init" }
        tree.assertIsRBTree()
        Assertions.assertEquals(0, tree.size)
    }

    @Test
    @DisplayName("insert sorted elements")
    public fun testInsertSortedElements() {
        for (i in 0..20) {
            tree.insert(i, i)
            tree.assertIsRBTree()
            Assertions.assertEquals(i + 1, tree.size)
        }

    }

    @Test
    @DisplayName("insert reversed sorted elements")
    public fun testInsertReverseSortedElements() {
        for (i in 20 downTo 0) {
            tree.insert(i, i)
            tree.assertIsRBTree()
            Assertions.assertEquals((20 - i) + 1, tree.size)
        }

    }

    @Test
    @DisplayName("insert a lot of unsorted elements")
    public fun testInsertUnsortedElements() {
        val elements: MutableSet<Int> = mutableSetOf()
        for (i in 0..256) {
            val value = randomizer.nextInt()
            tree.insert(value, value)
            elements.add(value)
            tree.assertIsRBTree()
            Assertions.assertEquals(elements.size, tree.size)
        }
    }

    @Test
    @DisplayName("remove root with children")
    public fun testRemoveRootWithChildren() {
        for (i in 0..20) tree.insert(i, i)
        val root: Pair<Int, Int> = tree.getRoot()
        Assertions.assertEquals(root.second, tree.remove(root.first))
        tree.assertIsRBTree()
        Assertions.assertEquals(20, tree.size)
    }

    @Test
    @DisplayName("remove black node with children")
    public fun testRemoveBlackNodeWithChildren() {
        for (i in 0..20) tree.insert(i, i)
        Assertions.assertEquals(15, tree.remove(15))
        tree.assertIsRBTree()
        Assertions.assertEquals(20, tree.size)
    }

    @Test
    @DisplayName("remove red node with children")
    public fun testRemoveRedNodeWithChildren() {
        for (i in 0..20) tree.insert(i, i)
        Assertions.assertEquals(1, tree.remove(1))
        tree.assertIsRBTree()
        Assertions.assertEquals(20, tree.size)
    }

    @Test
    @DisplayName("remove random node")
    public fun testRemoveRandomNodeChildren() {
        for (i in 0..20) tree.insert(i, i)
        val key = randomizer.nextInt(0..20)
        try {
            Assertions.assertEquals(key, tree.remove(key))
            tree.assertIsRBTree()
            Assertions.assertEquals(20, tree.size)
        } catch (e: AssertionError) {
            throw AssertionError(
                "Try remove node with key $key from tree: ${tree.toStringWithTreeView()}",
                e
            )
        }
    }

    @Test
    @DisplayName("remove root without children")
    public fun testRemoveRootWithoutChildren() {
        tree.insert(0, 0)
        Assertions.assertEquals(0, tree.remove(0))
        Assertions.assertEquals(0, tree.size)

    }

    @Test
    @DisplayName("remove black node without children")
    public fun testRemoveBlackNodeWithoutChildren() {
        for (i in 0..20) tree.insert(i, i)
        Assertions.assertEquals(6, tree.remove(6))
        tree.assertIsRBTree()
        Assertions.assertEquals(20, tree.size)
    }

    @Test
    @DisplayName("remove red node without children")
    public fun testRemoveRedNodeWithoutChildren() {
        for (i in 0..21) tree.insert(i, i)
        Assertions.assertEquals(20, tree.remove(20))
        tree.assertIsRBTree()
        Assertions.assertEquals(21, tree.size)
    }

    @Test
    @DisplayName("remove root with one left child")
    public fun testRemoveRootWithOneLeftChild() {
        tree.insert(0, 0)
        tree.insert(-1, -1)
        Assertions.assertEquals(0, tree.remove(0))
        Assertions.assertEquals(1, tree.size)
        Assertions.assertEquals(Pair(-1, -1), tree.getRoot())
    }

    @Test
    @DisplayName("remove min node at empty tree")
    public fun testRemoveMinNodeAtEmptyTree() {
        tree.assertRemoveMinNode(
            tree_view = null,
            expected = null
        )
    }

    @Test
    @DisplayName("remove min node without children")
    public fun testRemoveMinNodeWithoutChildren() {
        tree.assertRemoveMinNode(
            tree_view = RBTreeNode(1, 1, isRed = false),
            expected = null
        )
        tree.assertRemoveMinNode(
            tree_view = RBTreeNode(1, 1, isRed = true),
            expected = null
        )
    }

    @Test
    @DisplayName("remove min node with red child")
    public fun testRemoveMinNodeWithRedChild() {
        tree.assertRemoveMinNode(
            tree_view = RBTreeNode(
                1, 1, isRed = false,
                leftChild = RBTreeNode(0, 0, isRed = true),
                rightChild = null
            ),
            expected = RBTreeNode(1, 1, isRed = false)
        )
    }

    @Test
    @DisplayName("remove min node with two child")
    public fun testRemoveMinNodeWithTwoChild() {
        tree.assertRemoveMinNode(
            tree_view = RBTreeNode(
                1, 1, isRed = false,
                leftChild = RBTreeNode(0, 0, isRed = false),
                rightChild = RBTreeNode(2, 2, isRed = false),
            ),
            expected = RBTreeNode(
                2, 2, isRed = true,
                leftChild = RBTreeNode(1, 1, isRed = true),
                rightChild = null
            )
        )
    }

    @Test
    @DisplayName("remove min node with big subtree")
    public fun testRemoveMinNodeWithBigSubtree() {
        tree.assertRemoveMinNode(
            tree_view = RBTreeNode(
                2, 2, isRed = false,
                leftChild = RBTreeNode(
                    0, 0, isRed = false,
                    leftChild = RBTreeNode(-1, -1, isRed = false),
                    rightChild = RBTreeNode(1, 1, isRed = false)
                ),
                rightChild = RBTreeNode(
                    4, 4, isRed = false,
                    leftChild = RBTreeNode(3, 3, isRed = false),
                    rightChild = RBTreeNode(5, 5, isRed = false)
                ),
            ),
            expected = RBTreeNode(
                4, 4, isRed = true,
                leftChild = RBTreeNode(
                    2, 2, isRed = true,
                    leftChild = RBTreeNode(
                        1, 1, isRed = false,
                        leftChild = RBTreeNode(0, 0, isRed = true),
                        rightChild = null
                    ),
                    rightChild = RBTreeNode(3, 3, isRed = false)
                ),
                rightChild = RBTreeNode(5, 5, isRed = false)
            )
        )
    }

    @Test
    @DisplayName("left rotate node without right")
    public fun testLeftRotateNodeWithoutRightChild() {
        val node = RBTreeNode(
            0, 0, false,
            RBTreeNode(-1, -1, true),
            null,
        )
        tree.assertNodeLeftRotation(
            node, node
        )
    }

    @Test
    @DisplayName("left rotate  subtree")
    public fun testLeftRotateOf() {
        tree.assertNodeLeftRotation(
            RBTreeNode(
                2, 2, false,
                RBTreeNode(
                    0, 0, true,
                    RBTreeNode(-1, -1, false),
                    RBTreeNode(1, 1, false)
                ),
                RBTreeNode(3, 3, false)
            ),
            RBTreeNode(
                0, 0, false,
                RBTreeNode(-1, -1, false),
                RBTreeNode(
                    2, 2, true,
                    RBTreeNode(1, 1, false),
                    RBTreeNode(3, 3, false),
                )
            ),
        )
    }

    @Test
    @DisplayName("right rotate node without left")
    public fun testRightRotateNodeWithoutLeftChild() {
        val node = RBTreeNode(
            0, 0, false,
            null,
            RBTreeNode(1, 1, true)
        )
        tree.assertNodeRightRotation(
            node, node
        )
    }

    @Test
    @DisplayName("right rotate of subtree")
    public fun testRightRotate() {
        tree.assertNodeRightRotation(
            RBTreeNode(
                0, 0, false,
                RBTreeNode(-1, -1, false),
                RBTreeNode(
                    2, 2, true,
                    RBTreeNode(1, 1, false),
                    RBTreeNode(3, 3, false),
                )
            ),
            RBTreeNode(
                2, 2, false,
                RBTreeNode(
                    0, 0, true,
                    RBTreeNode(-1, -1, false),
                    RBTreeNode(1, 1, false)
                ),
                RBTreeNode(3, 3, false)
            ),
        )
    }

    @Test
    @DisplayName("flip colors of node and repeat it")
    public fun testFlipColors() {
        val node = RBTreeNode(0, 0, false, RBTreeNode(-1, -1), RBTreeNode(1, 1))
        tree.assertNodeColorFlip(
            RBTreeNode(
                0, 0, true,
                RBTreeNode(-1, -1, false),
                RBTreeNode(1, 1, false)
            ), node
        )
        tree.assertNodeColorFlip(
            RBTreeNode(0, 0, false, RBTreeNode(-1, -1), RBTreeNode(1, 1)),
            node
        )
    }

    @Test
    @DisplayName("is left child of  red null node")
    public fun testIsRedLeftChildOfNullNode() {
        tree.assertNodeLeftChildColor(false, null)
    }

    @Test
    @DisplayName("is red left child of node which property isRed equals true")
    public fun testIsRedLeftChildOfNodeWhichPropertyEqualsRed() {
        tree.assertNodeLeftChildColor(
            true,
            RBTreeNode(
                0, 0, false,
                RBTreeNode(-1, -1, true),
                null
            )
        )
    }

    @Test
    @DisplayName("is red left child of node with property isRed equals false")
    public fun testIsRedLeftChildOfNodeWhichPropertyEqualsBlack() {
        tree.assertNodeLeftChildColor(
            false,
            RBTreeNode(
                0, 0, true,
                RBTreeNode(-1, -1, false),
                null
            )
        )
    }

    @Test
    @DisplayName("is red null node")
    public fun testIsRedNullNode() {
        tree.assertNodeColor(false, null)
    }

    @Test
    @DisplayName("is red node with property isRed equals true")
    public fun testIsRedNodeWithPropertyEqualsRed() {
        tree.assertNodeColor(true, RBTreeNode(0, 0, true))
    }

    @Test
    @DisplayName("is red node with property isRed equals false")
    public fun testIsRedNodeWithPropertyEqualsBlack() {
        tree.assertNodeColor(false, RBTreeNode(0, 0, false))
    }


    @Test
    @DisplayName("create node")
    public fun testCreateNode() {
        tree.assertNodeCreation(0, 0)
    }

    @Test
    @DisplayName("update root as null")
    public fun testUpdateRootAsNull() {
        tree.assertUpdateRoot(null)
    }

    @Test
    @DisplayName("update root as red node")
    public fun testUpdateRootAsRedNode() {
        tree.assertUpdateRoot(RBTreeNode(0, 0, true))
    }

    @Test
    @DisplayName("update root as black node")
    public fun testUpdateRootAsBlackNode() {
        tree.assertUpdateRoot(RBTreeNode(0, 0, false))
    }

}