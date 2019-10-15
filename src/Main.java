public class Main {

    public static void main(String[] args) {

        int populationSize = 500;
        int generationsAmount = 1000;
        int tournamentSize = 10;
        double Px = 0.8;
        double Pm = 0.03;
        String srcFilePath = "TSP/kroA100.tsp";

        ExperimentParameters parameters = new ExperimentParameters(populationSize, generationsAmount, tournamentSize,
                                                                    Px, Pm, srcFilePath);
        Algorithm EA = new Algorithm(parameters);
        EA.runEA();
    }
}
