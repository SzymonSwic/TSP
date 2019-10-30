package RunEnv;

import Enums.AlgorithmType;
import ExperimentEnv.Indiv;
import ExperimentEnv.Population;
import MyUtils.Utils;

import java.util.ArrayList;

public class Annealing extends Algorithm {

    public Annealing() {
        super(AlgorithmType.ANNEALING);
    }

    @Override
    void runExperimentInLoop(int iters) {
        for (int i = 0; i < iters; i++) {
            run();
            System.out.println("Experiment " + i + " done.");
        }
        raport.createSAResultFile();
    }

    @Override
    void run() {
        double currTemperature = parameters.startTemperature;
        double deltaTemp = parameters.coolingRate;
        this.currPopulation = new Population(1);
        ArrayList<Indiv> neighbors;
        //init random first searcher

        while (currTemperature > parameters.stopTemperature) {
            neighbors = getNeighbors();
            Indiv nextSearcher = getBestNeighbor(neighbors);
            if (getSercher().getFitness() > nextSearcher.getFitness()) {
                this.currPopulation.getIndivs().set(0, nextSearcher);
            } else {
                if (Utils.drawDecision(Math.exp((getSercher().getFitness() - nextSearcher.getFitness() ) / currTemperature))) {
                    this.currPopulation.getIndivs().set(0, nextSearcher);
                }
            }
            raport.loadSAPopulationToBuffer(currTemperature, getSercher(), nextSearcher, neighbors);
            currTemperature -= currTemperature * deltaTemp;
        }
    }

    private Indiv getBestNeighbor(ArrayList<Indiv> candidates) {
        Indiv best = candidates.get(0);
        for (int i = 1; i < candidates.size(); i++) {
            if (candidates.get(i).compareTo(best) > 0)
                best = candidates.get(i);
        }
        return best;
    }

    private ArrayList<Indiv> getNeighbors() {
        ArrayList<Indiv> result = new ArrayList<>();
        int emergencyCounter = 0;
        while (result.size() < parameters.neighborsAmount && emergencyCounter < parameters.neighborsAmount * 3) {
            result.add(getNeighbor(getSercher()));
            emergencyCounter++;
        }
        return result;
    }

    private Indiv getNeighbor(Indiv ind) {
        Indiv neighbor = ind.getCopy();
        neighbor.mutationSwap(Utils.getRandomInt(0, neighbor.getRoute().size()));
        return neighbor;
    }

    private Indiv getSercher() {
        return this.currPopulation.getIndivs().get(0);
    }
}
