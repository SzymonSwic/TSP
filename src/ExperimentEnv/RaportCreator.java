package ExperimentEnv;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Locale;

public class RaportCreator {

    ArrayList<SinglePopulationScore> experimentResult;
    int populationCounter;
    private static int raportCounter = 0;

    public RaportCreator() {
        this.experimentResult = new ArrayList<>();
        this.populationCounter = 0;
    }

    public void loadPopulationToBuffer(Population population) {
        this.populationCounter++;
        experimentResult.add(getScores(population));
    }

    private SinglePopulationScore getScores(Population population) {
//        for (Indiv i : population.getIndivs())
//            System.out.println(i.toString());
//        System.out.println("--------------------------------------------");

        Indiv best = population.indivs.get(0);
        Indiv worst = best;

        for (Indiv indiv : population.indivs) {
            if (indiv.compareTo(best) > 0)
                best = indiv;
            else if (indiv.compareTo(worst) < 0)
                worst = indiv;
        }
        double avg = countAvg(population);

        return new SinglePopulationScore(this.populationCounter, best.getFitness(), avg, worst.getFitness());
    }

    private double countAvg(Population population) {
        double counter = 0;
        for (Indiv i : population.getIndivs()) {
            counter += i.getFitness();
        }
        return counter / population.getIndivs().size();
    }

    public void showResults() {
        System.out.println("Nr worst   -  best   -  average");
        for (SinglePopulationScore score : experimentResult) {
            System.out.println(score);
        }
    }

    public void createResultFile() {
        raportCounter++;
        File file = new File("results/raport" + raportCounter + ".csv");
        try (PrintWriter pw = new PrintWriter(file)) {
            pw.println("nr, best, avg, worst");
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

        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append(this.popNumber).append(",");
            builder.append(String.format(Locale.US,"%.2f",this.worstScore)).append(",");
            builder.append(String.format(Locale.US,"%.2f",this.bestScore)).append(",");
            builder.append(String.format(Locale.US,"%.2f",this.avgScore));
            return builder.toString();
        }
    }
}
