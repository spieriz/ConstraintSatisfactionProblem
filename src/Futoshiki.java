import java.util.ArrayList;

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
