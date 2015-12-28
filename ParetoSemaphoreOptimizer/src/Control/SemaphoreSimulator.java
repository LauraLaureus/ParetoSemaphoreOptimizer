package Control;

import Model.Simulator;
import geneticAlgoritm.GeneticAlgorithmForSemaphoreOptimization;
import javax.swing.JFrame;
import org.math.plot.*;

/**
 *
 * @author Laura
 */
public class SemaphoreSimulator {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        geneticAlgoritm.GeneticAlgorithmForSemaphoreOptimization ga = new GeneticAlgorithmForSemaphoreOptimization(200,0.1d,0.02d);
        ga.compute();
        plotMaxAndMean(ga.getX(),ga.getY());
        
    }

    public static double maine(double[][] d) {

        boolean[][] result = new boolean[d.length][d[0].length];
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[0].length; j++) {
                if (d[i][j] == 1) {
                    result[i][j] = true;
                } else {
                    result[i][j] = false;
                }
            }
        }

        return new Simulator().simulate(result);
    }

    private static void plotMaxAndMean(double[] max,double[] mean) {
        Plot2DPanel plot = new Plot2DPanel();

        
        // add a line plot to the PlotPanel
        plot.addLinePlot("Max plot", getSequenceArrayTo(max.length),max);
        plot.addLinePlot("Mean plot", getSequenceArrayTo(mean.length),mean);
        plot.addLegend("SOUTH");
        
        
        // put the PlotPanel in a JFrame, as a JPanel
        JFrame frame = new JFrame("Semaphore Simulator Results");
        frame.setContentPane(plot);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private static double[] getSequenceArrayTo(int b){
        double[] result = new double[b];
        for (int i = 0; i < result.length; i++) {
            result[i]=i+1;
        }
        return result;
    }
}
