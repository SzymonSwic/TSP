package RunEnv;

public class Measure{
    public double avg, deviation, best;

    public Measure(double avg, double deviation, double best) {
        this.avg = avg;
        this.deviation = deviation;
        this.best = best;
    }

    public String toString(){
        return this.avg+"   "+this.deviation+"  "+this.best;
    }

}
