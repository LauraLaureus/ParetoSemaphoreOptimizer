package Model;

/**
 *
 * @author Laura
 */
public class OutputCell extends Cell{

    private int carsOut = 0;

    public OutputCell() {
        this.currentStatus = Status.FREE;
    }
    

    @Override
    public void determineNextStatus() {
        if (this.currentStatus == Status.BUSY)
            carsOut+=1;
        if (this.neighbours.get(0).currentStatus ==  Status.FREE)
            this.nextStatus = Status.FREE;
        else
            this.nextStatus = Status.BUSY;
    }

    public int getCarsOut() {
        return carsOut;
    }

    @Override
    public String toString() {
        return "OutputCell{" + "carsOut=" + carsOut + "currentStatus"+this.currentStatus+ '}';
    }
    
    
    
}
