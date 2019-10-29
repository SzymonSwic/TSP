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
//
        String srcFilePath = "TSP/kroA100.tsp";
        int neighborsAmount = 3000;
        int tabuListSize = 50;
        int stopCounter = 1000;
        result.add(new ExperimentParameters(srcFilePath, neighborsAmount, tabuListSize, stopCounter));

        //baza
//        int populationSize = 100;
//        int generationsAmount = 100;
//        int tournamentSize = 10;
//        double Px = 0.8;
//        double Pm = 0.03;
//        String srcFilePath = "TSP/nrw1379.tsp";
//        SelectionType selectionType = SelectionType.TOURNAMENT;
//        MutationType mutationType = MutationType.INV;
//        CrossoverType crossoverType = CrossoverType.PMX;
//        result.add(new ExperimentParameters( srcFilePath, populationSize, generationsAmount, tournamentSize, selectionType, Px, crossoverType, Pm,mutationType));
//
//        //roulette test
//        selectionType = SelectionType.ROULETTE;
//        result.add(new ExperimentParameters( srcFilePath, populationSize, generationsAmount, tournamentSize, selectionType, Px, crossoverType, Pm,mutationType));
//
//        //OX test
//        selectionType = SelectionType.TOURNAMENT;
//        crossoverType = CrossoverType.ORDER;
//        result.add(new ExperimentParameters( srcFilePath, populationSize, generationsAmount, tournamentSize, selectionType, Px, crossoverType, Pm,mutationType));
//
//        //inversion test
//        crossoverType = CrossoverType.PMX;
//        mutationType = MutationType.INV;
//        Pm = 0.007;
//        result.add(new ExperimentParameters( srcFilePath, populationSize, generationsAmount, tournamentSize, selectionType, Px, crossoverType, Pm,mutationType));

//        //wieksze populacje
//        populationSize = 800;
//        result.add(new ExperimentParameters( srcFilePath, populationSize, generationsAmount, tournamentSize, selectionType, Px, crossoverType, Pm,mutationType));
//
//        //wiecej pokolen
//        populationSize = 300;
//        generationsAmount = 5000;
//        result.add(new ExperimentParameters( srcFilePath, populationSize, generationsAmount, tournamentSize, selectionType, Px, crossoverType, Pm,mutationType));
//
//        //wiekszy turniej
//        generationsAmount = 2000;
//        tournamentSize = 50;
//        result.add(new ExperimentParameters( srcFilePath, populationSize, generationsAmount, tournamentSize, selectionType, Px, crossoverType, Pm,mutationType));
//
//
//        //mniejsze krzyzowanie
//        generationsAmount = 2000;
//        tournamentSize = 10;
//        Px = 0.4;
//        result.add(new ExperimentParameters( srcFilePath, populationSize, generationsAmount, tournamentSize, selectionType, Px, crossoverType, Pm,mutationType));
//
//        //wieksza mutacja
//        Px = 0.8;
//        Pm = 0.1;
//        result.add(new ExperimentParameters( srcFilePath, populationSize, generationsAmount, tournamentSize, selectionType, Px, crossoverType, Pm,mutationType));
//
//
//        populationSize = 500;
//        generationsAmount = 5000;
//        tournamentSize = 10;
//        Px = 0.8;
//        Pm = 0.007;
//        srcFilePath = "TSP/kroA200.tsp";
//        result.add(new ExperimentParameters( srcFilePath, populationSize, generationsAmount, tournamentSize, selectionType, Px, crossoverType, Pm,mutationType));
//
//        populationSize = 500;
//        generationsAmount = 5000;
//        tournamentSize = 10;
//        Px = 0.8;
//        Pm = 0.007;
//        srcFilePath = "TSP/fl417.tsp";
//        result.add(new ExperimentParameters( srcFilePath, populationSize, generationsAmount, tournamentSize, selectionType, Px, crossoverType, Pm,mutationType));
//
//        populationSize = 500;
//        generationsAmount = 5000;
//        tournamentSize = 10;
//        Px = 0.8;
//        Pm = 0.007;
//        srcFilePath = "TSP/ali535.tsp";
//        result.add(new ExperimentParameters( srcFilePath, populationSize, generationsAmount, tournamentSize, selectionType, Px, crossoverType, Pm,mutationType));
//



        return result;
    }
}
