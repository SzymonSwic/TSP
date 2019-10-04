package ExperimentEnv;

import java.util.ArrayList;

public class Indiv {
    private ArrayList<Integer> route;
    private double routeLength;


    private void updateRouteLength() {
        this.routeLength = 0.0;
        for (int i = 0; i < route.size() - 1; i++) {
            int cityNr1 = route.get(i);
            int cityNr2 = route.get(i + 1);
            routeLength += TSPProblem.getNeighborhoodMatrix()[cityNr1][cityNr2];
        }
    }

    public ArrayList<Integer> getRoute() {
        return route;
    }

    public double getRouteLength() {
        return routeLength;
    }
}
