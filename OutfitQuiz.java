
import java.util.Scanner;  // for reading from keyboard
import javafoundations.LinkedBinaryTree; // for the LinkedBinaryTree and all
/**
 * Write a description of class AnimalExpert here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class OutfitQuiz

//add linked binary tree to java foundations 
{
    // instance variables - replace the example below with your own
    protected LinkedBinaryTree<String> dTree;
    protected Outfit[] outfits;

    /**
     * Constructor for objects of class AnimalExpert
     */
    public OutfitQuiz()
    {
        Top o1t = new Top("Striped Hooded Sweater", "grey", "striped", "cold", "stripe_sweater.jpeg", "long");
        Bottom o1b = new Bottom("Blue Jeans", "blue", "denim", "neutral", "blue_jeans.jpeg", "long");
        Outfit o1 = new Outfit(o1t, o1b);
        o1.setOccasion("casual");

        Top o2t = new Top("UCSC Crewneck Sweatshirt", "maroon", "print", "cold", "ucsc_crew.jpeg", "long");
        Bottom o2b = new Bottom("Dark Jeans", "dark blue", "denim", "neutral", "dark_jeans.jpeg", "long");
        Outfit o2 = new Outfit(o2t, o2b);
        o2.setOccasion("casual");

        Top o3t = new Top("Grey Cardigan", "grey", "cableknit", "cold", "grey_cardigan.jpeg", "long");
        Bottom o3b = new Bottom("Formal Pants", "black", "plain", "neutral", "formal pants.jpeg", "long");
        Outfit o3 = new Outfit(o3t, o3b);
        o3.setOccasion("formal");

        Top o4t = new Top("Grey Babydoll Top", "grey", "plain", "cold", "grey_babydoll_longsleeve.jpeg", "long");
        Bottom o4b = new Bottom("Low Rise Jeans", "blue", "denim", "neutral", "low_rise_jeans.jpeg", "long");
        Outfit o4 = new Outfit(o4t, o4b);
        o4.setOccasion("casual");

        Top o5t = new Top("Flowy Tube Top", "cream", "crepe", "warm", "flowy_tube.jpeg", "sleeveless");
        Bottom o5b = new Bottom("Short Denim Skirt", "light blue", "denim", "warm", "green_denim_skirt.jpeg", "short");
        Outfit o5 = new Outfit(o5t, o5b);
        o5.setOccasion("casual");

        Top o6t = new Top("Striped Tube Top", "navy", "striped", "warm", "stiped_tube.jpeg", "sleeveless");
        Bottom o6b = new Bottom("Lace Maxi Skirt", "white", "lace", "warm", "white_lace_maxi.jpeg", "long");
        Outfit o6 = new Outfit(o6t, o6b);
        o6.setOccasion("casual");

        Top o7t = new Top("Yellow V-Tank", "yellow", "plain", "warm", "yellow_v_tank.jpeg", "short");
        Bottom o7b = o3b;
        Outfit o7 = new Outfit(o7t, o7b);
        o7.setOccasion("formal");

        Top o8t = new Top("Lace Tank Top", "white", "lace", "warm", "cream_lace_tank.jpeg", "short");
        Bottom o8b = new Bottom("Studded Jeans", "blue", "denim", "neutral", "studded_jeans.jpeg", "long");
        Outfit o8 = new Outfit(o8t, o8b);
        o8.setOccasion("casual");

        outfits = new {o1,o2,o3,o4,o5,o6,o7,o8};



        LinkedBinaryTree<String> t1 = new LinkedBinaryTree<String>("Try this!\n" + o1);
        LinkedBinaryTree<String> t2 = new LinkedBinaryTree<String>("Try this!\n" + o2);
        LinkedBinaryTree<String> t3 = new LinkedBinaryTree<String>("Try this!\n" + o3);
        LinkedBinaryTree<String> t4 = new LinkedBinaryTree<String>("Try this!\n" + o4);
        LinkedBinaryTree<String> t5 = new LinkedBinaryTree<String>("Try this!\n" + o5);
        LinkedBinaryTree<String> t6 = new LinkedBinaryTree<String>("Try this!\n" + o6);
        LinkedBinaryTree<String> t7 = new LinkedBinaryTree<String>("Try this!\n" + o7);
        LinkedBinaryTree<String> t8 = new LinkedBinaryTree<String>("Try this!\n" + o8);

        LinkedBinaryTree<String> q3a = new LinkedBinaryTree<String>("Do you want a hoodie?", t1, t2);
        LinkedBinaryTree<String> q3b = new LinkedBinaryTree<String>("Is it a formal occasion?", t3, t4);
        LinkedBinaryTree<String> q3c = new LinkedBinaryTree<String>("Do you want a short skirt?", t5, t6);
        LinkedBinaryTree<String> q3d = new LinkedBinaryTree<String>("Is it a formal occasion?", t7, t8);

        LinkedBinaryTree<String> q2a = new LinkedBinaryTree<String>("Do you want to wear a sweater?", q3a, q3b);
        LinkedBinaryTree<String> q2b = new LinkedBinaryTree<String>("Do you want to wear a skirt?", q3c, q3d);

        LinkedBinaryTree<String> q1 = new LinkedBinaryTree<String>("Is it cold outside?");

        dTree = q1;
        
    }

    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    public void guessMyAnimal()
    {
        boolean playing =  true;
        String answer;
        
        Scanner scanner = new Scanner(System.in);
        
        while(playing){
            System.out.println("\n==I will try to guess the animal you are thinking of!==");
            
            System.out.println("\nQ: " + dTree.getRootElement() + " (y/n).");
            
            answer = scanner.next();
            
            if(answer.trim().toLowerCase().equals("y")){
                System.out.println("\nQ: " + dTree.getRight().getRootElement() + " (y/n).");
                
                answer = scanner.next();
                
                if(answer.trim().toLowerCase().equals("y")){
                    System.out.println("\nQ: " + dTree.getRight().getRight().getRootElement() + " (y/n).");
                    
                    answer = scanner.next();
                    
                    if(answer.trim().toLowerCase().equals("y")){
                        System.out.println("\nAND...: " + dTree.getRight().getRight().getRight().getRootElement());
                    }
                    else{
                        System.out.println("\nAND...: " + dTree.getRight().getRight().getLeft().getRootElement());
                    }
                }
                else{
                    System.out.println("\nQ: " + dTree.getRight().getLeft().getRootElement() + " (y/n).");
                    
                    answer = scanner.next();
                    
                    if(answer.trim().toLowerCase().equals("y")){
                        System.out.println("\nAND...: " + dTree.getRight().getLeft().getRight().getRootElement());
                    }
                    else{
                        System.out.println("\nAND...: " + dTree.getRight().getLeft().getLeft().getRootElement());
                    }
                    
                }
            }
            else{
                System.out.println("\nQ: " + dTree.getLeft().getRootElement() + " (y/n).");
                
                answer = scanner.next();
                
                if(answer.trim().toLowerCase().equals("y")){
                    System.out.println("\nQ: " + dTree.getLeft().getRight().getRootElement() + " (y/n).");
                    
                    answer = scanner.next();
                    
                    if(answer.trim().toLowerCase().equals("y")){
                        System.out.println("\nAND...: " + dTree.getLeft().getRight().getRight().getRootElement());
                    }
                    else{
                        System.out.println("\nAND...: " + dTree.getLeft().getRight().getLeft().getRootElement());
                    }
                }
                else{
                    System.out.println("\nQ: " + dTree.getLeft().getLeft().getRootElement() + " (y/n).");
                    
                    answer = scanner.next();
                    
                    if(answer.trim().toLowerCase().equals("y")){
                        System.out.println("\nAND...: " + dTree.getLeft().getLeft().getRight().getRootElement());
                    }
                    else{
                        System.out.println("\nAND...: " + dTree.getLeft().getLeft().getLeft().getRootElement());
                    }
                    
                }
            }
            
            
            System.out.println("\nWould you like to play again? (y/n).");
            
            answer = scanner.next();
            
            if(answer.trim().toLowerCase().equals("n")){
                playing = false;
                scanner.close();
                System.out.println("Thanks for playing! Goodbye!");
            }
        }
        
    }
    
    public static void main(String[] args){
        AnimalExpert a = new AnimalExpert();
        
        a.guessMyAnimal();
    }
}