package tree_tripper.nodes.binary_nodes


/**
 * A red-black tree node.
 *
 * @param K the key type
 * @param V the value type
 */
public class RBTreeNode<K : Comparable<K>, V>(
    key: K,
    value: V
) : AbstractBSTreeNode<K, V, RBTreeNode<K, V>>(key, value) {
    var isRed: Boolean = true

    public constructor(key: K, value: V, isRed: Boolean = true) : this(key, value) {
        this.isRed = isRed
    }

    public constructor(
        key: K,
        value: V,
        isRed: Boolean = true,
        leftChild: RBTreeNode<K, V>? = null,
        rightChild: RBTreeNode<K, V>? = null
    ) : this(key, value, isRed) {
        this.leftChild = leftChild
        this.rightChild = rightChild
    }

    override fun dateEqual(other: Any?): Boolean {
        if (other == null) return false
        if (this === other) return true
        if (javaClass != other.javaClass) return false

        other as RBTreeNode<*, *>
        return super.dateEqual(other) && this.isRed == other.isRed
    }

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (this === other) return true
        if (javaClass != other.javaClass) return false

        other as RBTreeNode<*, *>
        return super.equals(other) && this.isRed == other.isRed
    }

    override fun hashCode(): Int {
        return 31 * super.hashCode() + isRed.hashCode()
    }

    override fun toStringSimpleView(): String {
        return "${super.toStringSimpleView()} - ${colorName()}"
    }

    /**
     * Returns the color name of this node.
     */
    private fun colorName(): String {
        return if (isRed) "RED" else "BLACK"
    }

}
