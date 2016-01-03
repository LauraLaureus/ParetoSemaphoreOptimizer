package Model;

import java.util.ArrayList;

/**
 *
 * @author Laura
 */
public abstract class Cell {

    public Status currentStatus;
    protected Status nextStatus;
    protected Status prevStatus;
    protected ArrayList<Cell> neighbours;
    public RoadSense sense;

    public void update() {
        prevStatus = currentStatus;
        currentStatus = nextStatus;
        nextStatus = null;
    }

    public abstract void determineNextStatus();

    public void setNeighbours(ArrayList<Cell> neigh) {
        this.neighbours = neigh;
    }

    public void reset() {
        currentStatus = Status.FREE;
        nextStatus = null;
    }

    double getPollution() {

        if (currentStatus == Status.FREE) {
            return 0d;
        } else {
            if (nextStatus == Status.FREE) {
                if (prevStatus == Status.BUSY) {
                    return 1d;
                }else{
                    return 0;
                }
            }else{
                return 0d;
            }
        }
        /*if(currentStatus == Status.BUSY && nextStatus == Status.FREE)
         return 0.5d;
         else if (currentStatus == Status.BUSY && nextStatus == Status.BUSY)
         return 1d;
         else
         return 0d;*/
    }
}
