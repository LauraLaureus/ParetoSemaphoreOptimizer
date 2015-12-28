package Model;

import java.util.ArrayList;

/**
 *
 * @author Laura
 */
public abstract class Cell {
    public Status currentStatus;
    protected Status nextStatus;
    protected ArrayList<Cell> neighbours;
    public RoadSense sense;
    
    public void update(){
        currentStatus = nextStatus;
        nextStatus = null;
    }
    
    public abstract void determineNextStatus();
    
    public void setNeighbours(ArrayList<Cell> neigh){
        this.neighbours = neigh;
    }
    
    public void reset(){
        currentStatus = Status.FREE;
        nextStatus = null;
    }

    double getPollution() {
        if(currentStatus == Status.BUSY && nextStatus == Status.FREE)
            return 0.5d;
        else if (currentStatus == Status.BUSY && nextStatus == Status.BUSY)
            return 1d;
        else
            return 0d;
    }
}
