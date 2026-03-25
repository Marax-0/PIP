public class Testcase {

    // ===== Graph Test Cases =====

    /** TestCase 1: 5 nodes (0-4) */
    public static Graph getGraphTestCase1() {
        Graph g = new Graph();
        g.addEdge(0, 1, 2);
        g.addEdge(1, 2, 1);
        g.addEdge(0, 2, 5);
        g.addEdge(2, 3, 6);
        g.addEdge(2, 4, 3);
        g.addEdge(3, 4, 1);
        return g;
    }

    /** TestCase 2: 6 nodes (0-5) */
    public static Graph getGraphTestCase2() {
        Graph g = new Graph();
        g.addEdge(0, 1, 4);
        g.addEdge(0, 2, 3);
        g.addEdge(1, 2, 1);
        g.addEdge(1, 3, 2);
        g.addEdge(2, 4, 5);
        g.addEdge(3, 4, 1);
        g.addEdge(3, 5, 6);
        g.addEdge(4, 5, 2);
        return g;
    }

    // ===== Expression Test Cases =====

    /** สำหรับ Convert to Prefix / Postfix */
    public static String getConvertTestCase() {
        return "(A+B)*C";
    }

    /** สำหรับ ALU Stack (ต้องเป็นตัวเลข) */
    public static String getALUTestCase() {
        return "3+5*2";
    }
}
