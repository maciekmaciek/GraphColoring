import javax.swing.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Maciej Wolański
 * maciekwski@gmail.com
 * on 2015-03-22.
 */
public class Main {
    public static void main(String[]args)
    {
        JFileChooser jfc = new JFileChooser();
        int returnVal = jfc.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            String CURRENT_FILE_PATH = jfc.getSelectedFile().getPath();
            ArrayList<ArrayList<Integer>> graph = GraphLoader.loadGraph(CURRENT_FILE_PATH);
            if(graph!=null){
                int nodes = GraphLoader.NODES;
                int edges = GraphLoader.EDGES;
                Scanner sc = new Scanner(System.in);
                int population = sc.nextInt();
                Evolution ev = new Evolution(graph, nodes, edges, population);
                ev.evolve();
            }
        }
    }
}
