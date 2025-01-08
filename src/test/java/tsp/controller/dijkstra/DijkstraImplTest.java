package tsp.controller.dijkstra;

import org.junit.jupiter.api.Test;
import tsp.model.AdjacencyMatrix;
import tsp.model.Path;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DijkstraImplTest {

    private final Dijkstra dijkstra = new DijkstraImpl();

    @Test
    void GIVEN_fourDimensionalAdjacencyMatrix_THEN_shortestPathIs_B_A_C_D() {

        int[][] matrix = {{0, 1, 1, 0}, {1, 0, 5, 0}, {1, 5, 0, 4}, {0, 0, 4, 0}};
        AdjacencyMatrix adjacencyMatrix = new AdjacencyMatrix(matrix);
        int startPoint = 1;
        int endPoint = 3;

        List<Integer> expectedPath = List.of(1, 0, 2, 3);

        Path result = dijkstra.calculateShortestPathBetweenToNodes(adjacencyMatrix, startPoint,endPoint);

        System.out.println("correct " + expectedPath);
        System.out.println("result " + result.getPath());

        assertArrayEquals(expectedPath.toArray(), result.getPath().toArray());
    }

}