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
    var parent: RBTreeNode<K, V>? = null
    var isRed: Boolean = true

    override var leftChild: RBTreeNode<K, V>?
        get() = super.leftChild
        set(value) {
            if (value != null) value.parent = this
            super.leftChild = value
        }

    override var rightChild: RBTreeNode<K, V>?
        get() = super.rightChild
        set(value) {
            if (value != null) value.parent = this
            super.rightChild = value
        }

    public constructor(key: K, value: V, isRed: Boolean) : this(key, value) {
        this.isRed = isRed
    }

    public constructor(
        key: K, value: V, isRed: Boolean,
        leftChild: RBTreeNode<K, V>?,
        rightChild: RBTreeNode<K, V>?
    ) : this(key, value, isRed) {
        this.leftChild = leftChild
        this.rightChild = rightChild
    }

    public fun getUncle(): RBTreeNode<K, V>? {
        val parent = this.parent ?: return null
        if (parent.leftChild === this) return parent.rightChild
        return parent.leftChild
    }

    public fun flipColor(): Unit {
        isRed = !isRed
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
