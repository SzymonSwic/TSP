package ExperimentEnv;

import java.util.ArrayList;

public class TSPProblem {

    private String name, comment;
    private static int dimensions;
    private ArrayList<City> cities;
    private static double[][] neighborhoodMatrix;

    public TSPProblem(String name, String comment, int dims, ArrayList<City> cities) {
        this.name = name;
        this.comment = comment;
        this.cities = cities;
        dimensions = dims;
        neighborhoodMatrix = makeMatrix(cities);
    }

    private double[][] makeMatrix(ArrayList<City> src) {
        double[][] matrix = new double[dimensions][dimensions];
        for (int row = 0; row < dimensions; row++) {
            for (int col = 0; col < dimensions; col++) {
                matrix[row][col] = getDistanceBetween(src.get(row), src.get(col));
            }
        }
        return matrix;
    }

    private double getDistanceBetween(City c1, City c2) {
        return Math.sqrt(Math.pow(c2.getCoordX() - c1.getCoordX(), 2) + Math.pow(c2.getCoordY() - c1.getCoordY(), 2));
    }

//    getters and setters

    public String getName() {
        return name;
    }

    public String getComment() {
        return comment;
    }

    public static int getDimensions() {
        return dimensions;
    }

    public ArrayList<City> getCities() {
        return cities;
    }

    public static double[][] getNeighborhoodMatrix() {
        return neighborhoodMatrix;
    }

    public static void displayMatrix() {
        for (int row = 0; row < dimensions; row++) {
            for (int col = 0; col < dimensions; col++) {
                System.out.printf("%8s", Math.floor(neighborhoodMatrix[row][col]) + " ");
            }
            System.out.println();
        }
    }

    public void displayTSPProblem() {
        System.out.println("Name: " + this.name);
        System.out.println("Comment: " + this.comment);
        System.out.println("Dimensions: " + dimensions);
        System.out.println("Cities: ");
        for (City c : this.cities) {
            System.out.println(c);
        }
    }
}
