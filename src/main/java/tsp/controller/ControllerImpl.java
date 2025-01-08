package tsp.controller;

import tsp.model.AdjacencyMatrix;
import tsp.model.Path;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class ControllerImpl implements Controller{
    @Override
    public Path calculateShortestPath(AdjacencyMatrix adjacencyMatrix) {
        return null;
    }

    private List<List<Integer>> getIterations(int numbersUpTo){

        if (numbersUpTo == 0) {
            List<Integer> zeroList = new ArrayList<Integer>();
            zeroList.add(0);
            List<List<Integer>> toReturn = new ArrayList<List<Integer>>();
            toReturn.add(zeroList);
            return toReturn;
        }
        List<List<Integer>> priorIterations = getIterations(numbersUpTo - 1);
        List<List<Integer>> newIterations = new ArrayList<List<Integer>>();
        for (List<Integer> iteration : priorIterations){
            for (int i = 0; i < numbersUpTo; i++){
                List<Integer> tempOld = new ArrayList<Integer>(iteration);
                tempOld.add(i, numbersUpTo);
                newIterations.add(tempOld);
            }
        }

        return newIterations;
    }
}
