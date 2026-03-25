import java.util.Scanner;

public class Input {

    public static Graph inputGraph(Scanner sc) {
        Graph g = new Graph();
        System.out.print("Enter number of edges: ");
        int numEdges = Integer.parseInt(sc.nextLine().trim());
        System.out.println("Enter each edge: <vertex1> <vertex2> <weight>");
        for (int i = 1; i <= numEdges; i++) {
            System.out.print("Edge " + i + ": ");
            String[] parts = sc.nextLine().trim().split("\\s+");
            int u = Integer.parseInt(parts[0]);
            int v = Integer.parseInt(parts[1]);
            double w = Double.parseDouble(parts[2]);
            g.addEdge(u, v, w);
        }
        return g;
    }

    public static String inputExpression(Scanner sc) {
        System.out.print("Input expression: ");
        return sc.nextLine().trim();
    }
}
