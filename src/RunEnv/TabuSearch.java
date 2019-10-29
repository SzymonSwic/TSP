package RunEnv;

import Enums.AlgorithmType;
import ExperimentEnv.Indiv;
import ExperimentEnv.Population;
import MyUtils.Utils;

import java.util.ArrayList;

class TabuSearch extends Algorithm {
    //tabu parematers: neighbors size, tabu list size,

    private TabuList tabuList;

    public TabuSearch(){
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
        tabuList = new TabuList(parameters.tabuListSize);
        int badNeighborsCounter = 0;

        this.currPopulation = new Population(1);
        ArrayList<Indiv> neighbors = getNeighbors();
        raport.loadTSPopulationToBuffer(getSercher(), neighbors); //init random first searcher

        while (badNeighborsCounter < parameters.stopCondition) {
            neighbors = getNeighbors();
            Indiv nextSearcher = getBestNeighbor(neighbors);

            if(nextSearcher.compareTo(getSercher()) > 0){
                this.currPopulation.getIndivs().set(0, nextSearcher);
                this.tabuList.add(nextSearcher);
                raport.loadTSPopulationToBuffer(getSercher(), neighbors);

                badNeighborsCounter = 0;
            }
            if (nextSearcher.compareTo(getSercher()) < 0)
                badNeighborsCounter++;

            if(badNeighborsCounter == parameters.stopCondition/2)
                System.out.println("No progress 50%");
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
            Indiv newNeighbor = getNeighbor(getSercher());
            if (!isTabu(newNeighbor))
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
