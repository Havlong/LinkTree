public interface TreeStructure {
    /**
     * Adds node, that is the new child of specified node
     *
     * @param parentNodeId nodeId in TreeStructure
     * @return nodeId of added Node
     */
    int add(int parentNodeId);

    /**
     * Adds new Tree with independent root
     *
     * @return nodeId of added Node
     */
    int addRoot();

    /**
     * Finds the root node of tree with specified node
     *
     * @param nodeId nodeId in TreeStructure
     * @return nodeId of Node, that is the furthest ancestor of provided nodeId
     */
    int rootId(int nodeId);

    /**
     * Solves "Least Common Ancestor" problem
     *
     * @param firstNodeId  one of two nodes to find common ancestor for
     * @param secondNodeId second of two nodes to find common ancestor for
     * @return nodeId of Node, that is ancestor to both nodes and has the deepest level of such nodes
     */
    Integer getLca(int firstNodeId, int secondNodeId);
}
