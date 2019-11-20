package RunEnv;

import Enums.AlgorithmType;
import ExperimentEnv.Indiv;
import ExperimentEnv.Population;
import Results.RaportCreator;
import ExperimentEnv.TSPProblem;
import ExperimentEnv.TSPProblemCreator;
import Results.RaportEA;
import Results.RaportSA;
import Results.RaportTS;

abstract class Algorithm {
    protected Population currPopulation;

    protected ExperimentParameters parameters;

    protected RaportCreator raport;

    public Algorithm(AlgorithmType type) {
        switch (type) {
            case EVOLUTION:
                this.raport = new RaportEA(parameters);
                break;
            case TABU:
                this.raport = new RaportTS(parameters);
                break;
            case ANNEALING:
                this.raport = new RaportSA(parameters);
        }
    }

    public void setupNewExperiment(ExperimentParameters parameters){
        this.parameters = parameters;
        this.raport.updateExperimentInfo(parameters);
        setupExperiment();
    }

    abstract void runExperimentInLoop(int iters);

    abstract void run();

    private void setupExperiment() {
        TSPProblemCreator creator = new TSPProblemCreator(parameters.srcFilePath);
        TSPProblem tspProblem = creator.create();
    }

    public Indiv getGreedyIndiv(int startCity){
        return currPopulation.getGreedyIndividual(startCity);
    }
}
