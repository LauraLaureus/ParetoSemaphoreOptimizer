/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticAlgoritm;

import Model.PollutionSimulator;
import Model.Simulator;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Laura
 */
public class GeneticAlgorithmForSemaphoreOptimization {

    private ArrayList<boolean[][]> population;
    private double[] fitness, pollution;
    private double[] maxFitness, meanFitness;
    private ArrayList<double[]> bestOfGeneration;
    private int[][] mask1, mask2, mask3;
    private final int populationSize;
    private final Simulator sim;
    private final PollutionSimulator sim_p;
    private final double selection_p, mutation_p;
    public double maxValue = 0;

    public double[] getX() {
        return maxFitness;
    }

    public double[] getY() {
        return meanFitness;
    }

    public ArrayList<double[]> getBestOfGeneration() {
        return this.bestOfGeneration;
    }

    public GeneticAlgorithmForSemaphoreOptimization(int populationSize, double selection_p, double mutation_p) {
        if (populationSize % 3 != 0) {
            populationSize += 3 - (populationSize % 3);
        }
        this.populationSize = populationSize;
        this.selection_p = selection_p;
        this.mutation_p = mutation_p;
        population = new ArrayList<>();
        this.sim = new Simulator();
        this.sim_p = new PollutionSimulator();
        generateInitialPopulation();
        generateMaskForThreeParentCrossover();
        this.bestOfGeneration = new ArrayList<>();
    }

    private void generateInitialPopulation() {
        for (int i = 0; i < populationSize; i++) {
            population.add(generateRandomBidimensionalMatrix());
        }
    }

    private boolean[][] generateRandomBidimensionalMatrix() {
        boolean[][] randomMatrix = new boolean[12][4];
        Random rand = new Random();
        rand.setSeed(System.currentTimeMillis());
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 4; j++) {
                randomMatrix[i][j] = rand.nextBoolean();
            }
        }
        return randomMatrix;
    }

    private void generateMaskForThreeParentCrossover() {
        mask1 = generateRandomMatrixInModule3();
        mask2 = generateInverseOfMask(mask1);
        mask3 = generateInverseOfMask(mask2);
    }

    public void compute() {
        maxFitness = new double[populationSize];
        meanFitness = new double[populationSize];
        for (int i = 0; i < populationSize; i++) {
            computeFitness();
            saveData(i);
            this.population = probabilistic_tourneau_selection();
            this.population = three_parent_crossover();
            mutation();
        }
    }

    private void computeFitness() {
        this.fitness = new double[population.size()];
        this.pollution = new double[population.size()];
        for (int i = 0; i < population.size(); i++) {
            fitness[i] = sim.simulate(population.get(i));
            pollution[i] = sim_p.simulate(population.get(i));
        }
    }

    private void saveData(int i) {
        maxFitness[i] = calculateMaxFitness();
        meanFitness[i] = calculateMeanFitness();
    }

    private double calculateMaxFitness() {
        double max_rate = this.fitness[0];
        double max_pollution = this.pollution[0];
        double max_combination = max_rate + max_pollution;

        for (int counter = 1; counter < fitness.length; counter++) {
            if (0.5d*fitness[counter] + 0.5d*pollution[counter] > max_combination) {
                max_rate = 0.5d*fitness[counter];
                max_pollution = 0.5d*pollution[counter];
                max_combination = max_rate + max_pollution;
            }
        }
        this.maxValue = max_combination;
        return max_combination;
    }

    private double calculateMeanFitness() {
        double sum = 0;
        for (int i = 0; i < fitness.length; i++) {
            sum += fitness[i] + pollution[i];
        }
        return sum / fitness.length;
    }

    private int[][] generateRandomMatrixInModule3() {
        int[][] randomMatrix = new int[12][4];
        Random rand = new Random();
        rand.setSeed(System.currentTimeMillis());
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 4; j++) {
                randomMatrix[i][j] = rand.nextInt(3);
            }
        }
        return randomMatrix;
    }

    private int[][] generateInverseOfMask(int[][] mask) {
        int[][] inverse = new int[12][4];
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 4; j++) {
                inverse[i][j] = (mask[i][j] + 1) % 3;
            }
        }
        return inverse;
    }

    private ArrayList<boolean[][]> probabilistic_tourneau_selection() {
        ArrayList<boolean[][]> result = new ArrayList<>();
        Random rand = new Random(System.currentTimeMillis());

        int indexA, indexB, min;
        double fitnessA, fitnessB, r;
        double[] aux = new double[2];

        for (int i = 0; i < fitness.length; i++) {

            indexA = rand.nextInt(fitness.length);
            indexB = rand.nextInt(fitness.length);
            if (indexA == indexB) {
                indexB = (indexA + 1) % fitness.length;
            }

            r = rand.nextDouble();
            fitnessA = 0.5d*fitness[indexA] + 0.5d*pollution[indexA];
            fitnessB = 0.5d*fitness[indexB] + 0.5d*pollution[indexB];

            if (fitnessA > 0d) {
                aux[0] = fitness[indexA];
                aux[1] = pollution[indexA];

                bestOfGeneration.add(aux);
            }
            if (fitnessB > 0d) {
                aux[0] = fitness[indexB];
                aux[1] = pollution[indexB];
                bestOfGeneration.add(aux);
            }

            if ((fitnessA < fitnessB && r < selection_p) || (fitnessA > fitnessB && r >= selection_p)) {
                result.add(population.get(indexA));
            } else {
                result.add(population.get(indexB));
            }
        }

        return result;
    }

    private ArrayList<boolean[][]> three_parent_crossover() {

        ArrayList<boolean[][]> result = new ArrayList<>();
        int[] permutation = generatePermutation();

        for (int i = 0; i < permutation.length; i += 3) {
            result.add(applyMask(permutation, i, mask1));
            result.add(applyMask(permutation, i, mask2));
            result.add(applyMask(permutation, i, mask3));
        }

        return result;
    }

    private int[] generatePermutation() {
        ArrayList<Integer> availablesForPermutation = new ArrayList<>();
        for (int i = 0; i < fitness.length; i++) {
            availablesForPermutation.add(i);
        }

        int[] permutation = new int[fitness.length];
        Random rnd = new Random(System.currentTimeMillis());
        int index;
        for (int i = 0; i < permutation.length; i++) {
            index = rnd.nextInt(availablesForPermutation.size());
            permutation[i] = availablesForPermutation.get(index);
            availablesForPermutation.remove(index);
        }
        return permutation;
    }

    private boolean[][] applyMask(int[] permutation, int i, int[][] mask) {
        boolean[][] result = new boolean[12][4];

        boolean[][] parent1, parent2, parent3, parent_selected;
        parent1 = population.get(permutation[i]);
        parent2 = population.get(permutation[i + 1]);
        parent3 = population.get(permutation[i + 2]);

        int parent_selected_index;

        for (int j = 0; j < result.length; j++) {
            for (int k = 0; k < result[0].length; k++) {
                parent_selected_index = mask[j][k];

                if (parent_selected_index == 0) {
                    parent_selected = parent1;
                } else if (parent_selected_index == 1) {
                    parent_selected = parent2;
                } else {
                    parent_selected = parent3;
                }

                result[j][k] = parent_selected[j][k];
            }
        }

        return result;
    }

    private void mutation() {

        Random rnd = new Random(System.currentTimeMillis());
        double aux;

        for (int i = 0; i < fitness.length; i++) {
            aux = rnd.nextDouble();
            if (aux <= mutation_p) {
                for (int r = 0; r < 12; r++) {
                    for (int c = 0; c < 4; c++) {
                        if (rnd.nextDouble() <= mutation_p) {
                            population.get(i)[r][c] = !population.get(i)[r][c];
                        }
                    }
                }
            }
        }

    }

}
