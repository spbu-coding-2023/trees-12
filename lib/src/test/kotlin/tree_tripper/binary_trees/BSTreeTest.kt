package tree_tripper.binary_trees

import org.junit.jupiter.api.Assertions
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
import kotlin.test.assertEquals


public class BSTreeTest {
    private lateinit var tree: BSTreeTestAssistant<Int, Int>

    @BeforeEach
    public fun setup() {
        tree = BSTreeTestAssistant()
    }

   @Test
   public fun maximkaTop() {
       assertEquals("a", "asdf")
   }

    @Test
    @DisplayName("tree initialization")
    public fun testTreeInitialization() {
        tree.assertNullRoot()
        Assertions.assertEquals(tree.size, 0, "Incorrect a tree initialization.")
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
        Assertions.assertEquals(tree.size, 1, "Incorrect resizing tree size.")
        Assertions.assertEquals(tree.getRoot(), Pair(1, -1), "Incorrect insert root")

        tree.insert(1, 0)
        Assertions.assertEquals(tree.size, 1, "Incorrect resizing tree size.")
        Assertions.assertEquals(tree.getRoot(), Pair(1, 0), "Incorrect change root")
    }

    @Test()
    @DisplayName("insert children root")
    public fun testInsertChildrenRoot() {
        tree.insert(2, -2)
        tree.insert(1, -1)
        tree.insert(3, -3)
        tree.assertIsBSTree()
        Assertions.assertEquals(tree.size, 3, "Incorrect resizing tree size.")
    }

    @ParameterizedTest(name = "{displayName}[{index}] {argumentsWithNames}")
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
        Assertions.assertEquals(tree.insertIfAbsent(1, -1), true)
        Assertions.assertEquals(tree.size, 1, "Incorrect resizing tree size.")
        Assertions.assertEquals(tree.getRoot(), Pair(1, -1), "Incorrect insert root")

        Assertions.assertEquals(tree.insertIfAbsent(1, 1), false)
        Assertions.assertEquals(tree.size, 1, "Incorrect resizing tree size.")
        Assertions.assertEquals(tree.getRoot(), Pair(1, -1), "Incorrect change root")
    }

    @Test
    @DisplayName("search root")
    public fun testSearchNode() {
        Assertions.assertEquals(tree.search(0), null, "Incorrect search in a empty tree.")

        tree.insert(1, -1)
        Assertions.assertEquals(tree.search(1), -1, "Incorrect search an existent root.")
    }

    @Test
    @DisplayName("search children root")
    public fun testSearchChildrenNode() {
        tree.insert(2, -2)
        tree.insert(1, -1)
        tree.insert(3, -3)
        Assertions.assertEquals(tree.search(1), -1, "Incorrect search an existent child root.")
        Assertions.assertEquals(tree.search(3), -3, "Incorrect search an existent child root.")
        Assertions.assertEquals(tree.search(0), null, "Incorrect search a non-existent child root.")
    }

    @ParameterizedTest(name = "{displayName}[{index}] {argumentsWithNames}")
    @MethodSource("getSizeAndTimeArguments")
    @DisplayName("search with size and time")
    public fun testSearchWithSizeAndTime(size: Int, seconds: Long) {
        val arrayKeys = IntArray(size)
        var index = 0
        repeat(size) {
            val keyRandom = Random.nextInt(-1000, 1000)
            arrayKeys[index++] = keyRandom
            tree.insert(keyRandom, keyRandom * (-1))
        }

        assertTimeout(Duration.ofSeconds(seconds)) {
            repeat(10) {
                val keyRandom = arrayKeys[Random.nextInt(0, size - 1)]
                Assertions.assertEquals(tree.search(keyRandom), (-1) * keyRandom,
                    "Incorrect search an existent node.")

                if ((keyRandom - 10) !in arrayKeys)
                Assertions.assertEquals(tree.search(keyRandom - 10), null,
                        "Incorrect search a non-existent node.")
            }
        }

        tree.assertIsBSTree()
    }

    @Test
    @DisplayName("search of default root")
    public fun testSearchOrDefault() {
        Assertions.assertEquals(tree.searchOrDefault(1, 0), 0,
            "Incorrect return of search a non-existent child root.")

        tree.insert(1, -1)
        Assertions.assertEquals(tree.searchOrDefault(1, 0), -1,
            "Incorrect return of search an existent child root.")
    }

    @Test
    @DisplayName("contains")
    public fun testContains() {
        Assertions.assertEquals(tree.contains(1), false, "Incorrect return of search a non-existent node.")

        tree.insert(1, -1)
        Assertions.assertEquals(tree.contains(1), true, "Incorrect return of search an existent node.")
    }

    @Test
    @DisplayName("set with brackets")
    public fun testSet() {
        tree[1] = -1
        Assertions.assertEquals(tree.getRoot(), Pair(1, -1), "Incorrect set.")

        tree[1] = 0
        Assertions.assertEquals(tree.getRoot(), Pair(1, 0), "Incorrect change of the value.")
    }

    @Test
    @DisplayName("get with brackets")
    public fun testGet() {
        Assertions.assertEquals(tree[1], null, "Incorrect get a non-existent node.")

        tree[1] = -1
        Assertions.assertEquals(tree[1], -1, "Incorrect get an existent node.")
    }

    @Test
    @DisplayName("get maximum in subtree without children")
    public fun testGetMaxInSubtree() {
        Assertions.assertEquals(tree.getMaxInSubtree(0), null,
            "Incorrect search a maximum key in a empty tree.")

        tree[1] = -1
        Assertions.assertEquals(tree.getMaxInSubtree(1), Pair(1, -1),
            "Incorrect search a maximum key in subtree without children.")
        Assertions.assertEquals(tree.getRoot(), Pair(1, -1), "A tree is damaged.")
    }

    @Test
    @DisplayName("get maximum in subtree with children")
    public fun testGetMaxInSubtreeWithChildren() {
        tree[5] = -5
        tree[1] = -1
        tree[3] = -3
        tree[2] = -2
        Assertions.assertEquals(tree.getMaxInSubtree(1), Pair(3, -3),
            "Incorrect search a maximum key in subtree with children.")
        tree.assertIsBSTree()
    }

    @Test
    @DisplayName("get maximum")
    public fun testGetMax() {
        Assertions.assertEquals(tree.getMax(), null, "Incorrect search a maximum key in a empty tree.")

        tree[2] = -2
        tree[3] = -3
        tree[1] = -1
        tree[4] = -4
        Assertions.assertEquals(tree.getMax(), Pair(4, -4), "Incorrect search a maximum key in a tree.")
    }

    @Test
    @DisplayName("get minimum in subtree without children")
    public fun testGetMinInSubtree() {
        Assertions.assertEquals(tree.getMinInSubtree(0), null,
            "Incorrect search a minimum key in a empty tree.")

        tree[1] = -1
        Assertions.assertEquals(tree.getMinInSubtree(1), Pair(1, -1),
            "Incorrect search a minimum key in subtree without children.")
        Assertions.assertEquals(tree.getRoot(), Pair(1, -1), "A tree is damaged.")
    }

    @Test
    @DisplayName("get minimum in subtree with children")
    public fun testGetMinInSubtreeWithChildren() {
        tree[5] = -5
        tree[1] = -1
        tree[3] = -3
        tree[2] = -2
        Assertions.assertEquals(tree.getMinInSubtree(3), Pair(2, -2),
            "Incorrect search a minimum key in a subtree.")
        tree.assertIsBSTree()
    }

    @Test
    @DisplayName("get minimum")
    public fun testGetMin() {
        Assertions.assertEquals(tree.getMin(), null, "Incorrect search a minimum key in a empty tree.")

        tree[2] = -2
        tree[3] = -3
        tree[1] = -1
        tree[4] = -4
        Assertions.assertEquals(tree.getMin(), Pair(1, -1), "Incorrect search a minimum key in a tree.")
    }

    @Test
    @DisplayName("remove root without children")
    public fun testRemove() {
        tree[1] = -1
        Assertions.assertEquals(tree.remove(1), -1, "Incorrect remove root.")
        Assertions.assertEquals(0, tree.size)
        tree.assertNullRoot()

        Assertions.assertEquals(tree.remove(1), null, "Incorrect remove a non-existent root.")
        Assertions.assertEquals(0, tree.size)
    }

    @Test
    @DisplayName("remove root with children")
    public fun testRemoveWithChildren() {
        tree[2] = -2
        tree[1] = -1
        tree[3] = -3
        Assertions.assertEquals(tree.remove(2), -2, "Incorrect remove a root.")
        Assertions.assertEquals(2, tree.size)
        tree.assertIsBSTree()

        Assertions.assertEquals(tree.search(1), -1, "Incorrect remove a root and lose the left child.")
        Assertions.assertEquals(tree.search(3), -3, "Incorrect remove a root and lose the right child.")

        Assertions.assertEquals(tree.remove(1), -1, "Incorrect remove a root.")
        tree.assertIsBSTree()
        Assertions.assertEquals(1, tree.size)

        Assertions.assertEquals(tree.search(3), -3, "Incorrect remove a root and lose the right child.")
    }

    @ParameterizedTest(name = "{displayName}[{index}] {argumentsWithNames}")
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
                val keyRandom = setKeys.elementAt(Random.nextInt(0, setKeys.size - 1))
                Assertions.assertEquals(tree.remove(keyRandom), (-1) * keyRandom,
                        "Incorrect return of remove an existent node.")
                setKeys.remove(keyRandom)

                if ((keyRandom - 10) !in setKeys)
                    Assertions.assertEquals(tree.remove(keyRandom - 10), null,
                        "Incorrect return of remove a non-existent node.")
            }
        }

        Assertions.assertEquals(tree.size, setKeys.size)
        tree.assertIsBSTree()
    }

    @Test
    @DisplayName("remove or default")
    public fun testRemoveOrDefault() {
        Assertions.assertEquals(tree.removeOrDefault(1, 0), 0,
            "Incorrect return of remove a non-existent node.")
        Assertions.assertEquals(0, tree.size)

        tree.insert(1, -1)
        Assertions.assertEquals(tree.removeOrDefault(1, 0), -1,
            "Incorrect return of remove an existent node.")
        Assertions.assertEquals(0, tree.size)
    }

    @Test
    @DisplayName("iterator")
    public fun testIterator() {
        Assertions.assertFalse(tree.iterator().hasNext(), "Incorrect check next.")
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
            Assertions.assertEquals(arrayPair[index++], it, "Incorrect iteration.")
        }
    }

    @Test
    @DisplayName("tree to string")
    public fun testToString() {
        var builder = StringBuilder("BSTreeTestAssistant(")
        tree.forEach { builder.append("${it.first}: ${it.second}, ") }
        builder.append(')')
        Assertions.assertEquals(tree.toString(), builder.toString(), "Incorrect construction string.")

        builder = StringBuilder("BSTreeTestAssistant(")
        tree[2] = -2
        tree[1] = -1
        tree[3] = -3
        tree[4] = -4
        tree.forEach { builder.append("${it.first}: ${it.second}, ") }
        repeat(2) { builder.deleteCharAt(builder.length - 1) }
        builder.append(')')
        Assertions.assertEquals(tree.toString(), builder.toString(), "Incorrect construction string.")
    }

    @Test
    @DisplayName("tree to string with tree view")
    public fun testToStringWithTreeView() {
        tree[2] = -2
        tree[1] = -1
        tree[3] = -3
        tree[4] = -4
        val string = "BSTreeTestAssistant(\n\t\t(4: -4)\n\t(3: -3)\n(2: -2)\n\t(1: -1)\n)"
        Assertions.assertEquals(tree.toStringWithTreeView(), string, "Incorrect construction string.")
    }

    public companion object {
        @JvmStatic
        fun getSizeAndTimeArguments() = listOf(
            Arguments.of(100, 1L),
            Arguments.of(10000, 1L)
        )
    }

}
