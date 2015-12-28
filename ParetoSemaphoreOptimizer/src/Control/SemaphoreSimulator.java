package Control;

import geneticAlgoritm.GeneticAlgorithmForSemaphoreOptimization;
import java.util.ArrayList;
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
        geneticAlgoritm.GeneticAlgorithmForSemaphoreOptimization ga = new GeneticAlgorithmForSemaphoreOptimization(100,0d,0.01d);
        ga.compute();
        plotMaxAndMean(ga.getX(),ga.getY());
        plotPareto(ga.getBestOfGeneration());
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

    private static void plotPareto(ArrayList<double[]> bestOfGeneration) {
        double[] fitness = new double[bestOfGeneration.size()];
        double[] pollution = new double[bestOfGeneration.size()];
        
        for (int i = 0; i < pollution.length; i++) {
            fitness[i] = bestOfGeneration.get(i)[0];
            pollution[i] = 1-bestOfGeneration.get(i)[1];
        }
        
        Plot2DPanel plot = new Plot2DPanel();
        plot.addScatterPlot("Best Of Generatio", fitness, pollution);
        
        // add a line plot to the PlotPanel
        //plot.addLinePlot("Best Of Generation", fitness,pollution);
        plot.addLegend("SOUTH");
        
        
        // put the PlotPanel in a JFrame, as a JPanel
        JFrame frame = new JFrame("Pareto Results");
        frame.setContentPane(plot);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
