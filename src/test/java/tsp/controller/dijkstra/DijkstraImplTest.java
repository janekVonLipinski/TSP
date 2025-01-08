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

        assertArrayEquals(expectedPath.toArray(), result.getPath().toArray());
    }

    @Test
    void GIVEN_sixDimensionalMatrix_Then_shortestPathIs_A_B_E_F() {

        int[][] matrix  = {
                {0, 1, 1, 0, 0, 0},
                {1, 0, 2, 5, 3, 0},
                {1, 2, 0, 6, 0, 12},
                {0, 5, 6, 0, 7, 0},
                {0, 3, 0, 7, 0, 3},
                {0, 0, 12, 0, 3, 0}
        };
        AdjacencyMatrix adjacencyMatrix = new AdjacencyMatrix(matrix);
        int startPoint = 0;
        int endPoint = 5;

        List<Integer> expectedPath = List.of(0, 1, 4, 5);

        Path result = dijkstra.calculateShortestPathBetweenToNodes(adjacencyMatrix, startPoint,endPoint);

        assertArrayEquals(expectedPath.toArray(), result.getPath().toArray());
    }

}