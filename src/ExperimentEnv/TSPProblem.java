package ExperimentEnv;

import java.util.ArrayList;

public class TSPProblem {

    private String name, comment;
    private int dimensions;
    private ArrayList<City> cities;
    private static double[][] neighborhoodMatrix;

    public TSPProblem(String name, String comment, int dimensions, ArrayList<City> cities) {
        this.name = name;
        this.comment = comment;
        this.dimensions = dimensions;
        this.cities = cities;
        neighborhoodMatrix = makeMatrix(cities);
    }


    //    making matrix
    private double[][] makeMatrix(ArrayList<City> src) {
        double[][] matrix = new double[this.dimensions][this.dimensions];
        for (int row = 0; row < this.dimensions; row++) {
            for (int col = 0; col < this.dimensions; col++) {
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

    public int getDimensions() {
        return dimensions;
    }

    public ArrayList<City> getCities() {
        return cities;
    }

    public static double[][] getNeighborhoodMatrix() {
        return neighborhoodMatrix;
    }

    public void displayMatrix() {
        for (int row = 0; row < this.dimensions; row++) {
            for (int col = 0; col < this.dimensions; col++) {
                System.out.print(neighborhoodMatrix[row][col] + " | ");
            }
            System.out.println();
        }
    }

    public void displayTSPProblem() {
        System.out.println("Name: " + this.name);
        System.out.println("Comment: " + this.comment);
        System.out.println("Dimensions: " + this.dimensions);
        System.out.println("Cities: ");
        for (City c : this.cities) {
            System.out.println(c);
        }

    }
}
