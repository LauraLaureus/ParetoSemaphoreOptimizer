package Control;

import View.MainFrame;
import geneticAlgoritm.GeneticAlgorithmForSemaphoreOptimization;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
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
        geneticAlgoritm.GeneticAlgorithmForSemaphoreOptimization ga = new GeneticAlgorithmForSemaphoreOptimization(200,0.2d,0.02d);
        ga.compute();
        
        plotMaxAndMean(ga.getX(),ga.getY());
        plotPareto(ga.getBestOfGeneration());
        
        MainFrame frame = new MainFrame(ga.getBestOfGeneration(), ga.maxValue);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
    }


    private static void plotMaxAndMean(double[] max,double[] mean) {
        Plot2DPanel plot = new Plot2DPanel();

        plot.addLinePlot("Max plot", getSequenceArrayTo(max.length),max);
        plot.addLinePlot("Mean plot", getSequenceArrayTo(mean.length),mean);
        plot.addLegend("SOUTH");
        
        JFrame frame = new JFrame ("Max Mean evolution");
        frame.setContentPane(plot);
        frame.pack();
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
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
            pollution[i] = bestOfGeneration.get(i)[1];
        }
        
        Plot2DPanel plot = new Plot2DPanel();
        plot.addScatterPlot("Best Of Generation", fitness, pollution);
        plot.getAxis(0).setLabelText("Fitness");
        plot.getAxis(1).setLabelText("1-Contaminación");
        
        plot.addLegend("SOUTH");
        JFrame frame = new JFrame ("Pareto Frontier");
        frame.setContentPane(plot);
        frame.pack();
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setVisible(true);
        
    }

}
