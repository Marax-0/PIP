import java.util.*; // นำเข้าไลบรารีพื้นฐานทั้งหมดของ Java เช่น List, Map, ArrayList

public class Kruskal<V> { // คลาสหลักของ Kruskal (V คือ Generic หมายถึงชื่อจุดยอดจะเป็น String, Integer
                          // ก็ได้)

    // เมธอดหลักสำหรับหา Minimum Spanning Tree (MST)
    public MSTResult<V> compute(Graph<V> g) {
        MSTResult<V> res = new MSTResult<>(); // สร้างกล่อง (Object) ไว้เก็บผลลัพธ์เส้นทางที่เลือกแล้ว
        UnionFind<V> uf = new UnionFind<>(g.vertices()); // สร้างระบบตรวจสอบวงวน (Cycle) ให้จุดยอดทุกจุด
        List<WeightedEdge<V>> edges = new ArrayList<>(g.allEdges()); // ดึงเส้นทางทั้งหมดในกราฟมากองรวมกันใน List

        // หัวใจของ Kruskal: เอาเส้นทางทั้งหมดมาเรียงลำดับ "น้ำหนัก" จากน้อยไปมาก
        edges.sort(Comparator.comparingDouble(WeightedEdge::weight));

        // วนลูปหยิบเส้นทางมาดูทีละเส้น (ตอนนี้เส้นที่เบาสุดอยู่คิวแรกสุด)
        for (WeightedEdge<V> e : edges) {
            // เช็คว่าถ้าเอาเส้นนี้ (เชื่อมจุด u กับ v) เข้าไป จะเกิดวงวนไหม?
            if (uf.union(e.u(), e.v())) { // ถ้า union คืนค่า true แปลว่าเชื่อมได้ ไม่เกิดวงวน
                res.add(e); // เก็บเส้นนี้เข้ากล่องผลลัพธ์

                // กฎของ Tree: ถ้ามี V จุด จะมีเส้นเชื่อมได้มากสุดแค่ V - 1 เส้น
                if (res.edges().size() == g.vertexCount() - 1) {
                    break; // ถ้าได้เส้นครบแล้ว ให้หยุดการทำงานทันที (ประหยัดเวลา)
                }
            }
        }
        return res; // คืนค่ากล่องผลลัพธ์กลับไปให้หน้า Main นำไปแสดงผล
    }
}

// ------------------------------------------------------------------
// คลาส Union-Find (ตัวช่วยเช็คว่าจุด 2 จุดเคยเชื่อมต่อกันไปแล้วหรือยัง)
// ------------------------------------------------------------------
class UnionFind<V> {
    private final Map<V, V> parent = new HashMap<>(); // สมุดจดว่า "ใครเป็นหัวหน้ากลุ่ม" ของแต่ละจุด
    private final Map<V, Integer> rank = new HashMap<>(); // สมุดจด "ระดับความลึก/ยศ" ของต้นไม้แต่ละกลุ่ม

    // ตอนเริ่มต้น สร้างระบบโดยให้ทุกจุดเป็นหัวหน้าของตัวเอง (แยกกันอยู่คนละกลุ่ม)
    public UnionFind(Collection<V> vertices) {
        for (V v : vertices) {
            parent.put(v, v); // ให้จุด v มีหัวหน้าคือ v (ตัวเอง)
            rank.put(v, 0); // เริ่มต้น ทุกคนยศ 0
        }
    }

    // เมธอดค้นหาว่า "ใครคือหัวหน้าใหญ่สุด" ของจุด x
    public V find(V x) {
        V p = parent.get(x); // ไปดูสมุดจดว่าหัวหน้าของ x คือใคร
        if (Objects.equals(p, x))
            return x; // ถ้าหัวหน้าคือตัวเอง แปลว่าเจอหัวหน้าใหญ่แล้ว (Root)
        V root = find(p); // ถ้ายังไม่ใช่ ให้ค้นหาต่อลึกลงไป (Recursive)
        parent.put(x, root); // Path Compression: อัปเดตสมุดจดให้ชี้ไปที่หัวหน้าใหญ่เลย
                             // คราวหน้าจะได้หาเร็วขึ้น
        return root; // คืนค่าชื่อหัวหน้าใหญ่
    }

    // เมธอดสำหรับ "จับกลุ่มรวมกัน"
    public boolean union(V a, V b) {
        V ra = find(a); // หาหัวหน้าใหญ่ของ a
        V rb = find(b); // หาหัวหน้าใหญ่ของ b
        if (Objects.equals(ra, rb))
            return false; // ถ้าหัวหน้าคนเดียวกัน แปลว่าอยู่กลุ่มเดียวกันแล้ว (ถ้ารวมอีกจะเกิดวงวน) คืนค่า
                          // false

        int rankA = rank.get(ra); // ขอดูยศหัวหน้า a
        int rankB = rank.get(rb); // ขอดูยศหัวหน้า b

        // กฎ: ให้กลุ่มที่ยศน้อยกว่า ไปขอเป็นลูกน้องกลุ่มที่ยศใหญ่กว่า
        // (ต้นไม้จะได้ไม่ลึกเกินไป)
        if (rankA < rankB)
            parent.put(ra, rb); // a ยศน้อยกว่า ให้ b เป็นหัวหน้า
        else if (rankA > rankB)
            parent.put(rb, ra); // b ยศน้อยกว่า ให้ a เป็นหัวหน้า
        else { // ถ้ายศเท่ากัน
            parent.put(rb, ra); // ยอมให้ a เป็นหัวหน้า
            rank.put(ra, rankA + 1); // แล้วเลื่อนยศให้ a 1 ขั้น
        }
        return true; // จับกลุ่มสำเร็จ คืนค่า true
    }
}