package RunEnv;

import Enums.AlgorithmType;
import ExperimentEnv.Indiv;
import ExperimentEnv.Population;
import MyUtils.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

public class Annealing extends Algorithm {

    public Annealing() {
        super(AlgorithmType.ANNEALING);
    }

    @Override
    void runExperimentInLoop(int iters) {
        for (int i = 0; i < iters; i++) {
            run();
            System.out.println("Experiment " + i + " done.");
        }
        raport.createResultFile();
    }

    @Override
    void run() {
        ArrayList<Double> chances = new ArrayList<>();
        double currTemperature = parameters.startTemperature;
        double deltaTemp = parameters.coolingRate;
        this.currPopulation = new Population(1);
        ArrayList<Indiv> neighbors;
        Indiv bestEver = getSercher();
        int iterCounter = 0;
        //init random first searcher0

        while (currTemperature > parameters.stopTemperature) {

            neighbors = getNeighbors();
            Indiv nextSearcher = getBestNeighbor(neighbors);

            Random r = new Random();
            double random = r.nextDouble();
            double pow = (nextSearcher.getFitness() - getSercher().getFitness()) / currTemperature;
            double exp = Math.pow(2.718281828459, pow);
            double chance = 1.0 / (1.0 + exp);
            chances.add(chance);

            if (getSercher().getFitness() > nextSearcher.getFitness()) {
                this.currPopulation.getIndivs().set(0, nextSearcher);
            } else if (random < chance) {
                this.currPopulation.getIndivs().set(0, nextSearcher);
            }

            if (bestEver.getFitness() > nextSearcher.getFitness()) {
                bestEver = nextSearcher.getCopy();
            }

            raport.loadToBuffer(new Generation(currTemperature, getSercher(), bestEver, neighbors));

            iterCounter++;
            currTemperature = currTemperature * deltaTemp;
        }
        File file = new File("results/CHANCES.csv");
        try (PrintWriter pw = new PrintWriter(file)) {
            for (double d : chances) {
                pw.println(d);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        raport.resetCounter();
    }

    private Indiv getBestNeighbor(ArrayList<Indiv> candidates) {
        Indiv best = candidates.get(0);
        for (int i = 1; i < candidates.size(); i++) {
            if (candidates.get(i).getFitness() < best.getFitness())
                best = candidates.get(i);
        }
        return best;
    }

    private ArrayList<Indiv> getNeighbors() {
        ArrayList<Indiv> result = new ArrayList<>();
        int emergencyCounter = 0;
        while (result.size() < parameters.neighborsAmount && emergencyCounter < parameters.neighborsAmount * 3) {
            result.add(getNeighbor(getSercher()));
            emergencyCounter++;
        }
        return result;
    }

    private Indiv getNeighbor(Indiv ind) {
        Indiv neighbor = ind.getCopy();
        neighbor.mutationSwap(Utils.getRandomInt(0, neighbor.getRoute().size()));
        return neighbor;
    }

    private Indiv getSercher() {
        return this.currPopulation.getIndivs().get(0);
    }
}
