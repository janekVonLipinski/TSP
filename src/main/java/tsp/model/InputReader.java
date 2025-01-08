package tsp.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class InputReader {
    /*
    Reads out an adjacency matrix of a text file.
    File should be formatted like this:
        1, 2, 3, 4
        5, 6, 7, 8
        9, 10, 11, 12
        13, 14, 15, 16
     */
    public static AdjacencyMatrix readFile(String filepath) {
        int[][] adjacencyMatrix = null;

        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            int rowCount = 0;
            int columnCount = 0;

            // First pass: determine the dimensions of the matrix
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",\\s*");
                columnCount = values.length;
                rowCount++;
            }

            if (columnCount != rowCount) throw new IllegalArgumentException("Given Matrix is not Quadratic");

            // Initialize the matrix with determined dimensions
            adjacencyMatrix = new int[rowCount][columnCount];

            // Reset the reader to the beginning of the file
            br.close();
            BufferedReader newBr = new BufferedReader(new FileReader(filepath));

            // Second pass: populate the matrix
            int row = 0;
            while ((line = newBr.readLine()) != null) {
                String[] values = line.split(",\\s*");
                for (int col = 0; col < values.length; col++) {
                    adjacencyMatrix[row][col] = Integer.parseInt(values[col]);
                }
                row++;
            }
            newBr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new AdjacencyMatrix(adjacencyMatrix);
    }
}

