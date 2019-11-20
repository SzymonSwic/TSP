package Results;

import Enums.AlgorithmType;
import ExperimentEnv.Indiv;
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

public class RaportTS extends RaportCreator {

    private ArrayList<TSScore> tabuResults;

    public RaportTS(ExperimentParameters params) {
        super(params, AlgorithmType.TABU);
        this.tabuResults = new ArrayList<>();
    }

    public void loadToBuffer(Generation generation) {
        this.populationCounter++;
        if (tabuResults.size() <= populationCounter) {
            tabuResults.add(getScores(generation.currentIndiv, generation.bestIndiv, generation.neighbors));
        } else {
            tabuResults.set(populationCounter, updateScore(tabuResults.get(populationCounter), getScores(generation.currentIndiv, generation.bestIndiv, generation.neighbors)));
        }
    }

    private TSScore getScores(Indiv searcher, Indiv best, ArrayList<Indiv> neighbors) {
        Indiv worst = neighbors.get(0);
        for (Indiv ind : neighbors) {
            if (ind.compareTo(worst) < 0)
                worst = ind;
        }
//        System.out.println(searcher.toString());
        return new TSScore(this.populationCounter, searcher.getFitness(), best.getFitness(), worst.getFitness());
    }

    private TSScore updateScore(TSScore oldScore, TSScore newScore) {
        oldScore.neighborScore = (oldScore.neighborScore + newScore.neighborScore) / 2;
        oldScore.worstScore = (oldScore.worstScore + newScore.worstScore) / 2;
        oldScore.bestScore = (oldScore.bestScore + newScore.bestScore) / 2;
        return oldScore;
    }

    public void createResultFile() {
        File file = new File("results/" + getRaportName() + ".csv");
        try (PrintWriter pw = new PrintWriter(file)) {
            pw.println("nr, bestNeighbor, best, worst");
            this.tabuResults
                    .stream()
                    .map(TSScore::toString)
                    .forEach(pw::println);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        drawChart();
        clearBuffer();
    }

    private String getRaportName() {
        return experimentData.srcFilePath.substring(4, experimentData.srcFilePath.indexOf(".")) +
                " neighbors- " + experimentData.neighborsAmount +
                " tabuSize- " + experimentData.tabuListSize +
                " stopCondition- " + experimentData.stopCondition;
    }

    private void clearBuffer() {
        this.tabuResults.clear();
        this.populationCounter = 0;
    }

    private class TSScore {
        int popNumber;
        double neighborScore, bestScore, worstScore;

        public TSScore(int popNumber, double neighborScore, double bestScore, double worstScore) {
            this.popNumber = popNumber;
            this.neighborScore = neighborScore;
            this.worstScore = worstScore;
            this.bestScore = bestScore;
        }

        public String toString() {
            String searcher = String.format(Locale.US, "%.2f", this.neighborScore);
            String best = String.format(Locale.US, "%.2f", this.bestScore);
            String worst = String.format(Locale.US, "%.2f", this.worstScore);

            if (this.bestScore == this.neighborScore)
                return this.popNumber + "," + searcher + "," + best + "," + worst;
            return this.popNumber + "," + searcher + "," + "," + worst;


        }
    }

    private void drawChart() {
        ArrayList<Integer> iters = new ArrayList<>();
        ArrayList<Double> neighbors = new ArrayList<>();
        ArrayList<Double> bests = new ArrayList<>();
        ArrayList<Double> worsts = new ArrayList<>();
        for (TSScore score : tabuResults) {
            iters.add(score.popNumber);
            neighbors.add(score.neighborScore);
            bests.add(score.bestScore);
            worsts.add(score.worstScore);
        }

        XYChart chart = new XYChartBuilder().width(1800).height(900)
                .title(experimentData.srcFilePath +
                        ", N = " + experimentData.neighborsAmount +
                        ", TabuSize = " + experimentData.tabuListSize +
                        ", Stop = " + experimentData.stopCondition)
                .build();
        chart.getStyler().setChartTitleBoxVisible(true);
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);
        chart.getStyler().setChartTitleVisible(true);
        chart.getStyler().setMarkerSize(3);
        chart.getStyler().setYAxisMin(getChartLimit());
        chart.addSeries("Best Neighbor", iters, neighbors);
        chart.addSeries("Best", iters, bests);
        chart.addSeries("Worst", iters, worsts);
        new SwingWrapper(chart).displayChart();
    }
}
