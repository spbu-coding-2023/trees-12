# Kotlin Library `TreeTripper`

## Description

Library `TreeTripper` - it is providing implementations of binary search trees data structures:
- [x] [`Binary Search Tree`](src/main/kotlin/tree_tripper/binary_trees/BSTree.kt), see more [information](https://en.wikipedia.org/wiki/Binary_search_tree)
- [x] [`AVL tree`](src/main/kotlin/tree_tripper/binary_trees/AVLTree.kt), see more [information](https://en.wikipedia.org/wiki/AVL_tree)
- [x] [`Red-black tree`](src/main/kotlin/tree_tripper/binary_trees/RBTree.kt), 
  see more [information](https://en.wikipedia.org/wiki/Left-leaning_red%E2%80%93black_tree) 

> [!IMPORTANT]
> The red-black tree is implemented based on the algorithm
> of left-linear red-black trees described by Robert Sedgewick 
> on his [website](https://sedgewick.io/) and [presentation](https://sedgewick.io/wp-content/uploads/2022/03/2008-09LLRB.pdf) about it

The library supports the extension both internally (future library updates) and externally (implemented by the user).

## Getting started
To run building library execute command:
```bash
./gradlew build
```

## Using library

### Basic operations
+ `insert`, see [docs](src/main/kotlin/tree_tripper/SearchTree.kt#L17)
+ `search`, see [docs](src/main/kotlin/tree_tripper/SearchTree.kt#L50)
+ `remove`, see [docs](src/main/kotlin/tree_tripper/SearchTree.kt#L36)

### Examples

##### Example 1 (importing)
```kotlin
import tree_tripper.binary_trees.BSTree
import tree_tripper.binary_trees.AVLTree
import tree_tripper.binary_trees.RBTree


val simpleTree = BSTree<String, Int>() // initialization of empty simple binary search tree
val avlTree = AVLTree<Int, StringBuilder>() // initialization of empty AVL tree
val rbTree = RBTree<String, LinkedHashSet<Long>>() // initialization of empty Red-Black tree
```

##### Example 2 (inserting)
Code:
```kotlin
import tree_tripper.binary_trees.BSTree

fun main() {
    val tree = BSTree<Int, Int>()

    tree.insert(key = 1, value = 1)
    tree.insert(key = 2, value = 2)
    tree.insert(key = 3, value = 3)
    tree.insert(key = 4, value = 4)
    tree.insert(key = 5, value = 5)
    
    println(tree)
}
```
Output:
```text
BSTree(1: 1, 2: 2, 3: 3, 4: 4, 5: 5, )
```

##### Example 2 (searching)
Code:
```kotlin
import tree_tripper.binary_trees.BSTree

fun main() {
    val tree = BSTree<Int, Int>()
    /*
      ...
      inserting from `example 1`
      ...
    */

    /* Existing element in tree */
    println(tree.search(key = 1))
    println(tree.search(key = 3))
    println(tree.search(key = 5))

    /* Unexciting element in tree */
    println(tree.search(key = -2))
    println(tree.search(key = 7))
}
```
Output:
```text
1
3
5
null
null
```

##### Example 3 (removing)
Code:
```kotlin
import tree_tripper.binary_trees.BSTree

fun main() {
    val tree = BSTree<Int, Int>()
    /*
      ...
      inserting from `example 1`
      ...
    */

    /* Existing element in tree */
    println(tree.remove(key = 1))
    println(tree.remove(key = 3))
    println(tree.remove(key = 5))

    /* Unexciting element in tree */
    println(tree.remove(key = -2))
    println(tree.remove(key = 7))

    println(tree)
}
```
Output:
```text
1
3
5
null
null
BSTree(2: 2, 4: 4, )
```

## Documentation
See more [_**documentation**_](src/main/kotlin/tree_tripper/SearchTree.kt) of library `TreeTripper` to learn more about it.
