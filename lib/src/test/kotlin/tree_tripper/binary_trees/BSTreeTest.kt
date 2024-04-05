package tree_tripper.binary_trees

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.assertTimeout
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import tree_tripper.binary_trees.assistants.BSTreeTestAssistant
import tree_tripper.iterators.IterationOrders
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
        assertEquals(tree.getSize(), 0, "Incorrect a tree initialization.")
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
        assertEquals(tree.getSize(), 1, "Incorrect resizing tree size.")

        tree.insert(1, 0)
        tree.assertIsBSTree()
        assertEquals(tree.getSize(), 1, "Incorrect resizing tree size.")
    }

    @Test
    @DisplayName("insert children root")
    public fun testInsertChildrenRoot() {
        tree.insert(2, -2)
        tree.insert(1, -1)
        tree.insert(3, -3)
        tree.assertIsBSTree()
        assertEquals(tree.getSize(), 3, "Incorrect resizing tree size.")
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
    @DisplayName("if absent insert root")
    public fun testInsertIfAbsentRoot() {
        assertEquals(tree.insertIfAbsent(1, -1), true)
        tree.assertIsBSTree()
        assertEquals(tree.getSize(), 1, "Incorrect resizing tree size.")

        assertEquals(tree.insertIfAbsent(1, 1), false)
        tree.assertIsBSTree()
        assertEquals(tree.getSize(), 1, "Incorrect resizing tree size.")
    }

    @Test
    @DisplayName("search root")
    public fun testSearchNode() {
        assertEquals(tree.search(0), null, "Incorrect search in a empty tree.")

        tree.insert(1, -1)
        assertEquals(tree.search(1), -1, "Incorrect search an existent root.")
    }

    @Test
    @DisplayName("search children root")
    public fun testSearchChildrenNode() {
        tree.insert(2, -2)
        tree.insert(1, -1)
        tree.insert(3, -3)
        assertEquals(tree.search(1), -1, "Incorrect search existent children root.")
        assertEquals(tree.search(3), -3, "Incorrect search existent children root.")
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
                    assertEquals(tree.search(keyRandom), (-1) * keyRandom,
                        "Incorrect search an existent node.")
                else
                    assertEquals(tree.search(keyRandom), null,
                        "Incorrect search a non-existent node.")
            }
        }

        tree.assertIsBSTree()
    }

    @Test
    @DisplayName("search of default")
    public fun testSearchOrDefault() {
        assertEquals(tree.searchOrDefault(1, 0), 0,
            "Incorrect return of search a non-existent node.")

        tree.insert(1, -1)
        assertEquals(tree.searchOrDefault(1, 0), -1,
            "Incorrect return of search an existent node.")
    }

    @Test
    @DisplayName("contains")
    public fun testContains() {
        assertEquals(tree.contains(1), false, "Incorrect return of search a non-existent node.")

        tree.insert(1, -1)
        assertEquals(tree.contains(1), true, "Incorrect return of search an existent node.")
    }

    @Test
    @DisplayName("set with brackets")
    public fun testSet() {
        tree[1] = -1
        assertEquals(tree.search(1), -1, "Incorrect set.")

        tree[1] = 1
        assertEquals(tree.search(1), 1, "Incorrect change of the value.")
    }

    @Test
    @DisplayName("get with brackets")
    public fun testGet() {
        assertEquals(tree[1], null, "Incorrect get a non-existent node.")

        tree[1] = -1
        assertEquals(tree[1], -1, "Incorrect get an existent node.")
    }

    @Test
    @DisplayName("get maximum in subtree")
    public fun testGetMaxInSubtree() {
        assertEquals(tree.getMaxInSubtree(0), null,
            "Incorrect search a maximum key in a empty tree.")
        tree.assertIsBSTree()

        tree[2] = -2
        assertEquals(tree.getMaxInSubtree(2), Pair(2, -2),
            "Incorrect search a maximum key in subtree without children.")
        tree.assertIsBSTree()
    }

    @Test
    @DisplayName("get maximum in subtree with children")
    public fun testGetMaxInSubtreeWithChildren() {
        tree[2] = -2
        tree[3] = -3
        tree[1] = -1
        assertEquals(tree.getMaxInSubtree(2), Pair(3, -3), "Incorrect search a maximum key in a subtree.")
        tree.assertIsBSTree()
    }

    @Test
    @DisplayName("get maximum")
    public fun testGetMax() {
        assertEquals(tree.getMax(), null, "Incorrect search a maximum key in a empty tree.")

        tree[2] = -2
        tree[3] = -3
        tree[1] = -1
        assertEquals(tree.getMax(), Pair(3, -3), "Incorrect search a maximum key in a tree.")
    }

    @Test
    @DisplayName("get minimum in subtree")
    public fun testGetMinInSubtree() {
        assertEquals(tree.getMinInSubtree(0), null,
            "Incorrect search a minimum key in a empty tree.")
        tree.assertIsBSTree()

        tree[2] = -2
        assertEquals(tree.getMinInSubtree(2), Pair(2, -2),
            "Incorrect search a minimum key in subtree without children.")
        tree.assertIsBSTree()
    }

    @Test
    @DisplayName("get minimum in subtree with children")
    public fun testGetMinInSubtreeWithChildren() {
        tree[2] = -2
        tree[3] = -3
        tree[1] = -1
        assertEquals(tree.getMinInSubtree(2), Pair(1, -1), "Incorrect search a minimum key in a subtree.")
        tree.assertIsBSTree()
    }

    @Test
    @DisplayName("get minimum")
    public fun testGetMin() {
        assertEquals(tree.getMin(), null, "Incorrect search a minimum key in a empty tree.")

        tree[2] = -2
        tree[3] = -3
        tree[1] = -1
        assertEquals(tree.getMin(), Pair(1, -1), "Incorrect search a minimum key in a tree.")
    }

    @Test
    @DisplayName("remove root")
    public fun testRemove() {
        tree[1] = -1
        assertEquals(tree.remove(1), -1, "Incorrect remove root.")
        assertEquals(0, tree.getSize())
        tree.assertIsBSTree()

        assertEquals(tree.remove(1), null, "Incorrect remove a non-existent root.")
        assertEquals(0, tree.getSize())
        tree.assertIsBSTree()
    }

    @Test
    @DisplayName("remove root with children")
    public fun testRemoveWithChildren() {
        tree[2] = -2
        tree[1] = -1
        tree[3] = -3
        assertEquals(tree.remove(2), -2, "Incorrect remove a root.")
        assertEquals(2, tree.getSize())
        tree.assertIsBSTree()
        assertEquals(tree.search(1), -1, "Incorrect remove a root and lose the left child.")
        assertEquals(tree.search(3), -3, "Incorrect remove a root and lose the right child.")

        assertEquals(tree.remove(1), -1, "Incorrect remove a root.")
        tree.assertIsBSTree()
        assertEquals(1, tree.getSize())
        assertEquals(tree.search(3), -3, "Incorrect remove a root and lose the right child.")
    }

    @ParameterizedTest
    @MethodSource("getSizeAndTimeArguments")
    @DisplayName("remove with size and time")
    public fun testRemoveWithSizeAndTime(size: Int, seconds: Long) {
        val setKeys: MutableSet<Int> = mutableSetOf()
        repeat(size) {
            val keyRandom = Random.nextInt(-1000, 1000)
            setKeys.add(keyRandom)
            tree[keyRandom] = (-1) * keyRandom
        }
        assertTimeout(Duration.ofSeconds(seconds)) {
            repeat(10) {
                val keyRandom = Random.nextInt(-1000, 1000)
                if (keyRandom in setKeys) {
                    assertEquals(tree.remove(keyRandom), (-1) * keyRandom,
                        "Incorrect return of remove an existent node."
                    )
                    setKeys.remove(keyRandom)
                } else
                    assertEquals(tree.remove(keyRandom), null,
                        "Incorrect return of remove a non-existent node.")
                assertEquals(setKeys.size, tree.getSize())
            }
        }

        tree.assertIsBSTree()
    }

    @Test
    @DisplayName("remove or default")
    public fun testRemoveOrDefault() {
        assertEquals(tree.removeOrDefault(1, 0), 0,
            "Incorrect return of remove a non-existent node.")
        assertEquals(0, tree.getSize())

        tree.insert(1, -1)
        assertEquals(tree.removeOrDefault(1, 0), -1,
            "Incorrect return of remove an existent node.")
        assertEquals(0, tree.getSize())
    }

    @Test
    @DisplayName("iterator")
    public fun testIterator() {
        assertFalse(tree.iterator().hasNext(), "Incorrect check next.")
    }

    @Test
    @DisplayName("for each")
    public fun testForEach() {
        tree[2] = -2
        tree[1] = -1
        tree[3] = -3
        tree[4] = -4
        val arrayPair = arrayOf(Pair(2, -2), Pair(1, -1), Pair(3, -3), Pair(4, -4))
        var index = 0
        tree.forEach(IterationOrders.WIDTH_ORDER) {
            assertEquals(arrayPair[index++], it, "Incorrect iteration.")
        }
    }

    @Test
    @DisplayName("tree to string")
    public fun testToString() {
        var builder = StringBuilder("BSTreeTestAssistant(")
        tree.forEach { builder.append("${it.first}: ${it.second}, ") }
        builder.append(')')
        assertEquals(tree.toString(), builder.toString(), "Incorrect construction string.")

        builder = StringBuilder("BSTreeTestAssistant(")
        tree[2] = -2
        tree[1] = -1
        tree[3] = -3
        tree[4] = -4
        tree.forEach { builder.append("${it.first}: ${it.second}, ") }
        repeat(2) { builder.deleteCharAt(builder.length - 1) }
        builder.append(')')
        assertEquals(tree.toString(), builder.toString(), "Incorrect construction string.")
    }

    @Test
    @DisplayName("tree to string with tree view")
    public fun testToStringWithTreeView() {
        tree[2] = -2
        tree[1] = -1
        tree[3] = -3
        tree[4] = -4
        val string = "BSTreeTestAssistant(\n\t\t(4: -4)\n\t(3: -3)\n(2: -2)\n\t(1: -1)\n)"
        assertEquals(tree.toStringWithTreeView(), string, "Incorrect construction string.")
    }

    public companion object {
        @JvmStatic
        fun getSizeAndTimeArguments() = listOf(
            Arguments.of(100, 1L),
            Arguments.of(10000, 1L)
        )
    }

}
