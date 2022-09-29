import java.util.ArrayList;
import java.util.Objects;

public class LinkCutTree implements TreeStructure {
    public final ArrayList<LCTNode> tree = new ArrayList<>();

    /**
     * Checks if node is splay-tree root
     *
     * @param nodeId nodeId of {@link LCTNode} to check
     * @return false if node is splay-tree root else true
     */
    private boolean isRoot(Integer nodeId) {
        LCTNode node = tree.get(nodeId);
        if (node.parentNodeId == null) return true;
        LCTNode parentNode = tree.get(node.parentNodeId);
        return !Objects.equals(parentNode.leftNodeId, nodeId) && !Objects.equals(parentNode.rightNodeId, nodeId);
    }

    private void rotateRight(Integer nodeId) {
        LCTNode node = tree.get(nodeId), parent = tree.get(node.parentNodeId);
        Integer grandParentNodeId = parent.parentNodeId;
        if ((parent.leftNodeId = node.rightNodeId) != null) {
            tree.get(parent.leftNodeId).parentNodeId = parent.nodeId;
        }
        node.rightNodeId = parent.nodeId;
        parent.parentNodeId = node.nodeId;
        if ((node.parentNodeId = grandParentNodeId) != null) {
            LCTNode grandParentNode = tree.get(grandParentNodeId);
            if (Objects.equals(grandParentNode.leftNodeId, parent.nodeId)) grandParentNode.leftNodeId = nodeId;
            else if (Objects.equals(grandParentNode.rightNodeId, parent.nodeId)) grandParentNode.rightNodeId = nodeId;
        }
    }

    private void rotateLeft(Integer nodeId) {
        LCTNode node = tree.get(nodeId), parent = tree.get(node.parentNodeId);
        Integer grandParentNodeId = parent.parentNodeId;
        if ((parent.rightNodeId = node.leftNodeId) != null) {
            tree.get(parent.rightNodeId).parentNodeId = parent.nodeId;
        }
        node.leftNodeId = parent.nodeId;
        parent.parentNodeId = nodeId;
        if ((node.parentNodeId = grandParentNodeId) != null) {
            LCTNode grandParentNode = tree.get(grandParentNodeId);
            if (Objects.equals(grandParentNode.leftNodeId, parent.nodeId)) grandParentNode.leftNodeId = nodeId;
            else if (Objects.equals(grandParentNode.rightNodeId, parent.nodeId)) grandParentNode.rightNodeId = nodeId;
        }
    }

    /**
     * Splay function, performs rotations, moving nodeId to the root of path binary tree
     *
     * @param nodeId {@link LCTNode} to put in the root
     */
    private void splay(Integer nodeId) {
        while (!isRoot(nodeId)) {
            LCTNode node = tree.get(nodeId), parentNode = tree.get(node.parentNodeId);
            if (isRoot(parentNode.nodeId)) {
                if (Objects.equals(parentNode.leftNodeId, nodeId)) rotateRight(nodeId);
                else rotateLeft(nodeId);
            } else {
                LCTNode grandParentNode = tree.get(parentNode.parentNodeId);
                if (Objects.equals(grandParentNode.leftNodeId, parentNode.nodeId)) {
                    if (Objects.equals(parentNode.leftNodeId, nodeId)) {
                        rotateRight(parentNode.nodeId);
                        rotateRight(nodeId);
                    } else {
                        rotateLeft(nodeId);
                        rotateRight(nodeId);
                    }
                } else {
                    if (Objects.equals(parentNode.rightNodeId, nodeId)) {
                        rotateLeft(parentNode.nodeId);
                        rotateLeft(nodeId);
                    } else {
                        rotateRight(nodeId);
                        rotateLeft(nodeId);
                    }
                }
            }
        }
    }

    /**
     * Link-Cut Tree basic function, restores path to Root from provided node
     *
     * @param nodeId {@link LCTNode} to restore the path from
     * @return pathParent to root
     */
    private Integer expose(int nodeId) {
        Integer lastNode = null;
        for (Integer parentNode = nodeId; parentNode != null; parentNode = tree.get(parentNode).parentNodeId) {
            splay(parentNode);
            tree.get(parentNode).leftNodeId = lastNode;
            lastNode = parentNode;
        }
        splay(nodeId);
        return lastNode;
    }

    private void link(int addedNodeId, int linkingNodeId) {
        expose(addedNodeId);
        if (tree.get(addedNodeId).rightNodeId != null) return;
        tree.get(addedNodeId).parentNodeId = linkingNodeId;
    }

    /**
     * Checks whether there is a path between two nodes (are they already in the same tree)
     *
     * @param firstNodeId  one of two nodes to check
     * @param secondNodeId second of two nodes to check
     * @return true if there is a path, else false is returned
     */
    public boolean hasPath(int firstNodeId, int secondNodeId) {
        if (firstNodeId == secondNodeId) return true;
        expose(secondNodeId);
        expose(firstNodeId);
        return tree.get(secondNodeId).parentNodeId != null;
    }

    @Override
    public int add(int parentNodeId) {
        int nodeId = addRoot();
        link(nodeId, parentNodeId);
        return nodeId;
    }

    @Override
    public int addRoot() {
        LCTNode newNode = new LCTNode(tree.size());
        tree.add(newNode);
        return newNode.nodeId;
    }

    @Override
    public Integer getLca(int firstNodeId, int secondNodeId) {
        if (!hasPath(firstNodeId, secondNodeId)) return null;
        expose(firstNodeId);
        return expose(secondNodeId);
    }

    @Override
    public boolean isParent(int parentNodeId, int childNodeId) {
        Integer lca = getLca(parentNodeId, childNodeId);
        return lca != null && lca == parentNodeId;
    }

    @Override
    public int rootId(int nodeId) {
        expose(nodeId);
        while (tree.get(nodeId).rightNodeId != null) nodeId = tree.get(nodeId).rightNodeId;
        splay(nodeId);
        return nodeId;
    }
}
