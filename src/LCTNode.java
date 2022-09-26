import java.util.Objects;

public final class LCTNode {
    public int nodeId; // ID used by DB
    public Integer leftNodeId = null; // Splay Tree left
    public Integer rightNodeId = null; // Splay Tree right
    public Integer parentNodeId = null; // Link Cut Tree parent

    public LCTNode(int nodeId) {
        this.nodeId = nodeId;
    }

    public LCTNode(int nodeId, Integer leftNodeId, Integer rightNodeId, Integer parentNodeId) {
        this.nodeId = nodeId;
        this.leftNodeId = leftNodeId;
        this.rightNodeId = rightNodeId;
        this.parentNodeId = parentNodeId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (LCTNode) obj;
        return this.nodeId == that.nodeId && Objects.equals(this.leftNodeId, that.leftNodeId) && Objects.equals(this.rightNodeId, that.rightNodeId) && Objects.equals(this.parentNodeId, that.parentNodeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nodeId, leftNodeId, rightNodeId, parentNodeId);
    }

    @Override
    public String toString() {
        return "Node[" + "nodeId=" + nodeId + ", " + "leftNodeId=" + leftNodeId + ", " + "rightNodeId=" + rightNodeId + ", " + "parentNodeId=" + parentNodeId + ']';
    }

}
