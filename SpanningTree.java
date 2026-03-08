import java.util.*;

public class SpanningTree<V> {
    // เมธอดสร้างต้นไม้ด้วยวิธี DFS (เข้าถ้ำลึกสุดทาง)
    public SpanningForest<V> buildDFS(SimpleGraph<V> g) {
        SpanningForest<V> res = new SpanningForest<>(); // กล่องเก็บผลลัพธ์ (Forest คือเผื่อมีกราฟหลายกลุ่มที่ขาดจากกัน)
        Set<V> visited = new HashSet<>(); // สมุดจดจุดที่เคยไปแล้ว

        // ลูปเผื่อไว้ในกรณีที่กราฟไม่ได้เชื่อมติดกันทั้งหมด (มีเกาะแยก)
        for (V start : g.vertices()) {
            if (visited.contains(start))
                continue; // ถ้าจุดนี้เคยไปแล้วให้ข้าม

            Deque<V> stack = new ArrayDeque<>(); // สร้าง Stack (เหมือนการกองหนังสือทับกัน) ใช้สำหรับจำทางกลับ
            visited.add(start); // จดว่าจุดนี้เริ่มไปแล้ว
            res.addVisited(start); // เก็บเข้าผลลัพธ์ด้วย
            stack.push(start); // โยนจุดเริ่มต้นลง Stack

            // ตราบใดที่ Stack ยังไม่ว่าง (ยังมีทางให้เดินต่อ หรือถอยกลับมาเดิน)
            while (!stack.isEmpty()) {
                V u = stack.pop(); // ดึงจุดใบบนสุดของ Stack ออกมาดู

                // หากิ่งที่เชื่อมกับจุด u
                for (Edge<V> e : g.edgesOf(u)) {
                    V v = e.other(u); // ดูว่าปลายทางคืออะไร
                    if (!visited.contains(v)) { // ถ้าปลายทางยังไม่เคยไป
                        visited.add(v); // จดว่าไปแล้ว
                        res.addVisited(v); // เก็บเข้าผลลัพธ์
                        res.addEdge(e); // เก็บเส้นทางที่ใช้เดินเข้าผลลัพธ์
                        stack.push(v); // โยนจุดใหม่ลง Stack เพื่อเดินลึกต่อไปในรอบหน้า
                    }
                }
            }
        }
        return res; // คืนค่าต้นไม้ที่เดินผ่านสำเร็จ
    }
}

// ------------------------------------------------------------------
// โครงสร้างของกราฟสำหรับ DFS (ไม่มีน้ำหนักมาเกี่ยวข้อง)
// ------------------------------------------------------------------
class Edge<V> {
    private final V u; // ปลายด้าน 1
    private final V v; // ปลายด้าน 2

    public Edge(V u, V v) {
        this.u = u;
        this.v = v;
    } // ตอนสร้างเส้น ต้องระบุปลาย 2 ด้าน

    public V u() {
        return u;
    } // ขอคืนค่า ปลายด้าน 1

    public V v() {
        return v;
    } // ขอคืนค่า ปลายด้าน 2

    // เมธอดสำหรับถามว่า "ถ้าฉันอยู่ด้าน u อีกด้านคืออะไร?"
    public V other(V x) {
        if (Objects.equals(x, u))
            return v;
        if (Objects.equals(x, v))
            return u;
        throw new IllegalArgumentException("Vertex not in edge: " + x);
    }

    @Override
    public String toString() {
        return u + " -- " + v;
    } // รูปร่างการปรินต์ออกมาโชว์
}

class SimpleGraph<V> {
    private final Map<V, List<Edge<V>>> adj = new LinkedHashMap<>(); // เก็บรายชื่อจุด และเส้นที่ติดกับจุดนั้นๆ

    public void addVertex(V v) {
        adj.computeIfAbsent(v, k -> new ArrayList<>());
    } // ถ้ายังไม่มีจุดนี้ให้สร้างขึ้นมา

    public void addUndirectedEdge(V a, V b) { // สร้างเส้นเชื่อมแบบไปกลับได้
        addVertex(a);
        addVertex(b); // กันเหนียว สร้างจุดก่อนถ้ามันยังไม่มี
        Edge<V> e = new Edge<>(a, b); // สร้างเส้น
        adj.get(a).add(e); // บอกจุด a ว่ามีเส้นนี้เชื่อมอยู่นะ
        adj.get(b).add(e); // บอกจุด b ว่ามีเส้นนี้เชื่อมอยู่นะ
    }

    public Set<V> vertices() {
        return adj.keySet();
    } // ขอรายชื่อจุดทั้งหมด

    public List<Edge<V>> edgesOf(V v) {
        return adj.getOrDefault(v, List.of());
    } // ขอเส้นทั้งหมดที่ติดกับจุด v
}

class SpanningForest<V> {
    private final List<Edge<V>> edges = new ArrayList<>(); // เก็บเส้นที่ได้ผลลัพธ์
    private final Set<V> visited = new LinkedHashSet<>(); // เก็บจุดที่ได้ผลลัพธ์

    public List<Edge<V>> edges() {
        return Collections.unmodifiableList(edges);
    }

    public Set<V> visitedVertices() {
        return Collections.unmodifiableSet(visited);
    }

    public void addEdge(Edge<V> e) {
        edges.add(e);
    }

    public void addVisited(V v) {
        visited.add(v);
    }
}