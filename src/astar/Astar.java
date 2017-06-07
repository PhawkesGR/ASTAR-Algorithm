package astar;

import java.util.PriorityQueue;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;


public class Astar {

    public static void main(String[] args) {

        Node S = new Node("S", 4);
        Node A = new Node("A", 2);
        Node B = new Node("B", 3);
        Node C = new Node("C", 4);
        Node D = new Node("D", 5);
        Node E = new Node("E", 6);
        Node F = new Node("F", 4);
        Node G = new Node("G", 0);
        Node H = new Node("H", 3);
        Node I = new Node("I", 2);
        Node J = new Node("J", 1);
        Node K = new Node("K", 5);
        Node L = new Node("L", 6);

        S.addEdge(A, 2);
        A.addEdge(B, 2);
        B.addEdge(C, 2);
        C.addEdge(D, 2);
        D.addEdge(E, 1);
        D.addEdge(G, 5);
        B.addEdge(G, 3);
        S.addEdge(K, 2);
        K.addEdge(L, 1);
        S.addEdge(F, 1);
        F.addEdge(H, 1);
        H.addEdge(I, 1);
        I.addEdge(J, 1);
        J.addEdge(G, 1);

        System.out.println("Path: ");
        AstarSearch(S, G);

    }

    public static void AstarSearch(Node source, Node goal) {

        Set<Node> explored = new HashSet<>();

        PriorityQueue<Node> queue = new PriorityQueue<>(20, (Node i, Node j) -> {
            if (i.f_scores > j.f_scores) {
                return 1;
            } else if (i.f_scores < j.f_scores) {
                return -1;
            } else {
                return 0;
            }
        });

        source.g_scores = 0;
        queue.add(source);

        while ((!queue.isEmpty())) {

            Node current = queue.poll();

            explored.add(current);
            if (!"G".equals(current.value)) {
                System.out.print(current.value + "-->");
            } else {
                System.out.print(current.value + "\n");
            }
            //goal found
            if (current.value.equals("G")) {
                break;
            }

            for (Edge e : current.edges) {

                Node child = e.target;

                int cost = e.cost;
                int temp_g_scores = current.g_scores + cost;
                int temp_f_scores = temp_g_scores + child.hValue;


                /*if child node has been evaluated and 
                the newer f_score is higher, skip*/
                if ((explored.contains(child)) && (temp_f_scores >= child.f_scores)) {
                    continue;
                } else if ((!queue.contains(child)) || (temp_f_scores < child.f_scores)) {

                    child.parent = current;
                    child.g_scores = temp_g_scores;
                    child.f_scores = temp_f_scores;

                    if (queue.contains(child)) {
                        queue.remove(child);
                    }

                    queue.add(child);

                }

            }

        }

    }

}

class Node {

    public final String value;
    public int g_scores;
    public int hValue;
    public int f_scores = 0;
    public ArrayList<Edge> edges = new ArrayList<>();
    public Node parent;

    public Node(String val, int hVal) {
        value = val;
        hValue = hVal;
    }

    public String toString() {
        return value;
    }

    public void addEdge(Node to, int cost) {
        Edge edge = new Edge(to, cost);
        this.edges.add(edge);
    }

}

class Edge {

    public int cost;
    public Node target;

    public Edge(Node targetNode, int costVal) {
        target = targetNode;
        cost = costVal;
    }
}
