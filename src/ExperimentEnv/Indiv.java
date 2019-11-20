package ExperimentEnv;

import Enums.CrossoverType;
import Enums.MutationType;
import MyUtils.Utils;

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
        double tmpCounter = 0.0;
        for (int i = 0; i < route.size() - 1; i++) {
            int cityNr1 = route.get(i);
            int cityNr2 = route.get(i + 1);
            double buffer = TSPProblem.getNeighborhoodMatrix()[cityNr1][cityNr2];
            tmpCounter += buffer;
        }
        tmpCounter += TSPProblem.getNeighborhoodMatrix()[route.get(route.size() - 1)][route.get(0)];
        setRouteLength(tmpCounter);
    }

    void mutation(MutationType type, double chance) {
        switch (type) {
            case SWAP:
                for(int i=0; i<this.route.size(); i++){
                    if(Utils.drawDecision(chance))
                        mutationSwap(i);
                }
                break;
            case INV:
                if(Utils.drawDecision(chance))
                    mutationInversion();
                break;
        }
    }

    Indiv[] crossover(Indiv par2, CrossoverType type) {
        Indiv[] result = new Indiv[0];
        switch (type) {
            case PMX:
                result = crossoverPMX(par2);
                break;
            case ORDER:
                result = crossoverOrder(par2);
                break;
        }
        return result;
    }

    public void mutationSwap(int gene) {
        Random r = new Random();
        int second = r.nextInt(route.size());

        while (gene == second) {
            second = r.nextInt(route.size());
        }
        Collections.swap(route, gene, second);
        updateRouteLength();
    }

    private void mutationInversion() {
        Random r = new Random();
        int begin = r.nextInt(route.size());
        int end = r.nextInt(route.size());

        while (begin == end) {
            begin = r.nextInt(route.size());
            end = r.nextInt(route.size());
        }

        if(begin > end){
            int buffer = begin;
            begin = end;
            end = buffer;
        }
        invert(route, begin, end);
    }

    private void invert(ArrayList<Integer> route, int begin, int end) {
        while (begin < end) {
            Collections.swap(route, begin, end);
            begin++;
            end--;
        }
    }

    private Indiv[] crossoverOrder(Indiv par2) {
        Random r = new Random();
        int sep1 = r.nextInt(route.size() - 1);
        int sep2 = r.nextInt(route.size() - 1);

        while (sep1 == sep2){
            sep1 = r.nextInt(route.size() - 1);
            sep2 = r.nextInt(route.size() - 1);
        }

        if(sep1 > sep2){
            int buffer = sep1;
            sep1 = sep2;
            sep2 = buffer;
        }

        Integer[] parent1 = route.toArray(new Integer[0]);
        Integer[] parent2 = par2.route.toArray(new Integer[0]);

        Integer[] child1 = getOrderChild(parent1, parent2, sep1, sep2);
        Integer[] child2 = getOrderChild(parent2, parent1, sep1, sep2);


        return new Indiv[]{new Indiv(new ArrayList<>(Arrays.asList(child1))),
                new Indiv(new ArrayList<>(Arrays.asList(child2)))};
    }

    private Integer[] getOrderChild(Integer[] parent1, Integer[] parent2, int sep1, int sep2) {
        Integer[] child = new Integer[parent1.length];
        Arrays.fill(child, -1);
        if (sep2 - sep1 >= 0) System.arraycopy(parent1, sep1, child, sep1, sep2 - sep1);

        int childIndex = sep2;
        int par2Index = sep2;
        while (contains(child, -1)) {
            if (!contains(child, parent2[par2Index])) {
                child[childIndex] = parent2[par2Index];
                childIndex = getNextCrossIndex(childIndex, child.length);
                par2Index = getNextCrossIndex(par2Index, parent2.length);
            } else
                par2Index = getNextCrossIndex(par2Index, parent2.length);
        }
        return child;
    }

    private int getNextCrossIndex(int val, int size) {
        if (val + 1 == size)
            return 0;
        return ++val;
    }

    private boolean contains(Integer[] arr, int val) {
        for (Integer integer : arr) {
            if (integer == val)
                return true;
        }
        return false;
    }

    public Indiv[] crossoverPMX(Indiv par2) {
        Random r = new Random();
        int sep = r.nextInt(route.size() - 1);

        Integer[] parent1 = route.toArray(new Integer[0]);
        Integer[] parent2 = par2.route.toArray(new Integer[0]);

        Integer[] child1 = getPMXChild(parent1, parent2, sep);
        Integer[] child2 = getPMXChild(parent2, parent1, sep);


        return new Indiv[]{new Indiv(new ArrayList<>(Arrays.asList(child1))),
                new Indiv(new ArrayList<>(Arrays.asList(child2)))};
    }

    private Integer[] getPMXChild(Integer[] parent1, Integer[] parent2, int sep) {
        Integer[] child = parent1.clone();
        for (int i = 0; i < sep; i++) {
            int toSwap = parent2[i];
            boolean valNotFound = true;
            for (int j = i; j < parent1.length && valNotFound; j++) {
                if (parent1[j] == toSwap) {
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

    private void setRouteLength(double routeLength) {
        this.routeLength = routeLength;
    }

    public double getFitness() {
        return getRouteLength();
    }

    private double getRouteLength() {
        updateRouteLength();
        return this.routeLength;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i : route) {
            builder.append(i).append(" ");
        }
        String fitnes = String.format("%.2f", this.getFitness());
        builder.append("- fitness: ").append(fitnes);
        return builder.toString();
//        return "fitness: "+String.format("%.2f", getFitness());
    }

    @Override
    public int compareTo(Indiv o) {
        return (int) (o.routeLength - this.routeLength);
    }

    public boolean isSame(Indiv ind) {
        for (int i = 0; i < ind.route.size(); i++) {
            if (!ind.route.get(i).equals(this.route.get(i)))
                return false;
        }
        return true;
    }

    private void swap(Integer[] array, int ind1, int ind2) {
        int buff = array[ind1];
        array[ind1] = array[ind2];
        array[ind2] = buff;
    }

    public Indiv getCopy() {
        ArrayList<Integer> copy = new ArrayList<>(0);
        copy.addAll(this.route);
        return new Indiv(copy);
    }
}
