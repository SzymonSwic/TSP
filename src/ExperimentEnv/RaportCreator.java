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

    private ArrayList<SinglePopulationScore> experimentResults;
    private ExperimentParameters experimentData;
    private int populationCounter;

    public RaportCreator() {
        this.experimentResults = new ArrayList<>();
    }

    public RaportCreator(ExperimentParameters params) {
        this.experimentResults = new ArrayList<>();
        this.experimentData = params;
        this.populationCounter = 0;
    }

    public void updateExperimentInfo(ExperimentParameters params) {
        this.experimentData = params;
        this.populationCounter = 0;
    }

    public void loadPopulationToBuffer(Population population) {
        this.populationCounter++;
        if (this.populationCounter > experimentData.generationsAmount) {
            this.populationCounter -= experimentData.generationsAmount;
            experimentResults.set(populationCounter, updateScore(experimentResults.get(populationCounter), getScores(population)));
        } else {
            experimentResults.add(getScores(population));
        }

    }

    private SinglePopulationScore getScores(Population population) {
        Indiv best = population.indivs.get(0);
        if (population.indivs.size() > 1) {
            Indiv worst = best;

            for (Indiv indiv : population.indivs) {
                if (indiv.compareTo(best) > 0)
                    best = indiv;
                else if (indiv.compareTo(worst) < 0)
                    worst = indiv;
            }
            double avg = countAvg(population);

            return new SinglePopulationScore(this.populationCounter, best.getFitness(), avg, worst.getFitness());
        }
        return new SinglePopulationScore(this.populationCounter, best.getFitness(), 0, 0);

    }

    private SinglePopulationScore updateScore(SinglePopulationScore oldScore, SinglePopulationScore newScore) {
        oldScore.bestScore = (oldScore.bestScore + newScore.bestScore) / 2;
        if (oldScore.avgScore != 0) {
            oldScore.worstScore = (oldScore.worstScore + newScore.worstScore) / 2;
            oldScore.avgScore = (oldScore.avgScore + newScore.avgScore) / 2;
        }


        return oldScore;
    }

    private double countAvg(Population population) {
        double counter = 0;
        for (Indiv i : population.getIndivs()) {
            counter += i.getFitness();
        }
        return counter / population.getIndivs().size();
    }

    public void createResultFile(AlgorithmType type) {
        File file = new File("results/" + getRaportName(type) + ".csv");
        try (PrintWriter pw = new PrintWriter(file)) {
            if(type.equals(AlgorithmType.EVOLUTION))
                pw.println("nr, best, avg, worst");
            else pw.println("nr, fitness");
            this.experimentResults
                    .stream()
                    .map(SinglePopulationScore::toString)
                    .forEach(pw::println);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        clearBuffer();
    }

    private void clearBuffer() {
        this.experimentResults.clear();
        this.populationCounter = 0;
    }

    private String getRaportName(AlgorithmType type) {
        switch (type) {
            case EVOLUTION:
                return experimentData.srcFilePath.substring(4, experimentData.srcFilePath.indexOf(".")) +
                        " pop-" + experimentData.populationSize +
                        " gen-" + experimentData.generationsAmount +
                        " px-" + (Double.toString(experimentData.Px).replace(".", ",")) +
                        " pm-" + (Double.toString(experimentData.Pm).replace(".", ",")) +
                        " tour-" + experimentData.tournamentSize +
                        " " + experimentData.selectionType + " " + experimentData.crossoverType + " " + experimentData.mutationType;
            case TABU:
                return experimentData.srcFilePath.substring(4, experimentData.srcFilePath.indexOf(".")) +
                        " neighbors- " + experimentData.neighborsAmount +
                        " tabuSize- " + experimentData.tabuListSize;
        }
        return "";
    }


    private class SinglePopulationScore {
        int popNumber;
        double worstScore, bestScore, avgScore;

        SinglePopulationScore(int popNumber, double bestScore, double avgScore, double worstScore) {
            this.popNumber = popNumber;
            this.worstScore = worstScore;
            this.bestScore = bestScore;
            this.avgScore = avgScore;
        }

        public String toString() {
            String bestScore = String.format(Locale.US, "%.2f", this.bestScore);
            String worstScore = String.format(Locale.US, "%.2f", this.worstScore);
            String avgScore = String.format(Locale.US, "%.2f", this.avgScore);

            if (this.worstScore > 0)
                return this.popNumber + "," + bestScore + "," + avgScore + "," + worstScore;
            return this.popNumber + "," + bestScore;
        }
    }
}
