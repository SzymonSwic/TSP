package ExperimentEnv;

import Enums.AlgorithmType;
import RunEnv.ExperimentParameters;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Locale;

public class RaportCreator {
    //raport is universal for all experiments - in tabu search population score has only number and best parameter (avg and worst equal 0)

    private AlgorithmType algorithmFlag;
    private ArrayList<EvolutionScore> evolutionResults;
    private ArrayList<TSScore> TSResults;
    private ExperimentParameters experimentData;
    private int populationCounter;

    public RaportCreator(AlgorithmType type) {
        this.algorithmFlag = type;
        switch (algorithmFlag){
            case EVOLUTION:  this.evolutionResults = new ArrayList<>(); break;
            case TABU:  this.TSResults = new ArrayList<>();
        }
    }

    public RaportCreator(ExperimentParameters params, AlgorithmType type) {
        this.evolutionResults = new ArrayList<>();
        this.experimentData = params;
        this.populationCounter = 0;
        this.algorithmFlag = type;
    }

    public void updateExperimentInfo(ExperimentParameters params) {
        this.experimentData = params;
        this.populationCounter = 0;
    }

    public void loadEvolutionPopulationToBuffer(Population population) {
        this.populationCounter++;
        if (this.populationCounter > experimentData.generationsAmount) {
            this.populationCounter -= experimentData.generationsAmount;
            evolutionResults.set(populationCounter, updateEvolutionScore(evolutionResults.get(populationCounter), getEvolutionScores(population)));
        } else {
            evolutionResults.add(getEvolutionScores(population));
        }
    }

    private EvolutionScore getEvolutionScores(Population population) {
        Indiv best = population.indivs.get(0);
        Indiv worst = best;

        for (Indiv indiv : population.indivs) {
            if (indiv.compareTo(best) > 0)
                best = indiv;
            else if (indiv.compareTo(worst) < 0)
                worst = indiv;
        }
        double avg = countAvg(population);

        return new EvolutionScore(this.populationCounter, best.getFitness(), avg, worst.getFitness());
    }

    private EvolutionScore updateEvolutionScore(EvolutionScore oldScore, EvolutionScore newScore) {
        oldScore.bestScore = (oldScore.bestScore + newScore.bestScore) / 2;
        if (oldScore.avgScore != 0) {
            oldScore.worstScore = (oldScore.worstScore + newScore.worstScore) / 2;
            oldScore.avgScore = (oldScore.avgScore + newScore.avgScore) / 2;
        }
        return oldScore;
    }

    public void createEvolutionResultFile() {
        File file = new File("results/" + getEvolutionRaportName() + ".csv");
        try (PrintWriter pw = new PrintWriter(file)) {
            pw.println("nr, best, avg, worst");
            this.evolutionResults
                    .stream()
                    .map(EvolutionScore::toString)
                    .forEach(pw::println);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        clearEvolutionBuffer();
    }

    private String getEvolutionRaportName() {
        return experimentData.srcFilePath.substring(4, experimentData.srcFilePath.indexOf(".")) +
                " pop-" + experimentData.populationSize +
                " gen-" + experimentData.generationsAmount +
                " px-" + (Double.toString(experimentData.Px).replace(".", ",")) +
                " pm-" + (Double.toString(experimentData.Pm).replace(".", ",")) +
                " tour-" + experimentData.tournamentSize +
                " " + experimentData.selectionType + " " + experimentData.crossoverType + " " + experimentData.mutationType;
    }

    private class EvolutionScore {
        int popNumber;
        double worstScore, bestScore, avgScore;

        EvolutionScore(int popNumber, double bestScore, double avgScore, double worstScore) {
            this.popNumber = popNumber;
            this.worstScore = worstScore;
            this.bestScore = bestScore;
            this.avgScore = avgScore;
        }

        public String toString() {
            String bestScore = String.format(Locale.US, "%.2f", this.bestScore);
            String worstScore = String.format(Locale.US, "%.2f", this.worstScore);
            String avgScore = String.format(Locale.US, "%.2f", this.avgScore);

            return this.popNumber + "," + bestScore + "," + avgScore + "," + worstScore;
        }
    }







    public void loadTSPopulationToBuffer(Indiv searcher, ArrayList<Indiv> neighbors){
        this.populationCounter++;
        TSResults.add(getTSScores(searcher, neighbors));
//        if (this.populationCounter > experimentData.generationsAmount) {
//            this.populationCounter -= experimentData.generationsAmount;
//            TSResults.set(populationCounter, updateTSScore(TSResults.get(populationCounter), getTSScores(searcher, neighbors)));
//        } else {
//
//        }
    }

    private TSScore getTSScores(Indiv searcher, ArrayList<Indiv> neighbors){
        Indiv worst = neighbors.get(0);
        for(Indiv ind: neighbors){
            if(ind.compareTo(worst) < 0)
                worst = ind;
        }
        return new TSScore(this.populationCounter, searcher.getFitness(), worst.getFitness());
    }

    private TSScore updateTSScore(TSScore oldScore, TSScore newScore) {
        oldScore.searcherScore = (oldScore.searcherScore + newScore.searcherScore) / 2;
        oldScore.worstScore = (oldScore.worstScore + newScore.worstScore) / 2;
        return oldScore;
    }

    public void createTSResultFile() {
        File file = new File("results/" + getTSRaportName() + ".csv");
        try (PrintWriter pw = new PrintWriter(file)) {
            pw.println("nr, searcher, worst");
            this.TSResults
                    .stream()
                    .map(TSScore::toString)
                    .forEach(pw::println);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        clearTSBuffer();
    }

    private String getTSRaportName() {
        return experimentData.srcFilePath.substring(4, experimentData.srcFilePath.indexOf(".")) +
                " neighbors- " + experimentData.neighborsAmount +
                " tabuSize- " + experimentData.tabuListSize;
    }

    private class TSScore {
        int popNumber;
        double searcherScore, worstScore;

        public TSScore(int popNumber, double searcherScore, double worstScore) {
            this.popNumber = popNumber;
            this.searcherScore = searcherScore;
            this.worstScore = worstScore;
        }

        public String toString() {
            String searcher = String.format(Locale.US, "%.2f", this.searcherScore);
            String worst = String.format(Locale.US, "%.2f", this.worstScore);

            return this.popNumber + ", " + searcher + ", " + worst;
        }
    }




    private void clearEvolutionBuffer() {
        this.evolutionResults.clear();
        this.populationCounter = 0;
    }

    private void clearTSBuffer() {
        this.TSResults.clear();
        this.populationCounter = 0;
    }

    private double countAvg(Population population) {
        double counter = 0;
        for (Indiv i : population.getIndivs()) {
            counter += i.getFitness();
        }
        return counter / population.getIndivs().size();
    }
}
