package ExperimentEnv;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class RaportCreator {

    ArrayList<SinglePopulationScore> experimentResult;
    int populationCounter;

    public RaportCreator() {
        this.experimentResult = new ArrayList<>();
        this.populationCounter = 0;
    }

    public void loadPopulationToBuffer(Population population) {
        this.populationCounter++;
        experimentResult.add(getScores(population));
    }

    private SinglePopulationScore getScores(Population population) {
        Indiv best = population.indivs.get(0);
        Indiv worst = best;

        for (Indiv indiv : population.indivs) {
            if (indiv.compareTo(best) > 0)
                best = indiv;
            else if (indiv.compareTo(worst) < 0)
                worst = indiv;
        }
        double avg = (best.getRouteLength()+worst.getRouteLength())/2;

        return new SinglePopulationScore(this.populationCounter, worst.getRouteLength(), best.getRouteLength(), avg);
    }

    public void showResults(){
        System.out.println("Nr worst   -  best   -  average");
        for( SinglePopulationScore score: experimentResult){
            System.out.println(score);
        }
    }

    public void createResultFile(){
        File file = new File("results/raport.csv");
        try (PrintWriter pw = new PrintWriter(file)){
            this.experimentResult
                    .stream()
                    .map(SinglePopulationScore::toString)
                    .forEach(pw::println);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private class SinglePopulationScore {
        int popNumber;
        double worstScore, bestScore, avgScore;

        public SinglePopulationScore(int popNumber, double worstScore, double bestScore, double avgScore) {
            this.popNumber = popNumber;
            this.worstScore = worstScore;
            this.bestScore = bestScore;
            this.avgScore = avgScore;
        }

        public String toString(){
            StringBuilder builder = new StringBuilder();
            builder.append(this.popNumber).append(",");
            builder.append(this.worstScore).append(",");
            builder.append(this.bestScore).append(",");
            builder.append(this.avgScore);
            return builder.toString();
        }
    }
}
