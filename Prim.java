import java.util.*;

public class Prim {
    public static List<Edge> compute(Graph g, int start) {
        List<Edge> result = new ArrayList<>();
        Set<Integer> visited = new HashSet<>();
        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingDouble(Edge::weight));

        visited.add(start);
        pq.addAll(g.edgesOf(start));

        while (!pq.isEmpty() && result.size() < g.vertexCount() - 1) {
            Edge e = pq.poll();
            boolean uIn = visited.contains(e.u());
            boolean vIn = visited.contains(e.v());
            if (uIn && vIn) continue;

            int newVertex = uIn ? e.v() : e.u();
            result.add(e);
            visited.add(newVertex);

            for (Edge ne : g.edgesOf(newVertex)) {
                if (!visited.contains(ne.other(newVertex))) pq.add(ne);
            }
        }
        return result;
    }
}