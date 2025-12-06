/**
 * Abstract class representing a piece of clothing.
 * @author Allison
 */
public abstract class Clothing {
    protected String name;
    protected String color;
    protected String pattern;
    protected String season;
    protected String imagePath;

    /**
     * Constructor for Clothing class.
     * @param name
     * @param color
     * @param pattern
     * @param season
     * @param imagePath
     */
    public Clothing(String name, String color, String pattern, String season, String imagePath) {
        this.name = name;
        this.color = color;
        this.pattern = pattern;
        this.season = season;
        this.imagePath = imagePath;
    }

    //Getters
    public String getName() { return name; }
    public String getColor() { return color; }
    public String getPattern() { return pattern; }
    public String getSeason() { return season; }
    public String getImagePath() { return imagePath; }

    //Setters
    public void setName(String name) { this.name = name; }
    public void setColor(String color) { this.color = color; }
    public void setPattern(String pattern) { this.pattern = pattern; }
    public void setSeason(String season) { this.season = season; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

    /**
     * String representation of Clothing
     * @return string representation of Clothing
     */
    @Override
    public String toString() {
        return name + " (" + color + ", " + pattern + ", " + season + ")";
    }

}