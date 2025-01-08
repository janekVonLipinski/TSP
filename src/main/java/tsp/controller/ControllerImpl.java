package tsp.controller;

import tsp.controller.dijkstra.DijkstraImpl;
import tsp.model.AdjacencyMatrix;
import tsp.model.Path;

import java.util.ArrayList;
import java.util.List;

public class ControllerImpl implements Controller{
    @Override
    public Path calculateShortestPath(AdjacencyMatrix adjacencyMatrix) {
        return calculateShortestPath(adjacencyMatrix, 0);
    }
    @Override
    public Path calculateShortestPath(AdjacencyMatrix adjacencyMatrix, int startingPoint){
        var dijkstra = new DijkstraImpl();
        List<List<Integer>> permutations = getPermutations(adjacencyMatrix.getMatrix().length - 1);
        permutations = permutations.stream().filter(x -> x.getFirst() == startingPoint).toList();
        List<Path> paths = new ArrayList<>();

        for (var permutation : permutations){
            var path = dijkstra.calculateShortestPathBetweenToNodes(adjacencyMatrix,
                    permutation.getFirst(), permutation.get(1));
            for (int i = 1; i < permutation.size() - 2; i++){
                var nextPath = dijkstra.calculateShortestPathBetweenToNodes(adjacencyMatrix,
                        permutation.get(i), permutation.get(i+1));
                path = path.chainPath(nextPath);
            }
            var lastPath = dijkstra.calculateShortestPathBetweenToNodes(adjacencyMatrix,
                    permutation.get(permutation.size()-2), permutation.getLast());
            path = path.chainPath(lastPath);
            var returnPath = dijkstra.calculateShortestPathBetweenToNodes(adjacencyMatrix,
                    permutation.getLast(), permutation.getFirst());
            paths.add(path.chainPath(returnPath));
        }

        var shortestPath = paths.getFirst();

        for (var path : paths){
            if (path.getCost() < shortestPath.getCost())
                shortestPath = path;
        }

        return shortestPath;
    }

    public List<List<Integer>> getPermutations(int numbersUpTo){

        if (numbersUpTo == 0) {
            List<Integer> zeroList = new ArrayList<>();
            zeroList.add(0);
            List<List<Integer>> toReturn = new ArrayList<>();
            toReturn.add(zeroList);
            return toReturn;
        }
        List<List<Integer>> priorIterations = getPermutations(numbersUpTo - 1);
        List<List<Integer>> newIterations = new ArrayList<>();
        for (List<Integer> iteration : priorIterations){
            for (int i = 0; i <= numbersUpTo; i++){
                List<Integer> tempOld = new ArrayList<>(iteration);
                tempOld.add(i, numbersUpTo);
                newIterations.add(tempOld);
            }
        }

        return newIterations;
    }
}
