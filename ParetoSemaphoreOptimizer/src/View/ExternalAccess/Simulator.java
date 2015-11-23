/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.ExternalAccess;

/**
 *
 * @author lala
 */
public class Simulator {
    
    private double[][] semaphoreSequence;
    private double pollutionFitness;
    private double vehicleFitness;
    
    public Simulator(double[][] semaphoreSequence){
        this.semaphoreSequence = semaphoreSequence;
        this.pollutionFitness = 0;
        this.vehicleFitness = 0;
    }
    
    public double run(){
    /*
        Create roads
        
        start loop for 7200 iterations
        addCars to roads;
        determine nextStatus;
        compute pollution for this iteration;
        save pollution for this iteration;
        update simulator.
        
        end loop block
        
        compute vehicle fitness
        
        return vehicle fitness + pollution fitness
    */
        return pollutionFitness + vehicleFitness;
    }
    
    public double getPollutionFitness(){
        return pollutionFitness;
    }
    
    public double getVehicleFitness(){
        return vehicleFitness;
    }
    
}
