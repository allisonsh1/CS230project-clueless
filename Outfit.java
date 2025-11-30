import java.time.LocalDate;

public class Outfit {
    private Top top;
    private Bottom bottom;
    private String occasion;
    private LocalDate dateWorn;

    public Outfit(Top top, Bottom bottom) {
        this.top = top;
        this.bottom = bottom;
        this.occasion = "casual";
        this.dateWorn = null;
    }

    public Top getTop() { return top; }
    public Bottom getBottom() { return bottom; }
    public String getOccasion() { return occasion; }
    public void setOccasion(String occasion) { this.occasion = occasion; }

    public LocalDate getDateWorn() { return dateWorn; }
    public void setDateWorn(LocalDate dateWorn) { this.dateWorn = dateWorn; }

    public boolean isMatch() {
        return false;
    }

    @Override
    public String toString() {
        return "Outfit: " + top + " + " + bottom + "(occasion: " + occasion + ", date: " + dateWorn + ")";
    }
}