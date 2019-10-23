package RunEnv;

import Enums.AlgorithmType;
import ExperimentEnv.Indiv;
import ExperimentEnv.Population;

import java.util.ArrayList;
import java.util.Collections;

public class TabuSearch extends Algorithm {
    //TODO tabu parematers: neighbors size, tabu list size,

    TabuList tabuList;

    public TabuSearch() {
        this.currPopulation = new Population(1);
        tabuList = new TabuList(parameters.tabuListSize);
    }

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
        for(int i = 0; i<parameters.searchIterations; i++){
            raport.loadPopulationToBuffer(this.currPopulation);
            Indiv nextSearcher = getBestNeighbor();
            this.tabuList.add(nextSearcher);
            this.currPopulation.getIndivs().set(0, nextSearcher);
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
        for (int i = 0; i < parameters.neighborsAmount; i++) {
            result.add(getNeighbor(getSercher()));
        }
        return result;
    }

    private Indiv getNeighbor(Indiv ind) {
        Indiv neighbor = ind.getCopy();
        neighbor.mutationSwap();
        return neighbor;
    }

    private boolean isTabu(){

    }

    private Indiv getSercher(){
        return this.currPopulation.getIndivs().get(0);
    }
}
