import java.util.*;

public class MainSpanning {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // สร้างตัวรับข้อมูลจากการพิมพ์คีย์บอร์ด
        boolean running = true; // ตัวแปรสำหรับคุมให้โปรแกรมทำงานวนไปเรื่อยๆ

        while (running) { // ลูปเมนูหลัก
            // ปรินต์หน้าตาเมนูโชว์บน Terminal
            System.out.println("\n=================================");
            System.out.println("     Graph Algorithms Menu       ");
            System.out.println("=================================");
            System.out.println("1. Kruskal's Algorithm (MST)");
            System.out.println("2. Prim's Algorithm (MST)");
            System.out.println("3. Spanning Tree (DFS)");
            System.out.println("4. Exit");
            System.out.print("Select an option (1-4): ");

            String choice = scanner.nextLine(); // รอรับข้อความที่ผู้ใช้พิมพ์

            if (choice.equals("4")) { // ถ้าพิมพ์ 4
                System.out.println("Exiting program... Goodbye!");
                running = false; // ปิดลูป
                continue; // กระโดดข้ามโค้ดด้านล่าง เพื่อจบโปรแกรม
            }

            // เลือกการทำงานตามเลขที่พิมพ์
            switch (choice) {
                case "1": // เลือก Kruskal
                    Graph<String> gKruskal = buildWeightedGraph(); // เรียกฟังก์ชันจำลองกราฟด้านล่าง
                    Kruskal<String> kruskal = new Kruskal<>(); // เรียกคลาส Kruskal
                    MSTResult<String> resKruskal = kruskal.compute(gKruskal); // สั่งคำนวณ
                    // ปรินต์ผลลัพธ์
                    System.out.println("\n--- Kruskal Result ---");
                    System.out.println("Edges: " + resKruskal.edges());
                    System.out.println("Total Weight: " + resKruskal.totalWeight());
                    break;
                case "2": // เลือก Prim
                    Graph<String> gPrim = buildWeightedGraph(); // เรียกฟังก์ชันจำลองกราฟด้านล่าง
                    Prim<String> prim = new Prim<>(); // เรียกคลาส Prim
                    MSTResult<String> resPrim = prim.compute(gPrim, "A"); // สั่งคำนวณ โดยระบุว่าเริ่มที่โหนด A
                    // ปรินต์ผลลัพธ์
                    System.out.println("\n--- Prim Result (Start at A) ---");
                    System.out.println("Edges: " + resPrim.edges());
                    System.out.println("Total Weight: " + resPrim.totalWeight());
                    break;
                case "3": // เลือก DFS Spanning Tree
                    SimpleGraph<String> gDFS = buildSimpleGraph(); // เรียกฟังก์ชันจำลองกราฟแบบไม่มีน้ำหนัก
                    SpanningTree<String> dfs = new SpanningTree<>(); // เรียกคลาส DFS
                    SpanningForest<String> forest = dfs.buildDFS(gDFS); // สั่งเดินสำรวจ
                    // ปรินต์ผลลัพธ์
                    System.out.println("\n--- DFS Spanning Tree Result ---");
                    System.out.println("Visited: " + forest.visitedVertices());
                    System.out.println("Edges:");
                    for (Edge<String> e : forest.edges())
                        System.out.println("  " + e); // ปรินต์ทีละบรรทัด
                    System.out.println("Total Edges: " + forest.edges().size());
                    break;
                default: // พิมพ์เลขอื่นมา
                    System.out.println("❌ Invalid option. Try again.");
            }
        }
        scanner.close(); // ปิดการเชื่อมต่อคีย์บอร์ดคืนทรัพยากรให้เครื่อง
    }

    // ฟังก์ชันช่วยสร้างกราฟตัวอย่าง สำหรับเมนู 1 และ 2 (มีน้ำหนัก)
    private static Graph<String> buildWeightedGraph() {
        Graph<String> g = new Graph<>();
        // มีจุดยอด 6 จุด (A, B, C, D, E, F)
        g.addUndirectedEdge("A", "B", 4);
        g.addUndirectedEdge("A", "C", 4);
        g.addUndirectedEdge("B", "C", 2); // จุดหลอก! โปรแกรมควรเลือก A-B และ B-C แล้วทิ้ง A-C ที่ทำให้เกิดวงวน
        g.addUndirectedEdge("B", "D", 5);
        g.addUndirectedEdge("C", "D", 5);
        g.addUndirectedEdge("C", "E", 6); // ทางเชื่อม E ที่แพง
        g.addUndirectedEdge("C", "F", 3);
        g.addUndirectedEdge("D", "F", 6);
        g.addUndirectedEdge("E", "F", 2); // ทางเชื่อม E ที่ถูกกว่า โปรแกรมควรเลือกเส้นนี้แทน C-E
        return g;
    }

    // ฟังก์ชันช่วยสร้างกราฟตัวอย่าง สำหรับเมนู 3 (ไม่มีน้ำหนัก)
    private static SimpleGraph<String> buildSimpleGraph() {
        SimpleGraph<String> g = new SimpleGraph<>();
        // สร้างกราฟ 6 จุดที่มีทางแยกและวงวนชัดเจนขึ้น
        g.addUndirectedEdge("A", "B");
        g.addUndirectedEdge("A", "C");
        g.addUndirectedEdge("B", "D");
        g.addUndirectedEdge("B", "E");
        g.addUndirectedEdge("C", "F");
        g.addUndirectedEdge("E", "F"); // สร้างวงวนก้อนใหญ่ A-B-E-F-C-A เพื่อทดสอบว่า DFS จะตัดจบถูกไหม
        return g;
    }
}

// ---------------------------------------------------------
// คลาสโครงสร้างกราฟแบบมีน้ำหนัก (แชร์ให้ Kruskal และ Prim ใช้ร่วมกัน)
// ---------------------------------------------------------
class WeightedEdge<V> {
    private final V u; // ปลายด้าน 1
    private final V v; // ปลายด้าน 2
    private final double w; // ค่าน้ำหนักความห่าง

    public WeightedEdge(V u, V v, double w) {
        this.u = u;
        this.v = v;
        this.w = w;
    }

    public V u() {
        return u;
    }

    public V v() {
        return v;
    }

    public double weight() {
        return w;
    }

    public V other(V x) { // หาปลายทางอีกด้าน
        if (Objects.equals(x, u))
            return v;
        if (Objects.equals(x, v))
            return u;
        throw new IllegalArgumentException("Vertex not in edge");
    }

    @Override
    public String toString() {
        return u + " --(" + w + ")-- " + v;
    }
}

class Graph<V> {
    private final Map<V, List<WeightedEdge<V>>> adj = new LinkedHashMap<>(); // เก็บเส้นทางที่ติดกับแต่ละจุด
    private final List<WeightedEdge<V>> edges = new ArrayList<>(); // เก็บเส้นทางทั้งหมดในระบบ (Kruskal ชอบใช้)

    public void addVertex(V v) {
        adj.computeIfAbsent(v, k -> new ArrayList<>());
    }

    public void addUndirectedEdge(V a, V b, double w) { // สร้างเส้นเชื่อมไปกลับ
        addVertex(a);
        addVertex(b);
        WeightedEdge<V> e = new WeightedEdge<>(a, b, w);
        adj.get(a).add(e);
        adj.get(b).add(e);
        edges.add(e);
    }

    public Set<V> vertices() {
        return adj.keySet();
    } // ขอชื่อจุดทั้งหมด

    public int vertexCount() {
        return adj.size();
    } // ขอนับว่ามีกี่จุด

    public List<WeightedEdge<V>> edgesOf(V v) {
        return adj.getOrDefault(v, List.of());
    } // ขอเส้นทางของจุด v

    public List<WeightedEdge<V>> allEdges() {
        return Collections.unmodifiableList(edges);
    } // ขอเส้นทางทั้งหมด

    public boolean containsVertex(V v) {
        return adj.containsKey(v);
    } // เช็คว่ามีจุดนี้ไหม
}

class MSTResult<V> {
    private final List<WeightedEdge<V>> mstEdges = new ArrayList<>(); // เส้นทางที่ถูกเลือกเป็นผลลัพธ์
    private double totalWeight = 0.0; // ผลรวมน้ำหนักที่เดินทั้งหมด

    public void add(WeightedEdge<V> e) { // ตอนเอาเส้นใส่ผลลัพธ์
        mstEdges.add(e); // เก็บเข้ากล่อง
        totalWeight += e.weight(); // บวกเลขสะสมไว้เลย
    }

    public List<WeightedEdge<V>> edges() {
        return Collections.unmodifiableList(mstEdges);
    }

    public double totalWeight() {
        return totalWeight;
    }
}