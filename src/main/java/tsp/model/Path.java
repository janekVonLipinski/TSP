package tsp.model;

import lombok.Data;

import java.util.List;

@Data
public class Path {

    private final List<Integer> path;
    private final int cost;
}
