import java.util.*;

public class Kruskal<V> {
    public MSTResult<V> compute(Graph<V> g) {
        MSTResult<V> res = new MSTResult<>();
        UnionFind<V> uf = new UnionFind<>(g.vertices());
        List<WeightedEdge<V>> edges = new ArrayList<>(g.allEdges());

        // 1. เรียงเส้นทางทั้งหมดจากน้ำหนักน้อยไปมาก
        edges.sort(Comparator.comparingDouble(WeightedEdge::weight));

        for (WeightedEdge<V> e : edges) {
            // 2. ถ้าเชื่อมแล้วไม่เกิดวงวน (Cycle) ให้เก็บเข้าผลลัพธ์
            if (uf.union(e.u(), e.v())) {
                res.add(e);

                // 3. ถ้าได้เส้นครบ V-1 เส้น แปลว่าเชื่อมครบทุกจุดแล้ว ให้หยุดลูป
                if (res.edges().size() == g.vertexCount() - 1) {
                    break;
                }
            }
        }
        return res;
    }
}

/**
 * โครงสร้างข้อมูล Disjoint Set (Union-Find)
 * ใช้สำหรับตรวจสอบและป้องกันการเกิดวงวน (Cycle) ในกราฟรวดเร็วขึ้น
 */
class UnionFind<V> {
    private final Map<V, V> parent = new HashMap<>();
    private final Map<V, Integer> rank = new HashMap<>();

    public UnionFind(Collection<V> vertices) {
        for (V v : vertices) {
            parent.put(v, v);
            rank.put(v, 0);
        }
    }

    public V find(V x) {
        V p = parent.get(x);
        if (Objects.equals(p, x))
            return x;

        // Path Compression: อัปเดตให้ชี้ไปที่หัวหน้าใหญ่สุดโดยตรง
        // เพื่อให้อ่านค่าเร็วขึ้น
        V root = find(p);
        parent.put(x, root);
        return root;
    }

    public boolean union(V a, V b) {
        V ra = find(a);
        V rb = find(b);
        if (Objects.equals(ra, rb))
            return false; // ถ้าหัวหน้าเดียวกัน แปลว่าเกิดวงวน

        // Union by rank: รวมต้นไม้โดยให้กลุ่มที่ยศน้อยกว่าไปต่อท้ายกลุ่มใหญ่
        int rankA = rank.get(ra);
        int rankB = rank.get(rb);

        if (rankA < rankB) {
            parent.put(ra, rb);
        } else if (rankA > rankB) {
            parent.put(rb, ra);
        } else {
            parent.put(rb, ra);
            rank.put(ra, rankA + 1);
        }
        return true;
    }
}