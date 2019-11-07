package RunEnv;

import Enums.AlgorithmType;
import ExperimentEnv.Indiv;
import ExperimentEnv.Population;
import MyUtils.Utils;

import java.util.ArrayList;

class TabuSearch extends Algorithm {
    //tabu parematers: neighbors size, tabu list size,

    private TabuList tabuList;

    public TabuSearch() {
        super(AlgorithmType.TABU);
    }

    @Override
    void runExperimentInLoop(int iters) {
        for (int i = 0; i < iters; i++) {
            run();
            System.out.println("Experiment " + i + " done.");
        }
        raport.createTSResultFile();
    }

    @Override
    void run() {
        System.out.println("Tabu Search start");
        tabuList = new TabuList(parameters.tabuListSize);
        int badNeighborsCounter = 0;

        this.currPopulation = new Population(1);
        ArrayList<Indiv> neighbors;
        Indiv bestNeighbor = getSercher(); //init random first searcher

        while (badNeighborsCounter < parameters.stopCondition) {
            neighbors = getNeighbors(bestNeighbor);
            if (!neighbors.isEmpty()) {
                bestNeighbor = getBestNeighbor(neighbors);

                if (bestNeighbor.getFitness() < getSercher().getFitness()) {
                    this.currPopulation.getIndivs().set(0, bestNeighbor);
                    badNeighborsCounter = 0;
                } else {
                    badNeighborsCounter++;
                }

                this.tabuList.add(bestNeighbor);
                raport.loadTSPopulationToBuffer(bestNeighbor, getSercher(), neighbors);

                if (badNeighborsCounter == parameters.stopCondition / 2)
                    System.out.println("No progress 50%");
            }

        }
    }

    private Indiv getBestNeighbor(ArrayList<Indiv> candidates) {
        Indiv best = candidates.get(0);
        for (int i = 0; i < candidates.size(); i++) {
            if (candidates.get(i).compareTo(best) > 0)
                best = candidates.get(i);
        }
        return best;
    }

    private ArrayList<Indiv> getNeighbors(Indiv bestIndiv) {
        ArrayList<Indiv> result = new ArrayList<>();
        int emergencyCounter = 0;
        while (result.size() < parameters.neighborsAmount && emergencyCounter < parameters.neighborsAmount * 3) {
            Indiv newNeighbor = getNeighbor(bestIndiv);
            if (!isTabu(newNeighbor))
                result.add(getNeighbor(bestIndiv));
            emergencyCounter++;
        }
        return result;
    }

    private Indiv getNeighbor(Indiv ind) {
        Indiv neighbor = ind.getCopy();
        neighbor.mutationSwap(Utils.getRandomInt(0, neighbor.getRoute().size()));
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
