public class Bottom extends Clothing {
    private String length; // e.g., "shorts", "ankle", "full"

    public Bottom(String name, String color, String pattern, String season, String imagePath, String length) {
        super(name, color, pattern, season, imagePath);
        this.length = length;
    }

    public String getLength() { return length; }
    public void setLength(String s) { this.length = s; }

    @Override
    public String toString() {
        return "Bottom: " + super.toString() + " [" + length + "]";
    }
}