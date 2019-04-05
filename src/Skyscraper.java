public class Skyscraper {
    private int dimensions;

    private int[] restrictionsTop;
    private int[] restrictionsRight;
    private int[] restrictionsBottom;
    private int[] restrictionsLeft;

    Skyscraper(int dimensions){
        this.dimensions = dimensions;
    }

    public int getDimensions() {
        return dimensions;
    }

    public void setRestrictionsTop(int[] restrictionsTop) {
        this.restrictionsTop = restrictionsTop;
    }

    public void setRestrictionsRight(int[] restrictionsRight) {
        this.restrictionsRight = restrictionsRight;
    }

    public void setRestrictionsBottom(int[] restrictionsBottom) {
        this.restrictionsBottom = restrictionsBottom;
    }

    public void setRestrictionsLeft(int[] restrictionsLeft) {
        this.restrictionsLeft = restrictionsLeft;
    }
}
