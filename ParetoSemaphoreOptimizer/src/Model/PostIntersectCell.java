package Model;

/**
 *
 * @author lala
 */
public class PostIntersectCell extends Cell {

    public PostIntersectCell() {
        this.currentStatus = Status.FREE;
    }

    @Override
    public void determineNextStatus() {

        if ((interesting() && this.neighbours.get(0).currentStatus == Status.BUSY 
                && this.currentStatus == Status.FREE)||
                        (this.currentStatus==Status.BUSY &&
                this.neighbours.get(0).currentStatus==Status.BUSY)){
            this.nextStatus = Status.BUSY;
        }else{
            this.nextStatus = Status.FREE;
        }
       

    }

    
    @Override
    public String toString() {
        return "PostIntersectCell{" + "currentStatus" + this.currentStatus + '}';
    }

    private boolean interesting() {
        IntersectCell intersection = (IntersectCell) this.neighbours.get(0);
        return ((intersection.currentGoesVertical && this.sense == RoadSense.Vertical)
                || (this.sense == RoadSense.Horizontal && !intersection.currentGoesVertical));
    }

}
