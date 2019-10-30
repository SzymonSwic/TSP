package RunEnv;

import Enums.CrossoverType;
import Enums.MutationType;
import Enums.SelectionType;

public class ExperimentParameters {
    public int populationSize, generationsAmount, tournamentSize;
    public double Px, Pm;
    public String srcFilePath;
    public MutationType mutationType;
    public CrossoverType crossoverType;
    public SelectionType selectionType;

    public int neighborsAmount, tabuListSize, stopCondition;
    public double startTemperature, stopTemperature, coolingRate;

    public ExperimentParameters(String src, double startTemperature, double stopTemperature, double coolingRate, int neighborsAmount) {
        this.srcFilePath = src;
        this.startTemperature = startTemperature;
        this.stopTemperature = stopTemperature;
        this.coolingRate = coolingRate;
        this.neighborsAmount = neighborsAmount;
        this.generationsAmount = (int)((1-stopTemperature)/coolingRate);
    }

    ExperimentParameters(String src, int neighborsAmount, int tabuListSize, int stopCondition) {
        this.srcFilePath = src;
        this.neighborsAmount = neighborsAmount;
        this.tabuListSize = tabuListSize;
        this.stopCondition = stopCondition;
        this.generationsAmount = stopCondition;
    }

    public ExperimentParameters(String src, int populationSize, int generationsAmount, int tournamentSize, SelectionType selectionType, double px, CrossoverType crossoverType, double pm, MutationType mutationType) {
        this.populationSize = populationSize;
        this.generationsAmount = generationsAmount;
        this.tournamentSize = tournamentSize;
        Px = px;
        Pm = pm;
        this.selectionType = selectionType;
        this.mutationType = mutationType;
        this.crossoverType = crossoverType;
        srcFilePath = src;
    }
}
