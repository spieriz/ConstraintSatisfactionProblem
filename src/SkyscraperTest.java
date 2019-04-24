import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class SkyscraperTest {

    @Test
    void checkIfBoardMeetsRestrictionsEmpty() {
        String filename = "files/test_sky_4_0.txt";

        FileParser fileParser = new FileParser(new File(filename), FileParser.CSP_TYPE.SKYSCRAPER);

        fileParser.parseHeader();
        fileParser.parseSkyscraperFile();

        Skyscraper skyscraper = fileParser.getSkyscraper();
        skyscraper.fillBoardWithZero();

        //assertFalse(skyscraper.checkIfBoardMeetsRestrictions(skyscraper.getBoard()));
    }

    @Test
    void checkVisibility() {
        String filename = "files/test_sky_4_0.txt";

        FileParser fileParser = new FileParser(new File(filename), FileParser.CSP_TYPE.SKYSCRAPER);

        fileParser.parseHeader();
        fileParser.parseSkyscraperFile();

        Skyscraper skyscraper = fileParser.getSkyscraper();

        int[][] board = {
                {1,3,2,4},
                {3,1,4,2},
                {2,4,3,3},
                {4,2,2,1}
        };

        assertTrue(skyscraper.checkVisibilityRestrictionsTop(board));
        assertTrue(skyscraper.checkVisibilityRestrictionsRight(board));
        assertTrue(skyscraper.checkVisibilityRestrictionsBottom(board));
        assertTrue(skyscraper.checkVisibilityRestrictionsLeft(board));
    }
}