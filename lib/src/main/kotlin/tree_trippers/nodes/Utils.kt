package tree_trippers.nodes

public fun <N, R> notNullNodeAction(node: N?, nullNodeResult: R, action: (N) -> (R)): R {
    if (node == null) return nullNodeResult
    return action(node)
}

public fun <N> notNullNodeUpdate(node: N?, updateAction: (N) -> (Unit)): Unit {
    if (node == null) return
    return updateAction(node)
}