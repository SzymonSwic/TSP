package RunEnv;

import ExperimentEnv.Population;
import ExperimentEnv.RaportCreator;
import ExperimentEnv.TSPProblem;
import ExperimentEnv.TSPProblemCreator;

class Algorithm {
    private Population currPopulation;
    private Population newPopulation;
    private ExperimentParameters parameters;

    private RaportCreator raport;

    public Algorithm() {
        this.raport = new RaportCreator();
    }

    public void setupNewExperiment(ExperimentParameters parameters){
        this.parameters = parameters;
        this.raport.updateExperimentInfo(parameters);
        setupExperiment();
    }

    public void runExperimentInLoop(int iters){
        for(int i=0; i<iters; i++){
            runEA();
            System.out.println("Experiment "+i+" done.");
        }
        raport.createResultFile();
    }

    private void runEA() {
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

    private void setupExperiment() {
        TSPProblemCreator creator = new TSPProblemCreator(parameters.srcFilePath);
        TSPProblem tspProblem = creator.create();
    }
}
