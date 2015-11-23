package Model;

/**
 *
 * @author lala
 */
public class CommonCell extends Cell {

    public CommonCell() {
        this.currentStatus = Status.FREE;
    }
    

    @Override
    public void determineNextStatus() {
        if(
                (this.currentStatus == Status.FREE && neighbours.get(0).currentStatus == Status.BUSY) ||
                (this.currentStatus == Status.BUSY && neighbours.get(1).currentStatus == Status.BUSY) 
          )
            this.nextStatus = Status.BUSY;
        else
            this.nextStatus = Status.FREE;
    }

    @Override
    public String toString() {
        return "CommonCell{" + this.currentStatus + '}';
    }

    
}
