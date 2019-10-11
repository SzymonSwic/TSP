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
        routeLength += TSPProblem.getNeighborhoodMatrix()[route.get(route.size() - 1)][0];
    }

    public void mutationPair() {
        Random r = new Random();
        int first = r.nextInt(route.size());
        int second = r.nextInt(route.size());

        while (first == second) {
            second = r.nextInt(route.size());
        }
        Collections.swap(route, first, second);
        updateRouteLength();
    }

    public Indiv[] crossoverPMX(Indiv par2) {
        Random r = new Random();
        int sep = r.nextInt(route.size() - 1);
        System.out.println("CROSSOVER");
        System.out.println("SEP = "+sep);

        Integer[] parent1 = route.toArray(new Integer[0]);
        Integer[] parent2 = par2.route.toArray(new Integer[0]);

        Integer[] child1 = getPMXChild(parent1, parent2, sep);
        Integer[] child2 = getPMXChild(parent2, parent1, sep);


        return new Indiv[]{new Indiv(new ArrayList<>(Arrays.asList(child1))),
                new Indiv(new ArrayList<>(Arrays.asList(child2)))};
    }

    private Integer[] getPMXChild(Integer[] parent1, Integer[] parent2, int sep){
        Integer[] child = parent1.clone();
        for (int i = 0; i < sep; i++) {
            int toSwap = parent2[i];
            boolean valNotFound = true;
            for (int j = 0; j < parent1.length && valNotFound; j++) {
                if(parent1[j] == toSwap){
                    swap(child, j, i);
                    valNotFound = false;
                }
            }
        }
        return child;
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

    private void swap(Integer[] array, int ind1, int ind2){
        int buff = array[ind1];
        array[ind1] = array[ind2];
        array[ind2] = buff;
    }
}
