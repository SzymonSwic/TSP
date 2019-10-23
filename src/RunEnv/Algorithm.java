package RunEnv;

import ExperimentEnv.Population;
import ExperimentEnv.RaportCreator;
import ExperimentEnv.TSPProblem;
import ExperimentEnv.TSPProblemCreator;

abstract class Algorithm {
    protected Population currPopulation;

    protected ExperimentParameters parameters;

    protected RaportCreator raport;

    public Algorithm() {
        this.raport = new RaportCreator();
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
}
