package Results;

import Enums.AlgorithmType;
import ExperimentEnv.Indiv;
import ExperimentEnv.Population;
import RunEnv.ExperimentParameters;

import java.util.ArrayList;

public abstract class RaportCreator implements Raportable{
    //raport is universal for all experiments - in tabu search population score has only number and best parameter (avg and worst equal 0)

    private AlgorithmType algorithmFlag;

    protected ExperimentParameters experimentData;
    protected int populationCounter;

    public RaportCreator(ExperimentParameters params, AlgorithmType type) {
        this.experimentData = params;
        this.populationCounter = 0;
        this.algorithmFlag = type;
    }

    public void updateExperimentInfo(ExperimentParameters params) {
        this.experimentData = params;
        this.populationCounter = 0;
    }

    protected double countAvg(Population population) {
        double counter = 0;
        for (Indiv i : population.getIndivs()) {
            counter += i.getFitness();
        }
        return counter / population.getIndivs().size();
    }

    protected double getChartLimit() {
        switch (experimentData.srcFilePath) {
            case "TSP/berlin11_modified.tsp":
                return 4038.0;
            case "TSP/berlin52.tsp":
                return 7542.0;
            case "TSP/kroA100.tsp":
                return 21282.0;
            case "TSP/kroA150.tsp":
                return 26524.0;
            case "TSP/kroA200.tsp":
                return 29368.0;
            case "TSP/fl417.tsp":
                return 11861.0;
            case "TSP/ali535.tsp":
                return 202339.0;
            case "TSP/gr666.tsp":
                return 294358.0;
            default:
                return 0.0;
        }
    }

    protected double getBest(ArrayList<Double> src) {
        double result = Double.MAX_VALUE;
        for (Double d : src) {
            if (d < result)
                result = d;
        }
        return result;
    }

    public void resetCounter() {
        this.populationCounter = 0;
    }
}

