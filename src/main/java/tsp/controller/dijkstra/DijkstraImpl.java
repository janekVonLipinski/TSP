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
                        .anyMatch(node -> node.index() == finalIndexOfNeighbour);

                if (wasVisited) {
                    continue;
                }

                int cost = neighboursOfCurrentNode[indexOfNeighbour];

                if (existsNoPath(cost)) {
                    continue;
                }

                int costOfNode = cost + currentNode.cost();

                Optional<Node> hasBeenSeen = nextNodes.stream()
                        .filter(node -> node.index() == finalIndexOfNeighbour)
                        .findFirst();

                if (hasBeenSeen.isEmpty()) {
                    Node node = new Node(finalIndexOfNeighbour, costOfNode, currentNode);
                    nextNodes.add(node);
                    continue;
                }

                Node sameNodeFoundPreviously = hasBeenSeen.get();

                if (isNewPathShorter(sameNodeFoundPreviously, costOfNode)) {
                    replaceNode(nextNodes, sameNodeFoundPreviously, finalIndexOfNeighbour, costOfNode, currentNode);
                }
            }
        }

        return createPathFromNodes(visited);
    }

    private boolean isNewPathShorter(Node sameNodeFoundPreviously, int costOfNode) {
        return sameNodeFoundPreviously.cost() > costOfNode;
    }

    private boolean existsNoPath(int cost) {
        return cost == 0;
    }

    private void replaceNode(PriorityQueue<Node> nextNodes, Node sameNodeFoundPreviously, int finalIndexOfNeighbour, int costOfNode, Node currentNode) {
        nextNodes.remove(sameNodeFoundPreviously);
        Node node = new Node(finalIndexOfNeighbour, costOfNode, currentNode);
        nextNodes.add(node);
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
