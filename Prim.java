import java.util.*;

public class Prim<V> {
    public MSTResult<V> compute(Graph<V> g, V start) {
        if (!g.containsVertex(start)) {
            throw new IllegalArgumentException("Start vertex not found: " + start);
        }

        MSTResult<V> res = new MSTResult<>();
        Set<V> visited = new HashSet<>();

        // Priority Queue สำหรับจัดเรียงให้เส้นทางที่สั้นที่สุด (Min-Heap)
        // อยู่คิวแรกเสมอ
        PriorityQueue<WeightedEdge<V>> pq = new PriorityQueue<>(
                Comparator.comparingDouble(WeightedEdge::weight));

        visited.add(start);
        pq.addAll(g.edgesOf(start));

        // หยุดทำงานเมื่อได้เส้นเชื่อมครบตามคุณสมบัติของ Tree (จำนวนจุดยอด - 1)
        while (!pq.isEmpty() && res.edges().size() < g.vertexCount() - 1) {
            WeightedEdge<V> e = pq.poll();
            V u = e.u();
            V v = e.v();

            boolean uIn = visited.contains(u);
            boolean vIn = visited.contains(v);

            // ป้องกันการเกิดวงวน (Cycle) ถ้าเคยไปมาแล้วทั้ง 2 ฝั่งให้ข้าม
            if (uIn && vIn)
                continue;
            if (!uIn && !vIn)
                continue;

            // ระบุว่าฝั่งไหนคือจุดใหม่ที่เพิ่งค้นพบ
            V newVertex = uIn ? v : u;

            res.add(e);
            visited.add(newVertex);

            // นำเส้นทางทั้งหมดรอบๆ จุดใหม่ โยนเข้าคิวเพื่อพิจารณาในรอบถัดไป
            for (WeightedEdge<V> ne : g.edgesOf(newVertex)) {
                V other = ne.other(newVertex);
                if (!visited.contains(other)) {
                    pq.add(ne);
                }
            }
        }
        return res;
    }
}