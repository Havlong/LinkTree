import java.util.List;

public class Main {
    public static void main(String[] args) {
        if (testTreeStructure(new LinkCutTree())) System.out.println("LinkCutTree Passed Tests");
        else System.out.println("Something doesn't quite work in LinkCutTree");
        if (testTreeStructure(new BinUps())) System.out.println("BinUps Passed Tests");
        else System.out.println("Something doesn't quite work in BinUps");
    }

    public static boolean testTreeStructure(TreeStructure treeStructure) {
        int node1 = treeStructure.addRoot();
        int node2 = treeStructure.add(node1);
        int node3 = treeStructure.add(node1);
        int node4 = treeStructure.add(node2);
        int node5 = treeStructure.add(node4);
        int node6 = treeStructure.add(node1);
        int node7 = treeStructure.add(node4);
        int node8 = treeStructure.add(node7);

        for (int node : List.of(node1, node2, node3, node4, node5, node6, node7, node8)) {
            if (treeStructure.rootId(node) != node1) return false;
        }

        return treeStructure.getLca(node5, node8) == node4 && treeStructure.getLca(node3, node6) == node1;
    }
}
