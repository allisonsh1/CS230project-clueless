import java.io.PrintWriter;
import java.io.File;
import java.util.Scanner;
import javafoundations.CircularArrayQueue;

/**
 * Save history data to a file 
 */
public class SavingHistory {
    private File fileHistory = new File("history.txt");
    
    /**
     * Write outfit history to file.
     * Format: topName|topColor|topPattern|topSeason|topImagePath|topSleeveLength|bottomName|bottomColor|bottomPattern|bottomSeason|bottomImagePath|bottomLength
     * 
     * @param history The queue of outfits to save
     * @throws Exception if file writing fails
     */
    public void saveHistory(CircularArrayQueue<Outfit> history) throws Exception {
        PrintWriter writer = new PrintWriter(fileHistory);
        
        // Temporary queue to preserve original history
        CircularArrayQueue<Outfit> temp = new CircularArrayQueue<>();
        
        // Write each outfit to file and preserve in temp queue
        while (!history.isEmpty()) {
            Outfit outfit = history.dequeue();
            
            Top top = (Top) outfit.getTop();
            Bottom bottom = (Bottom) outfit.getBottom();
            
            // Save outfit data in parseable format with all attributes
            writer.println(
                top.getName() + "|" + 
                top.getColor() + "|" +
                top.getPattern() + "|" + 
                top.getSeason() + "|" +
                top.getImagePath() + "|" +
                top.getSleeveLength() + "|" +
                bottom.getName() + "|" +
                bottom.getColor() + "|" +
                bottom.getPattern() + "|" +
                bottom.getSeason() + "|" +
                bottom.getImagePath() + "|" +
                bottom.getLength()
            );
            
            temp.enqueue(outfit);
        }
        
        // Restore original history queue
        while (!temp.isEmpty()) {
            history.enqueue(temp.dequeue());
        }
        
        writer.close();
    }

    /**
     * Read outfit history from file and reconstruct Outfit objects.
     * 
     * @return A queue containing the saved outfits
     * @throws Exception if file reading fails or file doesn't exist
     */
    public CircularArrayQueue<Outfit> readHistory() throws Exception {
        CircularArrayQueue<Outfit> pastOutfits = new CircularArrayQueue<>();
        
        // Check if file exists
        if (!fileHistory.exists()) {
            System.out.println("History file does not exist");
            return pastOutfits; // Return empty queue if no history file
        }
        
        Scanner scanner = new Scanner(fileHistory);
        int lineNumber = 0;
        
        while (scanner.hasNextLine()) {
            lineNumber++;
            String line = scanner.nextLine().trim();
            
            // Skip empty lines
            if (line.isEmpty()) {
                continue;
            }
            
            try {
                // Parse: topName|topColor|topPattern|topSeason|topImagePath|topSleeveLength|bottomName|bottomColor|bottomPattern|bottomSeason|bottomImagePath|bottomLength
                String[] parts = line.split("\\|");
                
                System.out.println("Line " + lineNumber + ": Found " + parts.length + " parts");
                
                if (parts.length == 12) {
                    // Create Top with all attributes
                    Top top = new Top(
                        parts[0],  // name
                        parts[1],  // color
                        parts[2],  // pattern
                        parts[3],  // season
                        parts[4],  // imagePath
                        parts[5]   // sleeveLength
                    );
                    
                    // Create Bottom with all attributes
                    Bottom bottom = new Bottom(
                        parts[6],  // name
                        parts[7],  // color
                        parts[8],  // pattern
                        parts[9],  // season
                        parts[10], // imagePath
                        parts[11]  // length
                    );
                    
                    // Create and add Outfit
                    Outfit outfit = new Outfit(top, bottom);
                    pastOutfits.enqueue(outfit);
                    System.out.println("Successfully loaded outfit: " + outfit.toString());
                } else {
                    System.err.println("Line " + lineNumber + ": Expected 12 parts but got " + parts.length);
                    System.err.println("Line content: " + line);
                }
            } catch (Exception e) {
                System.err.println("Error parsing line " + lineNumber + ": " + e.getMessage());
                e.printStackTrace();
            }
        }
        
        scanner.close();
        System.out.println("Total outfits loaded: " + pastOutfits.size());
        return pastOutfits;
    }
}