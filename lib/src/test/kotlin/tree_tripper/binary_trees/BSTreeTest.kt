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


class BSTreeTest {
    private lateinit var tree: BSTreeTestAssistant<Int, Int>

    @BeforeEach
    fun setup() {
        tree = BSTreeTestAssistant()
    }

    @Test
    @DisplayName("tree initialization")
    public fun treeInitialization() {
        tree.assertRootInitialization()
        assertEquals(tree.getSize(), 0, "Incorrect a tree initialization")
    }

    @Test
    @DisplayName("create node")
    public fun createNode() {
        tree.assertWasCreatedNode(5, 10)
    }

    @Test
    @DisplayName("insert root")
    public fun insertRoot() {
        tree.insert(0, 0)
        tree.assertIsBSTree()
        assertEquals(tree.getSize(), 1, "Incorrect resizing tree size")
    }

    @Test
    @DisplayName("insert children root")
    public fun insertChildrenRoot() {
        tree.insert(0, 0)
        tree.insert(-1, -1)
        tree.insert(1, 1)
        tree.assertIsBSTree()
        assertEquals(tree.getSize(), 3, "Incorrect resizing tree size")
    }

    @Test
    @DisplayName("insert 5 elements")
    public fun insertElements() {
        tree.insert(0, 0)
        tree.insert(2, 2)
        tree.insert(-1, -1)
        tree.insert(1, 1)
        tree.insert(3, 3)
        tree.assertIsBSTree()
        assertEquals(tree.getSize(), 5, "Incorrect resizing tree size")
    }

    @ParameterizedTest
    @MethodSource("sizeAndTime")
    @DisplayName("insert with size and time")
    public fun insertWithSizeAndTime(size: Int, seconds: Long) {
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
    public fun insertIfAbsentRoot() {
        assertEquals(tree.insertIfAbsent(0, 0), true)
        tree.assertIsBSTree()
        assertEquals(tree.insertIfAbsent(0, 10), false)
        assertEquals(tree.getSize(), 1, "Incorrect resizing tree size")
    }

    companion object {
        @JvmStatic
        fun sizeAndTime() = listOf(
            Arguments.of(100, 1L),
            Arguments.of(10000, 1L)
        )
    }

}
