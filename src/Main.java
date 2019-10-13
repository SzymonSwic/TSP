public class Main {

    public static void main(String[] args) {

        int populationSize = 200;
        int generationsAmount = 1000;
        int tournamentSize = (int) (populationSize*0.08);
        double Px = 0.7;
        double Pm = 0.01;
        String srcFilePath = "TSP/berlin52.tsp";

        ExperimentParameters parameters = new ExperimentParameters(populationSize, generationsAmount, tournamentSize,
                                                                    Px, Pm, srcFilePath);
        Algorithm EA = new Algorithm(parameters);
        EA.runEA();

    }
}
