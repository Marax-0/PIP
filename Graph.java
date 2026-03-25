import java.util.*;

public class Graph {
    private Map<Integer, List<Edge>> adj = new TreeMap<>();
    private List<Edge> allEdges = new ArrayList<>();

    public void addVertex(int v) {
        adj.computeIfAbsent(v, k -> new ArrayList<>());
    }

    public void addEdge(int u, int v, double weight) {
        addVertex(u);
        addVertex(v);
        Edge e = new Edge(u, v, weight);
        adj.get(u).add(e);
        adj.get(v).add(e);
        allEdges.add(e);
    }

    public Set<Integer> vertices() { return adj.keySet(); }
    public int vertexCount() { return adj.size(); }
    public List<Edge> edgesOf(int v) { return adj.getOrDefault(v, List.of()); }
    public List<Edge> allEdges() { return Collections.unmodifiableList(allEdges); }
    public boolean containsVertex(int v) { return adj.containsKey(v); }
}
