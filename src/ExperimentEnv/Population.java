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

    public Indiv[] getChildren(int parent1, int parent2, double chance, CrossoverType type){
        if (Utils.drawDecision(chance)) {
            return this.getIndivs().get(parent1).crossover(this.getIndivs().get(parent2), type);
        }
        return new Indiv[]{this.getIndivs().get(parent1), this.getIndivs().get(parent2)};
    }

    public void tryMutation(int indivIndex, double chance, MutationType type) {
        indivs.get(indivIndex).mutation(type, chance);
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

    public String toString() {
        Indiv best = this.indivs.get(0);
        Indiv worst = this.indivs.get(0);
        double avg = 0;
        if (this.indivs.size() > 1) {

            for (Indiv indiv : this.indivs) {
                if (indiv.compareTo(best) > 0)
                    best = indiv;
                else if (indiv.compareTo(worst) < 0)
                    worst = indiv;
            }
            avg = countAvg(this);
        }
        return best.getFitness()+" - "+avg+" - "+worst.getFitness();
    }

    private double countAvg(Population population) {
        double counter = 0;
        for (Indiv i : population.getIndivs()) {
            counter += i.getFitness();
        }
        return counter / population.getIndivs().size();
    }


}
