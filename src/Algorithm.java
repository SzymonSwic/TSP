import ExperimentEnv.Population;
import ExperimentEnv.RaportCreator;
import ExperimentEnv.TSPProblem;
import ExperimentEnv.TSPProblemCreator;

public class Algorithm {
    Population currPopulation;
    Population newPopulation;
    ExperimentParameters parameters;

    RaportCreator raport;

    public Algorithm(ExperimentParameters parameters) {
        this.parameters = parameters;
        this.raport = new RaportCreator();
        setupExperiment();
    }

    public void setupNewExperiment(ExperimentParameters parameters){
        this.parameters = parameters;
        setupExperiment();
    }

    public void runEA() {
        currPopulation = new Population(TSPProblem.getDimensions()); //create first random generation
        newPopulation = currPopulation.copy(); //buffer for creating new generations
        for (int generationNr = 0; generationNr < parameters.generationsAmount; generationNr++) {
            for (int i = 0; i < parameters.populationSize; i++) {
                int candidate1 = currPopulation.getTournamentWinnerIndex(parameters.tournamentSize);
                int candidate2 = currPopulation.getTournamentWinnerIndex(parameters.tournamentSize);

                newPopulation.tryCrossover(currPopulation, candidate1, candidate2, parameters.Px);

                newPopulation.tryMutation(candidate1, parameters.Pm);
                newPopulation.tryMutation(candidate2, parameters.Pm);
            }
            raport.loadPopulationToBuffer(currPopulation);
            currPopulation = newPopulation.copy();
            newPopulation = currPopulation.copy();
        }
        raport.createResultFile();
    }

    private void setupExperiment() {
        TSPProblemCreator creator = new TSPProblemCreator(parameters.srcFilePath);
        TSPProblem tspProblem = creator.create();
    }
}
