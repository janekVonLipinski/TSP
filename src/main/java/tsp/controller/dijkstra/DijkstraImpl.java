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

                int finalIndexOfNeighbour = indexOfNeighbour;

                boolean wasVisited = visited.stream()
                        .anyMatch(i -> i.index() == finalIndexOfNeighbour);

                if (wasVisited) {
                    continue;
                }

                int cost = neighboursOfCurrentNode[indexOfNeighbour];

                if (cost == 0) {
                    continue;
                }

                int costOfNode = cost + currentNode.cost();

                Optional<Node> hasBeenSeen = nextNodes.stream()
                        .filter(i -> i.index() == finalIndexOfNeighbour)
                        .findFirst();

                if (hasBeenSeen.isEmpty()) {
                    Node node = new Node(finalIndexOfNeighbour, costOfNode, currentNode);
                    nextNodes.add(node);
                    continue;
                }

                Node sameNodeFoundPreviously = hasBeenSeen.get();

                if (sameNodeFoundPreviously.cost() > costOfNode) {
                    nextNodes.remove(sameNodeFoundPreviously);
                    Node node = new Node(finalIndexOfNeighbour, costOfNode, currentNode);
                    nextNodes.add(node);
                }
            }
        }

        return createPathFromNodes(visited);
    }

    private Path createPathFromNodes(List<Node> visited) {
        Node node = visited.getLast();
        Node current = node;
        int cost = node.cost();
        List<Integer> path = new ArrayList<>();

        while (current != null) {
            path.add(current.index());
            current = current.predecessor();
        }

        Collections.reverse(path);

        return new Path(path, cost);
    }
}
