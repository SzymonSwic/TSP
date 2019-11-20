package RunEnv;

import Enums.AlgorithmType;
import ExperimentEnv.Indiv;
import ExperimentEnv.Population;
import Results.RaportCreator;
import ExperimentEnv.TSPProblem;
import ExperimentEnv.TSPProblemCreator;
import Results.RaportEA;
import Results.RaportSA;
import Results.RaportTS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

abstract class Algorithm {
    protected Population currPopulation;

    protected ExperimentParameters parameters;

    protected RaportCreator raport;

    public Algorithm(AlgorithmType type) {
        switch (type) {
            case EVOLUTION:
                this.raport = new RaportEA(parameters);
                break;
            case TABU:
                this.raport = new RaportTS(parameters);
                break;
            case ANNEALING:
                this.raport = new RaportSA(parameters);
        }
    }

    public void setupNewExperiment(ExperimentParameters parameters){
        this.parameters = parameters;
        this.raport.updateExperimentInfo(parameters);
        setupExperiment();
    }

    abstract void runExperimentInLoop(int iters);

    abstract void run();

    private void setupExperiment() {
        TSPProblemCreator creator = new TSPProblemCreator(parameters.srcFilePath);
        TSPProblem tspProblem = creator.create();
    }

    public Measure getGreedyResults(int iterations){
        Population greedyPop = getGreedyPopulation(iterations);
        double avgCtr = 0.0;
        double best = Double.MAX_VALUE;
        double deviation = 0.0;
        for(Indiv greedyIndiv: greedyPop.getIndivs()){
            if(greedyIndiv.getFitness() < best){
                best = greedyIndiv.getFitness();
            }
            avgCtr += greedyIndiv.getFitness();
        }
        return new Measure(avgCtr/greedyPop.getIndivs().size(), deviation, best);
    }

    public Measure getRandomResults(int iterations){
        Population greedyPop = getRandomPopulation(iterations);
        double avgCtr = 0.0;
        double best = Double.MAX_VALUE;
        double deviation = 0.0;
        for(Indiv greedyIndiv: greedyPop.getIndivs()){
            if(greedyIndiv.getFitness() < best){
                best = greedyIndiv.getFitness();
            }
            avgCtr += greedyIndiv.getFitness();
        }
        return new Measure(avgCtr/greedyPop.getIndivs().size(), deviation, best);
    }

    private Population getRandomPopulation(int iterations) {
        return new Population(iterations);
    }

    private Population getGreedyPopulation(int iterations) {
        ArrayList<Indiv> greedyIndivs = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < iterations; i++) {
            greedyIndivs.add(getGreedyIndividual(r.nextInt(TSPProblem.getDimensions())));
        }
        return new Population(greedyIndivs);
    }

    private Indiv getGreedyIndividual(int startCity) {
        ArrayList<Integer> result = new ArrayList<>();
        ArrayList<CityDistance> currCityNearestsList;
        result.add(startCity);
        for (int i = 0; i < TSPProblem.getDimensions() - 1; i++) {
            currCityNearestsList = getNearestCitiesList(result.get(result.size() - 1));
            int tmpItr = 0;
            boolean added = false;
            while (!added) {
                if (result.contains(currCityNearestsList.get(tmpItr).number)) {
                    tmpItr++;
                } else {
                    result.add(currCityNearestsList.get(tmpItr).number);
                    added = true;
                }
            }
        }
        return new Indiv(result);
    }

    private ArrayList<CityDistance> getNearestCitiesList(int cityNum) {
        ArrayList<CityDistance> originalDistances = new ArrayList<>();

        for (int i = 0; i < TSPProblem.getDimensions(); i++) {
            originalDistances.add(new CityDistance(i, TSPProblem.getNeighborhoodMatrix()[cityNum][i]));
        }

        Collections.sort(originalDistances);
        return originalDistances;
    }

    private class CityDistance implements Comparable<CityDistance> {
        public int number;
        public double distance;

        public CityDistance(int number, double distance) {
            this.number = number;
            this.distance = distance;
        }

        @Override
        public int compareTo(CityDistance o) {
            if (this.distance > o.distance)
                return 1;
            else if (this.distance < o.distance)
                return -1;
            return 0;
        }
    }
}
