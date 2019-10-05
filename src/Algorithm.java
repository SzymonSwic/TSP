import ExperimentEnv.Population;
import ExperimentEnv.RaportCreator;
import ExperimentEnv.TSPProblem;
import ExperimentEnv.TSPProblemCreator;

public class Algorithm {
    Population currPopulation;
    Population newPopulation;

    RaportCreator raport;

    public Algorithm() {
        this.currPopulation = new Population(TSPProblem.getDimensions());
        this.raport = new RaportCreator();
    }

    public void start(){
        raport.loadPopulationToBuffer(currPopulation);
        raport.createResultFile();
    }
}
