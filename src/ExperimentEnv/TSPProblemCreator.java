package ExperimentEnv;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TSPProblemCreator {

    private TSPProblemData TSPData;
    private String srcFilePath;

    public TSPProblemCreator(String path) {
        this.TSPData = new TSPProblemData();
        this.srcFilePath = path;
    }

    public TSPProblem create() {
        parseTSPFile();
        return initTSPProblem();
    }

    private void parseTSPFile() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(srcFilePath));
            String line = reader.readLine();
            while (!line.equals("EOF")) {
                proccessLine(line);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private TSPProblem initTSPProblem() {
        return new TSPProblem(TSPData.name, TSPData.comment, TSPData.dimensions, TSPData.cities);
    }

    private void proccessLine(String line) {
        switch (line.substring(0, 4)) {
            case "NAME":
                this.TSPData.name = line.substring(6);
                break;
            case "COMM":
                this.TSPData.comment = line.substring(9);
                break;
            case "DIME":
                this.TSPData.dimensions = Integer.parseInt(line.substring(11));
                break;
        }
        if (Character.isDigit(line.charAt(0))) {
            List<String> data = Arrays.asList(line.split(" "));
            TSPData.cities.add(new City(Integer.parseInt(data.get(0)),
                    Double.parseDouble(data.get(1)),
                    Double.parseDouble(data.get(2)))
            );
        }
    }

    private class TSPProblemData {
        public String name, comment;
        int dimensions;
        ArrayList<City> cities = new ArrayList<>();
    }

}
