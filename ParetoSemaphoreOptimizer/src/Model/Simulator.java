package Model;

/**
 *
 * @author lala
 */
public class Simulator {

    private final Road h = new Road(RoadSense.Horizontal);
    private final Road h2 = new Road(RoadSense.Horizontal);
    private final Road v = new Road(RoadSense.Vertical);
    private final Road v2 = new Road(RoadSense.Vertical);
    //añadir el simulate updator

    public Simulator() {
        h.intersectRoads(v, 1, 1);
        h.intersectRoads(v2, 2, 1);
        h2.intersectRoads(v, 1, 2);
        h2.intersectRoads(v2, 2, 2);
    }

    public double simulate(boolean[][] semaphoreHistory) {

        for (int i = 0; i < 7200; i++) {
                h.addCar();
                h2.addCar();
                v.addCar();
                v2.addCar();
            if (i % 10 == 0) {
                boolean[] thisTime = semaphoreHistory[i % 10];
                h.sem1NextStatus(thisTime[0]);
                v.sem1NextStatus(!thisTime[0]);

                h.sem2NextStatus(thisTime[1]);
                v2.sem1NextStatus(!thisTime[1]);

                h2.sem1NextStatus(thisTime[2]);
                v.sem2NextStatus(!thisTime[2]);

                h2.sem2NextStatus(thisTime[3]);
                v2.sem2NextStatus(!thisTime[3]);
            }

            
            updateSimulator();
        }

        return (double) ((h.count() + v.count() + h2.count() + v2.count()) / 28800d);

    }
    
    private void updateSimulator() {
        h.determineNextStatusInRange(0, 3);//a
        h.updateInRange(0, 2);
        v.determineNextStatusInRange(0, 3);//b
        v.updateInRange(0, 2);
        v2.determineNextStatusInRange(0, 3); //c
        v2.updateInRange(0, 2);
        h2.determineNextStatusInRange(0, 3); //h
        h2.updateInRange(0, 2);

        //First intersection TOP+LEFT
        if (h.sem1.semStatus == SemaphoreStatus.OPEN) {
            h.determineNextStatusInRange(4, 8); //j
            v.determineNextStatusInRange(5, 8); //i
            h.updateInRange(3, 7);
            v.updateInRange(3, 3);
            v.updateInRange(5, 7);
        } else {
            v.determineNextStatusInRange(4, 8); //i
            h.determineNextStatusInRange(5, 8); //j
            v.updateInRange(3, 7);
            h.updateInRange(3, 3);
            h.updateInRange(5, 7);
        }

        //TOP+RIGHT
        if (h.sem2.semStatus == SemaphoreStatus.OPEN) {
            h.determineNextStatusInRange(9, 13); //d
            v2.determineNextStatusInRange(5, 8); //k
            h.updateInRange(8, 13);
            v2.updateInRange(3, 3);
            v2.updateInRange(5, 7);
        } else {
            v2.determineNextStatusInRange(4, 8);//k
            h.determineNextStatusInRange(10, 13); //d
            v2.updateInRange(3, 7);
            h.updateInRange(8, 8);
            h.updateInRange(10, 13);
        }

        //BOTTOM LEFT
        if (h2.sem1.semStatus == SemaphoreStatus.OPEN) {
            h2.determineNextStatusInRange(4, 8);
            v.determineNextStatusInRange(10, 13);
            h2.updateInRange(3, 7);
            v.updateInRange(8, 8);
            v.updateInRange(10, 13);
        } else {
            v.determineNextStatusInRange(9, 13);
            h2.determineNextStatusInRange(5, 8);
            v.updateInRange(8, 13);
            h2.updateInRange(3, 3);
            h2.updateInRange(5, 7);
        }

        //BOTTOM RIGHT
        if (h2.sem2.semStatus == SemaphoreStatus.OPEN) {
            h2.determineNextStatusInRange(9, 13);
            v2.determineNextStatusInRange(10, 13);
            h2.updateInRange(8, 13);
            v2.updateInRange(8, 8);
            v2.updateInRange(10, 13);
        } else {
            v2.determineNextStatusInRange(9, 13);
            h2.determineNextStatusInRange(10, 13);
            v2.updateInRange(8, 13);
            h2.updateInRange(8, 8);
            h2.updateInRange(10, 13);
        }
    }
}
