package ExperimentEnv;

import Enums.AlgorithmType;
import RunEnv.ExperimentParameters;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;

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
    private ArrayList<SAScore> SAResults;
    private ExperimentParameters experimentData;
    private int populationCounter;

    public RaportCreator(AlgorithmType type) {
        this.algorithmFlag = type;
        switch (algorithmFlag) {
            case EVOLUTION:
                this.evolutionResults = new ArrayList<>();
                break;
            case TABU:
                this.TSResults = new ArrayList<>();
                break;
            case ANNEALING:
                this.SAResults = new ArrayList<>();
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

    //  EA

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
        drawChartEA();
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

    private void clearEvolutionBuffer() {
        this.evolutionResults.clear();
        this.populationCounter = 0;
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


    //TABU SEARCH


    public void loadTSPopulationToBuffer(Indiv searcher, Indiv best, ArrayList<Indiv> neighbors) {
        this.populationCounter++;
        TSResults.add(getTSScores(searcher, best, neighbors));
//        if (this.populationCounter > experimentData.generationsAmount) {
//            this.populationCounter -= experimentData.generationsAmount;
//            TSResults.set(populationCounter, updateTSScore(TSResults.get(populationCounter), getTSScores(searcher, neighbors)));
//        } else {
//
//        }
    }

    private TSScore getTSScores(Indiv searcher, Indiv best, ArrayList<Indiv> neighbors) {
        Indiv worst = neighbors.get(0);
        for (Indiv ind : neighbors) {
            if (ind.compareTo(worst) < 0)
                worst = ind;
        }
//        System.out.println(searcher.toString());
        return new TSScore(this.populationCounter, searcher.getFitness(), best.getFitness(), worst.getFitness());
    }

    private TSScore updateTSScore(TSScore oldScore, TSScore newScore) {
        oldScore.neighborScore = (oldScore.neighborScore + newScore.neighborScore) / 2;
        oldScore.worstScore = (oldScore.worstScore + newScore.worstScore) / 2;
        return oldScore;
    }

    public void createTSResultFile() {
        File file = new File("results/" + getTSRaportName() + ".csv");
        try (PrintWriter pw = new PrintWriter(file)) {
            pw.println("nr, bestNeighbor, best, worst");
            this.TSResults
                    .stream()
                    .map(TSScore::toString)
                    .forEach(pw::println);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        drawChartTS();
        clearTSBuffer();
    }

    private String getTSRaportName() {
        return experimentData.srcFilePath.substring(4, experimentData.srcFilePath.indexOf(".")) +
                " neighbors- " + experimentData.neighborsAmount +
                " tabuSize- " + experimentData.tabuListSize +
                " stopCondition- " + experimentData.stopCondition;
    }

    private void clearTSBuffer() {
        this.TSResults.clear();
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

    //      SA

    public void loadSAPopulationToBuffer(double temp, Indiv searcher, Indiv best, ArrayList<Indiv> neighbors) {
        this.populationCounter++;
        SAResults.add(getSAScores(temp, searcher, best, neighbors));
    }

    private SAScore getSAScores(double temp, Indiv searcher, Indiv best, ArrayList<Indiv> neighbors) {
        Indiv worst = neighbors.get(0);
        for (Indiv ind : neighbors) {
            if (ind.compareTo(worst) < 0)
                worst = ind;
        }
//        System.out.println(searcher.toString());
        return new SAScore(temp, searcher.getFitness(), best.getFitness(), worst.getFitness());
    }

    public void createSAResultFile() {
        File file = new File("results/" + getSARaportName() + ".csv");
        try (PrintWriter pw = new PrintWriter(file)) {
            pw.println("nr, bestNeighbor, best, worst");
            this.SAResults
                    .stream()
                    .map(SAScore::toString)
                    .forEach(pw::println);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        drawChartSA();
        clearSABuffer();
    }

    private String getSARaportName() {
        return experimentData.srcFilePath.substring(4, experimentData.srcFilePath.indexOf(".")) +
                " temp start - " + experimentData.startTemperature +
                " temp stop - " + experimentData.stopTemperature +
                " coolingRate - " + experimentData.coolingRate;
    }

    private void clearSABuffer() {
        this.SAResults.clear();
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

    private double countAvg(Population population) {
        double counter = 0;
        for (Indiv i : population.getIndivs()) {
            counter += i.getFitness();
        }
        return counter / population.getIndivs().size();
    }


    private void drawChartSA(){
        ArrayList<Integer> iters = new ArrayList<>();
        ArrayList<Double> neighbors = new ArrayList<>();
        ArrayList<Double> bests = new ArrayList<>();
        ArrayList<Double> worsts = new ArrayList<>();
        int counter = 1;
        for(SAScore score: SAResults){
            iters.add(counter);
            counter++;
            neighbors.add(score.neighborScore);
            bests.add(score.bestScore);
            worsts.add(score.worstScore);
        }
        XYChart chart = new XYChartBuilder().width(1800).height(900).build();
        chart.getStyler().getDecimalPattern();
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);
        chart.getStyler().setChartTitleVisible(false);
        chart.getStyler().setMarkerSize(3);
        chart.addSeries("Best Neighbor", iters, neighbors);
        chart.addSeries("Best", iters, bests);
        chart.addSeries("Worst", iters, worsts);
        new SwingWrapper(chart).displayChart();
    }

    private void drawChartTS(){
        ArrayList<Integer> iters = new ArrayList<>();
        ArrayList<Double> neighbors = new ArrayList<>();
        ArrayList<Double> bests = new ArrayList<>();
        ArrayList<Double> worsts = new ArrayList<>();
        for(TSScore score: TSResults){
            iters.add(score.popNumber);
            neighbors.add(score.neighborScore);
            bests.add(score.bestScore);
            worsts.add(score.worstScore);
        }
        XYChart chart = new XYChartBuilder().width(1800).height(900).build();
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);
        chart.getStyler().setChartTitleVisible(false);
        chart.getStyler().setMarkerSize(3);
        chart.addSeries("Best Neighbor", iters, neighbors);
        chart.addSeries("Best", iters, bests);
        chart.addSeries("Worst", iters, worsts);
        new SwingWrapper(chart).displayChart();
    }

    private void drawChartEA(){
        ArrayList<Integer> iters = new ArrayList<>();
        ArrayList<Double> bests = new ArrayList<>();
        ArrayList<Double> avgs = new ArrayList<>();
        ArrayList<Double> worsts = new ArrayList<>();
        for(EvolutionScore score: evolutionResults){
            iters.add(score.popNumber);
            bests.add(score.bestScore);
            avgs.add(score.avgScore);
            worsts.add(score.worstScore);
        }
        XYChart chart = new XYChartBuilder().width(1800).height(900).build();
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);
        chart.getStyler().setChartTitleVisible(false);
        chart.getStyler().setMarkerSize(3);
        chart.addSeries("Best", iters, bests);
        chart.addSeries("Avg", iters, avgs);
        chart.addSeries("Worst", iters, worsts);
        new SwingWrapper(chart).displayChart();
    }
}

