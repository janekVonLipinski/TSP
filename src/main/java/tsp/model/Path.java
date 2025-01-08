package tsp.model;

import lombok.Data;

import java.util.List;

@Data
public class Path {

    private final List<Integer> path;
    private final int cost;

    public Path chainPath(Path nextPath){
        path.removeLast();
        path.addAll(nextPath.path);
        int nextCost = cost + nextPath.cost;
        return new Path(path, nextCost);
    }
}
