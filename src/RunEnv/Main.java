package RunEnv;

import ExperimentEnv.RaportCreator;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
//        ArrayList<Integer> iters = new ArrayList<>();
//        ArrayList<Double> chance = new ArrayList<>();
//        double temper = 0.1;
//        int counter = 0;
//        while (temper > 1.0 || counter < 1000) {
//            temper = temper * 0.95;
//            chance.add(1 / (1 + Math.exp(0.5 / temper)));
//            iters.add(counter);
//            counter++;
//        }
//
//        XYChart chart = new XYChartBuilder().width(1800).height(900).build();
//        chart.getStyler().getDecimalPattern();
//        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);
//        chart.getStyler().setChartTitleVisible(true);
//        chart.getStyler().setMarkerSize(1);
//        chart.addSeries("Best Neighbor", iters, chance);
//        new SwingWrapper(chart).displayChart();



        ArrayList<ExperimentParameters> parametersList = getExperimentsConfig();
//        Algorithm algorithm = new Evolutionary();
//        Algorithm algorithm = new TabuSearch();
        Algorithm algorithm = new Annealing();
        for(ExperimentParameters singleExperiment: parametersList){
            algorithm.setupNewExperiment(singleExperiment);
            algorithm.runExperimentInLoop(1);
        }
}

    private static ArrayList<ExperimentParameters> getExperimentsConfig() {
        ArrayList<ExperimentParameters> result = new ArrayList<>();

        String srcFilePath = "TSP/kroA200.tsp";
        double startTemp = 50000.0;
        double stopTemp = 1.0;
        double coolingRate = 0.99996;
        int neighbors = 5;
        result.add(new ExperimentParameters(srcFilePath, startTemp, stopTemp, coolingRate, neighbors));

//        String srcFilePath = "TSP/berlin52.tsp";
//        int neighborsAmount = 20;
//        int tabuListSize = 300;
//        int stopCounter = 2000;
//        result.add(new ExperimentParameters(srcFilePath, neighborsAmount, tabuListSize, stopCounter));

//        //test
//        int populationSize = 10;
//        int generationsAmount = 50;
//        int tournamentSize = 2;
//        double Px = 0.8;
//        double Pm = 0.02;
//        String srcFilePath = "TSP/berlin11_modified.tsp";
//        result.add(new ExperimentParameters(populationSize, generationsAmount, tournamentSize, Px, Pm, srcFilePath));
//


        //baza
//        int populationSize = 100;
//        int generationsAmount = 100;
//        int tournamentSize = 10;
//        double Px = 0.8;
//        double Pm = 0.03;
//        String srcFilePath = "TSP/nrw1379.tsp";
//        SelectionType selectionType = SelectionType.TOURNAMENT;
//        MutationType mutationType = MutationType.INV;
//        CrossoverType crossoverType = CrossoverType.PMX;
//        result.add(new ExperimentParameters( srcFilePath, populationSize, generationsAmount, tournamentSize, selectionType, Px, crossoverType, Pm,mutationType));
//
//        //roulette test
//        selectionType = SelectionType.ROULETTE;
//        result.add(new ExperimentParameters( srcFilePath, populationSize, generationsAmount, tournamentSize, selectionType, Px, crossoverType, Pm,mutationType));
//
//        //OX test
//        selectionType = SelectionType.TOURNAMENT;
//        crossoverType = CrossoverType.ORDER;
//        result.add(new ExperimentParameters( srcFilePath, populationSize, generationsAmount, tournamentSize, selectionType, Px, crossoverType, Pm,mutationType));
//
//        //inversion test
//        crossoverType = CrossoverType.PMX;
//        mutationType = MutationType.INV;
//        Pm = 0.007;
//        result.add(new ExperimentParameters( srcFilePath, populationSize, generationsAmount, tournamentSize, selectionType, Px, crossoverType, Pm,mutationType));

//        //wieksze populacje
//        populationSize = 800;
//        result.add(new ExperimentParameters( srcFilePath, populationSize, generationsAmount, tournamentSize, selectionType, Px, crossoverType, Pm,mutationType));
//
//        //wiecej pokolen
//        populationSize = 300;
//        generationsAmount = 5000;
//        result.add(new ExperimentParameters( srcFilePath, populationSize, generationsAmount, tournamentSize, selectionType, Px, crossoverType, Pm,mutationType));
//
//        //wiekszy turniej
//        generationsAmount = 2000;
//        tournamentSize = 50;
//        result.add(new ExperimentParameters( srcFilePath, populationSize, generationsAmount, tournamentSize, selectionType, Px, crossoverType, Pm,mutationType));
//
//
//        //mniejsze krzyzowanie
//        generationsAmount = 2000;
//        tournamentSize = 10;
//        Px = 0.4;
//        result.add(new ExperimentParameters( srcFilePath, populationSize, generationsAmount, tournamentSize, selectionType, Px, crossoverType, Pm,mutationType));
//
//        //wieksza mutacja
//        Px = 0.8;
//        Pm = 0.1;
//        result.add(new ExperimentParameters( srcFilePath, populationSize, generationsAmount, tournamentSize, selectionType, Px, crossoverType, Pm,mutationType));
//
//
//        populationSize = 500;
//        generationsAmount = 5000;
//        tournamentSize = 10;
//        Px = 0.8;
//        Pm = 0.007;
//        srcFilePath = "TSP/kroA200.tsp";
//        result.add(new ExperimentParameters( srcFilePath, populationSize, generationsAmount, tournamentSize, selectionType, Px, crossoverType, Pm,mutationType));
//
//        populationSize = 500;
//        generationsAmount = 5000;
//        tournamentSize = 10;
//        Px = 0.8;
//        Pm = 0.007;
//        srcFilePath = "TSP/fl417.tsp";
//        result.add(new ExperimentParameters( srcFilePath, populationSize, generationsAmount, tournamentSize, selectionType, Px, crossoverType, Pm,mutationType));
//
//        populationSize = 500;
//        generationsAmount = 5000;
//        tournamentSize = 10;
//        Px = 0.8;
//        Pm = 0.007;
//        srcFilePath = "TSP/ali535.tsp";
//        result.add(new ExperimentParameters( srcFilePath, populationSize, generationsAmount, tournamentSize, selectionType, Px, crossoverType, Pm,mutationType));
//


        return result;
    }
}
