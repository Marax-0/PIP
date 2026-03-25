public class Edge {
    private int u, v;
    private double weight;

    public Edge(int u, int v, double weight) {
        this.u = u;
        this.v = v;
        this.weight = weight;
    }

    public int u() { return u; }
    public int v() { return v; }
    public double weight() { return weight; }

    public int other(int x) {
        if (x == u) return v;
        if (x == v) return u;
        throw new IllegalArgumentException("Vertex not in edge: " + x);
    }

    public String toString() {
        return u + " - " + v;
    }
}
