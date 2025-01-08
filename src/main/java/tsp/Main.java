package tsp;

import tsp.controller.Controller;
import tsp.controller.ControllerImpl;
import tsp.model.AdjacencyMatrix;
import tsp.model.InputReader;
import tsp.model.Path;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("java -jar <filepath> <Optional: Starting Point>");
            return;
        }

        AdjacencyMatrix adjacencyMatrix = InputReader.readFile(args[0]);

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
