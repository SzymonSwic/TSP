package ExperimentEnv;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Indiv implements Comparable<Indiv> {
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
        routeLength += TSPProblem.getNeighborhoodMatrix()[route.get(route.size()-1)][0];
    }

    public void muationPair() {
        Random r = new Random();
        int first = r.nextInt(route.size());
        int second = r.nextInt(route.size());

        while (first == second) {
            second = r.nextInt(route.size());
        }
        Collections.swap(route, first, second);
        updateRouteLength();
    }

    public Indiv[] crossoverPMX(Indiv parent2) {
        Random r = new Random();
        int sep = r.nextInt(route.size());

        Integer[] child1 = route.toArray(new Integer[0]);
        Integer[] child2 = parent2.route.toArray(new Integer[0]);

        for (int i=0; i<child1.length; i++) {

        }






        Indiv[] result = {new Indiv(new ArrayList<>(Arrays.asList(child1))),
                          new Indiv(new ArrayList<>(Arrays.asList(child2)))};
        return result;
    }

    public ArrayList<Integer> getRoute() {
        return route;
    }

    public double getRouteLength() {
        return routeLength;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i : route) {
            builder.append(i).append(" ");
        }
        return builder.toString();
    }

    @Override
    public int compareTo(Indiv o) {
        return (int) (o.routeLength - this.routeLength);
    }
}
