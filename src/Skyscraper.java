import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

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

    private boolean checkUniquenessRestrictions(int[][] board) {
        return checkUniquenessRows(board) && checkUniquenessColumns(board);
    }

    private boolean checkUniquenessRows(int[][] board) {
        boolean meetsRestrictions = true;

        for (int row = 0; row < dimensions && meetsRestrictions; row++) {
            Set<Integer> itemsSet = new TreeSet<>();
            int zeros = 0;

            for (int column = 0; column < dimensions; column++) {
                if (board[row][column] != 0)
                    itemsSet.add(board[row][column]);
                else
                    zeros++;
            }
            if (itemsSet.size() != dimensions - zeros) {
                meetsRestrictions = false;
            }
        }
        return meetsRestrictions;
    }

    private boolean checkUniquenessColumns(int[][] board) {
        boolean meetsRestrictions = true;

        for (int column = 0; column < dimensions && meetsRestrictions; column++) {
            Set<Integer> itemsSet = new TreeSet<>();
            int zeros = 0;

            for (int row = 0; row < dimensions; row++) {
                if (board[row][column] != 0)
                    itemsSet.add(board[row][column]);
                else
                    zeros++;
            }

            if (itemsSet.size() != dimensions - zeros) {
                meetsRestrictions = false;
            }
        }
        return meetsRestrictions;
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
