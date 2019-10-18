package RunEnv;

import Enums.CrossoverType;
import Enums.MutationType;
import Enums.SelectionType;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        ArrayList<ExperimentParameters> parametersList = getExperimentsConfig();
        Algorithm EA = new Algorithm();
        for(ExperimentParameters singleExperiment: parametersList){
            EA.setupNewExperiment(singleExperiment);
            EA.runExperimentInLoop(10);
        }
    }

    public static ArrayList<ExperimentParameters> getExperimentsConfig(){
        ArrayList<ExperimentParameters> result = new ArrayList<>();

//        //test
//        int populationSize = 10;
//        int generationsAmount = 50;
//        int tournamentSize = 2;
//        double Px = 0.8;
//        double Pm = 0.02;
//        String srcFilePath = "TSP/berlin11_modified.tsp";
//        result.add(new ExperimentParameters(populationSize, generationsAmount, tournamentSize, Px, Pm, srcFilePath));


        //baza
        int populationSize = 100;
        int generationsAmount = 1000;
        int tournamentSize = 10;
        double Px = 0.8;
        double Pm = 0.2;
        String srcFilePath = "TSP/berlin11_modified.tsp";
        SelectionType selectionType = SelectionType.TOURNAMENT;
        MutationType mutationType = MutationType.SWAP;
        CrossoverType crossoverType = CrossoverType.PMX;
        result.add(new ExperimentParameters(populationSize, generationsAmount, tournamentSize, selectionType, Px, crossoverType, Pm,mutationType, srcFilePath));

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
