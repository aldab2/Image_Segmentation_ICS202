
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import javax.xml.datatype.DatatypeConfigurationException;

public class WeightedGraph<E> {

    public LinkedList<Node> nodes;

    private String totalMSTMap = "";

    public WeightedGraph() {
        this.nodes = new LinkedList<Node>();
    }

    public String mst() {
        return totalMSTMap;
    }

    WeightedGraph<E> add(E item) {

        nodes.add(new Node(item));

        return this;
    }

    void set(E replaceThis, E withThis) {

        getNode(replaceThis).set(withThis);
    }

    public Node getNode(E item) {

        for (Node node : nodes)
            if (node.item == item)
                return node;

        Node n = new Node(item);
        nodes.add(n);
        return n;
    }

    WeightedGraph<E> addOneWayEdge(E origin, int weight, E target) {

        Node x = getNode(origin);
        Node y = getNode(target);

        x.add(weight, y);

        return this;
    }

    WeightedGraph<E> addTwoWayEdge(E origin, int weight, E target) {
        addOneWayEdge(origin, weight, target);
        addOneWayEdge(target, weight, origin);

        return this;
    }

    /*
     * Assigns each node in the graph a value equal to the 
     * lowest possible sum of edges leading to that node
     * from the origin node.
     */
    private void computePaths(E e) { 

        Node origin = getNode(e); // paths from here

        origin.distance = 0; // Distance from origin to origin is 0.

        // Queue storing unvisited nodes
        PriorityQueue<Node> nodeQueue = new PriorityQueue<>();

        // Origin is added, then removed after we add connected nodes
        nodeQueue.add(origin);

        while (!nodeQueue.isEmpty()) {

            // current Node with minimum total distance from origin
            Node n = nodeQueue.poll();

            // for each edge from that node.
            for (Edge edge : n.edges) {
                Node t = edge.target;
                int distanceThroughN = n.distance + edge.weight;
                if (t.distance == null || distanceThroughN < t.distance) {
                    /*
                     * Since we evaluate the relative weight of each node
                     * with regards to their respective positioning in 
                     * the node-priorityQueue by comparing their distance 
                     * from the origin, altering this number means we 
                     * have to rebuild the priorityQueue. Not from scratch,
                     * but we do need to reposition the node in question.
                     * We do this, simply by removing the node, and then
                     * re-adding it.
                     */
                    nodeQueue.remove(t);
                    t.distance = distanceThroughN;
                    t.prev = n; // draw trail to mark shortest path
                    nodeQueue.add(t);
                }
            }
        }
    }

    public String getShortestPath(E from, E to) throws DatatypeConfigurationException {

        Node target = getNode(to);

        computePaths(from);

        String result = "";

        List<Node> path = new ArrayList<Node>();

        for (Node n = target; n != null; n = n.prev) 
            path.add(n);  

        Collections.reverse(path);

        int i = path.size();
        for (Node node : path) 
            result += node + (i --> 1 ? " -> " : ": ");

        return result + " " + target.distance;
    }

    Integer getDistance(E item) {
        /*
         * returns null if compute paths have not yet been called
         * or if the node is not connected.
         */
        return getNode(item).distance;
    }

    public int prim(E start) throws DatatypeConfigurationException {

        int nodeCount = nodes.size();

        if (nodeCount < 1)
            return 0;

        Node startNode = getNode(start);

        if (startNode.edges.size() == 0)
            throw new DatatypeConfigurationException("Disconnected start-node");

        nodeCount--;

        startNode.isConnected = true;

        int prim = 0;

        PriorityQueue<Edge> totalConnections = new PriorityQueue<>(startNode.edges);

        Edge currentEdge = startNode.edges.poll();

        boolean allConnected = false;

        while (nodeCount > 0) {

            /*
             * We need to remove any first element that is already connected
             */
            while (totalConnections.size() > 0 && currentEdge.target.isConnected) {
                currentEdge = totalConnections.poll();
            }

            if (!currentEdge.target.isConnected) {

                totalMSTMap += currentEdge + "\n";

                prim += currentEdge.weight;

                currentEdge.target.isConnected = true;

                nodeCount--;

                totalConnections.addAll(currentEdge.target.edges);

                allConnected = true;

                for (Edge e : totalConnections) {

                    if (!e.target.isConnected)
                        allConnected = false;
                }
            }
            if (allConnected && nodeCount > 0)
                throw new DatatypeConfigurationException("Unavailable node");
        }
        return prim;
    }


    public class Node implements Comparable<Node> {

        E item;

        boolean isConnected = false; // prim

        Integer distance; // Dijkstra // null = infinity

        Node prev; // Dijkstra // previous in total shortest path
        // from origin

        PriorityQueue<Edge> edges;

        public Node(E item) {
            this.item = item;
            this.edges = new PriorityQueue<Edge>();
        }

        void set(E replacement) {
            this.item = replacement;
        }

        Node add(int weight, Node target) {
            this.edges.add(new Edge(this, weight, target));

            return this;
        }
        Node doubleWayAdd(int weight, Node target) {
            this.edges.add(new Edge(this, weight, target));
            target.edges.add(new Edge(target, weight, this));
            
            
            return this;
        }
        @Override
        public int compareTo(Node o) {

            return distance < o.distance ? -1 : (distance > o.distance ? 1 : 0);
        }

        @Override
        public String toString() {

            return item.toString();
        }
    }

    public class Edge implements Comparable<Edge> {

        Node origin;
        Node target;

        int weight;

        public Edge(Node origin, int weight, Node target) {
            this.origin = origin;
            this.weight = weight;
            this.target = target;
        }

        @Override
        public String toString() {
            return origin.item + " -> " + target.item + " " + weight;
        }

        @Override
        public int compareTo(Edge o) {
            return (this.weight < o.weight ? -1 : (this.weight > o.weight ? 1 : 0));
        }
    }
    
}
