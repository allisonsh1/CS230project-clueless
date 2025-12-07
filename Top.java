/**
 * Represents a top clothing item with specific attributes.
 * @author Allison
 */
public class Top extends Clothing {
    private String sleeveLength; // e.g., "short", "long", "sleeveless"

    /**
     * Constructor for Top class.
     * @param name
     * @param color
     * @param pattern
     * @param season
     * @param imagePath
     * @param sleeveLength
     */
    public Top(String name, String color, String pattern, String season, String imagePath, String sleeveLength) {
        super(name, color, pattern, season, imagePath);
        this.sleeveLength = sleeveLength;
    }

    /**
     * Getter and Setter for sleeveLength
     * @return
     */
    public String getSleeveLength() { return sleeveLength; }
    public void setSleeveLength(String s) { this.sleeveLength = s; }

    /**
     * String representation of Top
     * @return string representation of Top
     */
    @Override
    public String toString() {
        return "Top: " + super.toString() + " [" + sleeveLength + "]";
    }
}