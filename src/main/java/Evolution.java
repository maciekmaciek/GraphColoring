import com.google.common.collect.Multimap;

import java.security.SecureRandom;
import java.util.ArrayList;

/**
 * Created by Maciej Wolański
 * maciekwski@gmail.com
 * on 2015-03-21.
 */
public class Evolution {
    private Chromosome[] population;
    private ArrayList<ArrayList<Integer>> graph;
    private ArrayList<Chromosome> winners;
    private int nodes;
    private int edges;
    private int colors;

    public Evolution(ArrayList<ArrayList<Integer>> graph, int nodes, int edges, int population) {
        this.graph = graph;
        this.nodes = nodes;
        this.edges = edges;
        this.population = new Chromosome[population];
        colors = calcNumberOfColors();
        generateFirstPopulation();
        evaluatePopulation();
    }

    private void evaluatePopulation() {
        for(int i = 0; i<population.length; i++){
            population[i].evaluate();
        }
    }


    private void generateFirstPopulation() {
        SecureRandom sr = new SecureRandom();

        for(int i = 0; i < population.length; i++){
            population[i].generateGenes(colors);
        }
    }


    public Chromosome evolve(){
        //select()
        //crossover()
        //mutate()
        minimizeErrors();
        evaluatePopulation();
        Chromosome fittest = findFittest();
        return fittest;
    }

    private void minimizeErrors() {
        for(int i = 0; i<population.length; i++){
            population[i].minimizeErrors(graph);
        }
    }

    private Chromosome findFittest() {
        Chromosome fittest = population[0];

        for(int i = 1; i < population.length; i++)
            fittest = population[i].fitness > fittest.fitness ? population[i] : fittest;

        return fittest;
    }

    private int calcNumberOfColors() {  //
        int maxN = 0;
        for(int i = 0; i <population.length; i++){
            maxN = graph.get(i).size() > maxN ? graph.get(i).size() : maxN;
        }
        return edges/nodes + maxN;
    }


}
