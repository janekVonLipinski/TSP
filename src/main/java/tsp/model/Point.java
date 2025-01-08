package tsp.model;

import lombok.Data;

@Data
public class Point {
    private final String name;
    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.name = "";
        this.x = x;
        this.y = y;
    }

    public Point(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }
}
