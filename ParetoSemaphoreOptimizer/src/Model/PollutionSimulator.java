package Model;

/**
 *
 * @author Laura
 */
public class PollutionSimulator {

    private final Road h = new Road(RoadSense.Horizontal);
    private final Road h2 = new Road(RoadSense.Horizontal);
    private final Road v = new Road(RoadSense.Vertical);
    private final Road v2 = new Road(RoadSense.Vertical);
    private double[] pollution;

    public PollutionSimulator() {
        h.intersectRoads(v, 1, 1);
        h.intersectRoads(v2, 2, 1);
        h2.intersectRoads(v, 1, 2);
        h2.intersectRoads(v2, 2, 2);
        pollution = new double[7200];
    }

    public double simulate(boolean[][] semaphoreHistory) {
        double fitness = 0;
        for (int i = 0; i < 7200; i++) {
            h.addCar();
            h2.addCar();
            v.addCar();
            v2.addCar();
            if (i % 10 == 0) {
                boolean[] thisTime = semaphoreHistory[i % 12];
                h.sem1NextStatus(thisTime[0]);
                v.sem1NextStatus(!thisTime[0]);

                h.sem2NextStatus(thisTime[1]);
                v2.sem1NextStatus(!thisTime[1]);

                h2.sem1NextStatus(thisTime[2]);
                v.sem2NextStatus(!thisTime[2]);

                h2.sem2NextStatus(thisTime[3]);
                v2.sem2NextStatus(!thisTime[3]);
            }

            fitness += updateSimulator();
        }
        
        this.reset();
        return 1-fitness/(7200*44);

    }

    private double updateSimulator() {
        double result = 0;

        h.determineNextStatusInRange(0, 3);//a
        result += h.getContaminationInRange(0, 3);
        h.updateInRange(0, 2);

        v.determineNextStatusInRange(0, 3);//b
        result += v.getContaminationInRange(0, 3);
        v.updateInRange(0, 2);

        v2.determineNextStatusInRange(0, 3); //c
        result += v2.getContaminationInRange(0, 3);
        v2.updateInRange(0, 2);

        h2.determineNextStatusInRange(0, 3); //h
        result += h2.getContaminationInRange(0, 3);
        h2.updateInRange(0, 2);

        //First intersection TOP+LEFT
        if (h.sem1.semStatus == SemaphoreStatus.OPEN) {
            h.determineNextStatusInRange(4, 8); //j
            v.determineNextStatusInRange(5, 8); //i
            result += h.getContaminationInRange(4, 8);
            result += v.getContaminationInRange(5, 8);
            h.updateInRange(3, 7);
            v.updateInRange(3, 3);
            v.updateInRange(5, 7);
        } else {
            v.determineNextStatusInRange(4, 8); //i
            h.determineNextStatusInRange(5, 8); //j
            result += h.getContaminationInRange(4, 8);
            result += v.getContaminationInRange(5, 8);
            v.updateInRange(3, 7);
            h.updateInRange(3, 3);
            h.updateInRange(5, 7);
        }

        //TOP+RIGHT
        if (h.sem2.semStatus == SemaphoreStatus.OPEN) {
            h.determineNextStatusInRange(9, 13); //d
            v2.determineNextStatusInRange(5, 8); //k
            result += h.getContaminationInRange(9, 13);
            result += v2.getContaminationInRange(5, 8);
            h.updateInRange(8, 13);
            v2.updateInRange(3, 3);
            v2.updateInRange(5, 7);
        } else {
            v2.determineNextStatusInRange(4, 8);//k
            h.determineNextStatusInRange(10, 13); //d
            result += h.getContaminationInRange(10, 13);
            result += v2.getContaminationInRange(4, 8);
            v2.updateInRange(3, 7);
            h.updateInRange(8, 8);
            h.updateInRange(10, 13);
        }

        //BOTTOM LEFT
        if (h2.sem1.semStatus == SemaphoreStatus.OPEN) {
            h2.determineNextStatusInRange(4, 8);
            v.determineNextStatusInRange(10, 13);
            result += h2.getContaminationInRange(4, 8);
            result += v.getContaminationInRange(10, 13);
            h2.updateInRange(3, 7);
            v.updateInRange(8, 8);
            v.updateInRange(10, 13);
        } else {
            v.determineNextStatusInRange(9, 13);
            h2.determineNextStatusInRange(5, 8);
            result += h2.getContaminationInRange(5, 8);
            result += v.getContaminationInRange(9, 13);
            v.updateInRange(8, 13);
            h2.updateInRange(3, 3);
            h2.updateInRange(5, 7);
        }

        //BOTTOM RIGHT
        if (h2.sem2.semStatus == SemaphoreStatus.OPEN) {
            h2.determineNextStatusInRange(9, 13);
            v2.determineNextStatusInRange(10, 13);
            result += h2.getContaminationInRange(9, 13);
            result += v2.getContaminationInRange(10, 13);
            h2.updateInRange(8, 13);
            v2.updateInRange(8, 8);
            v2.updateInRange(10, 13);
        } else {
            v2.determineNextStatusInRange(9, 13);
            h2.determineNextStatusInRange(10, 13);
            result += v2.getContaminationInRange(9, 13);
            result += h2.getContaminationInRange(10, 13);
            v2.updateInRange(8, 13);
            h2.updateInRange(8, 8);
            h2.updateInRange(10, 13);
        }
        return result/44d;
    }

    public void reset() {
        h.reset();
        h2.reset();
        v.reset();
        v2.reset();

    }
}
