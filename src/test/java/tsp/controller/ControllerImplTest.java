package tsp.controller;

import org.junit.jupiter.api.Test;
import tsp.model.AdjacencyMatrix;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ControllerImplTest {

    @Test
    void GIVEN_PermutationsOf2_THEN_expect() {
        //Arrange
        var impl = new ControllerImpl();

        //Act
        var perms = impl.getPermutations(2);

        //Assert
        assertEquals(perms.size(), 6);
        assertEquals(3, perms.getFirst().size());
        assertEquals(3, perms.get(1).size());
        assertEquals(3, perms.get(2).size());
        assertEquals(3, perms.get(3).size());
        assertEquals(3, perms.get(4).size());
        assertEquals(3, perms.get(5).size());
    }
    @Test
    void GIVEN_PermutationsOf0_THEN_expect() {
        //Arrange
        var impl = new ControllerImpl();

        //Act
        var perms = impl.getPermutations(0);

        //Assert
        assertEquals(perms.size(), 1);
        assertEquals(1,perms.getFirst().size());
        assertEquals(0, perms.getFirst().getFirst());
    }

    @Test
    void GIVEN_adjMatrix_THEN_shortestEulerCircle(){
        //Arrange
        var impl = new ControllerImpl();
        int[][] matrix = {{0, 1, 1, 0}, {1, 0, 5, 0}, {1, 5, 0, 4}, {0, 0, 4, 0}};
        AdjacencyMatrix adjacencyMatrix = new AdjacencyMatrix(matrix);
        int startPoint = 1;
        List<Integer> expectedPath = List.of(1, 0, 2, 3, 2, 0, 1);
        int expectedCost = 12;

        //Act
        var path = impl.calculateShortestPath(adjacencyMatrix, startPoint);

        //Assert
        assertEquals(expectedCost, path.getCost());
        assertEquals(expectedPath, path.getPath());
    }
}