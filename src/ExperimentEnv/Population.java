package ExperimentEnv;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Population {
    ArrayList<Indiv> indivs;

    public Population(int size) {
        initRandomGeneration(size);
    }

    public Population(ArrayList<Indiv> indivs) {
        this.indivs = indivs;
    }

    private void initRandomGeneration(int size) {
        this.indivs = new ArrayList<>();
        ArrayList<Integer> newRoute = IntStream.rangeClosed(0, TSPProblem.getDimensions()-1)
                                .boxed().collect(Collectors.toCollection(ArrayList::new));
        for (int i = 0; i < size; i++) {
            Collections.shuffle(newRoute);
            indivs.add(new Indiv(newRoute));
        }
    }

    public ArrayList<Indiv> selecion() {
        //TODO
        return indivs;
    }

    public ArrayList<Indiv> getIndivs() {
        return indivs;
    }
}
