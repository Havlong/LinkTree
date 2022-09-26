public class BinUpsNode {
    public int nodeId;
    public int depth = 0;
    public int parentNodeId = nodeId;
    public int jumpNodeId = nodeId;

    public BinUpsNode(int nodeId) {
        this.nodeId = nodeId;
    }

    public BinUpsNode(int nodeId, BinUpsNode parentNode) {
        this.nodeId = nodeId;
        this.depth = parentNode.depth + 1;
        this.parentNodeId = parentNode.nodeId;

        BinUpsNode firstJump = BinUps.tree.get(parentNode.jumpNodeId);
        BinUpsNode secondJump = BinUps.tree.get(firstJump.jumpNodeId);

        if (parentNode.depth - firstJump.depth == firstJump.depth - secondJump.depth)
            this.jumpNodeId = secondJump.nodeId;
        else
            this.jumpNodeId = this.parentNodeId;
    }
}
