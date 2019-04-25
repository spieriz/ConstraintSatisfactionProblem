import java.util.*;

public class Futoshiki {
    private int[][] board;
    private int dimensions;
    private ArrayList<FutoshikiRestriction> restrictions;
    private ArrayList<Integer> itemsList;

    private Map<Integer, ArrayList<Integer>> relationSmallerMap;
    private Map<Integer, ArrayList<Integer>> relationBiggerMap;

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

    /**
     * Create and assign map of relation (smaller -> bigger)
     */
    void createRelationsSmallerMap() {
        Map<Integer, ArrayList<Integer>> relationSmallerMap = new HashMap<>();

        for (int i = 0; i < dimensions * dimensions; i++) {
            relationSmallerMap.put(i, new ArrayList<>());
        }

        for (FutoshikiRestriction restriction : restrictions) {
            relationSmallerMap.get(restriction.getRowSmaller() * dimensions + restriction.getColumnSmaller())
                    .add(restriction.getRowBigger() * dimensions + restriction.getColumnBigger());
        }

        this.relationSmallerMap = relationSmallerMap;
    }

    /**
     * Create and assign map of relation (smaller -> bigger)
     */
    void createRelationsBiggerMap() {
        Map<Integer, ArrayList<Integer>> relationBiggerMap = new HashMap<>();

        for (int i = 0; i < dimensions * dimensions; i++) {
            relationBiggerMap.put(i, new ArrayList<>());
        }

        for (FutoshikiRestriction restriction : restrictions) {
            relationBiggerMap.get(restriction.getRowBigger() * dimensions + restriction.getColumnBigger())
                    .add(restriction.getRowSmaller() * dimensions + restriction.getColumnSmaller());
        }

        this.relationBiggerMap = relationBiggerMap;
    }

    String printRelationsSmallerMap() {
        StringBuilder stringBuilder = new StringBuilder();

        this.relationSmallerMap.forEach((k, v) -> {
            stringBuilder.append(k.toString()).append(": ").append(v.toString()).append("\n");
        });

        return stringBuilder.toString();
    }

    String printRelationsBiggerMap() {
        StringBuilder stringBuilder = new StringBuilder();

        this.relationBiggerMap.forEach((k, v) -> {
            stringBuilder.append(k.toString()).append(": ").append(v.toString()).append("\n");
        });

        return stringBuilder.toString();
    }

    /**
     * Generate domain for single element - array based on min and max
     *
     * @param min - minimum value in domain
     * @param max - maximum value in domain
     * @return - ArrayList<Integer> - domain of the element
     */
    private ArrayList<Integer> generateSingleDomain(int min, int max) {
        ArrayList<Integer> domain = new ArrayList<>();
        for (int i = min; i <= max; i++) {
            domain.add(i);
        }
        return domain;
    }

    /**
     * Get map of elements which are smaller than other (relation rule)
     * <Integer, Boolean>
     * Integer - index of an element in board
     * Boolean: true if smaller than other, false if not
     *
     * @return - Map<Integer, Boolean>
     */
    private Map<Integer, Boolean> getRestrictionSmallerMap() {
        Map<Integer, Boolean> smallerMap = new HashMap<Integer, Boolean>();
        for (int i = 0; i < dimensions * dimensions; i++) {
            smallerMap.put(i, false);
        }

        for (FutoshikiRestriction restriction : restrictions) {
            smallerMap.put(restriction.getRowSmaller() * dimensions + restriction.getColumnSmaller(), true);
        }

        return smallerMap;
    }

    /**
     * Get map of elements which are bigger than other (relation rule)
     * <Integer, Boolean>
     * Integer - index of an element in board
     * Boolean: true if bigger than other, false if not
     *
     * @return - Map<Integer, Boolean>
     */
    private Map<Integer, Boolean> getRestrictionBiggerMap() {
        Map<Integer, Boolean> biggerMap = new HashMap<Integer, Boolean>();
        for (int i = 0; i < dimensions * dimensions; i++) {
            biggerMap.put(i, false);
        }

        for (FutoshikiRestriction restriction : restrictions) {
            biggerMap.put(restriction.getRowBigger() * dimensions + restriction.getColumnBigger(), true);
        }

        return biggerMap;
    }

    /**
     * Generate domains of all elements in the board.
     * Structure: ArrayList< #index of the element in the board
     * ArrayList<Integer> #list with domain of the element
     * >
     *
     * @param globalMin - global minimum value in domain
     * @param globalMax - global maximum value in domain
     * @return - ArrayList<ArrayList<Integer>> - list of domains
     */
    ArrayList<ArrayList<Integer>> generateDomains(int globalMin, int globalMax) {
        ArrayList<ArrayList<Integer>> domainsList = new ArrayList<>();

        Map smallerMap = getRestrictionSmallerMap();
        Map biggerMap = getRestrictionBiggerMap();

        for (int i = 0; i < dimensions * dimensions; i++) {
            int localMin = globalMin;
            int localMax = globalMax;

            if ((boolean) smallerMap.get(i)) {
                localMax--;
            }

            if ((boolean) biggerMap.get(i)) {
                localMin++;
            }

            domainsList.add(generateSingleDomain(localMin, localMax));
        }

        return domainsList;
    }

    String printDomains(ArrayList<ArrayList<Integer>> domainsList) {
        StringBuilder stringBuilder = new StringBuilder();

        int iterator = 0;

        for (ArrayList<Integer> domain : domainsList) {
            if (domain.size() > 1) {
                stringBuilder.append(domain.get(0)).append("-").append(domain.get(domain.size() - 1));
            } else if (domain.size() == 1) {
                stringBuilder.append(" ").append(domain.get(0)).append(" ");
            } else {
                stringBuilder.append(" X ");
            }
            stringBuilder.append(" ");
            iterator++;
            if (iterator % dimensions == 0) {
                stringBuilder.append("\n");
            }
        }

        return stringBuilder.toString();
    }

    /**
     * Check if board is completed (all fields are set and all conditions has been met)
     * @param board - board of integers
     * @return - boolean
     */
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

    /**
     * Check if board match to uniqueness restrictions (unique values in rows an columns)
     * @param board - board of integers
     * @return - boolean
     */
    private boolean checkUniquenessRestrictions(int[][] board) {
        return checkUniquenessRows(board) && checkUniquenessColumns(board);
    }

    /**
     * Check if all rows contains only unique values (ignore zeros)
     * @param board - board of integers
     * @return - boolean
     */
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

    /**
     * Check if all columns contains only unique values (ignore zeros)
     * @param board - board of integers
     * @return - boolean
     */
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

    /**
     * Check if all relation conditions are met
     * @param board - board of integers
     * @return - boolean
     */
    boolean checkRelationsRestrictions(int[][] board) {
        boolean meetsRestrictions = true;

        for (int i = 0; i < restrictions.size() && meetsRestrictions; i++) {
            meetsRestrictions = checkRestriction(restrictions.get(i), board);
        }

        return meetsRestrictions;
    }

    /**
     * Check if single relation condition is met
     * @param restriction - restriction (relation condition)
     * @param board       - board of integers
     * @return - boolean
     */
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
                    board = calculateFutoshikiBacktracking(board, nextCell(cell));
                }
            }
        }

        return board;
    }

    private int nextCell(int cell) {
        return cell + 1 < dimensions * dimensions ? cell + 1 : 0;
    }

    int getMostRestricted() {
        int index = 0;

        int[] restrictionsArray = new int[dimensions * dimensions];

        for (FutoshikiRestriction restriction : restrictions) {
            int indexSmaller = restriction.getRowSmaller() * dimensions + restriction.getColumnSmaller();
            int indexBigger = restriction.getRowBigger() * dimensions + restriction.getColumnBigger();

            restrictionsArray[indexSmaller]++;
            restrictionsArray[indexBigger]++;
        }

        for (int i = 0; i < restrictionsArray.length; i++) {
            if (restrictionsArray[i] > restrictionsArray[index]) {
                index = i;
            }
        }

        return index;
    }

    int getLessRestricted() {
        int index = 0;

        int[] restrictionsArray = new int[dimensions * dimensions];

        for (FutoshikiRestriction restriction : restrictions) {
            int indexSmaller = restriction.getRowSmaller() * dimensions + restriction.getColumnSmaller();
            int indexBigger = restriction.getRowBigger() * dimensions + restriction.getColumnBigger();

            restrictionsArray[indexSmaller]++;
            restrictionsArray[indexBigger]++;
        }

        for (int i = 0; i < restrictionsArray.length; i++) {
            if (restrictionsArray[i] < restrictionsArray[index]) {
                index = i;
            }
        }

        return index;
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
