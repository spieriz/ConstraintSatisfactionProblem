import java.util.ArrayList;
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
    }

    void fillBoardWithZero() {
        for (int i = 0; i < dimensions; i++) {
            Arrays.fill(board[i], 0);
        }
    }

    private int getMaxValue(int index){
        int row = index / dimensions;
        int column = index % dimensions;

        int maxTop = dimensions - (restrictionsTop[column] - (row + 1));
        int maxRight = dimensions - (restrictionsRight[row] - (dimensions - (column )));
        int maxBottom = dimensions - (restrictionsBottom[column] - (dimensions - (row)));
        int maxLeft = dimensions - (restrictionsLeft[row] - (column + 1));

        return Math.min(Math.min(Math.min(maxTop, maxRight), Math.min(maxBottom, maxLeft)), dimensions);
    }

    ArrayList<Integer> generateDomains() {
        ArrayList<Integer> domainsList = new ArrayList<>();

        for (int i = 0; i < dimensions * dimensions; i++) {
            domainsList.add(getMaxValue(i));
        }

        return domainsList;
    }

    String printDomain(ArrayList<Integer> domain) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 1; i <= domain.size(); i++) {
            stringBuilder.append(domain.get(i - 1)).append(" ");
            if (i % dimensions == 0)
                stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }

    int[][] calculateSkyscraperBacktracking(int[][] board, int currentIndex) {
        ArrayList<Integer> boardList = boardToList(board);

        for (int cell = currentIndex; cell < dimensions * dimensions && !isCompleted(board); cell++) {

            for (int value = 1; value <= dimensions && !isCompleted(board); value++) {
                boardList.remove(cell);
                boardList.add(cell, value);

                board = boardListToBoard(boardList);

                if (checkUniquenessRestrictions(board) && !isCompleted(board)) {
                    board = calculateSkyscraperBacktracking(board, cell + 1);
                }
            }
        }

        return board;
    }

    private int[][] boardListToBoard(ArrayList<Integer> itemsList) {
        int[][] newBoard = new int[dimensions][dimensions];

        for (int row = 0; row < dimensions; row++) {
            for (int col = 0; col < dimensions; col++) {
                newBoard[row][col] = itemsList.get(row * dimensions + col);
            }
        }

        return newBoard;
    }

    private ArrayList<Integer> boardToList(int[][] board) {
        ArrayList<Integer> newItemsList = new ArrayList<>();

        for (int row = 0; row < dimensions; row++) {
            for (int col = 0; col < dimensions; col++) {
                newItemsList.add(board[row][col]);
            }
        }

        return newItemsList;
    }

    boolean isCompleted(int[][] board) {
        int zeros = 0;
        for (int row = 0; row < dimensions; row++) {
            for (int col = 0; col < dimensions; col++) {
                if (board[row][col] == 0) {
                    zeros++;
                }
            }
        }
        return zeros == 0 && checkIfBoardMeetsRestrictions(board);
    }

    boolean checkIfBoardMeetsRestrictions(int[][] board) {
        return checkVisibilityRestrictions(board) && checkUniquenessRestrictions(board);
    }

    private boolean checkVisibilityRestrictions(int[][] board) {
        return checkVisibilityRestrictionsTop(board) &&
                checkVisibilityRestrictionsRight(board) &&
                checkVisibilityRestrictionsBottom(board) &&
                checkVisibilityRestrictionsLeft(board);
    }

    boolean checkVisibilityRestrictionsTop(int[][] board) {
        boolean meetRestrictions = true;

        for (int column = 0; column < dimensions && meetRestrictions; column++) {
            int highest = board[0][column];
            int visible = 1;
            for (int row = 1; row < dimensions; row++) {
                if (board[row][column] > highest) {
                    highest = board[row][column];
                    visible++;
                }
            }
            if (restrictionsTop[column] != 0 && visible != restrictionsTop[column]) {
                meetRestrictions = false;
            }
        }

        return meetRestrictions;
    }

    boolean checkVisibilityRestrictionsRight(int[][] board) {
        boolean meetRestrictions = true;

        for (int row = 0; row < dimensions && meetRestrictions; row++) {
            int highest = board[dimensions - 1][0];
            int visible = 1;
            for (int column = dimensions - 2; column >= 0; column--) {
                if (board[row][column] > highest) {
                    highest = board[row][column];
                    visible++;
                }
            }
            if (restrictionsRight[row] != 0 && visible != restrictionsRight[row]) {
                meetRestrictions = false;
            }
        }

        return meetRestrictions;
    }

    boolean checkVisibilityRestrictionsBottom(int[][] board) {
        boolean meetRestrictions = true;

        for (int column = 0; column < dimensions && meetRestrictions; column++) {
            int highest = board[dimensions - 1][column];
            int visible = 1;
            for (int row = dimensions - 2; row >= 0; row--) {
                if (board[row][column] > highest) {
                    highest = board[row][column];
                    visible++;
                }
            }
            if (restrictionsBottom[column] != 0 && visible != restrictionsBottom[column]) {
                meetRestrictions = false;
            }
        }

        return meetRestrictions;
    }

    boolean checkVisibilityRestrictionsLeft(int[][] board) {
        boolean meetRestrictions = true;

        for (int row = 0; row < dimensions && meetRestrictions; row++) {
            int highest = board[row][0];
            int visible = 1;
            for (int column = 1; column < dimensions; column++) {
                if (board[row][column] > highest) {
                    highest = board[row][column];
                    visible++;
                }
            }
            if (restrictionsLeft[row] != 0 && visible != restrictionsLeft[row]) {
                meetRestrictions = false;
            }
        }

        return meetRestrictions;
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

    String boardToString(int[][] board) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int row = 0; row < dimensions; row++) {
            for (int col = 0; col < dimensions; col++) {
                stringBuilder.append(board[row][col]).append(" ");
            }
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }
}
