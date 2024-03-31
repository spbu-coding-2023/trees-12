package tree_tripper.nodes


/**
 * Checks the search tree [node] for null and if it is not null then executes the [action]
 *
 * @return the result of applying the given [action] function to the given [node], or the [nullNodeResult] if the [node] is null.
 */
public fun <K, V, N: SearchTreeNode<K, V, N>, R> notNullNodeAction(node: N?, nullNodeResult: R, action: (N) -> (R)): R {
    if (node == null) return nullNodeResult
    return action(node)
}

/**
 * Checks the search tree [node] for null and if it is not null then executes the [updateAction]
 */
public fun <K, V, N: SearchTreeNode<K, V, N>> notNullNodeUpdate(node: N?, updateAction: (N) -> (Unit)): Unit {
    if (node == null) return
    return updateAction(node)
}
