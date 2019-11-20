package RunEnv;

import ExperimentEnv.Indiv;
import ExperimentEnv.Population;

import java.util.ArrayList;

public class Generation {

    public Population population;


    public double currTemperature;
    public Indiv currentIndiv, bestIndiv;
    public ArrayList<Indiv> neighbors;

    public Generation(Population population){
        this.population = population;
    }

    public Generation(double currTemperature, Indiv currentIndiv, Indiv bestIndiv, ArrayList<Indiv> neighbors) {
        this.currTemperature = currTemperature;
        this.currentIndiv = currentIndiv;
        this.bestIndiv = bestIndiv;
        this.neighbors = neighbors;
    }

    public Generation(Indiv currentIndiv, Indiv bestIndiv, ArrayList<Indiv> neighbors) {
        this.currentIndiv = currentIndiv;
        this.bestIndiv = bestIndiv;
        this.neighbors = neighbors;
    }
}
