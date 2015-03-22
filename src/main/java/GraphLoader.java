import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Maciej Wolański
 * maciekwski@gmail.com
 * on 2015-03-22.
 */
public class GraphLoader {
    private GraphLoader(){}

    static int NODES;
    static int EDGES;

    public static ArrayList<ArrayList<Integer>> loadGraph(String path){
        ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line ="";
            String[] tokens;

            while((line = br.readLine()) != null){
                if(line.startsWith("p")){
                    tokens = line.split(" ");
                    NODES = Integer.parseInt(tokens[2]);
                    EDGES = Integer.parseInt(tokens[3]);

                    for(int i = 0; i < NODES; i++){
                        result.add(new ArrayList<>());
                    }
                } else if(line.startsWith("e")){
                    tokens = line.split(" ");
                    int node = Integer.parseInt(tokens[1]);
                    int edge = Integer.parseInt(tokens[2]);
                    result.get(node).add(edge);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
