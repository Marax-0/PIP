import java.util.*;

public class Kruskal {
    public static List<Edge> compute(Graph g) {
        List<Edge> result = new ArrayList<>();
        List<Edge> edges = new ArrayList<>(g.allEdges());
        edges.sort(Comparator.comparingDouble(Edge::weight));

        Map<Integer, Integer> parent = new HashMap<>();
        Map<Integer, Integer> rank = new HashMap<>();
        for (int v : g.vertices()) { parent.put(v, v); rank.put(v, 0); }

        for (Edge e : edges) {
            int ru = find(parent, e.u());
            int rv = find(parent, e.v());
            if (ru != rv) {
                result.add(e);
                union(parent, rank, ru, rv);
                if (result.size() == g.vertexCount() - 1) break;
            }
        }
        return result;
    }

    private static int find(Map<Integer, Integer> parent, int x) {
        if (parent.get(x) != x) parent.put(x, find(parent, parent.get(x)));
        return parent.get(x);
    }

    private static void union(Map<Integer, Integer> parent, Map<Integer, Integer> rank, int a, int b) {
        if (rank.get(a) < rank.get(b)) parent.put(a, b);
        else if (rank.get(a) > rank.get(b)) parent.put(b, a);
        else { parent.put(b, a); rank.put(a, rank.get(a) + 1); }
    }
}