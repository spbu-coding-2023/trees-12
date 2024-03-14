package tree_trippers.nodes.binary_nodes

public class RBTreeNode<K: Comparable<K>, V>(key: K, value: V): AbstractBSTreeNode<K, V, RBTreeNode<K, V>>(key, value) {
    var isRed: Boolean = false
    var blackHeight: Int = 1

    override fun updateNodeData() {
        TODO("Not yet implemented")
    }

    // TODO(Create constructors for red-black nodes)
    // TODO(Create private method for update black height `updateBlackHeight`)
    // TODO(Connect method `updateBlackHeight` to setters of properties: `isRed` and `leftChild` and `rightChild`)
}