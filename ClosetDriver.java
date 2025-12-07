public class ClosetDriver {
    
    public static void main(String[] args) {
        System.out.println("!!!CLUELESS CLOSET CS230X!!!");
        
        Closet closet = new Closet(8);
        System.out.println("Creating closet and adding items.");
        closet.addTop(new Top("Blue Shirt", "blue", "solid", "warm", 
            "images/blue_shirt.png", "short"));
        closet.addTop(new Top("Green Shirt", "green", "solid", "warm", 
            "images/green_shirt.png", "short"));
        closet.addBottom(new Bottom("Blue Jeans", "blue", "denim", "warm", 
            "images/blue_jeans.png", "full"));
        closet.addBottom(new Bottom("Khaki Pants", "khaki", "solid", "warm", 
            "images/khaki_pants.png", "full"));
        System.out.println("Closet loaded with 2 tops and 2 bottoms");
        
        System.out.println("Creating carousels from closet rows...");
        Carousel topCarousel = new Carousel(closet, 0);
        Carousel bottomCarousel = new Carousel(closet, 1);
        
        System.out.println("CAROUSEL NAVIGATION");
        System.out.println("Current top: " + topCarousel.current());
        System.out.println("Current bottom: " + bottomCarousel.current());
        System.out.println();
        
        System.out.println("Rotating to next top:");
        topCarousel.next();
        System.out.println("Current top: " + topCarousel.current());
        System.out.println();
        
        System.out.println("Rotating to next bottom:");
        bottomCarousel.next();
        System.out.println("Current bottom: " + bottomCarousel.current());
        System.out.println();
        

        System.out.println("OUTFIT HISTORY");
        OutfitHistory history = new OutfitHistory();
        
        System.out.println("Saving current outfit:");
        Outfit outfit1 = new Outfit((Top)topCarousel.current(), (Bottom)bottomCarousel.current());
        history.addOutfit(outfit1);
        System.out.println("Saved: " + outfit1);
        System.out.println();
        
        topCarousel.next();
        bottomCarousel.next();
        System.out.println("Saving another outfit:");
        Outfit outfit2 = new Outfit((Top)topCarousel.current(), (Bottom)bottomCarousel.current());
        history.addOutfit(outfit2);
        System.out.println("Saved: " + outfit2);
        System.out.println();
        
        System.out.println("TESTING OUTFIT MATCHING WITH MATCHER");
        Matcher matcher = new Matcher();
        System.out.println();
        
        System.out.println("Testing outfit 1:");
        System.out.println(outfit1);
        boolean clashes1 = matcher.clashes(outfit1);
        if (clashes1) {
            System.out.println("Result: CLASHES!");
        } else {
            System.out.println("Result: MATCHES!");
        }
        System.out.println();
        
        System.out.println("Testing outfit 2:");
        System.out.println(outfit2);
        boolean clashes2 = matcher.clashes(outfit2);
        if (clashes2) {
            System.out.println("Result: CLASHES!");
        } else {
            System.out.println("Result: MATCHES!");
        }
        System.out.println();

        // Display history
        System.out.println("Final Display:");
        System.out.println(history.getHistoryDisplay());
        
    }
}