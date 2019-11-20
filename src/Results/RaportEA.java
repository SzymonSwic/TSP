package Results;

import Enums.AlgorithmType;
import ExperimentEnv.Indiv;
import ExperimentEnv.Population;
import RunEnv.ExperimentParameters;
import RunEnv.Generation;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Locale;

public class RaportEA extends RaportCreator {

    private ArrayList<EvolutionScore> evolutionResults;

    public RaportEA(ExperimentParameters params) {
        super(params, AlgorithmType.EVOLUTION);
        evolutionResults = new ArrayList<>();
    }

    public void loadToBuffer(Generation generation) {
        Population population = generation.population;
        super.populationCounter++;
        if(evolutionResults.size() <= populationCounter)
            evolutionResults.add(getScores(population));
        else
            evolutionResults.set(populationCounter, updateScore(evolutionResults.get(populationCounter), getScores(population)));
    }

    public EvolutionScore getScores(Population population) {
        Indiv best = population.getIndivs().get(0);
        Indiv worst = best;

        for (Indiv indiv : population.getIndivs()) {
            if (indiv.compareTo(best) > 0)
                best = indiv;
            else if (indiv.compareTo(worst) < 0)
                worst = indiv;
        }
        double avg = countAvg(population);

        return new EvolutionScore(this.populationCounter, best.getFitness(), avg, worst.getFitness());
    }

    public EvolutionScore updateScore(EvolutionScore oldScore, EvolutionScore newScore) {
        oldScore.bestScore = (oldScore.bestScore + newScore.bestScore) / 2;
        if (oldScore.avgScore != 0) {
            oldScore.worstScore = (oldScore.worstScore + newScore.worstScore) / 2;
            oldScore.avgScore = (oldScore.avgScore + newScore.avgScore) / 2;
        }
        return oldScore;
    }

    public void createResultFile() {
        File file = new File("results/" + getRaportName() + ".csv");
        try (PrintWriter pw = new PrintWriter(file)) {
            pw.println("nr, best, avg, worst");
            this.evolutionResults
                    .stream()
                    .map(ExperimentScore::toString)
                    .forEach(pw::println);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        drawChart();
        clearBuffer();
    }

    public String getRaportName() {
        return experimentData.srcFilePath.substring(4, experimentData.srcFilePath.indexOf(".")) +
                " pop-" + experimentData.populationSize +
                " gen-" + experimentData.generationsAmount +
                " px-" + (Double.toString(experimentData.Px).replace(".", ",")) +
                " pm-" + (Double.toString(experimentData.Pm).replace(".", ",")) +
                " tour-" + experimentData.tournamentSize +
                " " + experimentData.selectionType + " " + experimentData.crossoverType + " " + experimentData.mutationType;
    }

    public void clearBuffer() {
        this.evolutionResults.clear();
        this.populationCounter = 0;
    }

    private class EvolutionScore extends ExperimentScore{
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

    void printResearchResult() {
        System.out.println(experimentData.srcFilePath.substring(4) + "\n"
                + experimentData.generationsAmount + "\n"
                + experimentData.populationSize + "\n"
                + experimentData.Px + "\n"
                + experimentData.Pm + "\n"
                + experimentData.tournamentSize);
    }

    public void drawChart() {
        ArrayList<Integer> iters = new ArrayList<>();
        ArrayList<Double> bests = new ArrayList<>();
        ArrayList<Double> avgs = new ArrayList<>();
        ArrayList<Double> worsts = new ArrayList<>();
        double best = Double.MAX_VALUE;
        double avg = 0.0;
        for (EvolutionScore score : evolutionResults) {
            iters.add(score.popNumber);
            bests.add(score.bestScore);
            avgs.add(score.avgScore);
            worsts.add(score.worstScore);
            if (score.bestScore < best)
                best = score.bestScore;
            avg += score.avgScore;
        }

        printResearchResult();
        System.out.println("Best ever | avg");
        System.out.println(best + "   " + avg / avgs.size());

        XYChart chart = new XYChartBuilder().width(1800).height(900).build();
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);
        chart.getStyler().setChartTitleVisible(false);
        chart.getStyler().setMarkerSize(3);
        chart.addSeries("Best", iters, bests);
        chart.addSeries("Avg", iters, avgs);
        //   chart.addSeries("Worst", iters, worsts);
        new SwingWrapper(chart).displayChart();
    }
}
