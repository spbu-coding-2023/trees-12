package tree_trippers.nodes.binary_nodes


public class RBTreeNode<K: Comparable<K>, V>(
    key: K,
    value: V
): AbstractBSTreeNode<K, V, RBTreeNode<K, V>>(key, value) {
    var isRed: Boolean = true

    override fun toStringSimpleView(): String {
        return "${super.toStringSimpleView()} - ${colorName()}"
    }

    private fun colorName(): String {
        return if (isRed) "RED" else "BLACK"
    }

}
