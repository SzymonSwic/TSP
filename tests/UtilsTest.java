import ExperimentEnv.Indiv;
import ExperimentEnv.Population;
import ExperimentEnv.TSPProblem;
import ExperimentEnv.TSPProblemCreator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;


import java.util.ArrayList;
import java.util.Arrays;

public class UtilsTest {

    @Test
    public void populationCreationTest() {
        TSPProblemCreator creator = new TSPProblemCreator("TSP/berlin11_modified.tsp");
        TSPProblem tspProblem = creator.create();

        ArrayList<Indiv> indivs = getSamplePopulation().getIndivs();
        System.out.println("INDIVS:");
        for (Indiv ind : indivs) {
            System.out.println(ind);
        }

        System.out.println(indivs.get(0).getRouteLength());

        Assert.assertEquals(indivs.get(0).getRouteLength(), 3731);

    }

    private Population getSamplePopulation() {
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(4,10,5,3,8,9,7,2,0,1,6));
        ArrayList<Indiv> popInsert = new ArrayList<>();
        popInsert.add(new Indiv(list));
        return new Population(popInsert);
    }
}
