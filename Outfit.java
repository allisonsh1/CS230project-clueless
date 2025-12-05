public class Outfit {
    private Top top;
    private Bottom bottom;
    private String occasion;

    public Outfit(Top top, Bottom bottom) {
        this.top = top;
        this.bottom = bottom;
        this.occasion = "casual";
    }

    public Top getTop() { return top; }
    public Bottom getBottom() { return bottom; }
    public String getOccasion() { return occasion; }
    public void setOccasion(String occasion) { this.occasion = occasion; }


    /**
     * WORK IN PROGRESS!
     * @return
     */
    public boolean isMatch() {
        return false;
    }

    /**
     * Returns a String representation of the outfit.
     */
    @Override
    public String toString() {
        return "Outfit: " + top + " + " + bottom + "(occasion: " + occasion + ")";
    }
}