import ExperimentEnv.Population;
import ExperimentEnv.TSPProblem;
import ExperimentEnv.TSPProblemCreator;

public class Main {

    public static void main(String[] args) {
        TSPProblemCreator creator = new TSPProblemCreator("TSP/berlin52.tsp");
        TSPProblem tspProblem = creator.create();
        Population pop = new Population(10);
    }
}
