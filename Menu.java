import java.util.*;

public class Menu {
    private Scanner sc = new Scanner(System.in);

    public void mainMenu() {
        while (true) {
            System.out.println("\n===== MAIN MENU =====");
            System.out.println("1. Prefix/Postfix Converter");
            System.out.println("2. Minimum Spanning Tree");
            System.out.println("3. Shortest Path (Dijkstra)");
            System.out.println("4. Exit");
            System.out.print("Select: ");
            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1": converterMenu(); break;
                case "2": mstMenu(); break;
                case "3": shortestPathMenu(); break;
                case "4": System.out.println("Goodbye!"); return;
                default: System.out.println("Invalid option.");
            }
        }
    }

    private void converterMenu() {
        while (true) {
            System.out.println("\n===== CONVERTER MENU =====");
            System.out.println("1. Convert to Prefix");
            System.out.println("2. Convert to Postfix");
            System.out.println("3. Show ALU Stack");
            System.out.println("4. Exit");
            System.out.print("Select: ");
            String choice = sc.nextLine().trim();
            if (choice.equals("4")) return;

            String expr = getExpression(choice);
            if (expr == null) continue;

            switch (choice) {
                case "1":
                    System.out.println("Prefix: " + Infix.toPrefix(expr));
                    break;
                case "2":
                    System.out.println("Postfix: " + Infix.toPostfix(expr));
                    break;
                case "3":
                    String postfix = Infix.toPostfix(expr);
                    System.out.println("Postfix: " + postfix);
                    System.out.println(ALUlog.evaluate(postfix));
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private String getExpression(String converterChoice) {
        System.out.println("1. Input manually");
        System.out.println("2. Use TestCase");
        System.out.print("Select: ");
        String choice = sc.nextLine().trim();
        if (choice.equals("1")) {
            return Input.inputExpression(sc);
        } else if (choice.equals("2")) {
            String testCase;
            if (converterChoice.equals("3")) {
                testCase = Testcase.getALUTestCase();
            } else {
                testCase = Testcase.getConvertTestCase();
            }
            System.out.println("TestCase: " + testCase);
            return testCase;
        }
        return null;
    }

    private void mstMenu() {
        Graph g = getGraph();
        if (g == null) return;

        System.out.println("\n===== MST ALGORITHM =====");
        System.out.println("1. Prim");
        System.out.println("2. Kruskal");
        System.out.print("Select: ");
        String choice = sc.nextLine().trim();

        switch (choice) {
            case "1":
                int primStart = selectStartNode(g);
                List<Edge> primResult = Prim.compute(g, primStart);
                System.out.println("\n===== PRIM MST =====");
                System.out.println("MST:");
                double primTotal = 0;
                for (Edge e : primResult) {
                    System.out.println(e.u() + " - " + e.v() + " (w=" + (int) e.weight() + ")");
                    primTotal += e.weight();
                }
                System.out.println("Total Weight = " + (int) primTotal);
                break;
            case "2":
                List<Edge> kruskalResult = Kruskal.compute(g);
                System.out.println("\n===== KRUSKAL MST =====");
                System.out.println("Edge\tWeight");
                double kruskalTotal = 0;
                for (Edge e : kruskalResult) {
                    System.out.println(e.u() + " - " + e.v() + "\t" + (int) e.weight());
                    kruskalTotal += e.weight();
                }
                System.out.println("Total Weight = " + (int) kruskalTotal);
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

    private void shortestPathMenu() {
        Graph g = getGraph();
        if (g == null) return;

        int startNode = selectStartNode(g);
        Map<Integer, Integer> dist = Dijkstra.compute(g, startNode);

        System.out.println("\n===== SHORTESTPATH =====");
        System.out.println("Shortest Path:");
        for (Map.Entry<Integer, Integer> entry : dist.entrySet()) {
            System.out.println(startNode + " -> " + entry.getKey() + " = " + entry.getValue());
        }
    }

    private Graph getGraph() {
        System.out.println("1. Input manually");
        System.out.println("2. Use TestCase");
        System.out.print("Select: ");
        String choice = sc.nextLine().trim();
        if (choice.equals("1")) {
            return Input.inputGraph(sc);
        } else if (choice.equals("2")) {
            System.out.println("1. Graph TestCase 1");
            System.out.println("2. Graph TestCase 2");
            System.out.print("Select: ");
            String tc = sc.nextLine().trim();
            if (tc.equals("1")) return Testcase.getGraphTestCase1();
            if (tc.equals("2")) return Testcase.getGraphTestCase2();
        }
        return null;
    }

    private int selectStartNode(Graph g) {
        System.out.println("\n===== SELECT START NODE =====");
        for (int v : g.vertices()) {
            System.out.println(v + ". Node " + v);
        }
        System.out.print("Select node: ");
        return Integer.parseInt(sc.nextLine().trim());
    }
}
