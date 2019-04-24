import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

public class Futoshiki {
    private int[][] board;
    private int dimensions;
    private ArrayList<FutoshikiRestriction> restrictions;
    private ArrayList<Integer> itemsList;

    Futoshiki(int dimensions) {
        this.dimensions = dimensions;
        restrictions = new ArrayList<>();
    }

    void setBoard(int[][] board) {
        this.board = board;
    }

    int getDimensions() {
        return dimensions;
    }

    void addRestriction(FutoshikiRestriction restriction) {
        restrictions.add(restriction);
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
        return checkRelationsRestrictions(board) && checkUniquenessRestrictions(board);
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

    boolean checkRelationsRestrictions(int[][] board) {
        boolean meetsRestrictions = true;

        for (int i = 0; i < restrictions.size() && meetsRestrictions; i++) {
            meetsRestrictions = checkRestriction(restrictions.get(i), board);
        }

        return meetsRestrictions;
    }

    private boolean checkRestriction(FutoshikiRestriction restriction, int[][] board) {
        int smallerValue = board[restriction.getRowSmaller()][restriction.getColumnSmaller()];
        int biggerValue = board[restriction.getRowBigger()][restriction.getColumnBigger()];

        if (smallerValue != 0 && biggerValue != 0) {
            return smallerValue < biggerValue;
        }

        return true;
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

    int[][] calculateFutoshikiBacktracking(int[][] board, int currentIndex) {
        ArrayList<Integer> boardList = boardToList(board);

        while (boardList.get(currentIndex) != 0) {
            currentIndex++;
        }

        for (int cell = currentIndex; cell < dimensions * dimensions && !isCompleted(board); cell++) {

            for (int value = 1; value <= dimensions && !isCompleted(board); value++) {
                boardList.remove(cell);
                boardList.add(cell, value);

                board = boardListToBoard(boardList);

                if (checkIfBoardMeetsRestrictions(board) && !isCompleted(board)) {
                    board = calculateFutoshikiBacktracking(board, cell + 1);
                }
            }
        }

        return board;
    }

    String restrictionsToString() {
        StringBuilder stringBuilder = new StringBuilder();

        for (FutoshikiRestriction restriction : restrictions) {
            stringBuilder.append(restriction).append("\n");
        }

        return stringBuilder.toString();
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

    int[][] getBoard() {
        return board;
    }
}
