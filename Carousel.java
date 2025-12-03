import javafoundations.CircularArrayQueue;

/**
 * A carousel that uses a CircularArrayQueue to allow
 * circular navigation through clothing items from a closet.
 */
public class Carousel {
    private CircularArrayQueue<Clothing> items;
    private Clothing current;
    
    /**
     * Creates a carousel from a specific row in the closet.
     * 
     * @param closet The closet to pull items from
     * @param row The row to use (0 for tops, 1 for bottoms)
     */
    public Carousel(Closet closet, int row) {
        items = new CircularArrayQueue<>();
        
        Clothing[][] array = closet.getArray();
        for (int c = 0; c < array[row].length; c++) {
            if (array[row][c] != null) {
                items.enqueue(array[row][c]);
            }
        }
        
        if (!items.isEmpty()) {
            current = items.first();
        }
    }

    /**
     * Rotates the carousel forward by one position
     * The current item moves to the back and the next item becomes current.
     */
    public void next() {
        Clothing item = items.dequeue();
        items.enqueue(item);
        
        current = items.first();
        
    }
    
    /**
     * Gets the current clothing item.
     * 
     * @return The current item.
     */
    public Clothing current() {
        return current;
    }
    
    /**
     * Gets the number of items in the carousel.
     * 
     * @return The size of the carousel
     */
    public int size() {
        return items.size();
    }

    /**
     * Is the carousel empty?
     * 
     * @return true if the carousel has no items, false if it has anything
     */
    public boolean isEmpty() {
        return items.isEmpty();
    }
    
    /**
     * Returns a string representation of al items in the carousel
     * 
     * @return String representation of the carousel
     */
    @Override
    public String toString() {
        
        String result = "CAROUSEL OF " + items.size() + " ITEMS\n";
        CircularArrayQueue<Clothing> temp = new CircularArrayQueue<>();
        int count = 1;
        
        while (!items.isEmpty()) {
            Clothing item = items.dequeue();
        
            result += "  " + count++ + ". " + item;
            if(item==current){
                result+= " !!IS CURRENT!!";
            }
            result+="\n";
            temp.enqueue(item);
        }
        
        while (!temp.isEmpty()) {
            items.enqueue(temp.dequeue());
        }
        
        return result;
    }
}