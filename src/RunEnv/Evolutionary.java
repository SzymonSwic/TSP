package RunEnv;

import Enums.AlgorithmType;
import ExperimentEnv.Indiv;
import ExperimentEnv.Population;

public class Evolutionary extends Algorithm{

    private Population newPopulation;

    public Evolutionary(){
        super(AlgorithmType.EVOLUTION);
    }

    @Override
    public void runExperimentInLoop(int iters){
        for(int i=0; i<iters; i++){
            run();
            System.out.println("Experiment "+i+" done.");
        }
        System.out.println();
        raport.createResultFile();
    }

    @Override
    public void run(){
        System.out.println("Algorithm start");
        currPopulation = new Population(parameters.populationSize); //create first random generation
        newPopulation = new Population(); //buffer for creating new generations
        for (int generationNr = 0; generationNr < parameters.generationsAmount; generationNr++) {
            for (int i = 0; i < parameters.populationSize/2; i++) {
                int candidate1 = currPopulation.getSelectionWinner(parameters.tournamentSize, parameters.selectionType);
                int candidate2 = currPopulation.getSelectionWinner(parameters.tournamentSize, parameters.selectionType);

                Indiv[] children = currPopulation.getChildren(candidate1, candidate2, parameters.Px, parameters.crossoverType);
                newPopulation.getIndivs().add(children[0]);
                newPopulation.getIndivs().add(children[1]);

                newPopulation.tryMutation(newPopulation.getIndivs().size()-2, parameters.Pm, parameters.mutationType);
                newPopulation.tryMutation(newPopulation.getIndivs().size()-1, parameters.Pm, parameters.mutationType);
            }
            raport.loadToBuffer(new Generation(currPopulation));
            currPopulation = newPopulation.copy();
            newPopulation = new Population();
        }
        raport.resetCounter();
    }
}
