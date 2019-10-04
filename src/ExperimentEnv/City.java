package ExperimentEnv;

public class City {
    private int number;
    private double coordX, coordY;

    public City(int number, double coordX, double coordY) {
        this.number = number;
        this.coordX = coordX;
        this.coordY = coordY;
    }

    public int getNumber() {
        return number;
    }

    public double getCoordX() {
        return coordX;
    }

    public double getCoordY() {
        return coordY;
    }

    public String toString() {
        return this.number + " " + this.coordX + " " + this.coordY;
    }
}
