import java.util.*;

public class Prim<V> {
    // เมธอดหลัก รับกราฟ และ รับ "จุดเริ่มต้น" (start)
    public MSTResult<V> compute(Graph<V> g, V start) {
        // เช็คก่อนว่า จุดเริ่มต้นที่กรอกมา มันมีอยู่ในกราฟจริงๆ ไหม
        if (!g.containsVertex(start)) {
            throw new IllegalArgumentException("Start vertex not found: " + start); // ถ้าไม่มี ให้แจ้ง Error
        }

        MSTResult<V> res = new MSTResult<>(); // กล่องเก็บเส้นทางผลลัพธ์
        Set<V> visited = new HashSet<>(); // สมุดจดรายชื่อจุดที่ "เดินผ่านไปแล้ว" (เพื่อป้องกันการเดินซ้ำ/วงวน)

        // PriorityQueue คือ "คิวแบบพิเศษ" ที่จะจัดให้เส้นทางที่ "น้ำหนักน้อยที่สุด"
        // มาอยู่หน้าสุดเสมอ
        PriorityQueue<WeightedEdge<V>> pq = new PriorityQueue<>(
                Comparator.comparingDouble(WeightedEdge::weight));

        visited.add(start); // เริ่มต้น: จดชื่อจุดแรกเข้าสมุดว่าผ่านแล้ว
        pq.addAll(g.edgesOf(start)); // เอาเส้นทางทุกเส้นที่เชื่อมกับจุดเริ่มต้น โยนใส่คิวไว้

        // ทำไปเรื่อยๆ จนกว่าคิวจะว่าง หรือ ได้เส้นเชื่อมครบ V-1 เส้น
        while (!pq.isEmpty() && res.edges().size() < g.vertexCount() - 1) {
            WeightedEdge<V> e = pq.poll(); // ดึงเส้นที่สั้นที่สุด (น้ำหนักน้อยสุด) ออกมาจากคิว
            V u = e.u(); // ดูปลายทางด้านนึงของเส้น
            V v = e.v(); // ดูปลายทางอีกด้านของเส้น

            boolean uIn = visited.contains(u); // เช็คว่าด้าน u เคยผ่านไปหรือยัง?
            boolean vIn = visited.contains(v); // เช็คว่าด้าน v เคยผ่านไปหรือยัง?

            if (uIn && vIn)
                continue; // ถ้าปลายทางทั้ง 2 ด้านเคยผ่านแล้ว ให้ข้ามเส้นนี้ไปเลย (ป้องกันวงวน)
            if (!uIn && !vIn)
                continue; // ถ้าไม่เคยผ่านทั้ง 2 ด้านเลย (เป็นไปได้ยาก แต่กันเหนียวไว้ก่อน) ให้ข้าม

            // หาว่าด้านไหนคือจุดใหม่ที่เราเพิ่งค้นพบ
            V newVertex = uIn ? v : u;

            res.add(e); // เลือกเส้นนี้! เก็บเข้ากล่องผลลัพธ์
            visited.add(newVertex); // จดชื่อจุดใหม่เข้าสมุดว่าผ่านแล้ว

            // เอาเส้นทางรอบๆ จุดใหม่ที่เราเพิ่งเดินไปถึง
            // โยนเข้าคิวเพื่อเป็นตัวเลือกในรอบถัดไป
            for (WeightedEdge<V> ne : g.edgesOf(newVertex)) {
                V other = ne.other(newVertex); // ดูปลายทางของเส้นใหม่
                if (!visited.contains(other)) { // ถ้าปลายทางนั้นยังไม่เคยไป
                    pq.add(ne); // โยนเส้นนั้นเข้าคิวรอพิจารณา
                }
            }
        }
        return res; // คืนค่าผลลัพธ์กลับไป
    }
}