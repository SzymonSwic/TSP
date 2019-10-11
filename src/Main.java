import ExperimentEnv.Indiv;
import ExperimentEnv.Population;
import ExperimentEnv.TSPProblem;
import ExperimentEnv.TSPProblemCreator;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        TSPProblemCreator creator = new TSPProblemCreator("TSP/berlin11_modified.tsp");
        TSPProblem tspProblem = creator.create();

        Population population = getSamplePopulation();
        System.out.println("Length:" + population.getIndivs().get(0).getRouteLength());

    }

    private static Population getSamplePopulation() {
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        ArrayList<Indiv> popInsert = new ArrayList<>();
        popInsert.add(new Indiv(list));
        return new Population(popInsert);
    }
}
