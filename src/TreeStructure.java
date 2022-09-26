import java.util.List;

public interface TreeStructure {
    /**
     * Adds node, that is the new child of specified node
     *
     * @param parentNodeId nodeId in TreeStructure
     * @return nodeId of added {@link LCTNode}
     */
    int add(int parentNodeId);

    /**
     * Adds new Tree with independent root
     *
     * @return nodeId of added {@link LCTNode}
     */
    int addRoot();

    /**
     * Queries for the treePath to root
     *
     * @param nodeId nodeId of startingPoint in TreeStructure
     * @param limit  specify if only set amount is to be returned else use value -1
     * @return list of nodeIds from {@link LCTNode} specified by nodeId to the tree root
     */
    List<Integer> getRootPath(int nodeId, int limit);

    /**
     * Solves "Least Common Ancestor" problem
     *
     * @param firstNodeId  one of two nodes to find common ancestor for
     * @param secondNodeId second of two nodes to find common ancestor for
     * @return nodeId of {@link LCTNode}, that is ancestor to both nodes and has the deepest level of such nodes
     */
    Integer getLca(int firstNodeId, int secondNodeId);
}
