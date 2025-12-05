import java.util.Scanner;
import javafoundations.LinkedBinaryTree;

/**
 * OutfitQuiz uses a decision tree to recommend outfits based on user preferences.
 * Users answer yes/no questions to navigate through the tree and find a suitable outfit.
 */
public class OutfitQuiz {
    protected LinkedBinaryTree<String> dTree;
    protected Outfit[] outfits;

    /**
     * Constructor for OutfitQuiz
     * Builds the decision tree with outfit recommendations
     */
    public OutfitQuiz() {
        // Create outfit 1
        Top o1t = new Top("Striped Hooded Sweater", "grey", "striped", "cold", "stripe_sweater.jpeg", "long");
        Bottom o1b = new Bottom("Blue Jeans", "blue", "denim", "neutral", "blue_jeans.jpeg", "long");
        Outfit o1 = new Outfit(o1t, o1b);
        o1.setOccasion("casual");

        // Create outfit 2
        Top o2t = new Top("UCSC Crewneck Sweatshirt", "maroon", "print", "cold", "ucsc_crew.jpeg", "long");
        Bottom o2b = new Bottom("Dark Jeans", "dark blue", "denim", "neutral", "dark_jeans.jpeg", "long");
        Outfit o2 = new Outfit(o2t, o2b);
        o2.setOccasion("casual");

        // Create outfit 3
        Top o3t = new Top("Grey Cardigan", "grey", "cableknit", "cold", "grey_cardigan.jpeg", "long");
        Bottom o3b = new Bottom("Formal Pants", "black", "plain", "neutral", "formal_pants.jpeg", "long");
        Outfit o3 = new Outfit(o3t, o3b);
        o3.setOccasion("formal");

        // Create outfit 4
        Top o4t = new Top("Grey Babydoll Top", "grey", "plain", "cold", "grey_babydoll_longsleeve.jpeg", "long");
        Bottom o4b = new Bottom("Low Rise Jeans", "blue", "denim", "neutral", "low_rise_jeans.jpeg", "long");
        Outfit o4 = new Outfit(o4t, o4b);
        o4.setOccasion("casual");

        // Create outfit 5
        Top o5t = new Top("Flowy Tube Top", "cream", "crepe", "warm", "flowy_tube.jpeg", "sleeveless");
        Bottom o5b = new Bottom("Short Denim Skirt", "light blue", "denim", "warm", "green_denim_skirt.jpeg", "short");
        Outfit o5 = new Outfit(o5t, o5b);
        o5.setOccasion("casual");

        // Create outfit 6
        Top o6t = new Top("Striped Tube Top", "navy", "striped", "warm", "stiped_tube.jpeg", "sleeveless");
        Bottom o6b = new Bottom("Lace Maxi Skirt", "white", "lace", "warm", "white_lace_maxi.jpeg", "long");
        Outfit o6 = new Outfit(o6t, o6b);
        o6.setOccasion("casual");

        // Create outfit 7
        Top o7t = new Top("Yellow V-Tank", "yellow", "plain", "warm", "yellow_v_tank.jpeg", "short");
        Bottom o7b = o3b; // Reuse formal pants from outfit 3
        Outfit o7 = new Outfit(o7t, o7b);
        o7.setOccasion("formal");

        // Create outfit 8
        Top o8t = new Top("Lace Tank Top", "white", "lace", "warm", "cream_lace_tank.jpeg", "short");
        Bottom o8b = new Bottom("Studded Jeans", "blue", "denim", "neutral", "studded_jeans.jpeg", "long");
        Outfit o8 = new Outfit(o8t, o8b);
        o8.setOccasion("casual");

        // Fixed array initialization syntax
        outfits = new Outfit[]{o1, o2, o3, o4, o5, o6, o7, o8};

        // Build the decision tree leaves (outfit recommendations)
        LinkedBinaryTree<String> t1 = new LinkedBinaryTree<String>("Try this!\n" + o1.toString());
        LinkedBinaryTree<String> t2 = new LinkedBinaryTree<String>("Try this!\n" + o2.toString());
        LinkedBinaryTree<String> t3 = new LinkedBinaryTree<String>("Try this!\n" + o3.toString());
        LinkedBinaryTree<String> t4 = new LinkedBinaryTree<String>("Try this!\n" + o4.toString());
        LinkedBinaryTree<String> t5 = new LinkedBinaryTree<String>("Try this!\n" + o5.toString());
        LinkedBinaryTree<String> t6 = new LinkedBinaryTree<String>("Try this!\n" + o6.toString());
        LinkedBinaryTree<String> t7 = new LinkedBinaryTree<String>("Try this!\n" + o7.toString());
        LinkedBinaryTree<String> t8 = new LinkedBinaryTree<String>("Try this!\n" + o8.toString());

        // Build level 3 questions (left = yes, right = no)
        LinkedBinaryTree<String> q3a = new LinkedBinaryTree<String>("Do you want a hoodie?", t1, t2);
        LinkedBinaryTree<String> q3b = new LinkedBinaryTree<String>("Is it a formal occasion?", t3, t4);
        LinkedBinaryTree<String> q3c = new LinkedBinaryTree<String>("Do you want a short skirt?", t5, t6);
        LinkedBinaryTree<String> q3d = new LinkedBinaryTree<String>("Is it a formal occasion?", t7, t8);

        // Build level 2 questions
        LinkedBinaryTree<String> q2a = new LinkedBinaryTree<String>("Do you want to wear a sweater?", q3a, q3b);
        LinkedBinaryTree<String> q2b = new LinkedBinaryTree<String>("Do you want to wear a skirt?", q3c, q3d);

        // Build root question (left = yes [cold], right = no [warm])
        LinkedBinaryTree<String> q1 = new LinkedBinaryTree<String>("Is it cold outside?", q2a, q2b);

        dTree = q1;
    }

    /**
     * Runs the outfit recommendation quiz.
     * Guides user through yes/no questions to find the perfect outfit.
     */
    public void guessMyOutfit() {
        boolean playing = true;
        String answer;
        
        Scanner scanner = new Scanner(System.in);
        
        while (playing) {
            System.out.println("\n== I will help you find the perfect outfit! ==");
            
            // Start at root
            LinkedBinaryTree<String> current = dTree;
            
            // Navigate through the tree
            while (current.getLeft() != null && current.getRight() != null) {
                System.out.println("\nQ: " + current.getRootElement() + " (y/n)");
                answer = scanner.next();
                
                if (answer.trim().toLowerCase().equals("y")) {
                    current = current.getLeft();  // Yes branch
                } else {
                    current = current.getRight(); // No branch
                }
            }
            
            // Display the final recommendation
            System.out.println("\n" + current.getRootElement());
            
            // Ask to play again
            System.out.println("\nWould you like to try again? (y/n)");
            answer = scanner.next();
            
            if (answer.trim().toLowerCase().equals("n")) {
                playing = false;
                scanner.close();
                System.out.println("\nThanks for using the outfit quiz! Goodbye!");
            }
        }
    }
    
    /**
     * Gets all outfits in the quiz
     */
    public Outfit[] getOutfits() {
        return outfits;
    }
    
    /**
     * Main method for testing
     */
    public static void main(String[] args) {
        OutfitQuiz quiz = new OutfitQuiz();
        quiz.guessMyOutfit();
    }
}