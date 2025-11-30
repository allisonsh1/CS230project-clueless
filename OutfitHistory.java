import javafoundations.CircularArrayQueue;

/**
 * Manages a history of saved outfits using a CircularArrayQueue.
 * Maintains a maximum of 7 outfits, automatically removing the oldest
 * when capacity is reached.
 */
public class OutfitHistory {
    private CircularArrayQueue<Outfit> history;
    private static final int MAX_CAPACITY = 7;
    
    /**
     * Constructor.
     */
    public OutfitHistory() {
        history = new CircularArrayQueue<>();
    }
    
    /**
     * Adds an outfit to the history. If the history is at capacity,
     * the oldest outfit is automatically removed.
     * 
     * @param outfit The outfit to add
     */
    public void addOutfit(Outfit outfit) {
        if (history.size() >= MAX_CAPACITY) {
            history.dequeue();
        }
        
        history.enqueue(outfit);
    }
    
    
    /**
     * Returns a formatted string of all outfits in the history.
     * 
     * @return The outfit history
     */
    public String getHistoryDisplay() {
        if (history.isEmpty()) {
            return "No outfits saved yet :(";
        }
        
        String result = "Saved Outfits (" + history.size() + "/" + MAX_CAPACITY + "):\n\n";
        
        CircularArrayQueue<Outfit> temp = new CircularArrayQueue<>();
        int count = 1;
        
        while (!history.isEmpty()) {
            Outfit o = history.dequeue();
            result += count++ + ". ";
            result += o.getTop().getName() + " + ";
            result += o.getBottom().getName() + "\n";
            result += "   (" + o.getTop().getColor() + " / ";
            result += o.getBottom().getColor() + ")\n\n";
            temp.enqueue(o);
        }
        
        while (!temp.isEmpty()) {
            history.enqueue(temp.dequeue());
        }
        
        return result;
    }
    
    /**
     * Gets the number of outfits currently in history.
     * 
     * @return The size of the history
     */
    public int size() {
        return history.size();
    }
    
    /**
     * Checks if the history is empty.
     * 
     * @return true if no outfits are saved
     */
    public boolean isEmpty() {
        return history.isEmpty();
    }
}