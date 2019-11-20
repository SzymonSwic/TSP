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


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class UtilsTest {

    @Before
    public void init() {
        TSPProblemCreator creator = new TSPProblemCreator("TSP/nrw1379.tsp");
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
    public void fitnessTest() {
        Indiv ind = new Indiv(new ArrayList<>(Arrays.asList(386, 374, 396, 416, 415, 414, 385, 373, 384, 413, 412, 411, 372, 371, 395, 394, 410, 409, 393, 383, 369, 368, 408, 370, 375, 397, 376, 398, 377, 378, 249, 273, 251, 275, 252, 276, 254, 277, 253, 255, 279, 257, 281, 258, 282, 260, 283, 259, 261, 285, 263, 287, 264, 286, 262, 284, 280, 256, 278, 274, 250, 288, 266, 289, 265, 267, 291, 268, 292, 270, 293, 269, 271, 295, 272, 296, 294, 290, 100, 98, 97, 99, 91, 80, 70, 60, 74, 79, 85, 78, 84, 73, 66, 69, 62, 65, 87, 83, 90, 94, 82, 77, 81, 86, 76, 72, 89, 93, 88, 92, 68, 64, 61, 95, 96, 63, 67, 75, 58, 57, 55, 52, 41, 44, 46, 43, 38, 26, 16, 11, 3, 1, 2, 8, 10, 14, 13, 29, 28, 22, 12, 19, 27, 20, 15, 17, 18, 9, 7, 0, 23, 25, 30, 31, 24, 21, 6, 4, 5, 348, 308, 309, 323, 333, 349, 350, 334, 310, 311, 335, 312, 351, 352, 353, 324, 313, 325, 354, 355, 356, 336, 326, 314, 315, 337, 316, 338, 317, 318, 153, 177, 155, 179, 157, 181, 159, 183, 161, 185, 163, 187, 165, 189, 167, 191, 169, 193, 171, 195, 173, 197, 175, 199, 176, 200, 198, 174, 196, 172, 194, 170, 192, 168, 190, 166, 188, 164, 186, 162, 184, 160, 182, 158, 180, 156, 178, 154, 33, 35, 37, 40, 42, 47, 49, 45, 48, 50, 51, 54, 53, 56, 101, 103, 104, 102, 71, 59, 39, 36, 34, 32, 347, 330, 322, 303, 304, 331, 305, 332, 306, 307, 105, 129, 107, 131, 109, 133, 111, 135, 112, 134, 110, 132, 108, 130, 106, 136, 114, 138, 116, 140, 118, 139, 115, 137, 113, 117, 141, 119, 143, 121, 145, 123, 147, 125, 149, 127, 151, 128, 150, 126, 148, 124, 146, 122, 144, 120, 142, 152, 346, 345, 321, 302, 320, 344, 343, 342, 301, 300, 329, 328, 341, 340, 327, 319, 298, 297, 339, 299, 359, 388, 401, 400, 387, 379, 358, 357, 399, 389, 360, 361, 402, 403, 404, 380, 362, 381, 405, 406, 407, 390, 382, 363, 364, 391, 365, 392, 366, 367, 201, 225, 203, 227, 205, 229, 206, 230, 208, 231, 207, 209, 233, 210, 234, 212, 235, 211, 213, 237, 215, 239, 216, 238, 214, 236, 232, 228, 204, 226, 202, 240, 218, 241, 217, 219, 243, 221, 245, 223, 247, 224, 246, 222, 244, 220, 242, 248)));
        Assert.assertEquals(ind.getFitness(), 15632, 5);
    }

    @Test
    public void crossoverPMXTest() {
        ArrayList<Indiv> indivs = getSamplePopulation().getIndivs();
        Indiv[] result = indivs.get(0).crossoverPMX(indivs.get(1));
        System.out.println(result[0]);
        System.out.println(result[1]);
    }

    private Population getSamplePopulation() {
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(3, 4, 8, 2, 7, 1, 6, 5));
        ArrayList<Integer> list2 = new ArrayList<>(Arrays.asList(4, 2, 5, 1, 6, 8, 3, 7));
        ArrayList<Indiv> popInsert = new ArrayList<>();
        popInsert.add(new Indiv(list));
        popInsert.add(new Indiv(list2));
        return new Population(popInsert);
    }

    @Test
    public void inversionTest() {
        Population sample = getSamplePopulation();
        System.out.println(sample.getIndivs().get(0).toString());
        sample.tryMutation(0, 1.0, MutationType.INV);
        System.out.println(sample.getIndivs().get(0).toString());
    }

    @Test
    public void orderCrossTest() {
        Population sample = getSamplePopulation();
        sample.tryCrossover(sample, 0, 1, 1.0, CrossoverType.ORDER);
    }

    @Test
    public void tabuListInsertTest() {
        TabuList hood = new TabuList(3);
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        ArrayList<Integer> list2 = new ArrayList<>(Arrays.asList(2, 3, 4, 5, 1));
        ArrayList<Integer> list3 = new ArrayList<>(Arrays.asList(3, 4, 5, 1, 2));
        ArrayList<Integer> list4 = new ArrayList<>(Arrays.asList(4, 5, 1, 2, 3));
        ArrayList<Integer> list5 = new ArrayList<>(Arrays.asList(5, 1, 2, 3, 4));
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

    @Test
    public void checkIndiv() {
        Indiv sample = getIndivFromFile();
        Assert.assertEquals(5600, sample.getFitness(), 0.5);
    }

    @Test
    public void checkMatrix(){
        System.out.println(TSPProblem.getName());
//        TSPProblem.displayMatrix();
    }

    private Indiv getIndivFromFile() {
        ArrayList<Integer> cities = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("D:\\programy\\metaheurystyki\\tests\\nrw.txt"));
            String line = reader.readLine();
            while (!line.equals("EOF")) {
                cities.add(Integer.parseInt(line));
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Indiv(cities);
    }
}
