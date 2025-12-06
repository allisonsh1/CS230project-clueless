/**
 * Represents an outfit consisting of a top and a bottom.
 * @author Allison
 */
public class Outfit {
    private Top top;
    private Bottom bottom;
    private String occasion;

    /**
     * Constructor for Outfit class.
     * @param top
     * @param bottom
     */
    public Outfit(Top top, Bottom bottom) {
        this.top = top;
        this.bottom = bottom;
        this.occasion = "casual";
    }

    /**
     * Getters and Setters
     * @return
     */
    public Top getTop() { return top; }
    public Bottom getBottom() { return bottom; }
    public String getOccasion() { return occasion; }
    public void setOccasion(String occasion) { this.occasion = occasion; }


    /**
     * Checks if the outfit is a match
     * @return
     */
    public boolean isMatch() {
        return false;
    }

    /**
     * String representation of Outfit
     * @return string representation of Outfit
     */
    @Override
    public String toString() {
        return "Outfit: " + top + " + " + bottom + "(occasion: " + occasion + ")";
    }
}