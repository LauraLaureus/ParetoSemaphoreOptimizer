package Model;

/**
 *
 * @author Laura
 */
/**
 * 
 * Contains an 3Dimensional matrix related to the chromosomas set for the genetic algoritm.
 */
public class SemaphoreSequence {

    private boolean[][][] setSequence;
    private int index;
    private final int size;
    
    public SemaphoreSequence(int size) {
        this.size = size;
        this.setSequence = generateRandomInitialMatrix();
    }

    private boolean[][][] generateRandomInitialMatrix() {
        boolean[][][] result = new boolean[size][12][4];
        
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[0].length; j++) {
                for (int k = 0; k < result[0][0].length; k++) {
                    System.out.println("k "+k+" j "+j+ " i "+i);
                }
                
            }
            
        }
        
        return result;
    }
        
        
}
