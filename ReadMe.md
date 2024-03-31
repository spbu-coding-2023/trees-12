[//]: # (Project readme template from https://github.com/othneildrew/Best-README-Template/)
<a name="readme-top"></a>

[![MIT License](https://img.shields.io/badge/License-MIT-green.svg)](https://choosealicense.com/licenses/mit/)


<h1 align="center">TreeTripper</h1>

## Description

Library `TreeTripper`: provides implementations of the following binary search tree data structures:
- [x] [`Binary Search Tree`](lib/src/main/kotlin/tree_tripper/binary_trees/BSTree.kt), see more [information](https://en.wikipedia.org/wiki/Binary_search_tree)
- [x] [`AVL tree`](lib/src/main/kotlin/tree_tripper/binary_trees/AVLTree.kt), see more [information](https://en.wikipedia.org/wiki/AVL_tree)
- [x] [`Red-black tree`](lib/src/main/kotlin/tree_tripper/binary_trees/RBTree.kt),
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
+ `insert`, see [docs](lib/src/main/kotlin/tree_tripper/SearchTree.kt#L17)
+ `search`, see [docs](lib/src/main/kotlin/tree_tripper/SearchTree.kt#L50)
+ `remove`, see [docs](lib/src/main/kotlin/tree_tripper/SearchTree.kt#L36)

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

##### Example 3 (searching)
Code:
```kotlin
import tree_tripper.binary_trees.BSTree

fun main() {
    val tree = BSTree<Int, Int>()
    /*
      ...
      inserting from `example 2`
      ...
    */

    /* Existing element in tree */
    println(tree.search(key = 1))
    println(tree.search(key = 3))
    println(tree.search(key = 5))

    /* Unexciting element in tree */
    println(tree.search(key = -2))
    println(tree.search(key = 7))

    /* Alternative search method */
    println(tree[2])
    println(tree[0])
}
```
Output:
```text
1
3
5
null
null
2
null
```

##### Example 4 (removing)
Code:
```kotlin
import tree_tripper.binary_trees.BSTree

fun main() {
    val tree = BSTree<Int, Any>()
  /*
    ...
    inserting from `example 2`
    ...
  */

    /* Existing element in tree */
    println(tree.remove(key = 1))
    println(tree.remove(key = 3))
    println(tree.remove(key = 5))

    /* Unexciting element in tree */
    println(tree.remove(key = -2))
    println(tree.removeWithDefault(key = 7, "Element not found"))

    println(tree)
}
```
Output:
```text
1
3
5
null
Element not found
BSTree(2: 2, 4: 4, )
```

##### Example 5 (tree-like printing)
Code:
```kotlin
import tree_tripper.binary_trees.BSTree
import tree_tripper.binary_trees.AVLTree
import tree_tripper.binary_trees.RBTree

fun main() {
    val simpleTree = BSTree<Int, Int>()
    val avlTree = AVLTree<Int, Int>()
    val rbTree = RBTree<Int, Int>()
    /*
      ...
      inserting similar to `example 2` for each tree
      ...
    */

    println("${simpleTree.toStringWithTreeView()}\n")
    println("${avlTree.toStringWithTreeView()}\n")
    println("${rbTree.toStringWithTreeView()}\n")
}
```
Output:
```text
BSTree(
				(5, 5)
			(4, 4)
		(3, 3)
	(2, 2)
(1, 1)
)

AVLTree(
		(5, 5)
	(4, 4)
		(3, 3)
(2, 2)
	(1, 1)
)

RBTree(
	(5, 5) - BLACK
(4, 4) - BLACK
		(3, 3) - BLACK
	(2, 2) - RED
		(1, 1) - BLACK
)
```

##### Example 6 (iterators)
Code:
```kotlin
import tree_tripper.binary_trees.AVLTree

fun main() {
  val tree = AVLTree<Int, Int>()
  /*
    ...
    inserting from `example 2`
    ...
  */
  
  println("WIDTH ORDER:")
  tree.forEach(IterationOrders.WIDTH_ORDER) {
    println(it)
  }
  println()
  println("INCREASING ORDER:")
  tree.forEach(IterationOrders.INCREASING_ORDER) {
    println(it)
  }
  println()
  println("DECREASING ORDER:")
  tree.forEach(IterationOrders.DECREASING_ORDER) {
    println(it)
  }
}
```
Output:
```text
WIDTH ORDER:
(2, 2)
(1, 1)
(4, 4)
(3, 3)
(5, 5)

INCREASING ORDER:
(1, 1)
(2, 2)
(3, 3)
(4, 4)
(5, 5)

DECREASING ORDER:
(5, 5)
(4, 4)
(3, 3)
(2, 2)
(1, 1)
```

## Documentation
See more [_**documentation**_](lib/src/main/kotlin/tree_tripper/SearchTree.kt) of library `TreeTripper` to learn more about it.

## Authors

- [@IliaSuponeff](https://github.com/IliaSuponeff)
- [@RodionovMaxim05](https://github.com/RodionovMaxim05)
- [@Friend-zva](https://github.com/Friend-zva), sometimes in commits his name is _**Vladimir Zaikin**_

## License

Distributed under the [MIT License](https://choosealicense.com/licenses/mit/). See [`LICENSE`](LICENSE) for more information.

<p align="right">(<a href="#readme-top">back to top</a>)</p>
