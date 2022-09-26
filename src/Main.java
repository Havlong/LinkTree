import java.util.List;

public class Main {
    public static void main(String[] args) {
        if (testTreeStructure(new LinkCutTree())) System.out.println("Passed Tests");
        else System.out.println("Something doesn't quite work");
    }

    public static boolean testTreeStructure(TreeStructure treeStructure) {
        int treeSize = 8;

        int node1 = treeStructure.addRoot();
        int node2 = treeStructure.add(node1);
        int node3 = treeStructure.add(node1);
        int node4 = treeStructure.add(node2);
        int node5 = treeStructure.add(node4);
        int node6 = treeStructure.add(node1);
        int node7 = treeStructure.add(node4);
        int node8 = treeStructure.add(node7);

        List<Integer> path = treeStructure.getRootPath(node7, treeSize);

        return path.equals(List.of(node4, node2, node1)) &&
                treeStructure.getLca(node5, node8) == node4 &&
                treeStructure.getLca(node3, node6) == node1;
    }
}
