package RunEnv;

import Enums.AlgorithmType;
import ExperimentEnv.Population;

public class Evolutionary extends Algorithm{

    private Population newPopulation;

    @Override
    public void run(){
        System.out.println("Algorithm start");
        currPopulation = new Population(parameters.populationSize); //create first random generation
        newPopulation = currPopulation.copy(); //buffer for creating new generations
        for (int generationNr = 0; generationNr < parameters.generationsAmount; generationNr++) {
            for (int i = 0; i < parameters.populationSize; i++) {
                int candidate1 = currPopulation.getSelectionWinner(parameters.tournamentSize, parameters.selectionType);
                int candidate2 = currPopulation.getSelectionWinner(parameters.tournamentSize, parameters.selectionType);

                newPopulation.tryCrossover(currPopulation, candidate1, candidate2, parameters.Px, parameters.crossoverType);

                newPopulation.tryMutation(candidate1, parameters.Pm, parameters.mutationType);
                newPopulation.tryMutation(candidate2, parameters.Pm, parameters.mutationType);
            }
            raport.loadPopulationToBuffer(currPopulation);
            currPopulation = newPopulation.copy();
        }
    }

    @Override
    public void runExperimentInLoop(int iters){
        for(int i=0; i<iters; i++){
            run();
            System.out.println("Experiment "+i+" done.");
        }
        raport.createResultFile(AlgorithmType.EVOLUTION);
    }
}
