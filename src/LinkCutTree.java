import java.util.ArrayList;
import java.util.List;

public class LinkCutTree implements TreeStructure {
    private final ArrayList<Node> tree = new ArrayList<>();

    /**
     * Checks if node is splay-tree root
     *
     * @param nodeId nodeId of {@link Node} to check
     * @return false if node is splay-tree root else true
     */
    private boolean isNotRoot(int nodeId) {
        Integer parentNodeId = tree.get(nodeId).parentNodeId;
        if (parentNodeId == null) return false;
        Node parentNode = tree.get(parentNodeId);
        return parentNode.leftNodeId == nodeId || parentNode.rightNodeId == nodeId;
    }

    /**
     * Performs Zig or Zag rotation based on node provided
     * <p>
     * (I do not understand a thing, check
     * <a href="https://codeforces.com/blog/entry/69879">original code</a> or
     * <a href="https://neerc.ifmo.ru/wiki/index.php?title=Link-Cut_Tree">description</a>)
     *
     * @param nodeId {@link Node} to perform tree rotation around
     */
    private void rotate(int nodeId) {
        Node node = tree.get(nodeId);

        assert node.parentNodeId != null;

        int p = node.parentNodeId;
        Integer g = tree.get(p).parentNodeId;

        if (g != null) {
            Node grandParent = tree.get(g);
            if (grandParent.leftNodeId == p) {
                grandParent.leftNodeId = nodeId;
            } else if (grandParent.rightNodeId == p) {
                grandParent.rightNodeId = nodeId;
            }
        }

        node.parentNodeId = g;
        Node parent = tree.get(p);

        if (parent.leftNodeId == nodeId) {
            parent.leftNodeId = node.rightNodeId;
            if (parent.leftNodeId != null) tree.get(parent.leftNodeId).parentNodeId = p;
            node.rightNodeId = p;
        } else {
            parent.rightNodeId = node.leftNodeId;
            if (parent.rightNodeId != null) tree.get(parent.rightNodeId).parentNodeId = p;
            node.leftNodeId = p;
        }
        parent.parentNodeId = nodeId;
    }

    /**
     * Splay function, performs rotations, moving nodeId to the root of path binary tree
     *
     * @param nodeId {@link Node} to put in the root
     */
    private void splay(int nodeId) {
        while (isNotRoot(nodeId)) {
            int parentNodeId = tree.get(nodeId).parentNodeId; // parent of nodeId
            if (isNotRoot(parentNodeId)) { // do zig-zig or zig-zag
                int grandParentNodeId = tree.get(parentNodeId).parentNodeId; // grandparent of nodeId
                boolean doZigZig = (nodeId == tree.get(parentNodeId).leftNodeId) == (parentNodeId == tree.get(grandParentNodeId).leftNodeId);
                rotate(doZigZig ? parentNodeId : nodeId);
            }
            rotate(nodeId);
        }
    }

    /**
     * Link-Cut Tree basic function, restores path to Root from provided node
     *
     * @param nodeId {@link Node} to restore the path from
     * @return pathParent to root
     */
    private Integer expose(int nodeId) {
        Integer lastNode = null;
        for (Integer parentNode = nodeId; parentNode != null; parentNode = tree.get(parentNode).parentNodeId) {
            splay(parentNode);
            tree.get(parentNode).rightNodeId = lastNode;
            lastNode = parentNode;
        }
        splay(nodeId);
        return lastNode;
    }

    private void link(int addedNodeId, int linkingNodeId) {
        if (hasPath(addedNodeId, linkingNodeId) || isNotRoot(addedNodeId)) return;
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
        Node newNode = new Node(tree.size());
        tree.add(newNode);
        return newNode.nodeId;
    }

    @Override
    public List<Integer> getRootPath(int nodeId, int limit) {
        ArrayList<Integer> rootPath = new ArrayList<>();

        expose(nodeId);
        while (tree.get(nodeId).leftNodeId != null && rootPath.size() < limit) {
            nodeId = tree.get(nodeId).leftNodeId;
            rootPath.add(nodeId);
        }
        expose(nodeId);

        return rootPath;
    }

    @Override
    public Integer getLca(int firstNodeId, int secondNodeId) {
        if (!hasPath(firstNodeId, secondNodeId)) return null;
        expose(firstNodeId);
        return expose(secondNodeId);
    }
}
