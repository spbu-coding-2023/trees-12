package tree_tripper.iterators


/**
 * An enumeration of the possible orders in which to iterate over the elements of a tree.
 *
 * @property WIDTH_ORDER iterate over the elements in order of their width in the tree
 * @property INCREASING_ORDER iterate over the elements in order of their increasing depth in the tree
 * @property DECREASING_ORDER iterate over the elements in order of their decreasing depth in the tree
 */
enum class IterationOrders {
    WIDTH_ORDER,
    INCREASING_ORDER,
    DECREASING_ORDER,
}
