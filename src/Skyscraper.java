import java.util.Arrays;

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

    int getDimensions() {
        return dimensions;
    }

    void setBoard(int[][] board) {
        this.board = board;
    }

    int[][] getBoard() {
        return board;
    }

    void setRestrictionsTop(int[] restrictionsTop) {
        this.restrictionsTop = restrictionsTop;
    }

    void setRestrictionsRight(int[] restrictionsRight) {
        this.restrictionsRight = restrictionsRight;
    }

    void setRestrictionsBottom(int[] restrictionsBottom) {
        this.restrictionsBottom = restrictionsBottom;
    }

    void setRestrictionsLeft(int[] restrictionsLeft) {
        this.restrictionsLeft = restrictionsLeft;
    }

    String getRestrictionsString() {
        return "TOP: " + Arrays.toString(restrictionsTop) + "\n" +
                "RIGHT: " + Arrays.toString(restrictionsRight) + "\n" +
                "BOTTOM: " + Arrays.toString(restrictionsBottom) + "\n" +
                "LEFT: " + Arrays.toString(restrictionsLeft) + "\n";
    }
}
