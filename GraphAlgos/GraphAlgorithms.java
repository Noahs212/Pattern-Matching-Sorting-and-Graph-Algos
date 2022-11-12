import java.util.List;
import java.util.Set;
import java.util.Map;
import java.util.Queue;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.PriorityQueue;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Your implementation of various different graph algorithms.
 *
 * @author Noah Statton
 * @version 1.0
 *
 */
public class GraphAlgorithms {

    /**
     * Performs a breadth first search (bfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * You may import/use java.util.Set, java.util.List, java.util.Queue, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for BFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the bfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> bfs(Vertex<T> start, Graph<T> graph) {
        if (graph == null || start == null || !graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("An input is null or the graph doesnt contain the start");
        }
        List<Vertex<T>> visitedList = new ArrayList<>();
        Queue<Vertex<T>> vertexQueue = new ConcurrentLinkedQueue<>();
        visitedList.add(start);
        vertexQueue.add(start);
        while (!vertexQueue.isEmpty()) {
            Vertex<T> curr = vertexQueue.poll();
            for (VertexDistance j: graph.getAdjList().get(curr)) {
                if (!visitedList.contains(j.getVertex())) {
                    vertexQueue.add(j.getVertex());
                    visitedList.add(j.getVertex());
                }
            }
        }
        return visitedList;
    }

    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * *NOTE* You MUST implement this method recursively, or else you will lose
     * all points for this method.
     *
     * You may import/use java.util.Set, java.util.List, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for DFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> dfs(Vertex<T> start, Graph<T> graph) {
        if (graph == null || start == null || !graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("An input is null or the graph doesn't contain the start");
        }
        List<Vertex<T>> visited = new ArrayList<>();
        dfsHelper(start, graph, visited);
        return visited;
    }

    /**
     *
     * @param start vertex where current call is
     * @param graph graph being traversed
     * @param visitedList list of visited vertices
     * @param <T> generic type of vertices
     */
    public static <T> void dfsHelper(Vertex<T> start, Graph<T> graph, List<Vertex<T>> visitedList) {
        visitedList.add(start);
        for (VertexDistance j: graph.getAdjList().get(start)) {
            if (!visitedList.contains(j.getVertex())) {
                dfsHelper(j.getVertex(), graph, visitedList);
            }
        }
    }

    /**
     * Finds the single-source shortest distance between the start vertex and
     * all vertices given a weighted graph (you may assume non-negative edge
     * weights).
     *
     * Return a map of the shortest distances such that the key of each entry
     * is a node in the graph and the value for the key is the shortest distance
     * to that node from start, or Integer.MAX_VALUE (representing
     * infinity) if no path exists.
     *
     * You may import/use java.util.PriorityQueue,
     * java.util.Map, and java.util.Set and any class that
     * implements the aforementioned interfaces, as long as your use of it
     * is efficient as possible.
     *
     * You should implement the version of Dijkstra's where you use two
     * termination conditions in conjunction.
     *
     * 1) Check if all of the vertices have been visited.
     * 2) Check if the PQ is empty yet.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the Dijkstra's on (source)
     * @param graph the graph we are applying Dijkstra's to
     * @return a map of the shortest distances from start to every
     * other node in the graph
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start, Graph<T> graph) {
        if (graph == null || start == null || !graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("An input is null or the graph does not contain the start");
        }
        Map<Vertex<T>, Integer> shortestpath = new HashMap<>();
        PriorityQueue<VertexDistance<T>> vertexQueue = new PriorityQueue();
        vertexQueue.add(new VertexDistance<>(start, 0));
        shortestpath.put(start, 0);
        while (!vertexQueue.isEmpty() && shortestpath.size() < graph.getVertices().size() - 1) {
            VertexDistance<T> curr = vertexQueue.remove();
            for (VertexDistance j: graph.getAdjList().get(curr.getVertex())) {
                if (!shortestpath.containsKey(j.getVertex())) {
                    vertexQueue.add(new VertexDistance<>(j.getVertex(), j.getDistance() + curr.getDistance()));
                    shortestpath.put(j.getVertex(), j.getDistance() + curr.getDistance());
                }
            }
        }
        return shortestpath;


    }

    /**
     * Runs Kruskal's algorithm on the given graph and returns the Minimal
     * Spanning Tree (MST) in the form of a set of Edges. If the graph is
     * disconnected and therefore no valid MST exists, return null.
     *
     * You may assume that the passed in graph is undirected. In this framework,
     * this means that if (u, v, 3) is in the graph, then the opposite edge
     * (v, u, 3) will also be in the graph, though as a separate Edge object.
     *
     * The returned set of edges should form an undirected graph. This means
     * that every time you add an edge to your return set, you should add the
     * reverse edge to the set as well. This is for testing purposes. This
     * reverse edge does not need to be the one from the graph itself; you can
     * just make a new edge object representing the reverse edge.
     *
     * You may assume that there will only be one valid MST that can be formed.
     *
     * Kruskal's will also require you to use a Disjoint Set which has been
     * provided for you. A Disjoint Set will keep track of which vertices are
     * connected given the edges in your current MST, allowing you to easily
     * figure out whether adding an edge will create a cycle. Refer
     * to the DisjointSet and DisjointSetNode classes that
     * have been provided to you for more information.
     *
     * You should NOT allow self-loops or parallel edges into the MST.
     *
     * By using the Disjoint Set provided, you can avoid adding self-loops and
     * parallel edges into the MST.
     *
     * You may import/use java.util.PriorityQueue,
     * java.util.Set, and any class that implements the aforementioned
     * interfaces.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param graph the graph we are applying Kruskals to
     * @return the MST of the graph or null if there is no valid MST
     * @throws IllegalArgumentException if any input is null
     */
    public static <T> Set<Edge<T>> kruskals(Graph<T> graph) {
        if (graph == null) {
            throw new IllegalArgumentException("the graph is null");
        }
        DisjointSet<Vertex<T>> ds = new DisjointSet<>();
        PriorityQueue<Edge<T>> pq = new PriorityQueue<>(graph.getEdges());
        Set<Edge<T>> mst = new HashSet<>();

        //put each edge into disjoint set
        for (Vertex<T> vertex: graph.getVertices()) {
            ds.find(vertex);
        }

        while (!pq.isEmpty() || mst.size() < graph.getVertices().size() - 1) {
            Edge<T> curr = pq.poll();
            if (!ds.find(curr.getU()).equals(ds.find(curr.getV()))) {
                mst.add(curr);
                Edge<T> counterpart = new Edge<>(curr.getV(), curr.getU(), curr.getWeight());
                mst.add(counterpart);
                ds.union(curr.getU(), curr.getV());
            }
        }
        if (mst.size() < graph.getVertices().size() - 1) {
            return null;
        }
        return mst;
    }
}
