public class ExperimentParameters {
    public int populationSize, generationsAmount, tournamentSize;
    public double Px, Pm;
    public String srcFilePath;

    public ExperimentParameters(int populationSize, int generationsAmount, int tournamentSize, double px, double pm, String src) {
        this.populationSize = populationSize;
        this.generationsAmount = generationsAmount;
        this.tournamentSize = tournamentSize;
        Px = px;
        Pm = pm;
        srcFilePath = src;
    }
}
