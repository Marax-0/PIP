import java.util.*;

public class Dijkstra {
    public static Map<Integer, Integer> compute(Graph g, int source) {
        Map<Integer, Integer> dist = new TreeMap<>();
        Set<Integer> visited = new HashSet<>();

        for (int v : g.vertices()) dist.put(v, Integer.MAX_VALUE);
        dist.put(source, 0);

        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        pq.add(new int[]{source, 0});

        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int u = curr[0];
            if (visited.contains(u)) continue;
            visited.add(u);

            for (Edge e : g.edgesOf(u)) {
                int v = e.other(u);
                int newDist = dist.get(u) + (int) e.weight();
                if (newDist < dist.get(v)) {
                    dist.put(v, newDist);
                    pq.add(new int[]{v, newDist});
                }
            }
        }
        return dist;
    }
}
