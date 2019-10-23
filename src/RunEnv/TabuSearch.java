package RunEnv;

import Enums.AlgorithmType;
import ExperimentEnv.Indiv;
import ExperimentEnv.Population;

import java.util.ArrayList;

class TabuSearch extends Algorithm {
    //tabu parematers: neighbors size, tabu list size,

    private TabuList tabuList;

    @Override
    void runExperimentInLoop(int iters) {
        for (int i = 0; i < iters; i++) {
            run();
            System.out.println("Experiment " + i + " done.");
        }
        raport.createResultFile(AlgorithmType.TABU);
    }

    @Override
    void run() {
        this.currPopulation = new Population(1);
        tabuList = new TabuList(parameters.tabuListSize);
        boolean bestLocalFound = false;
        int iterationsCounter = 0;
        while (iterationsCounter < parameters.searchIterations && !bestLocalFound) {
            raport.loadPopulationToBuffer(this.currPopulation);
            Indiv nextSearcher = getBestNeighbor();
            if (nextSearcher != null) {
                this.currPopulation.getIndivs().set(0, nextSearcher);
                this.tabuList.add(nextSearcher);
            } else bestLocalFound = true;
            iterationsCounter++;
        }
    }

    private Indiv getBestNeighbor() {
        ArrayList<Indiv> candidates = getNeighbors();
        Indiv best = candidates.get(0);
        for (int i = 1; i < candidates.size(); i++) {
            if (candidates.get(i).getFitness() < best.getFitness())
                best = candidates.get(i);
        }
        return best;
    }

    private ArrayList<Indiv> getNeighbors() {
        ArrayList<Indiv> result = new ArrayList<>();
        int emergencyCounter = 0;
        while (result.size() < parameters.neighborsAmount && emergencyCounter < parameters.neighborsAmount * 3) {
            Indiv newNeighbor = getNeighbor(getSercher());
            if (!isTabu(newNeighbor))
                result.add(getNeighbor(getSercher()));
            emergencyCounter++;
        }
        return result;
    }

    private Indiv getNeighbor(Indiv ind) {
        Indiv neighbor = ind.getCopy();
        neighbor.mutationSwap();
        return neighbor;
    }

    private boolean isTabu(Indiv ind) {
        for (Indiv taboo : this.tabuList.getTabu()) {
            if (ind.isSame(taboo))
                return true;
        }
        return false;
    }

    private Indiv getSercher() {
        return this.currPopulation.getIndivs().get(0);
    }
}
