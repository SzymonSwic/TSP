package RunEnv;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        ArrayList<ExperimentParameters> parametersList = getExperimentsConfig();
//        Algorithm algorithm = new Evolutionary();
        Algorithm algorithm = new TabuSearch();
        for(ExperimentParameters singleExperiment: parametersList){
            algorithm.setupNewExperiment(singleExperiment);
            algorithm.runExperimentInLoop(1);
        }
    }

    private static ArrayList<ExperimentParameters> getExperimentsConfig(){
        ArrayList<ExperimentParameters> result = new ArrayList<>();

//        //test
//        int populationSize = 10;
//        int generationsAmount = 50;
//        int tournamentSize = 2;
//        double Px = 0.8;
//        double Pm = 0.02;
//        String srcFilePath = "TSP/berlin11_modified.tsp";
//        result.add(new ExperimentParameters(populationSize, generationsAmount, tournamentSize, Px, Pm, srcFilePath));

        String srcFilePath = "TSP/test.tsp";
        int neighborsAmount = 7;
        int tabuListSize = 5;
        int searchIterations = 5;
        result.add(new ExperimentParameters(srcFilePath, neighborsAmount, tabuListSize, searchIterations));

        //baza
//        int populationSize = 100;
//        int generationsAmount = 1000;
//        int tournamentSize = 10;
//        double Px = 0.8;
//        double Pm = 0.2;
//        String srcFilePath = "TSP/berlin11_modified.tsp";
//        SelectionType selectionType = SelectionType.TOURNAMENT;
//        MutationType mutationType = MutationType.SWAP;
//        CrossoverType crossoverType = CrossoverType.PMX;
//        result.add(new ExperimentParameters(populationSize, generationsAmount, tournamentSize, selectionType, Px, crossoverType, Pm,mutationType, srcFilePath));

//        //wieksze populacje
//        populationSize = 500;
//        srcFilePath = "TSP/kroA150.tsp";
//        result.add(new ExperimentParameters(populationSize, generationsAmount, tournamentSize, Px, Pm, srcFilePath));
//
//        //wiecej pokolen
//        populationSize = 200;
//        generationsAmount = 5000;
//        srcFilePath = "TSP/kroA150.tsp";
//        result.add(new ExperimentParameters(populationSize, generationsAmount, tournamentSize, Px, Pm, srcFilePath));
//
//        //wiekszy turniej
//        populationSize = 200;
//        generationsAmount = 2000;
//        tournamentSize = 30;
//        srcFilePath = "TSP/kroA150.tsp";
//        result.add(new ExperimentParameters(populationSize, generationsAmount, tournamentSize, Px, Pm, srcFilePath));
//
//
//        //mniejsze krzyzowanie
//        populationSize = 200;
//        generationsAmount = 2000;
//        tournamentSize = 10;
//        Px = 0.4;
//        srcFilePath = "TSP/kroA150.tsp";
//        result.add(new ExperimentParameters(populationSize, generationsAmount, tournamentSize, Px, Pm, srcFilePath));
//
//        //wieksza mutacja
//        populationSize = 200;
//        generationsAmount = 2000;
//        tournamentSize = 10;
//        Px = 0.8;
//        Pm = 0.1;
//        srcFilePath = "TSP/kroA150.tsp";
//        result.add(new ExperimentParameters(populationSize, generationsAmount, tournamentSize, Px, Pm, srcFilePath));
//
//
//        populationSize = 500;
//        generationsAmount = 5000;
//        tournamentSize = 10;
//        Px = 0.8;
//        Pm = 0.03;
//        srcFilePath = "TSP/kroA200.tsp";
//        result.add(new ExperimentParameters(populationSize, generationsAmount, tournamentSize, Px, Pm, srcFilePath));
//
//        populationSize = 500;
//        generationsAmount = 5000;
//        tournamentSize = 10;
//        Px = 0.8;
//        Pm = 0.03;
//        srcFilePath = "TSP/fl417.tsp";
//        result.add(new ExperimentParameters(populationSize, generationsAmount, tournamentSize, Px, Pm, srcFilePath));
//
//        populationSize = 500;
//        generationsAmount = 5000;
//        tournamentSize = 10;
//        Px = 0.8;
//        Pm = 0.03;
//        srcFilePath = "TSP/ali535.tsp";
//        result.add(new ExperimentParameters(populationSize, generationsAmount, tournamentSize, Px, Pm, srcFilePath));
//




        return result;
    }
}
