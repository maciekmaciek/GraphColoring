import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by Maciej Wolański
 * maciekwski@gmail.com
 * on 2015-03-22.
 */
public class Main {
    public static void main(String[]args)
    {
        /*JFileChooser jfc = new JFileChooser();
        int returnVal = jfc.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
        */
            String CURRENT_FILE_PATH = args[0];//jfc.getSelectedFile().getPath();
            ArrayList<ArrayList<Integer>> graph = GraphLoader.loadGraph(CURRENT_FILE_PATH);
            Chromosome[] allTimeWinners;

            if(graph!=null){
                int nodes = GraphLoader.NODES;
                int edges = GraphLoader.EDGES;
                //Scanner sc = new Scanner(System.in);
                int population = 20;//sc.nextInt();

                int colors = 5;//calcNumberOfColors(nodes, edges, graph);
                allTimeWinners = new Chromosome[colors];
                boolean isFound = true;

                for(int i = colors-1; i > 0 && isFound; i--) {
                    Evolution ev = new Evolution(graph, nodes, edges, population, i+1);
                    allTimeWinners[i] = ev.evolve();
                    if(allTimeWinners[i].fitness < 1)
                        isFound = false;
                }
            }
        }
   // }

    static int calcNumberOfColors(int nodes, int edges, ArrayList<ArrayList<Integer>> graph) {  //
        int maxN = 0;
        for(int i = 0; i <nodes; i++){
            maxN = graph.get(i).size() > maxN ? graph.get(i).size() : maxN;
        }
        return Math.min(edges/nodes + maxN, nodes);
    }
}
