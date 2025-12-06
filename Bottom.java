/**
 * This is a class that represents a bottom clothing item.
 * @author Allison
 */
public class Bottom extends Clothing {
    private String length;

    /**
     * Constructor for Bottom class.
     * @param name
     * @param color
     * @param pattern
     * @param season
     * @param imagePath
     * @param length
     */
    public Bottom(String name, String color, String pattern, String season, String imagePath, String length) {
        super(name, color, pattern, season, imagePath);
        this.length = length;
    }

    /**
     * Getter for length
     * @return length of the bottom
     */
    public String getLength() { return length; }

    /**
     * Setter for length
     * @param s new length of the bottom
     */
    public void setLength(String s) { this.length = s; }

    /**
     * String representation of Bottom
     * @return string representation of Bottom
     */
    @Override
    public String toString() {
        return "Bottom: " + super.toString() + " [" + length + "]";
    }
}