public class Skyscraper {
    private int dimensions;

    private int[] restrictionsTop;
    private int[] restrictionsRight;
    private int[] restrictionsBottom;
    private int[] restrictionsLeft;

    private int[][] board;

    Skyscraper(int dimensions) {
        this.dimensions = dimensions;
        board = new int[dimensions][dimensions];

        Arrays.fill(board, 0);
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
