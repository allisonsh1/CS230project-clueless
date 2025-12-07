import java.util.ArrayList;
import javafoundations.AdjListsGraph;

/**
 * Matcher class to determine if an outfit has clashing elements based on color, pattern, and season using graphs.
 * @author Rue
 */
public class Matcher{
    protected AdjListsGraph<String> clashesC; //color classes
    protected AdjListsGraph<String> clashesP; //pattern classes
    protected AdjListsGraph<String> clashesS; //season classes

    /**
     * Constructor for Matcher class.
     */
    public Matcher(){
        clashesC = new AdjListsGraph<String>();
        clashesP = new AdjListsGraph<String>();
        clashesP = new AdjListsGraph<String>();

        clashesP.addVertex("striped");
        clashesP.addVertex("polka dots");
        clashesP.addVertex("denim");
        clashesP.addVertex("flannel");

        clashesP.addEdge("striped", "polka dots");
        clashesP.addEdge("denim", "flannel");
        clashesP.addEdge("polka dots", "flannel");
        clashesP.addEdge("striped", "flannel");

        clashesC.addVertex("green");
        clashesC.addVertex("red");
        clashesC.addVertex("brown");
        clashesC.addVertex("neon yellow");

        clashesC.addEdge("green", "red");
        clashesC.addEdge("brown", "red");
        clashesC.addEdge("brown", "yellow");

        clashesS.addVertex("cold");
        clashesS.addVertex("warm");
        clashesS.addEdge("cold", "warm");
    }

    /**
     * Determines if the given outfit has clashing elements.
     * @param outfit the outfit to check for clashes
     * @return true if there are clashing elements, false otherwise
     */
    public boolean clashes(Outfit outfit){
        ArrayList<String> potentialColorClashes = new ArrayList<String>();
        ArrayList<String> potentialPatternClashes = new ArrayList<String>();
        ArrayList<String> potentialSeasonClashes = new ArrayList<String>();

        potentialColorClashes = (ArrayList<String>) clashesC.getNeighbors(outfit.getTop().color);
        potentialPatternClashes = (ArrayList<String>) clashesP.getNeighbors(outfit.getTop().pattern);
        potentialSeasonClashes = (ArrayList<String>) clashesS.getNeighbors(outfit.getTop().season);

        for(String clash : potentialColorClashes){
            if(outfit.getBottom().color.equals(clash)){
                return true;
            }
        }

        for(String clash : potentialPatternClashes){
            if(outfit.getBottom().pattern.equals(clash)){
                return true;
            }
        }

        for(String clash : potentialSeasonClashes){
            if(outfit.getBottom().color.equals(clash)){
                return true;
            }
        }

        return false;
        
    }

}