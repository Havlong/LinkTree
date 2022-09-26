import java.util.ArrayList;
import java.util.List;

public class BinUps implements TreeStructure {
    public static final ArrayList<BinUpsNode> tree = new ArrayList<>();

    /**
     * Finds the ancestor of node on k-th level above
     *
     * @param nodeId nodeId of {@link BinUpsNode} to check
     * @param k      difference between ancestor level and node level
     * @return nodeId of k-th level ancestor
     */
    private int findLevelAncestor(int nodeId, int k) {
        BinUpsNode node = tree.get(nodeId);
        int h = Math.max(node.depth - k, 0);

        while (node.depth != h) {
            BinUpsNode jumpNode = tree.get(node.jumpNodeId);
            if (jumpNode.depth >= h) node = jumpNode;
            else node = tree.get(node.parentNodeId);
        }
        return node.nodeId;
    }

    @Override
    public int add(int parentNodeId) {
        BinUpsNode node = new BinUpsNode(tree.size(), tree.get(parentNodeId));
        tree.add(node);
        return node.nodeId;
    }

    @Override
    public int addRoot() {
        BinUpsNode node = new BinUpsNode(tree.size());
        tree.add(node);
        return node.nodeId;
    }

    @Override
    public List<Integer> getRootPath(int nodeId, int limit) {
        ArrayList<Integer> rootPath = new ArrayList<>();

        BinUpsNode node = tree.get(nodeId);
        for (int i = 0; i < limit; i++) {
            node = tree.get(node.parentNodeId);
            rootPath.add(node.nodeId);
            if (node.parentNodeId == node.nodeId) break;
        }

        return rootPath;
    }

    @Override
    public Integer getLca(int firstNodeId, int secondNodeId) {
        BinUpsNode firstNode = tree.get(firstNodeId);
        BinUpsNode secondNode = tree.get(secondNodeId);

        if (firstNode.depth > secondNode.depth) {
            firstNode = tree.get(findLevelAncestor(firstNode.nodeId, firstNode.depth - secondNode.depth));
        } else {
            secondNode = tree.get(findLevelAncestor(secondNode.nodeId, secondNode.depth - firstNode.depth));
        }
        while (firstNode.nodeId != secondNode.nodeId) {
            if (firstNode.jumpNodeId != secondNode.jumpNodeId) {
                firstNode = tree.get(firstNode.jumpNodeId);
                secondNode = tree.get(secondNode.jumpNodeId);
            } else {
                firstNode = tree.get(firstNode.parentNodeId);
                secondNode = tree.get(secondNode.parentNodeId);
            }
        }
        return firstNode.nodeId;
    }
}
