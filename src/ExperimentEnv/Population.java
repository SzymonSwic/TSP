package ExperimentEnv;

import MyUtils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Population {
    ArrayList<Indiv> indivs;

    public Population(int size) {
        initRandomGeneration(size);
    }

    public Population(ArrayList<Indiv> indivs) {
        this.indivs = indivs;
    }

    public Population(){
        this.indivs = new ArrayList<>();
    }

    private void initRandomGeneration(int size) {
        this.indivs = new ArrayList<>();
        ArrayList<Integer> newRoute = IntStream.rangeClosed(0, TSPProblem.getDimensions()-1)
                                .boxed().collect(Collectors.toCollection(ArrayList::new));
        for (int i = 0; i < size; i++) {
            Collections.shuffle(newRoute);
            indivs.add(new Indiv(newRoute));
        }
    }

    public Indiv selecionTournament(int tournamentSize) {
        Indiv best = indivs.get(Utils.getRandomInt(0, indivs.size()-1));
        for(int i=0; i<tournamentSize; i++){
            Indiv opponent = indivs.get(Utils.getRandomInt(0, indivs.size()-1));
            if(best.getFitness() > opponent.getFitness())
                best = opponent;
        }
        return best;
    }

    public ArrayList<Indiv> getIndivs() {
        return indivs;
    }

    public int getTournamentWinnerIndex(int tournamentSize){
        return indivs.indexOf(selecionTournament(tournamentSize));
    }

    public void tryCrossover(int parent1, int parent2, double chance){
        if(Utils.drawDecision(chance)){

        }
    }
}
