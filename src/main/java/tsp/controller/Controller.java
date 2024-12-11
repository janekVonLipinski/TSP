package tsp.controller;

import tsp.model.AdjacencyMatrix;
import tsp.model.Path;

public interface Controller {

    Path calculateShortestPath(AdjacencyMatrix adjacencyMatrix);
}
