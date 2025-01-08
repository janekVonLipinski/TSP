package tsp.controller.dijkstra;

import tsp.model.AdjacencyMatrix;
import tsp.model.Path;

import java.util.*;

public class DijkstraImpl implements Dijkstra {

    private record Node(int index, int cost, Node predecessor) {}

    @Override
    public Path calculateShortestPathBetweenToNodes(AdjacencyMatrix adjacencyMatrix, int startNode, int endNode) {

        int[][] matrix = adjacencyMatrix.getMatrix();
        List<Node> visited = new ArrayList<>();
        PriorityQueue<Node> nextNodes = new PriorityQueue<>(Comparator.comparingInt(Node::cost));

        Node firstNode = new Node(startNode, 0, null);
        nextNodes.add(firstNode);

        while (!nextNodes.isEmpty()) {

            Node currentNode = nextNodes.poll();
            visited.add(currentNode);
            int indexOfCurrentNode = currentNode.index();

            if (indexOfCurrentNode == endNode) {
                break;
            }

            int[] neighboursOfCurrentNode = matrix[indexOfCurrentNode];

            for (int indexOfNeighbour = 0; indexOfNeighbour < neighboursOfCurrentNode.length; indexOfNeighbour++) {

                boolean wasVisited = visited.stream()
                        .anyMatch(i -> i.index == indexOfNeighbour);

                if (wasVisited) {
                    continue;
                }
            }
        }

        return null;
    }
}
