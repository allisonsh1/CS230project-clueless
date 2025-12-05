
/**
 * Uses a 2D array to store clothes. The top row holds all the tops and the bottom row all the bottoms.
 */
public class Closet {
    private Clothing[][] closet;
    private int maxCols;

    /**
     * Constructor to create a closet with a given size,
     * @param maxCols       How many tops/bottoms can be added.
     */
    public Closet(int maxCols) {
        this.maxCols = maxCols;
        this.closet = new Clothing[2][maxCols];
    }

    /**
     * Increases the size of the closet by double.
     */
    private void expand(){
        int newSize = maxCols*2;
        Clothing[][] newCloset = new Clothing[2][newSize];

        for(int i = 0; i<2; i++){
            for(int j = 0; j < maxCols; j++){
                newCloset[i][j]=closet[i][j];
            }
        }

        closet = newCloset;
        maxCols = newSize;
    }

    /**
     * Adds a top to first free spot in tops row. If the top row is full, expands.
     * @param t     The top to be added
     */
    public void addTop(Top t) {
        for (int c = 0; c < maxCols; c++) {
            if (closet[0][c] == null) { closet[0][c] = t; return; }
        }
        expand();
        addTop(t);
    }

    /**
     * Adds a bottom to first free spot in bottom row. If the bottom row is full, expands.
     * @param b     The bottom to be added
     */
    public void addBottom(Bottom b) {
        for (int c = 0; c < maxCols; c++) {
            if (closet[1][c] == null) { closet[1][c] = b; return; }
        }
        expand();
        addBottom(b);
    }

    public Clothing[][] getArray() {
        return closet;
    }
}