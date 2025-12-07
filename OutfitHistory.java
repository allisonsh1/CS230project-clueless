import javafoundations.CircularArrayQueue;

/**
 * Manages a history of saved outfits using a CircularArrayQueue.
 * Maintains a maximum of 7 outfits and automatically removes the oldest when capacity is reached.
 * 
 * @author Allison
 */
public class OutfitHistory {
    private CircularArrayQueue<Outfit> history;
    private static final int MAX_CAPACITY = 7;
    
    /**
     * Constructor for OutfitHistory class.
     */
    public OutfitHistory() {
        history = new CircularArrayQueue<>();
    }
    
    /**
     * Adds an outfit to the history. If the history is at capacity, the oldest outfit is automatically removed.
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
     * @return The size of the history
     */
    public int size() {
        return history.size();
    }
    
    /**
     * Checks if the history is empty.
     * @return true if no outfits are saved
     */
    public boolean isEmpty() {
        return history.isEmpty();
    }

    /**
     * Gets the outfit history queue.
     * @return
     */
    public CircularArrayQueue<Outfit> getHistory() {
        return history;
    }


    public static void main(String[] args)throws Exception {
        //TEST CODE FOR SAVING TO FILE
        /*Top top1 = new Top("T-Shirt", "Red", "Solid", "Summer", "path/to/image1.jpg", "Short");
        Bottom bottom1 = new Bottom("Jeans", "Blue", "Solid", "All", "path/to/image2.jpg", "Long");
        Outfit outfit1 = new Outfit(top1, bottom1);
        OutfitHistory outfitHistory = new OutfitHistory();
        outfitHistory.addOutfit(outfit1);
        SavingHistory savingHistory = new SavingHistory();

        savingHistory.saveHistory(outfitHistory.getHistory()); 
        */

        //TEST CODE FOR READING FROM FILE
        SavingHistory savingHistory = new SavingHistory();
        CircularArrayQueue<Outfit> loadedHistory = savingHistory.readHistory();
        OutfitHistory outfitHistory = new OutfitHistory();
        while (!loadedHistory.isEmpty()) {
            outfitHistory.addOutfit(loadedHistory.dequeue());
        }
        System.out.println(outfitHistory.getHistoryDisplay());
    }
}