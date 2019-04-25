import java.util.*;

public class Futoshiki {
    private int[][] board;
    private int dimensions;
    private ArrayList<FutoshikiRestriction> restrictions;
    private ArrayList<Integer> itemsList;

    /*
    Key - ID of the element which is smaller than its values
    Value - List of IDs of elements which are bigger than Key
     */
    private Map<Integer, ArrayList<Integer>> relationSmallerMap;

    /*
    Key - ID of the element which is bigger than its values
    Value - List of IDs of elements which are smaller than Key
    */
    private Map<Integer, ArrayList<Integer>> relationBiggerMap;

    private Map<Integer, Boolean> smallerMap;
    private Map<Integer, Boolean> biggerMap;

    int recursiveCounter = 0;

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
     * Get map of elements which are smaller than key element (relation rule)
     * <Integer, Boolean>
     * Integer - index of an element in board
     * Boolean: true if smaller than other, false if not
     */
    void generateRestrictionSmallerMap() {
        Map<Integer, Boolean> smallerMap = new HashMap<>();
        for (int i = 0; i < dimensions * dimensions; i++) {
            smallerMap.put(i, false);
        }

        for (FutoshikiRestriction restriction : restrictions) {
            smallerMap.put(restriction.getRowSmaller() * dimensions + restriction.getColumnSmaller(), true);
        }

        this.smallerMap = smallerMap;
    }

    /**
     * Get map of elements which are bigger than key element (relation rule)
     * <Integer, Boolean>
     * Integer - index of an element in board
     * Boolean: true if bigger than other, false if not
     */
    void generateRestrictionBiggerMap() {
        Map<Integer, Boolean> biggerMap = new HashMap<>();
        for (int i = 0; i < dimensions * dimensions; i++) {
            biggerMap.put(i, false);
        }

        for (FutoshikiRestriction restriction : restrictions) {
            biggerMap.put(restriction.getRowBigger() * dimensions + restriction.getColumnBigger(), true);
        }

        this.biggerMap = biggerMap;
    }

    /**
     * Calculate minimum value of an element, based on it's neighbours (smaller than it)
     *
     * @param board       - int[][]
     * @param elementsIDs - ArrayList of ID of elements which are smaller than current checking element
     * @return - minimum value in element's domain
     */
    private int getSmallestDomainValue(int[][] board, ArrayList<Integer> elementsIDs, int currentElementID) {
        int value = 1;

        int countZeros = 0;
        for (int id : elementsIDs) {
            if (board[id / dimensions][id % dimensions] == 0) {
                countZeros++;
            }
        }

        // If all neighbours are not set yet (value=0) and there are relations with them
        if (countZeros == elementsIDs.size() && biggerMap.get(currentElementID))
            return value + 1;
            // If all neighbours are not set yet (value=0) but there are no relations with them
        else if (countZeros == elementsIDs.size())
            return value;

        // If some of neighbours are set and there are relations with them
        for (int id : elementsIDs) {
            int elementValue = board[id / dimensions][id % dimensions];
            if (elementValue >= value && elementValue != 0) {
                value = elementValue + 1;
                // +1 because element have to be bigger, not equal
            }
        }
        return value;
    }

    /**
     * Calculate maximum value of an element, based on it's neighbours (smaller than it)
     *
     * @param board       - int[][]
     * @param elementsIDs - ArrayList of ID of elements which are bigger than current checking element
     * @return - maximum value in element's domain
     */
    private int getBiggestDomainValue(int[][] board, ArrayList<Integer> elementsIDs, int currentElementID) {
        int value = dimensions;

        int countZeros = 0;
        for (int id : elementsIDs) {
            if (board[id / dimensions][id % dimensions] == 0) {
                countZeros++;
            }
        }

        // If all neighbours are not set yet (value=0) and there are relations with them
        if (countZeros == elementsIDs.size() && smallerMap.get(currentElementID))
            return value - 1;
            // If all neighbours are not set yet (value=0) but there are no relations with them
        else if (countZeros == elementsIDs.size())
            return value;

        // If some of neighbours are set and there are relations with them
        for (int id : elementsIDs) {
            int elementValue = board[id / dimensions][id % dimensions];
            if (elementValue <= value && elementValue != 0) {
                value = elementValue - 1;
                // -1 because element have to be smaller, not equal
            }
        }
        return value;
    }

    boolean checkIfDomainMeetsRestrictions(ArrayList<ArrayList<Integer>> domainsList) {
        boolean meetRestrictions = true;

        for (int i = 0; i < domainsList.size() && meetRestrictions; i++) {
            if (domainsList.get(i).isEmpty()) {
                meetRestrictions = false;
            }
        }

        return meetRestrictions;
    }

    /**
     * Generate domains of all elements in the board.
     * Structure: ArrayList< #index of the element in the board
     * ArrayList<Integer> #list with domain of the element
     * >
     *
     * @return - ArrayList<ArrayList<Integer>> - list of domains
     */
    ArrayList<ArrayList<Integer>> generateDomains(int[][] board) {
        ArrayList<ArrayList<Integer>> domainsList = new ArrayList<>();

        ArrayList<Integer> boardList = boardToList(board);

        for (int i = 0; i < dimensions * dimensions; i++) {
            int localMin;
            int localMax;

            // if element is unset
            if (boardList.get(i) == 0) {
                localMin = getSmallestDomainValue(board, relationBiggerMap.get(i), i);
                localMax = getBiggestDomainValue(board, relationSmallerMap.get(i), i);

                //System.out.println("id: " + i + " min: " + localMin + " max: " + localMax);
            } else {
                localMin = boardList.get(i);
                localMax = boardList.get(i);
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
     *
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
     *
     * @param board - board of integers
     * @return - boolean
     */
    private boolean checkUniquenessRestrictions(int[][] board) {
        return checkUniquenessRows(board) && checkUniquenessColumns(board);
    }

    /**
     * Check if all rows contains only unique values (ignore zeros)
     *
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
     *
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
     *
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
     *
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

    /**
     * Convert arrayList to board (int[][] array)
     *
     * @param itemsList - array list with elements of board
     * @return - int[][] board
     */
    private int[][] boardListToBoard(ArrayList<Integer> itemsList) {
        int[][] newBoard = new int[dimensions][dimensions];

        for (int row = 0; row < dimensions; row++) {
            for (int col = 0; col < dimensions; col++) {
                newBoard[row][col] = itemsList.get(row * dimensions + col);
            }
        }

        return newBoard;
    }

    /**
     * Convert board (int[][] array) to arrayList
     *
     * @param board - int[][] board
     * @return - ArrayList
     */
    private ArrayList<Integer> boardToList(int[][] board) {
        ArrayList<Integer> newItemsList = new ArrayList<>();

        for (int row = 0; row < dimensions; row++) {
            for (int col = 0; col < dimensions; col++) {
                newItemsList.add(board[row][col]);
            }
        }

        return newItemsList;
    }

    int[][] calculateFutoshikiForwardChecking(int[][] board, int currentIndex) {
        ArrayList<Integer> boardList = boardToList(board);

        while (boardList.get(currentIndex) != 0) {
            currentIndex++;
        }

        for (int cell = currentIndex; cell < dimensions * dimensions && !isCompleted(board); cell++) {

            for (int value = 1; value <= dimensions && !isCompleted(board); value++) {
                boardList.remove(cell);
                boardList.add(cell, value);

                board = boardListToBoard(boardList);
                //System.out.println(boardToString(board));
                //ArrayList<ArrayList<Integer>> domainList = generateDomains(board);

                if (checkIfBoardMeetsRestrictions(board) && checkIfDomainMeetsRestrictions(generateDomains(board)) && !isCompleted(board)) {
                    //System.out.println(boardToString(board));
                    recursiveCounter++;
                    board = calculateFutoshikiForwardChecking(board, nextCell(cell));
                }
            }
        }

        return board;
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
                    recursiveCounter++;
                    board = calculateFutoshikiBacktracking(board, nextCell(cell));
                }
            }
        }

        return board;
    }

    /**
     * Calculate index of next cell (prevents out of bound exception when starting from other place than 0)
     *
     * @param cell - current cell index
     * @return - next cell index
     */
    private int nextCell(int cell) {
        return cell + 1 < dimensions * dimensions ? cell + 1 : 0;
    }

    private int nextCellMostRestricted(int[][] board, int current) {
        ArrayList<ArrayList<Integer>> domains = generateDomains(board);

        ArrayList<Integer> boardList = boardToList(board);

        int idSmallest = 0;

        for (int i = 0; i < domains.size(); i++) {
            if (domains.get(i).size() < domains.get(idSmallest).size() && boardList.get(i) == 0) {
                idSmallest = i;
            }
        }

        if (idSmallest == current) {
            idSmallest++;
        }

        return idSmallest;
    }

    /**
     * @return - index of most restricted element
     */
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

    /**
     * @return - index of less restricted element
     */
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

    String printRelationsSmallerMap() {
        StringBuilder stringBuilder = new StringBuilder();

        this.relationSmallerMap.forEach((k, v) ->
                stringBuilder.append(k.toString()).append(": ").append(v.toString()).append("\n"));

        return stringBuilder.toString();
    }

    String printRelationsBiggerMap() {
        StringBuilder stringBuilder = new StringBuilder();

        this.relationBiggerMap.forEach((k, v) ->
                stringBuilder.append(k.toString()).append(": ").append(v.toString()).append("\n"));

        return stringBuilder.toString();
    }

    int[][] getBoard() {
        return board;
    }
}
