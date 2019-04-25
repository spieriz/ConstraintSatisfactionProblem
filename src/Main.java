import java.io.File;

public class Main {
    public static void main(String[] args) {

        //long startTime = System.currentTimeMillis();
        //calculateSkyscraperBacktracking("test_sky_4_0");
        //System.out.println(System.currentTimeMillis() - startTime);
        multiThreadingFutoshiki();
    }

    private static void multiThreadingFutoshiki() {
        for (int dimensions = 7; dimensions < 8; dimensions++) {
            for (int file = 0; file < 1; file++) {
                String filenameFutoshiki = "test_futo_" + dimensions + "_" + file;
                Runnable futoBack = () ->
                        calculateFutoshikiBacktracking(filenameFutoshiki);

                Runnable futoForward = () ->
                        calculateFutoshikiForwardChecking(filenameFutoshiki);

                Thread t1 = new Thread(futoBack);
                t1.start();

                Thread t2 = new Thread(futoForward);
                t2.start();
            }
        }
    }

    private static void calculateSkyscraperBacktracking(String filename) {
        FileParser fileParser = new FileParser(new File("files/" + filename + ".txt"), FileParser.CSP_TYPE.SKYSCRAPER);

        fileParser.parseHeader();
        fileParser.parseSkyscraperFile();

        Skyscraper skyscraper = fileParser.getSkyscraper();
        skyscraper.fillBoardWithZero();
        System.out.println(skyscraper.getRestrictionsString());

        skyscraper.setBoard(skyscraper.calculateSkyscraperBacktracking(skyscraper.getBoard(), 0));
        //System.out.println(skyscraper.printDomain(skyscraper.generateDomains()));
        System.out.println(skyscraper.boardToString(skyscraper.getBoard()));
    }

    private static void calculateFutoshikiBacktracking(String filename) {
        long startTime = System.currentTimeMillis();

        FileParser fileParser = new FileParser(new File("files/" + filename + ".txt"), FileParser.CSP_TYPE.FUTOSHIKI);

        fileParser.parseHeader();
        fileParser.parseFutoshikiFile();

        Futoshiki futoshiki = fileParser.getFutoshiki();
        //System.out.println(futoshiki.restrictionsToString());
        //System.out.println(futoshiki.checkIfBoardMeetsRestrictions(futoshiki.getBoard()));
        //System.out.println(futoshiki.printDomains(futoshiki.generateDomains(futoshiki.getBoard(), 1, 4)));

        int mostRestricted = futoshiki.getMostRestricted();
        int lessRestricted = futoshiki.getLessRestricted();
        //System.out.println(mostRestricted);
        //System.out.println(lessRestricted);

        futoshiki.setBoard(futoshiki.calculateFutoshikiBacktracking(futoshiki.getBoard(), lessRestricted));
        int[][] board = futoshiki.getBoard();
        //System.out.println("relations: " + futoshiki.checkRelationsRestrictions(board));

        System.out.println("\n\nBacktracking: " + filename + "\n" +
                futoshiki.boardToString(board) +
                "Completed: " + futoshiki.isCompleted(board) + "\n" +
                "Time: " + (System.currentTimeMillis() - startTime) + "\n" +
                "Function call: " + futoshiki.recursiveCounter
        );
    }

    private static void calculateFutoshikiForwardChecking(String filename) {
        long startTime = System.currentTimeMillis();

        FileParser fileParser = new FileParser(new File("files/" + filename + ".txt"), FileParser.CSP_TYPE.FUTOSHIKI);

        fileParser.parseHeader();
        fileParser.parseFutoshikiFile();

        Futoshiki futoshiki = fileParser.getFutoshiki();
        //System.out.println(futoshiki.restrictionsToString());
        //System.out.println(futoshiki.checkIfBoardMeetsRestrictions(futoshiki.getBoard()));
        futoshiki.generateRestrictionSmallerMap();
        futoshiki.generateRestrictionBiggerMap();
        futoshiki.createRelationsSmallerMap();
        futoshiki.createRelationsBiggerMap();
        //System.out.println(futoshiki.printDomains(futoshiki.generateDomains(futoshiki.getBoard())));

        int mostRestricted = futoshiki.getMostRestricted();
        int lessRestricted = futoshiki.getLessRestricted();

        futoshiki.setBoard(futoshiki.calculateFutoshikiForwardChecking(futoshiki.getBoard(), lessRestricted));
        int[][] board = futoshiki.getBoard();
        //System.out.println(futoshiki.printRelationsSmallerMap());
        //System.out.println(futoshiki.printRelationsBiggerMap());

        System.out.println("\n\nForward checking: " + filename + "\n" +
                futoshiki.boardToString(board) +
                "Completed: " + futoshiki.isCompleted(board) + "\n" +
                "Time: " + (System.currentTimeMillis() - startTime) + "\n" +
                "Function call: " + futoshiki.recursiveCounter
        );
    }
}
