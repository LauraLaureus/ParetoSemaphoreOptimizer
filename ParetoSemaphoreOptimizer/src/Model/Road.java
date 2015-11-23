package Model;

import java.util.ArrayList;

/**
 *
 * @author lala
 */
public class Road {

    private final RoadSense sense;
    public final ArrayList<Cell> roadData;

    private final InputCell start;
    private final OutputCell end;

    public final SemaphoreCell sem1;
    public final SemaphoreCell sem2;

    public Road(RoadSense sense) {

        this.sense = sense;
        roadData = new ArrayList<>();

        //Auxiliar vars
        Cell next, prev, current;
        ArrayList<Cell> aux_neighbours = new ArrayList<>();

        //Create inputCell
        start = new InputCell();
        roadData.add(start);

        next = new CommonCell();
        aux_neighbours.add(next);
        start.neighbours = aux_neighbours; //PUNTEROOW
        roadData.add(next);

        current = next;
        next = new CommonCell();
        aux_neighbours = new ArrayList<>();
        aux_neighbours.add(start);
        aux_neighbours.add(next);
        current.neighbours = aux_neighbours;
        roadData.add(next);

        prev = current;
        current = next;
        next = new SemaphoreCell();
        aux_neighbours = new ArrayList<>();
        aux_neighbours.add(prev);
        aux_neighbours.add(next);
        current.neighbours = aux_neighbours;
        roadData.add(next);

        sem1 = (SemaphoreCell) next;

        prev = current;
        current = next;
        next = new IntersectCell();
        aux_neighbours = new ArrayList<>();
        aux_neighbours.add(prev);
        aux_neighbours.add(next);
        current.neighbours = aux_neighbours;
        roadData.add(next);

        prev = current;
        current = next;
        next = new PostIntersectCell();
        aux_neighbours = new ArrayList<>();
        aux_neighbours.add(prev);
        aux_neighbours.add(next);
        current.neighbours = aux_neighbours;
        roadData.add(next);

        prev = current;
        current = next;
        next = new CommonCell();
        aux_neighbours = new ArrayList<>();
        aux_neighbours.add(prev);
        aux_neighbours.add(next);
        current.neighbours = aux_neighbours;
        roadData.add(next);

        prev = current;
        current = next;
        next = new CommonCell();
        aux_neighbours = new ArrayList<>();
        aux_neighbours.add(prev);
        aux_neighbours.add(next);
        current.neighbours = aux_neighbours;
        roadData.add(next);

        prev = current;
        current = next;
        next = new SemaphoreCell();
        aux_neighbours = new ArrayList<>();
        aux_neighbours.add(prev);
        aux_neighbours.add(next);
        current.neighbours = aux_neighbours;
        roadData.add(next);

        sem2 = (SemaphoreCell) next;

        prev = current;
        current = next;
        next = new IntersectCell();
        aux_neighbours = new ArrayList<>();
        aux_neighbours.add(prev);
        aux_neighbours.add(next);
        current.neighbours = aux_neighbours;
        roadData.add(next);

        prev = current;
        current = next;
        next = new PostIntersectCell();
        aux_neighbours = new ArrayList<>();
        aux_neighbours.add(prev);
        aux_neighbours.add(next);
        current.neighbours = aux_neighbours;
        roadData.add(next);

        prev = current;
        current = next;
        next = new CommonCell();
        aux_neighbours = new ArrayList<>();
        aux_neighbours.add(prev);
        aux_neighbours.add(next);
        current.neighbours = aux_neighbours;
        roadData.add(next);

        prev = current;
        current = next;
        next = new CommonCell();
        aux_neighbours = new ArrayList<>();
        aux_neighbours.add(prev);
        aux_neighbours.add(next);
        current.neighbours = aux_neighbours;
        roadData.add(next);

        prev = current;
        current = next;
        next = new OutputCell();
        aux_neighbours = new ArrayList<>();
        aux_neighbours.add(prev);
        aux_neighbours.add(next);
        current.neighbours = aux_neighbours;
        roadData.add(next);

        aux_neighbours = new ArrayList<>();
        aux_neighbours.add(current);
        next.neighbours = aux_neighbours;

        end = (OutputCell) next;

        for (Cell cell : roadData) {
            cell.sense = this.sense;
        }
    }

    public void intersectRoads(Road r, int mySemNum, int rSemNum) {
        Road current;
        Road perpendicular;

        if (r.sense == RoadSense.Horizontal) {
            current = r;
            perpendicular = this;
        } else {
            current = this;
            perpendicular = r;
        }

        IntersectCell currentInter;
        IntersectCell perpendicularInter;
        if (mySemNum == 1) {
            currentInter = (IntersectCell) current.sem1.neighbours.get(1);
        } else {
            currentInter = (IntersectCell) current.sem2.neighbours.get(1);
        }

        if (rSemNum == 1) {
            perpendicularInter = (IntersectCell) perpendicular.sem1.neighbours.get(1);
        } else{
            perpendicularInter = (IntersectCell) perpendicular.sem2.neighbours.get(1);
        }

        ArrayList<Cell> verticalNeighboursForIntersectCell = perpendicularInter.neighbours;
        currentInter.perpendicularNeighbours = verticalNeighboursForIntersectCell;

        int index;
        for (Cell verticalNeighbour : verticalNeighboursForIntersectCell) {
            index = verticalNeighbour.neighbours.indexOf(perpendicularInter);
            verticalNeighbour.neighbours.set(index, currentInter);
        }

        index = perpendicular.roadData.indexOf(perpendicularInter);
        perpendicular.roadData.set(index, currentInter);
    }

    public void sem1NextStatus(boolean becomeActive) {
        if (becomeActive) {
            sem1.setSemaphoreNextStatus(SemaphoreStatus.OPEN);
        } else {
            sem1.setSemaphoreNextStatus(SemaphoreStatus.CLOSED);
        }

    }

    public void sem2NextStatus(boolean becomeActive) {
        if (becomeActive) {
            sem2.setSemaphoreNextStatus(SemaphoreStatus.OPEN);
        } else {
            sem2.setSemaphoreNextStatus(SemaphoreStatus.CLOSED);
        }
    }

    public void addCar() {
        start.addCarToQueue();
    }

    public int count() {
        return end.getCarsOut();
    }

    public void determineNextStatusAndUpdate() {
        for (Cell c : roadData) {
            if(c instanceof IntersectCell && !isInMySense((IntersectCell)c) )
                continue;
            c.determineNextStatus();
        }

        for (Cell c : roadData) {
            if(c instanceof IntersectCell && !isInMySense((IntersectCell)c))
                continue;
            c.update();
        }
    }
    
    public Cell getCellIn(int index){
        return roadData.get(index);
    }
    
    private boolean isInMySense(IntersectCell i){
        if(this.sense == RoadSense.Vertical && i.currentGoesVertical)
            return true;
        if(this.sense == RoadSense.Horizontal && !i.currentGoesVertical)
            return true;
        return false;
    }
    
    //ambos inclusive!!!
    public void determineNextStatusInRange(int i,int j){
        for (int k = i; k <= j ; k++) {
            roadData.get(k).determineNextStatus();
        }
    }
    
    public void updateInRange(int i, int j){
        for (int k = i; k <= j ; k++) {
            roadData.get(k).update();
        }
    }
    
    @Override
    public String toString() {
        String s = "Road{" + "sense=" + sense;
        for (Cell cell : roadData) {
            s += cell.toString();
            s += ",";
        }
        s += '}';
        return s;

    }

}
