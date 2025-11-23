public class Top extends Clothing {
    private String sleeveLength; // e.g., "short", "long", "sleeveless"

    public Top(String name, String color, String pattern, String season, String imagePath, String sleeveLength) {
        super(name, color, pattern, season, imagePath);
        this.sleeveLength = sleeveLength;
    }

    public String getSleeveLength() { return sleeveLength; }
    public void setSleeveLength(String s) { this.sleeveLength = s; }

    @Override
    public String toString() {
        return "Top: " + super.toString() + " [" + sleeveLength + "]";
    }
}