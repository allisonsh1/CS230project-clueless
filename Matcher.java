import java.util.ArrayList;
import javafoundations.AdjListsGraph;

/**
 * Matcher class to determine if an outfit has clashing elements based on color, pattern, and season using graphs.
 * @author Rue
 */
public class Matcher {
    protected AdjListsGraph<String> clashesC; //color clashes
    protected AdjListsGraph<String> clashesP; //pattern clashes
    protected AdjListsGraph<String> clashesS; //season clashes

    /**
     * Constructor for Matcher class.
     * Initializes the clash graphs with vertices and edges.
     */
    public Matcher() {
        clashesC = new AdjListsGraph<String>();
        clashesP = new AdjListsGraph<String>();
        clashesS = new AdjListsGraph<String>(); // FIXED: was clashesP again

        // Pattern clashes
        clashesP.addVertex("striped");
        clashesP.addVertex("polka dots");
        clashesP.addVertex("denim");
        clashesP.addVertex("flannel");

        clashesP.addEdge("striped", "polka dots");
        clashesP.addEdge("denim", "flannel");
        clashesP.addEdge("polka dots", "flannel");
        clashesP.addEdge("striped", "flannel");

        // Color clashes
        clashesC.addVertex("green");
        clashesC.addVertex("red");
        clashesC.addVertex("brown");
        clashesC.addVertex("neon yellow");

        clashesC.addEdge("green", "red");
        clashesC.addEdge("brown", "red");
        clashesC.addEdge("brown", "neon yellow");

        // Season clashes
        clashesS.addVertex("cold");
        clashesS.addVertex("warm");
        clashesS.addEdge("cold", "warm");
    }

    /**
     * Determines if the given outfit has clashing elements.
     * Checks for clashes in color, pattern, and season between top and bottom.
     * 
     * @param outfit the outfit to check for clashes
     * @return true if there are clashing elements, false otherwise
     */
    public boolean clashes(Outfit outfit) {
        // Get potential clashes from the top item
        ArrayList<String> potentialColorClashes = (ArrayList<String>) clashesC.getNeighbors(outfit.getTop().getColor());
        ArrayList<String> potentialPatternClashes = (ArrayList<String>) clashesP.getNeighbors(outfit.getTop().getPattern());
        ArrayList<String> potentialSeasonClashes = (ArrayList<String>) clashesS.getNeighbors(outfit.getTop().getSeason());

        // Check if any neighbors are null (item not in graph)
        if (potentialColorClashes == null) {
            potentialColorClashes = new ArrayList<String>();
        }
        if (potentialPatternClashes == null) {
            potentialPatternClashes = new ArrayList<String>();
        }
        if (potentialSeasonClashes == null) {
            potentialSeasonClashes = new ArrayList<String>();
        }

        // Check for color clashes
        for (String clash : potentialColorClashes) {
            if (outfit.getBottom().getColor().equals(clash)) {
                System.out.println("Color clash detected: " + outfit.getTop().getColor() + " clashes with " + clash);
                return true;
            }
        }

        // Check for pattern clashes
        for (String clash : potentialPatternClashes) {
            if (outfit.getBottom().getPattern().equals(clash)) {
                System.out.println("Pattern clash detected: " + outfit.getTop().getPattern() + " clashes with " + clash);
                return true;
            }
        }

        // Check for season clashes
        for (String clash : potentialSeasonClashes) {
            if (outfit.getBottom().getSeason().equals(clash)) {
                System.out.println("Season clash detected: " + outfit.getTop().getSeason() + " clashes with " + clash);
                return true;
            }
        }

        System.out.println("No clashes detected - outfit is good!");
        return false;
    }
}