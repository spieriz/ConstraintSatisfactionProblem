import java.io.File;

public class Main {
    public static void main(String[] args){
        calculateFutoshikiBacktracking();
    }

    private static void calculateFutoshikiBacktracking() {
        String filename = "files/test_futo_6_0.txt";

        FileParser fileParser = new FileParser(new File(filename), FileParser.CSP_TYPE.FUTOSHIKI);

        fileParser.parseHeader();
        fileParser.parseFutoshikiFile();

        Futoshiki futoshiki = fileParser.getFutoshiki();
        System.out.println(futoshiki.restrictionsToString());
        System.out.println(futoshiki.checkIfBoardMeetsRestrictions(futoshiki.getBoard()));

        futoshiki.setBoard(futoshiki.calculateFutoshikiBacktracking(futoshiki.getBoard(), 0));
        int[][] board = futoshiki.getBoard();
        System.out.println("\n\nlocal board:\n" + futoshiki.boardToString(board));
        System.out.println("relations: " + futoshiki.checkRelationsRestrictions(board));
        System.out.println(futoshiki.isCompleted(board));
    }
}
