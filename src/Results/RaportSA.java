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

public class RaportSA extends RaportCreator {

    private ArrayList<SAScore> annealingResults;

    public RaportSA(ExperimentParameters params) {
        super(params, AlgorithmType.ANNEALING);
        annealingResults = new ArrayList<>();
    }

    public void loadToBuffer(Generation generation) {
        this.populationCounter++;
        if(annealingResults.size() <= populationCounter){
            annealingResults.add(getScores(generation.currTemperature, generation.currentIndiv, generation.bestIndiv, generation.neighbors));
        } else {
            annealingResults.set(populationCounter, updateScore(annealingResults.get(populationCounter), getScores(generation.currTemperature, generation.currentIndiv, generation.bestIndiv, generation.neighbors)));
        }
    }

    private SAScore getScores(double temp, Indiv searcher, Indiv best, ArrayList<Indiv> neighbors) {
        Indiv worst = neighbors.get(0);
        for (Indiv ind : neighbors) {
            if (ind.compareTo(worst) < 0)
                worst = ind;
        }
//        System.out.println(searcher.toString());
        return new SAScore(temp, searcher.getFitness(), best.getFitness(), worst.getFitness());
    }

    private SAScore updateScore(SAScore oldScore, SAScore newScore) {
        oldScore.neighborScore = (oldScore.neighborScore + newScore.neighborScore) / 2;
        oldScore.worstScore = (oldScore.worstScore + newScore.worstScore) / 2;
        oldScore.bestScore = (oldScore.bestScore + newScore.bestScore) / 2;
        return oldScore;
    }

    public void createResultFile() {
        File file = new File("results/" + getRaportName() + ".csv");
        try (PrintWriter pw = new PrintWriter(file)) {
            pw.println("nr, bestNeighbor, best, worst");
            this.annealingResults
                    .stream()
                    .map(SAScore::toString)
                    .forEach(pw::println);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        drawChart();
        clearBuffer();
    }

    private String getRaportName() {
        return experimentData.srcFilePath.substring(4, experimentData.srcFilePath.indexOf(".")) +
                " temp start - " + experimentData.startTemperature +
                " temp stop - " + experimentData.stopTemperature +
                " coolingRate - " + experimentData.coolingRate;
    }

    private void clearBuffer() {
        this.annealingResults.clear();
        this.populationCounter = 0;
    }

    private class SAScore {
        double temperature;
        double neighborScore, bestScore, worstScore;

        public SAScore(double popNumber, double neighborScore, double bestScore, double worstScore) {
            this.temperature = popNumber;
            this.neighborScore = neighborScore;
            this.worstScore = worstScore;
            this.bestScore = bestScore;
        }

        public String toString() {
            String temperature = String.format(Locale.US, "%.2f", this.temperature);
            String searcher = String.format(Locale.US, "%.2f", this.neighborScore);
            String best = String.format(Locale.US, "%.2f", this.bestScore);
            String worst = String.format(Locale.US, "%.2f", this.worstScore);

            if (this.bestScore == this.neighborScore)
                return temperature + "," + searcher + "," + best + "," + worst;
            return temperature + "," + searcher + "," + "," + worst;
        }
    }


    private void drawChart() {
        ArrayList<Integer> iters = new ArrayList<>();
        ArrayList<Double> tempers = new ArrayList<>();
        ArrayList<Double> neighbors = new ArrayList<>();
        ArrayList<Double> bests = new ArrayList<>();
        ArrayList<Double> worsts = new ArrayList<>();
        int counter = 1;
        double best = Double.MAX_VALUE;
        for (SAScore score : annealingResults) {
            iters.add(counter);
            counter++;
            neighbors.add(score.neighborScore);
            bests.add(score.bestScore);
            worsts.add(score.worstScore);
            tempers.add(score.temperature * worsts.get(0) / experimentData.startTemperature);
            if (score.bestScore < best)
                best = score.bestScore;
        }
        XYChart chart = new XYChartBuilder().width(1800).height(900)
                .title(experimentData.srcFilePath +
                        ", N = " + experimentData.neighborsAmount +
                        ", Start T = " + experimentData.startTemperature +
                        ", Stop T = " + experimentData.stopTemperature +
                        ", Cooling = " + experimentData.coolingRate)
                .build();
        chart.getStyler().getDecimalPattern();
        chart.getStyler().setYAxisDecimalPattern("#,###.##");
        chart.getStyler().setXAxisDecimalPattern("#,###.##");
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);
        chart.getStyler().setChartTitleVisible(true);
        chart.getStyler().setYAxisMin(getChartLimit());
        chart.getStyler().setMarkerSize(4);
        chart.addSeries("Best Neighbor", iters, neighbors);
        chart.addSeries("Best", iters, bests);
//        chart.addSeries("Worst", iters, worsts);
//        chart.addSeries("Temperature", iters, tempers);
        new SwingWrapper(chart).displayChart();
        System.out.println("Best exer: " + getBest(bests));

    }
}
