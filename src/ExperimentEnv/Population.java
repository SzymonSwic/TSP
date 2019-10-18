package ExperimentEnv;

import MyUtils.Utils;
import Enums.CrossoverType;
import Enums.MutationType;
import Enums.SelectionType;

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

    public Population() {
        this.indivs = new ArrayList<>();
    }

    private void initRandomGeneration(int size) {
        this.indivs = new ArrayList<>();
        ArrayList<Integer> newRoute = IntStream.rangeClosed(0, TSPProblem.getDimensions() - 1)
                .boxed().collect(Collectors.toCollection(ArrayList::new));
        for (int i = 0; i < size; i++) {
            Collections.shuffle(newRoute);
            indivs.add(new Indiv(Utils.makeDeepCopyInteger(newRoute)));
        }
    }

    public Indiv selecionTournament(int tournamentSize) {
        Indiv best = indivs.get(Utils.getRandomInt(0, indivs.size() - 1));
        for (int i = 0; i < tournamentSize - 1; i++) {
            Indiv opponent = indivs.get(Utils.getRandomInt(0, indivs.size() - 1));
            if (best.getFitness() > opponent.getFitness())
                best = opponent;
        }
        return best;
    }

    public int getSelectionWinner(int tournamentSize, SelectionType type){
        int result = -1;
        switch (type){
            case TOURNAMENT: result = getTournamentWinnerIndex(tournamentSize); break;
            case ROULETTE: result = getRouletteWinnerIndex(); break;
        }
        return result;
    }

    public int getTournamentWinnerIndex(int tournamentSize) {
        return indivs.indexOf(selecionTournament(tournamentSize));
    }

    public int getRouletteWinnerIndex(){
        return indivs.indexOf(selectionRoulette());
    }

    public Indiv selectionRoulette() {
        double sum = getFitnessSum();
        double divider = 0;
        double[] ranges = new double[indivs.size()];
        for (int i = 0; i < ranges.length; i++) {
            double chance = 1 - (indivs.get(i).getFitness()/sum);
            divider += chance;
            ranges[i] = divider;
        }
        double rouletteSpin = Utils.getRandomDoubleInRange(0, divider);
        int counter = 0;
        while(ranges[counter] <= rouletteSpin){
            counter++;
        }
        return indivs.get(counter);
    }

    private double getFitnessSum() {
        double result = 0;
        for (Indiv i : indivs) {
            result += i.getFitness();
        }
        return result;
    }

    public void tryCrossover(Population parentPopulation, int parent1, int parent2, double chance, CrossoverType type) {
        if (Utils.drawDecision(chance)) {
            Indiv[] children = parentPopulation.getIndivs().get(parent1).crossover(parentPopulation.getIndivs().get(parent2), type);
            this.indivs.set(parent1, children[0]);
            this.indivs.set(parent2, children[1]);
        }
    }

    public void tryMutation(int indivIndex, double chance, MutationType type) {
        if (Utils.drawDecision(chance)) {
            indivs.get(indivIndex).mutation(type);
        }
    }

    public Population copy() {
        Population populationClone = new Population();
        for (Indiv ind : indivs) {
            populationClone.getIndivs().add(ind);
        }
        return populationClone;
    }

    public ArrayList<Indiv> getIndivs() {
        return indivs;
    }
}
