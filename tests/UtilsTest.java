import ExperimentEnv.Indiv;
import ExperimentEnv.Population;
import ExperimentEnv.TSPProblem;
import ExperimentEnv.TSPProblemCreator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;


import java.util.ArrayList;
import java.util.Arrays;

public class UtilsTest {

    @Before
    public void init(){
        TSPProblemCreator creator = new TSPProblemCreator("TSP/berlin11_modified.tsp");
        TSPProblem tspProblem = creator.create();
    }

    @Test
    public void populationCreationTest() {

        ArrayList<Indiv> indivs = getSamplePopulation().getIndivs();
        System.out.println("INDIVS:");
        for (Indiv ind : indivs) {
            System.out.println(ind);
        }

        System.out.println(indivs.get(0).getFitness());

//        Assert.assertEquals(indivs.get(0).getRouteLength(), 3731);

    }

    @Test
    public void crossoverPMXTest(){
        ArrayList<Indiv> indivs = getSamplePopulation().getIndivs();
        Indiv[] result = indivs.get(0).crossoverPMX(indivs.get(1));
        System.out.println(result[0]);
        System.out.println(result[1]);
    }

    private Population getSamplePopulation() {
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(5,7,1,3,6,4,2));
        ArrayList<Integer> list2 = new ArrayList<>(Arrays.asList(4,6,2,7,3,1,5));
        ArrayList<Indiv> popInsert = new ArrayList<>();
        popInsert.add(new Indiv(list));
        popInsert.add(new Indiv(list2));
        return new Population(popInsert);
    }
}
