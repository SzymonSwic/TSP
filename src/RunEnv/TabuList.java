package RunEnv;

import ExperimentEnv.Indiv;

import java.util.LinkedList;
import java.util.Queue;

public class TabuList {
    private Queue<Indiv> tabu;
    private int size;

    public TabuList(int size){
        this.tabu = new LinkedList<>();
        this.size = size;
    }

    public void add(Indiv newInd){
        this.tabu.add(newInd);
        if(this.tabu.size() > this.size)
            this.tabu.remove();
    }

    public Queue<Indiv> getTabu() {
        return tabu;
    }

    public String toString(){
        StringBuilder result = new StringBuilder();
        for(Indiv ind: tabu)
            result.append(ind.toString()).append("; ");
        return result.toString();
    }
}
