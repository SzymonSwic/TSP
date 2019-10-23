import ExperimentEnv.Indiv;
import ExperimentEnv.Population;
import ExperimentEnv.TSPProblem;
import ExperimentEnv.TSPProblemCreator;
import Enums.CrossoverType;
import Enums.MutationType;
import RunEnv.TabuList;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


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
    public void fitnessTest(){
        Indiv ind = new Indiv(new ArrayList<>(Arrays.asList(1,6,2,7,8,9,10,3,5,0,4)));
        Assert.assertEquals(ind.getFitness(), 4605, 5);
    }

    @Test
    public void crossoverPMXTest(){
        ArrayList<Indiv> indivs = getSamplePopulation().getIndivs();
        Indiv[] result = indivs.get(0).crossoverPMX(indivs.get(1));
        System.out.println(result[0]);
        System.out.println(result[1]);
    }

    private Population getSamplePopulation() {
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(3,4,8,2,7,1,6,5));
        ArrayList<Integer> list2 = new ArrayList<>(Arrays.asList(4,2,5,1,6,8,3,7));
        ArrayList<Indiv> popInsert = new ArrayList<>();
        popInsert.add(new Indiv(list));
        popInsert.add(new Indiv(list2));
        return new Population(popInsert);
    }

    @Test
    public void inversionTest(){
        Population sample = getSamplePopulation();
        System.out.println(sample.getIndivs().get(0).toString());
        sample.tryMutation(0, 1.0, MutationType.INV);
        System.out.println(sample.getIndivs().get(0).toString());
    }

    @Test
    public void orderCrossTest(){
        Population sample = getSamplePopulation();
        sample.tryCrossover(sample, 0, 1, 1.0, CrossoverType.ORDER);
    }

    @Test
    public void tabuListInsertTest(){
        TabuList hood = new TabuList(3);
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(1,2,3,4,5));
        ArrayList<Integer> list2 = new ArrayList<>(Arrays.asList(2,3,4,5,1));
        ArrayList<Integer> list3 = new ArrayList<>(Arrays.asList(3,4,5,1,2));
        ArrayList<Integer> list4 = new ArrayList<>(Arrays.asList(4,5,1,2,3));
        ArrayList<Integer> list5 = new ArrayList<>(Arrays.asList(5,1,2,3,4));
        hood.add(new Indiv(list));
        System.out.println(hood.toString());
        hood.add(new Indiv(list2));
        System.out.println(hood.toString());
        hood.add(new Indiv(list3));
        System.out.println(hood.toString());
        hood.add(new Indiv(list4));
        System.out.println(hood.toString());
        hood.add(new Indiv(list5));
        System.out.println(hood.toString());
    }
}
