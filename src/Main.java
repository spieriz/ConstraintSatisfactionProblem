import java.io.File;

public class Main {
    public static void main(String[] args){

       //calculateSkyscraperBacktracking();
       //calculateFutoshikiBacktracking();
        calculateFutoshikiForwardChecking();
    }

    private static void calculateSkyscraperBacktracking() {
        String filename = "files/test_sky_4_0.txt";

        FileParser fileParser = new FileParser(new File(filename), FileParser.CSP_TYPE.SKYSCRAPER);

        fileParser.parseHeader();
        fileParser.parseSkyscraperFile();

        Skyscraper skyscraper = fileParser.getSkyscraper();
        skyscraper.fillBoardWithZero();
        System.out.println(skyscraper.getRestrictionsString());

        skyscraper.setBoard(skyscraper.calculateSkyscraperBacktracking(skyscraper.getBoard(), 0));
        System.out.println(skyscraper.boardToString(skyscraper.getBoard()));
    }

    private static void calculateFutoshikiBacktracking() {
        long startTime = System.currentTimeMillis();

        String filename = "files/test_futo_4_0.txt";

        FileParser fileParser = new FileParser(new File(filename), FileParser.CSP_TYPE.FUTOSHIKI);

        fileParser.parseHeader();
        fileParser.parseFutoshikiFile();

        Futoshiki futoshiki = fileParser.getFutoshiki();
        System.out.println(futoshiki.restrictionsToString());
        System.out.println(futoshiki.checkIfBoardMeetsRestrictions(futoshiki.getBoard()));
        System.out.println(futoshiki.printDomains(futoshiki.generateDomains(1, 4)));

        int mostRestricted = futoshiki.getMostRestricted();
        int lessRestricted = futoshiki.getLessRestricted();
        System.out.println(mostRestricted);
        System.out.println(lessRestricted);

        futoshiki.setBoard(futoshiki.calculateFutoshikiBacktracking(futoshiki.getBoard(), lessRestricted));
        int[][] board = futoshiki.getBoard();
        System.out.println("\n\nlocal board:\n" + futoshiki.boardToString(board));
        System.out.println("relations: " + futoshiki.checkRelationsRestrictions(board));
        System.out.println(futoshiki.isCompleted(board));
        System.out.println(System.currentTimeMillis() - startTime);
    }

    private static void calculateFutoshikiForwardChecking() {
        long startTime = System.currentTimeMillis();

        String filename = "files/test_futo_4_0.txt";

        FileParser fileParser = new FileParser(new File(filename), FileParser.CSP_TYPE.FUTOSHIKI);

        fileParser.parseHeader();
        fileParser.parseFutoshikiFile();

        Futoshiki futoshiki = fileParser.getFutoshiki();
        System.out.println(futoshiki.restrictionsToString());
        System.out.println(futoshiki.checkIfBoardMeetsRestrictions(futoshiki.getBoard()));
        System.out.println(futoshiki.printDomains(futoshiki.generateDomains(1, 4)));
        futoshiki.createRelationsSmallerMap();
        futoshiki.createRelationsBiggerMap();
        System.out.println(futoshiki.printRelationsSmallerMap());
        System.out.println(futoshiki.printRelationsBiggerMap());
    }
}
