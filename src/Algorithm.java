import ExperimentEnv.*;

public class Algorithm {
    Population currPopulation;
    Population newPopulation;
    ExperimentParameters parameters;

    RaportCreator raport;

    public Algorithm(ExperimentParameters parameters) {
        this.parameters = parameters;
        this.raport = new RaportCreator();
    }

    public void runEA(){
        this.currPopulation = new Population(TSPProblem.getDimensions()); //create first random generation
        this.newPopulation = new Population(); //empty buffer for creating new generations
        for(int generationNr = 1; generationNr < parameters.generationsAmount; generationNr++){
            while(newPopulation.getIndivs().size() < parameters.populationSize){
                int candidate1 = currPopulation.getTournamentWinnerIndex(parameters.tournamentSize);
                int candidate2 = currPopulation.getTournamentWinnerIndex(parameters.tournamentSize);


            }
        }

    }

    public void start(){
        raport.loadPopulationToBuffer(currPopulation);
        raport.createResultFile();
    }

    public void setupExperiment(){
        TSPProblemCreator creator = new TSPProblemCreator(parameters.srcFilePath);
        TSPProblem tspProblem = creator.create();
    }
}
