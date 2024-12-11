package tsp.controller.dijkstra;

import tsp.model.AdjacencyMatrix;
import tsp.model.Path;

public interface Dijkstra {

    Path calculateShortestPathBetweenToNodes(AdjacencyMatrix adjacencyMatrix, int startNode, int endNode);
}
