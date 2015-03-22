import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by Maciej Wolański
 * maciekwski@gmail.com
 * on 2015-03-21.
 */
public class Chromosome {
    int[] genes;
    double fitness;
    int colors;

    public Chromosome(int[] genes, int colors) {
        this.genes = genes;
        colors = genes.length; //inicjalizuje
        this.colors = colors;//countColors();//liczy
    }

    public Chromosome(int length, int colors){
        fitness = 0;
        genes = new int[length];
        this.colors = colors;
    }

    public Chromosome(Chromosome old){
        this.fitness = old.getFitness();
        this.colors = old.colors;
        genes = new int[old.genes.length];
        genes = Arrays.copyOf(old.genes, old.genes.length);

    }
    public int[] getGenes() {
        return genes;
    }

    public void setGenes(int[] genes) {
        this.genes = genes;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public void generateGenes(){
        SecureRandom sr = new SecureRandom();
        for(int j = 0; j < genes.length; j++)
            genes[j] = sr.nextInt(colors);

        //this.colors = colors;
        //countColors();

    }
  public int countColors(){
        int[] col = new int[colors];

        for(int i = 0; i< genes.length; i++)
            col[genes[i]]++;

        int counter = 0;

        for(int c:col){
            counter = c == 0 ? counter : counter+1;
        }
        return colors = counter;
    }


    public void evaluate(ArrayList<ArrayList<Integer>> graph, int edges) {
        int conflicts = 0;
        for(int i = 0; i <genes.length; i++)
        {
            int current = genes[i];
            ArrayList<Integer> edgesArr = graph.get(i);
            for(int j = 0; j < edgesArr.size(); j++){
               if(genes[edgesArr.get(j)] == current)
                   conflicts++;
            }
        }
        fitness = conflicts/2;
    }

    public void minimizeErrors(ArrayList<ArrayList<Integer>> graph) {
        for(int i = 0; i<genes.length; i++){    //dla każdego genu zmień kolor na mniej konfliktowy
            int[] quantities = new int[colors];
            ArrayList<Integer> eg = graph.get(i);
            for(int j = 0; j < eg.size(); j++){   //przeszukiwanie krawędzi
                quantities[genes[eg.get(j)]]++;   //ile wystąpień koloru
            }

            int conflicts = quantities[genes[i]];
            if(conflicts > 1){ // jeżeli są konflikty

                boolean swap = false;
                for(int j = 0; j<quantities.length && !swap; j++) {//szukaj
                    if(quantities[j] > 0 && quantities[j] < conflicts) {
                        genes[i] = j;//zamień na któryś mniej konfliktowy
                        swap = true;
                    }
                }
            }
        }
    }
}
