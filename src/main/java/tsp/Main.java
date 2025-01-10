package tsp;

import tsp.controller.Controller;
import tsp.controller.ControllerImpl;
import tsp.model.AdjacencyMatrix;
import tsp.model.InputReader;
import tsp.model.Path;

import javax.swing.*;
import java.util.Arrays;
import java.util.Objects;

public class Main {

    public static void main(String[] args) {
        AdjacencyMatrix adjacencyMatrix;
        if (args.length > 0 && Objects.equals(args[0], "help")) {
            System.out.println("java -jar <filepath> [Starting Point]");
            return;
        }
        if (args.length == 0) {
            adjacencyMatrix = InputReader.readFile("src/main/resources/example_adjacency_matrix.txt");
        } else {
            adjacencyMatrix = InputReader.readFile(args[0]);
        }

        Controller controller = new ControllerImpl();


        Path path = (args.length > 1) ? controller.calculateShortestPath(adjacencyMatrix, Integer.parseInt(args[1])): controller.calculateShortestPath(adjacencyMatrix);

        JFrame frame = new JFrame("Graph Visualization");

        GUI panel = new GUI(adjacencyMatrix);
        frame.add(panel);
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        panel.visualizePath(path);

    }
}
