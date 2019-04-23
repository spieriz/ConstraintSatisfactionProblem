import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

public class Futoshiki {
    private int[][] board;
    private int dimensions;
    private ArrayList<Restriction> restrictions;

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

    void addRestriction(Restriction restriction) {
        restrictions.add(restriction);
    }

    boolean checkIfBoardMeetsRestrictions() {
        return checkRelationsRestrictions() && checkUniquenessRestrictions();
    }

    private boolean checkUniquenessRestrictions() {
        return checkUniquenessRows() && checkUniquenessColumns();
    }

    private boolean checkUniquenessRows() {
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

    private boolean checkUniquenessColumns() {
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

    private boolean checkRelationsRestrictions() {
        boolean meetsRestrictions = true;

        for (int i = 0; i < restrictions.size() && meetsRestrictions; i++) {
            meetsRestrictions = checkRestriction(restrictions.get(i));
        }

        return meetsRestrictions;
    }

    private boolean checkRestriction(Restriction restriction) {
        int smallerValue = board[restriction.getRowSmaller()][restriction.getColumnSmaller()];
        int biggerValue = board[restriction.getRowBigger()][restriction.getColumnBigger()];

        if (smallerValue != 0 && biggerValue != 0) {
            return smallerValue < biggerValue;
        }

        return true;
    }

    String restrictionsToString() {
        StringBuilder stringBuilder = new StringBuilder();

        for (Restriction restriction : restrictions) {
            stringBuilder.append(restriction).append("\n");
        }

        return stringBuilder.toString();
    }
}
