package tree_tripper.binary_trees

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.assertTimeout
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import tree_tripper.binary_trees.assistants.BSTreeTestAssistant
import java.time.Duration
import kotlin.random.Random
import kotlin.test.Test


public class BSTreeTest {
    private lateinit var tree: BSTreeTestAssistant<Int, Int>

    @BeforeEach
    public fun setup() {
        tree = BSTreeTestAssistant()
    }

    @Test
    @DisplayName("tree initialization")
    public fun testTreeInitialization() {
        tree.assertRootInitialization()
        assertEquals(tree.getSize(), 0, "Incorrect a tree initialization")
    }

    @Test
    @DisplayName("create node")
    public fun testCreateNode() {
        tree.assertWasCreatedNode(1, -1)
    }

    @Test
    @DisplayName("update root")
    public fun testUpdateRoot() {
        tree.assertWasUpdatedRoot(1, -1)
    }

    @Test
    @DisplayName("balance tree")
    public fun testBalanceTree() {
        tree.assertWasBalancedTree(1, -1)
    }

    @Test
    @DisplayName("insert root")
    public fun testInsertRoot() {
        tree.insert(1, -1)
        tree.assertIsBSTree()
        assertEquals(tree.getSize(), 1, "Incorrect resizing tree size")

        tree.insert(1, 0)
        tree.assertIsBSTree()
        assertEquals(tree.getSize(), 1, "Incorrect resizing tree size")
    }

    @Test
    @DisplayName("insert children root")
    public fun testInsertChildrenRoot() {
        tree.insert(2, -2)
        tree.insert(1, -1)
        tree.insert(3, -3)
        tree.assertIsBSTree()
        assertEquals(tree.getSize(), 3, "Incorrect resizing tree size")
    }

    @Test
    @DisplayName("insert 5 elements")
    public fun testInsertElements() {
        tree.insert(4, -4)
        tree.insert(5, -5)
        tree.insert(3, -3)
        tree.insert(2, -2)
        tree.insert(6, -6)
        tree.assertIsBSTree()
        assertEquals(tree.getSize(), 5, "Incorrect resizing tree size")
    }

    @ParameterizedTest
    @MethodSource("getSizeAndTimeArguments")
    @DisplayName("insert with size and time")
    public fun testInsertWithSizeAndTime(size: Int, seconds: Long) {
        assertTimeout(Duration.ofSeconds(seconds)) {
            repeat(size) {
                val keyRandom = Random.nextInt(-1000, 1000)
                tree.insert(keyRandom, keyRandom)
            }
        }

        tree.assertIsBSTree()
    }

    @Test
    @DisplayName("insert if absent root")
    public fun testInsertIfAbsentRoot() {
        assertEquals(tree.insertIfAbsent(1, -1), true)
        tree.assertIsBSTree()
        assertEquals(tree.getSize(), 1, "Incorrect resizing tree size")

        assertEquals(tree.insertIfAbsent(1, 1), false)
        tree.assertIsBSTree()
        assertEquals(tree.getSize(), 1, "Incorrect resizing tree size")
    }

    @Test
    @DisplayName("set with brackets")
    public fun testSet() {
        assertEquals(tree.search(1), null)

        tree[1] = -1
        assertEquals(tree.search(1), -1)
    }

    @Test
    @DisplayName("search node")
    public fun testSearchNode() {
        tree.insert(1, -1)
        assertEquals(tree.search(1), -1)
        tree.assertIsBSTree()

        assertEquals(tree.search(0), null)
        tree.assertIsBSTree()
    }

    @Test
    @DisplayName("search children node")
    public fun testSearchChildrenNode() {
        tree.insert(2, -2)
        tree.insert(1, -1)
        tree.insert(3, -3)
        assertEquals(tree.search(2), -2)
        assertEquals(tree.search(1), -1)
        assertEquals(tree.search(3), -3)
        tree.assertIsBSTree()
    }

    @ParameterizedTest
    @MethodSource("getSizeAndTimeArguments")
    @DisplayName("search with size and time")
    public fun testSearchWithSizeAndTime(size: Int, seconds: Long) {
        val array = IntArray(size)
        var index = 0
        repeat(size) {
            val keyRandom = Random.nextInt(-1000, 1000)
            array[index++] = keyRandom
            tree.insert(keyRandom, keyRandom * (-1))
        }

        assertTimeout(Duration.ofSeconds(seconds)) {
            repeat(10) {
                val keyRandom = Random.nextInt(-1000, 1000)
                if (keyRandom in array)
                    assertEquals(tree.search(keyRandom), (-1) * keyRandom)
                else
                    assertEquals(tree.search(keyRandom), null)
            }
        }

        tree.assertIsBSTree()
    }

    @Test
    @DisplayName("search of default")
    public fun testSearchOrDefault() {
        assertEquals(tree.searchOrDefault(1, 0), 0)

        tree.insert(1, -1)
        assertEquals(tree.searchOrDefault(1, 0), -1)
    }

    @Test
    @DisplayName("contains")
    public fun testContains() {
        assertEquals(tree.contains(1), false)

        tree.insert(1, -1)
        assertEquals(tree.contains(1), true)
    }

    @Test
    @DisplayName("get with brackets")
    public fun testGet() {
        assertEquals(tree[1], null)

        tree[1] = -1
        assertEquals(tree[1], -1)
    }

    @Test
    @DisplayName("get maximum in subtree")
    public fun testGetMaxInSubtree() {
        assertEquals(tree.getMaxInSubtree(0), null)
        tree.assertIsBSTree()

        tree[2] = -2
        assertEquals(tree.getMaxInSubtree(2), Pair(2, -2))
        tree.assertIsBSTree()

        tree[3] = -3
        tree[1] = -1
        assertEquals(tree.getMaxInSubtree(2), Pair(3, -3))
        tree.assertIsBSTree()
    }

    @Test
    @DisplayName("get maximum")
    public fun testGetMax() {
        assertEquals(tree.getMax(), null)

        tree[2] = -2
        assertEquals(tree.getMax(), Pair(2, -2))

        tree[3] = -3
        tree[1] = -1
        assertEquals(tree.getMax(), Pair(3, -3))
    }

    @Test
    @DisplayName("get minimum in subtree")
    public fun testGetMinInSubtree() {
        assertEquals(tree.getMinInSubtree(0), null)
        tree.assertIsBSTree()

        tree[2] = -2
        assertEquals(tree.getMinInSubtree(2), Pair(2, -2))
        tree.assertIsBSTree()

        tree[1] = -1
        tree[3] = -3
        assertEquals(tree.getMinInSubtree(2), Pair(1, -1))
        tree.assertIsBSTree()
    }

    @Test
    @DisplayName("get minimum")
    public fun testGetMin() {
        assertEquals(tree.getMin(), null)

        tree[2] = -2
        assertEquals(tree.getMin(), Pair(2, -2))

        tree[1] = -1
        tree[3] = -3
        assertEquals(tree.getMin(), Pair(1, -1))
    }

    @Test
    @DisplayName("remove")
    public fun testRemove() {
        assertEquals(tree.remove(0), null)
        tree.assertIsBSTree()

        tree[2] = -2
        assertEquals(tree.remove(2), -2)
        tree.assertIsBSTree()

        tree[2] = -2
        tree[1] = -1
        assertEquals(tree.remove(2), -2)
        tree.assertIsBSTree()

        tree[2] = -2
        tree[3] = -3
        assertEquals(tree.remove(2), -2)
        tree.assertIsBSTree()
        assertEquals(tree.remove(3), -3)
        tree.assertIsBSTree()
    }

    @ParameterizedTest
    @MethodSource("getSizeAndTimeArguments")
    @DisplayName("remove with size and time")
    public fun testRemoveWithSizeAndTime(size: Int, seconds: Long) {
        val array = IntArray(size)
        var index = 0
        repeat(size) {
            val keyRandom = Random.nextInt(-1000, 1000)
            array[index++] = keyRandom
            tree[keyRandom] = (-1) * keyRandom
        }

        assertTimeout(Duration.ofSeconds(seconds)) {
            repeat(10) {
                val keyRandom = Random.nextInt(-1000, 1000)
                if (keyRandom in array)
                    assertEquals(tree.remove(keyRandom), (-1) * keyRandom)
                else
                    assertEquals(tree.remove(keyRandom), null)
            }
        }

        tree.assertIsBSTree()
    }

    @Test
    @DisplayName("remove or default")
    public fun testRemoveOrDefault() {
        assertEquals(tree.removeOrDefault(1, 0), 0)
        tree.insert(1, -1)
        assertEquals(tree.removeOrDefault(1, 0), -1)
    }

    companion object {
        @JvmStatic
        fun getSizeAndTimeArguments() = listOf(
            Arguments.of(100, 1L),
            Arguments.of(10000, 1L)
        )
    }

}
