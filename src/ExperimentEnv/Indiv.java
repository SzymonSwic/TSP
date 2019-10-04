package ExperimentEnv;

import java.util.ArrayList;

public class Indiv {
    private ArrayList<Integer> route;
    private double routeLength;

    public Indiv(ArrayList<Integer> route) {
        this.route = route;
        updateRouteLength();
    }

    private void updateRouteLength() {
        this.routeLength = 0.0;
        for (int i = 0; i < route.size() - 1; i++) {
            int cityNr1 = route.get(i);
            int cityNr2 = route.get(i + 1);
            routeLength += TSPProblem.getNeighborhoodMatrix()[cityNr1][cityNr2];
        }
    }

    public void mutate(){
        //TODO
    }

    public Indiv crossover(Indiv parent){
        //TODO
        return parent;
    }

    public ArrayList<Integer> getRoute() {
        return route;
    }

    public double getRouteLength() {
        return routeLength;
    }

    public String toString(){
        StringBuilder builder = new StringBuilder();
        for(int i: route){
            builder.append(i).append(" ");
        }
        return builder.toString();
    }
}
