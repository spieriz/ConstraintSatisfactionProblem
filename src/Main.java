import java.io.File;

public class Main {
    public static void main(String[] args){

       calculateSkyscraperBacktracking();
       //calculateFutoshikiBacktracking();
    }

    private static void calculateSkyscraperBacktracking() {
        String filename = "files/test_sky_4_0.txt";

        FileParser fileParser = new FileParser(new File(filename), FileParser.CSP_TYPE.SKYSCRAPER);

        fileParser.parseHeader();
        fileParser.parseSkyscraperFile();

        Skyscraper skyscraper = fileParser.getSkyscraper();
        skyscraper.fillBoardWithZero();
        System.out.println(skyscraper.getRestrictionsString());

        skyscraper.setBoard(skyscraper.calculateSkyscraperBacktracking(skyscraper.getBoard(), 6));
        System.out.println(skyscraper.boardToString(skyscraper.getBoard()));
    }

    private static void calculateFutoshikiBacktracking() {
        String filename = "files/test_futo_4_0.txt";

        FileParser fileParser = new FileParser(new File(filename), FileParser.CSP_TYPE.FUTOSHIKI);

        fileParser.parseHeader();
        fileParser.parseFutoshikiFile();

        Futoshiki futoshiki = fileParser.getFutoshiki();
        System.out.println(futoshiki.restrictionsToString());
        System.out.println(futoshiki.checkIfBoardMeetsRestrictions(futoshiki.getBoard()));

        futoshiki.setBoard(futoshiki.calculateFutoshikiBacktracking(futoshiki.getBoard(), 6));
        int[][] board = futoshiki.getBoard();
        System.out.println("\n\nlocal board:\n" + futoshiki.boardToString(board));
        System.out.println("relations: " + futoshiki.checkRelationsRestrictions(board));
        System.out.println(futoshiki.isCompleted(board));
    }
}
