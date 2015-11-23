package Model;

/**
 *
 * @author Laura
 */
public class SemaphoreCell extends Cell{

    public SemaphoreStatus semStatus;
    private SemaphoreStatus semNextStatus;

    public SemaphoreCell() {
        this.currentStatus = Status.FREE;
        this.semStatus = SemaphoreStatus.OPEN;
    }
   
    
    
    public void setSemaphoreNextStatus(SemaphoreStatus sem){
        this.semNextStatus = sem;
    }

    @Override
    public void determineNextStatus() {
        if(semStatus  == SemaphoreStatus.CLOSED || (semStatus == SemaphoreStatus.OPEN && neighbours.get(1).currentStatus == Status.BUSY)){
            if (this.currentStatus == Status.FREE){
                this.nextStatus = this.neighbours.get(0).currentStatus;
            }else{
                this.nextStatus = Status.BUSY;
            }
        }else{
            if (this.currentStatus == Status.FREE){
                this.nextStatus = this.neighbours.get(0).currentStatus;
            }else{
                this.nextStatus = Status.FREE;
            }
        }
    }

    @Override
    public String toString() {
        return "SemaphoreCell{" + "semStatus=" + semStatus + " currentStatus"+ this.currentStatus+'}';
    }

    @Override
    public void update() {
        super.update();
        this.semStatus = semNextStatus;
    }
    
    
}
