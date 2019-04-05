public class Skyscraper {
    private int dimensions;

    private int[] floorsTop;
    private int[] floorsRight;
    private int[] floorsBottom;
    private int[] floorsLeft;

    Skyscraper(int dimensions){
        this.dimensions = dimensions;
    }

    public int getDimensions() {
        return dimensions;
    }

    public void setFloorsTop(int[] floorsTop) {
        this.floorsTop = floorsTop;
    }

    public void setFloorsRight(int[] floorsRight) {
        this.floorsRight = floorsRight;
    }

    public void setFloorsBottom(int[] floorsBottom) {
        this.floorsBottom = floorsBottom;
    }

    public void setFloorsLeft(int[] floorsLeft) {
        this.floorsLeft = floorsLeft;
    }
}
