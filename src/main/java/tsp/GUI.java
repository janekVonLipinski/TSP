package tsp;

import tsp.model.AdjacencyMatrix;
import tsp.model.Path;
import tsp.model.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GUI extends JPanel {
    private final int BORDERSPACE = 100;
    private List<Integer> pathPoints;
    private List<Point> points;
    private final AdjacencyMatrix adjazentMatrix;
    private Timer timer;
    private int currentArrowIndex = 0; // Keeps track of the current arrow being drawn

    public GUI(List<Point> points, AdjacencyMatrix adjazentMatrix) {
        this.points = points;
        this.adjazentMatrix = adjazentMatrix;
    }

    public GUI(AdjacencyMatrix adjazentMatrix) {
        this.points = arrangePointsInCircle(adjazentMatrix.getMatrix().length);
        this.adjazentMatrix = adjazentMatrix;
    }

    private List<Point> arrangePointsInCircle(int numberOfPoints) {
        List<Point> circlePoints = new ArrayList<>();
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int radius = Math.min(getWidth(), getHeight()) / 2 - BORDERSPACE;
        double angleStep = 2 * Math.PI / numberOfPoints;

        int name = 65;
        for (int i = 0; i < numberOfPoints; i++) {
            int x = (int) (centerX + radius * Math.cos(i * angleStep));
            int y = (int) (centerY + radius * Math.sin(i * angleStep));
            circlePoints.add(new Point(Character.toString(name), x, y));
            name++;
        }
        return circlePoints;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Get panel dimensions
        int width = getWidth();
        int height = getHeight();

        // Determine scaling factors
        int minX = points.stream().mapToInt(p -> p.getX()).min().orElse(0);
        int maxX = points.stream().mapToInt(p -> p.getX()).max().orElse(1);
        int minY = points.stream().mapToInt(p -> p.getY()).min().orElse(0);
        int maxY = points.stream().mapToInt(p -> p.getY()).max().orElse(1);

        double scaleX = (width - 2 * BORDERSPACE) / (double) (maxX - minX);
        double scaleY = (height - 2 * BORDERSPACE) / (double) (maxY - minY);

        // Scale and center points
        List<Point> scaledPoints = new ArrayList<>();
        for (Point point : points) {
            int scaledX = (int) ((point.getX() - minX) * scaleX) + BORDERSPACE;
            int scaledY = (int) ((point.getY() - minY) * scaleY) + BORDERSPACE;
            scaledPoints.add(new Point( point.getName(), scaledX, scaledY));
        }

        int[][] matrix = adjazentMatrix.getMatrix();
        boolean[][] drawnEdges = new boolean[matrix.length][matrix.length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] > 0 && !drawnEdges[i][j]) { // If there is a connection and not yet drawn
                    Point p1 = scaledPoints.get(i);
                    Point p2 = scaledPoints.get(j);

                    if (matrix[j][i] > 0) { // Bidirectional connection
                        drawnEdges[j][i] = true; // Mark the reverse edge as drawn

                        // Draw a line without an arrow
                        g2d.drawLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());

                        // Draw weight slightly offset next to the line
                        int dx = p2.getX() - p1.getX();
                        int dy = p2.getY() - p1.getY();
                        double length = Math.sqrt(dx * dx + dy * dy);
                        int weightX = (int) (p1.getX() + 0.5 * dx - 10 * dy / length);
                        int weightY = (int) (p1.getY() + 0.5 * dy + 10 * dx / length);
                        g2d.drawString(String.valueOf(matrix[i][j]), weightX, weightY);

                    } else { // Unidirectional connection
                        // Draw line
                        g2d.drawLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());

                        // Call drawArrow method
                        drawArrow(p1, p2, g2d);

                        // Draw weight slightly offset next to the line
                        int dx = p2.getX() - p1.getX();
                        int dy = p2.getY() - p1.getY();
                        double length = Math.sqrt(dx * dx + dy * dy);
                        int weightX = (int) (p1.getX() + 0.5 * dx - 10 * dy / length);
                        int weightY = (int) (p1.getY() + 0.5 * dy + 10 * dx / length);
                        g2d.drawString(String.valueOf(matrix[i][j]), weightX, weightY);
                    }

                    drawnEdges[i][j] = true; // Mark this edge as drawn
                }
            }
        }

        // Draw points with names if available
        for (Point point : scaledPoints) {
            g2d.setColor(Color.BLUE);
            g2d.fillOval(point.getX() - 5, point.getY() - 5, 10, 10);
            if (point.getName() != null) {
                g2d.drawString(point.getName(), point.getX() + 10, point.getY() - 10);
            }
        }



        // Draw the red arrows if pathPoints are set
        if (pathPoints != null && currentArrowIndex < pathPoints.size() - 1) {

            // Draw the path up to the currentArrowIndex
            for (int i = 0; i <= currentArrowIndex; i++) {
                g2d.setColor(Color.getHSBColor((float) (i * 0.05), 1, 1));
                Point p1 = scaledPoints.get(pathPoints.get(i));
                Point p2 = scaledPoints.get(pathPoints.get(i + 1));
                g2d.drawLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
                drawArrow(p1, p2, g2d);
            }
        }
    }

    // New method to draw arrow
    private void drawArrow(Point point1, Point point2, Graphics2D g2d) {
        int dx = point2.getX() - point1.getX();
        int dy = point2.getY() - point1.getY();
        double angle = Math.atan2(dy, dx);
        int arrowLength = 10;
        int arrowWidth = 5;

        int x1 = point2.getX() - (int) (arrowLength * Math.cos(angle) - arrowWidth * Math.sin(angle));
        int y1 = point2.getY() - (int) (arrowLength * Math.sin(angle) + arrowWidth * Math.cos(angle));

        int x2 = point2.getX() - (int) (arrowLength * Math.cos(angle) + arrowWidth * Math.sin(angle));
        int y2 = point2.getY() - (int) (arrowLength * Math.sin(angle) - arrowWidth * Math.cos(angle));

        g2d.fillPolygon(new int[]{point2.getX(), x1, x2}, new int[]{point2.getY(), y1, y2}, 3);
    }

    public void visualizePath(Path path) {
        this.pathPoints = path.getPath();
        currentArrowIndex = 0;

        // Set up the Timer to control the drawing with a delay
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentArrowIndex <= pathPoints.size() - 1) {
                    currentArrowIndex++;
                    repaint(); // Repaint the panel to draw the next arrow
                } else {
                    currentArrowIndex = 0; // Reset for looping animation
                    repaint(); // Restart the loop by repainting
                }
            }
        });
        timer.start();
    }

    // Example For the Visualisation
    public static void main(String[] args) {
        // Example points and adjacency matrix (Optional)
        List<Point> points = new ArrayList<>();
        points.add(new Point("A", 0, 0));
        points.add(new Point("B", 100, 0));
        points.add(new Point("C", 50, 100));
        points.add(new Point("D", 200, 100));
        points.add(new Point("E", 50, 200));

        // From row -> col
        int[][] matrix = {
                {0, 0, 1, 0, 1},
                {1, 0, 0, 1, 0},
                {0, 1, 0, 0, 0},
                {1, 1, 1, 0, 0},
                {0, 0, 1, 0, 0}
        };

        AdjacencyMatrix adjazentMatrix = new AdjacencyMatrix(matrix);

        // Create and display the GUI
        JFrame frame = new JFrame("Graph Visualization");

        // points are Optional
        GUI panel = new GUI(points, adjazentMatrix);
        frame.add(panel);
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        Path path = new Path(new ArrayList<>(Arrays.asList(0, 1, 2, 3, 1, 0, 4, 2)), 0);
        panel.visualizePath(path);
    }
}
