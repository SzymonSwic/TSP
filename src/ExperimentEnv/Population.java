package ExperimentEnv;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Population {
    ArrayList<Indiv> generation;

    public Population(int size) {
        initRandomGeneration(size);
    }

    public Population(ArrayList<Indiv> generation) {
        this.generation = generation;
    }

    private void initRandomGeneration(int size) {
        this.generation = new ArrayList<>();
        ArrayList<Integer> newRoute = IntStream.rangeClosed(0, TSPProblem.getDimensions()-1)
                                .boxed().collect(Collectors.toCollection(ArrayList::new));
        for (int i = 0; i < size; i++) {
            Collections.shuffle(newRoute);
            generation.add(new Indiv(newRoute));
            System.out.println(generation.get(i).toString());
        }
    }

    public ArrayList<Indiv> selecion() {
        //TODO
        return generation;
    }
}
