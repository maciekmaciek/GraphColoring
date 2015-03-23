import java.security.SecureRandom;
import java.util.ArrayList;

/**
 * Created by Maciej Wolański
 * maciekwski@gmail.com
 * on 2015-03-23.
 */
public class BruteForce {

    private ArrayList<ArrayList<Integer>> graph;
    private int nodes;
    private int edges;
    private int colors;
    private int[] result;
    private int fitness;

    public BruteForce(ArrayList<ArrayList<Integer>> graph, int nodes, int edges, int colors) {
        this.graph = graph;
        this.nodes = nodes;
        this.edges = edges;
        this.colors = colors;
        result = new int[nodes];
    }

    public double breakIt(){
        int i = 0;
        ArrayList<Double> results = new ArrayList<>();
        fitness = 100;
        for(i = 0; i< 1000000 && fitness != 0; i++){
            generateResult();
            evaluateResult(graph);

            if(i%100 == 0) {
                System.out.println("Pokolenie: " + (i + 1) + ", " + fitness);
            }
        }
        System.out.println("Pokolenie: " + (i + 1) + ", " + fitness);
        return fitness;
    }


    public void generateResult(){
        SecureRandom sr = new SecureRandom();
        for(int j = 0; j < result.length; j++)
            result[j] = sr.nextInt(colors);

        //this.colors = colors;
        //countColors();

    }


    public void evaluateResult(ArrayList<ArrayList<Integer>> graph) {
        int conflicts = 0;
        for(int i = 0; i <result.length; i++)
        {
            int current = result[i];
            ArrayList<Integer> edgesArr = graph.get(i);
            for(int j = 0; j < edgesArr.size(); j++){
                if(result[edgesArr.get(j)] == current)
                    conflicts++;
            }
        }
        fitness = conflicts/2;
    }
}
