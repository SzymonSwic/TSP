import ExperimentEnv.Population;
import ExperimentEnv.TSPProblem;
import ExperimentEnv.TSPProblemCreator;

public class Main {

    public static void main(String[] args) {
        TSPProblemCreator creator = new TSPProblemCreator("TSP/berlin11_modified.tsp");
        TSPProblem tspProblem = creator.create();

        Algorithm algorithm = new Algorithm();
        algorithm.start();
    }
}
