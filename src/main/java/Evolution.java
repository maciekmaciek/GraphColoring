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
    private final double MUTATION = 0.8;
    private final double CROSSOVER = 0.8;

    public Evolution(ArrayList<ArrayList<Integer>> graph, int nodes, int edges, int population, int colors) {
        this.graph = graph;
        this.nodes = nodes;
        this.edges = edges;
        this.population = new Chromosome[population];
        this.colors = colors;
        generateFirstPopulation();
        evaluatePopulation();
    }

    private void evaluatePopulation() {
        for (int i = 0; i < population.length; i++) {
            population[i].evaluate(graph);
        }
    }


    private void generateFirstPopulation() {
        for (int i = 0; i < population.length; i++) {
            population[i] = new Chromosome(nodes,colors);
            population[i].generateGenes();
        }
    }


    public Chromosome evolve() {
        ArrayList<Double> results1000 = new ArrayList<>();
        evaluatePopulation();
        Chromosome fittest;
        Chromosome worst;
        fittest = new Chromosome(findFittest());
        worst = new Chromosome(findWorst());
        double avg = 0;
        results1000.add((double)edges);
        double diff = edges;
        int i = 0;
        System.out.println("Pokolenie: " + i + ", " + fittest.fitness);

       // while (fittest.fitness != 0 && diff > 2.0 ) {
        while (fittest.fitness != 0 && i!= 100 ) {
            selectPopulation();
            crossoverPopulation();
            mutatePopulation();
            evaluatePopulation();
            fittest = new Chromosome(findFittest());
            worst = new Chromosome(findWorst());
            if(i%1 == 0) {
                System.out.println("Pokolenie: " + (i + 1));
                System.out.println("\tmax, " + fittest.fitness);
                System.out.println("\tmin, " + worst.fitness);
                System.out.println("\tavg, " + findAvg());

            }
            if (i % 1000 == 0) {
                results1000.add(fittest.fitness);
                if(i!=0)
                    diff = results1000.get(results1000.size()-2) - results1000.get(results1000.size()-1);

            }
            i++;
        }
        return fittest;
    }

    private void selectPopulation() {
        SecureRandom sr = new SecureRandom();
        Chromosome[] selected = new Chromosome[population.length];
        for (int i = 0; i < population.length; i++) {
            int op0 = sr.nextInt(population.length);
            int op1 = sr.nextInt(population.length);

            selected[i] = new Chromosome(compete(population[op0], population[op1]));
        }
        population = selected;
    }

    private Chromosome compete(Chromosome c0, Chromosome c1) {
        return c0.fitness < c1.fitness ? c0 : c1;
    }

    private void crossoverPopulation() {
        SecureRandom sr = new SecureRandom();
        Chromosome[] children = new Chromosome[population.length];
        for (int i = 0; i < population.length; i++) {
            int op;
            do {
                op = sr.nextInt(population.length);
            } while (op == i);

            if (sr.nextDouble() <= CROSSOVER)
                children[i] = new Chromosome(crossover(population[i].genes, population[op].genes),colors);
            else
                children[i] = new Chromosome(population[i]);
        }

        population = children;
    }

    private int[] crossover(int[] genes, int[] genes1) {
        int[] child = new int[genes.length];

        for (int i = 0; i < genes.length; i++) {
            child[i] = i < genes.length / 2 ? genes[i] : genes1[i];
        }
        return child;
    }


    private void mutatePopulation() {
        SecureRandom sr = new SecureRandom();
        for (Chromosome aPopulation : population) {

                aPopulation.minimizeErrorsV3(graph, MUTATION);
        }
    }

    private Chromosome findFittest() {
        Chromosome fittest = population[0];

        for (int i = 1; i < population.length; i++)
            fittest = population[i].fitness < fittest.fitness ? population[i] : fittest;

        return fittest;
    }
    private Chromosome findWorst() {
        Chromosome fittest = population[0];

        for (int i = 1; i < population.length; i++)
            fittest = population[i].fitness > fittest.fitness ? population[i] : fittest;

        return fittest;
    }

    private double findAvg() {
        double avg = 0;
        for (int i = 0; i < population.length; i++)
            avg += population[i].fitness;

        return avg/population.length;
    }


}
